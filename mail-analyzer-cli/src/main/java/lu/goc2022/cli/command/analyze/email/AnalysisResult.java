package lu.goc2022.cli.command.analyze.email;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class AnalysisResult {

	private final File emlFile;
	private final PhishingDecision phishingDecision;

}
