package lu.goc2022;

import lu.goc2022.cli.command.analyze.AnalyzeCommand;
import picocli.CommandLine;

public class MailAnalyzerCli {

	public static void main(String[] args) {

		args = new String[] { //
				"--in", "src/main/resources/in", //
				"--out", "src/main/resources/out", //
				"--thread", "10" //
		};

		AnalyzeCommand analyzeCommand = new AnalyzeCommand();
		new CommandLine(analyzeCommand).execute(args);
	}

}
