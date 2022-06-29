package lu.goc2022.cli.command.analyze;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.extern.slf4j.Slf4j;
import lu.goc2022.cli.command.analyze.email.AnalysisResult;
import lu.goc2022.cli.command.analyze.email.IPhishingDeciderStrategy;
import lu.goc2022.cli.command.analyze.email.ScoringRulesPhishingDecider;
import lu.goc2022.cli.command.analyze.email.SingleEmailAnalysisTask;
import lu.goc2022.cli.command.analyze.rules.ScoreDto;
import lu.goc2022.cli.command.analyze.rules.ScoringRuleDto;
import lu.goc2022.cli.command.analyze.rules.ScoringRules;
import lu.goc2022.cli.exceptions.MailAnalyzerException;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "analyze")
@Slf4j
public class AnalyzeCommand implements Callable<Integer> {

	private static final int MAX_THREAD_COUNT = 10;

	@Option(names = { "-i", "--in" }, required = true, description = "Folder containing input EML files")
	private File inDirectory;

	@Option(names = { "-o", "--out" }, required = true, description = "Folder that will contain output EML files")
	private File outDirectory;

	@Option(names = { "-t", "--thread" }, defaultValue = "1", description = "Number of parallel threads to start (must be >= 1 and <= " + MAX_THREAD_COUNT + ")")
	private int threadCount;

	@Override
	public Integer call() throws Exception {

		validateInputFolder();
		log.info("Input directory: {}", inDirectory);

		validateOutputFolder();
		log.info("Output directory: {}", outDirectory);

		validateThreadNumber();
		log.info("Number of thread(s): {}", threadCount);

		log.info("Getting last version of phishing rules...");
		ScoringRules phishingRules = loadingPhishingRules();

		log.info("Preparing all analysis tasks...");
		IPhishingDeciderStrategy phishingDecider = new ScoringRulesPhishingDecider(phishingRules); // This could be dynamic in real application
		List<SingleEmailAnalysisTask> allAnalysisTasks = prepareAllAnalysisTasks(phishingDecider);
		log.info("--> {} analysis to perform", allAnalysisTasks.size());

		log.info("Executing all {} analysis tasks on {} thread(s)...", allAnalysisTasks.size(), threadCount);
		List<Future<AnalysisResult>> allResultFuture = executeAllAnalysisTasks(allAnalysisTasks);

		// TODO save results in database

		return 0;
	}

	private void validateInputFolder() {
		if (!inDirectory.exists()) {
			throw new MailAnalyzerException("Input directory does not exist");
		} else if (!inDirectory.isDirectory()) {
			throw new MailAnalyzerException("Input is not a directory");
		} else if (!inDirectory.canRead()) {
			throw new MailAnalyzerException("Input is not readable");
		}
	}

	private void validateOutputFolder() {
		if (!outDirectory.exists()) {
			boolean dirCreated = outDirectory.mkdirs();
			if (!dirCreated) {
				throw new MailAnalyzerException("Could not create output directory");
			}
		} else if (!outDirectory.isDirectory()) {
			throw new MailAnalyzerException("Output is not a directory");
		} else if (!outDirectory.canWrite()) {
			throw new MailAnalyzerException("Output is not writable");
		}
	}

	private void validateThreadNumber() {
		if (threadCount < 1 || threadCount > MAX_THREAD_COUNT) {
			throw new MailAnalyzerException("Number of parallel threads must be >= 1 and <= " + MAX_THREAD_COUNT);
		}
	}

	private ScoringRules loadingPhishingRules() {
		// DISCLAIMER: This could be a direct HTTP call, but not in the scope of this hackathon

		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("src/main/resources/phishing-rules.json")) {
			Object obj = jsonParser.parse(reader); // Read JSON file
			return parsePhishingRules((JSONObject) obj); // Iterate over employee array
		} catch (IOException | ParseException e) {
			throw new MailAnalyzerException(e);
		}
	}

	private ScoringRules parsePhishingRules(JSONObject root) {
		ScoringRules phishingRules = new ScoringRules();

		JSONArray ipAddressesArray = (JSONArray) root.get("ipAddresses");
		ipAddressesArray.forEach(obj -> {
			phishingRules.getIpAddresses().add(parseRuleDto((JSONObject) obj));
		});

		JSONArray fromEmailsArray = (JSONArray) root.get("fromEmails");
		fromEmailsArray.forEach(obj -> {
			phishingRules.getFromEmails().add(parseRuleDto((JSONObject) obj));
		});

		JSONArray hostsArray = (JSONArray) root.get("hosts");
		hostsArray.forEach(obj -> {
			phishingRules.getHosts().add(parseRuleDto((JSONObject) obj));
		});

		JSONArray subjectsArray = (JSONArray) root.get("subjects");
		subjectsArray.forEach(obj -> {
			phishingRules.getSubjects().add(parseRuleDto((JSONObject) obj));
		});

		return phishingRules;
	}

	private ScoringRuleDto parseRuleDto(JSONObject ruleDto) {
		ScoringRuleDto rule = new ScoringRuleDto();
		rule.setValue((String) ruleDto.get("value"));
		rule.setScore(parseScoreDto((JSONObject) ruleDto.get("score")));
		return rule;
	}

	private ScoreDto parseScoreDto(JSONObject scoreDto) {
		ScoreDto score = new ScoreDto();
		score.setPhishingCount((Long) scoreDto.get("phishingCount"));
		score.setTotalCount((Long) scoreDto.get("totalCount"));
		score.setScore((Double) scoreDto.get("score"));

		return score;
	}

	/**
	 * Prepares the list of analysis tasks to execute (1 task per .eml file)
	 * 
	 * @param phishingRules
	 * @return
	 */
	private List<SingleEmailAnalysisTask> prepareAllAnalysisTasks(IPhishingDeciderStrategy phishingDecider) {
		List<SingleEmailAnalysisTask> allAnalysis = new ArrayList<>();

		File[] allEmlFiles = inDirectory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				boolean taken = pathname.exists() //
						&& pathname.isFile() //
						&& pathname.canRead() //
						&& pathname.getName().endsWith(".eml");

				if (log.isDebugEnabled()) {
					if (taken) {
						log.debug("File taken: {}", pathname);
					} else {
						log.debug("File ignored: {}", pathname);
					}
				}

				return taken;
			}
		});

		for (File emlFile : allEmlFiles) {
			allAnalysis.add(new SingleEmailAnalysisTask(emlFile, outDirectory, phishingDecider));
		}

		return allAnalysis;
	}

	/**
	 * Executes all analysis tasks in separate threads (=parallel). The method returns when all provided tasks are completed.
	 * 
	 * @param allAnalysisTasks
	 * @return
	 */
	private List<Future<AnalysisResult>> executeAllAnalysisTasks(List<SingleEmailAnalysisTask> allAnalysisTasks) {
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		List<Future<AnalysisResult>> allResultFuture;

		try {
			allResultFuture = executor.invokeAll(allAnalysisTasks);
		} catch (InterruptedException e) {
			throw new MailAnalyzerException(e);
		} finally {
			executor.shutdown();
		}

		return allResultFuture;
	}
}
