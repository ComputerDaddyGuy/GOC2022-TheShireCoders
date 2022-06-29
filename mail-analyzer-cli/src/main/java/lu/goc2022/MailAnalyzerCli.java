package lu.goc2022;

import lu.goc2022.cli.Analyze;
import picocli.CommandLine;

public class MailAnalyzerCli {

	public static void main(String[] args) {

		args = new String[] { "src/main/resources/in/8.eml" };

		Analyze analyzeCommand = new Analyze();
		new CommandLine(analyzeCommand).execute(args);
	}

}
