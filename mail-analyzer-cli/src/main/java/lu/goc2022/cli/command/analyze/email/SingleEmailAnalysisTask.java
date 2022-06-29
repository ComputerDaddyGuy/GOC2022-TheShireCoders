package lu.goc2022.cli.command.analyze.email;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.converter.EmailConverter;
import org.simplejavamail.email.EmailBuilder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.goc2022.cli.exceptions.MailAnalyzerException;

@AllArgsConstructor
@Slf4j
public class SingleEmailAnalysisTask implements Callable<AnalysisResult> {

	private final File emlFile;
	private final File outDirectory;
	private final IPhishingDeciderStrategy phishingDecider;

	@Override
	public AnalysisResult call() throws Exception {

		Email emailToAnalyze = EmailConverter.emlToEmail(emlFile);

		PhishingDecision phishingDecision = phishingDecider.isPhishing(emailToAnalyze);
		log.info("Decision for {}: {}", emlFile.getName(), phishingDecision);

		if (phishingDecision == PhishingDecision.DELETE) {
			// If phishing, ignore the email
			log.info("  --> Email ignored as recognized as phishing: {}", emlFile);
		} else {
			// else, the email will be written in out folder...
			Email emailToRewrite = emailToAnalyze;
			if (phishingDecision == PhishingDecision.SANITIZE) {
				// ... but may require sanitization first
				emailToRewrite = sanitize(emailToRewrite);
			}
			writeEmlToOutDirectoty(outDirectory, emlFile.getName(), emailToRewrite);
		}

		return new AnalysisResult(emlFile, phishingDecision);
	}

	/**
	 * Sanitzation: clear attachments + remove href links.
	 * 
	 * @param dangerousEmail
	 * @return the same email content
	 */
	private Email sanitize(Email dangerousEmail) {
		return EmailBuilder.copying(dangerousEmail) //
				.clearAttachments() //
				.withHTMLText( //
						"<h1>WARNING - THIS EMAIL IS CONSIDERED AS PHISHING BY POSEIDON</h1><br/>" //
								+ cleanUrls(dangerousEmail.getHTMLText()) //
				) //
				.buildEmail();
	}

	private String cleanUrls(String unsafeHtmlText) {
		String safeHtmlText = Jsoup.clean(unsafeHtmlText, Safelist.basic());
		safeHtmlText = Jsoup.parse(safeHtmlText).select("a[href]").remove().html();
		return safeHtmlText;
	}

	private void writeEmlToOutDirectoty(File outDirectory, String emlFilename, Email emailToRewrite) {
		String emlFileContent = EmailConverter.emailToEML(emailToRewrite);
		Path emlOutFile = Paths.get(outDirectory.getAbsolutePath(), emlFilename);
		try {
			Files.writeString(emlOutFile, emlFileContent);
		} catch (IOException e) {
			throw new MailAnalyzerException("Exception while writing " + emlOutFile, e);
		}
	}

}
