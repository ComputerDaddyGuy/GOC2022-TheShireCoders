package lu.goc2022;

import lu.goc2022.cli.command.analyze.AnalyzeCommand;
import picocli.CommandLine;

/**
 * The main class of the CLI. This application is based on Picocli.<br>
 * Once you've done <code>mvn package</code>, you can launch the CLI with the following: <code>java -cp target\mail-analyzer-cli-0.0.1-SNAPSHOT-jar-with-dependencies.jar lu.goc2022.MailAnalyzerCli</code>
 * 
 * 
 */
public class MailAnalyzerCli {

	public static void main(String[] args) {

		/*
		 * Uncomment this if you want to run directly from your IDE
		 */
//		args = new String[] { //
//				"--in", "src/main/resources/in", //
//				"--out", "src/main/resources/out", //
//				"--thread", "1" //
//		};

		AnalyzeCommand analyzeCommand = new AnalyzeCommand();
		new CommandLine(analyzeCommand).execute(args);
	}

}
