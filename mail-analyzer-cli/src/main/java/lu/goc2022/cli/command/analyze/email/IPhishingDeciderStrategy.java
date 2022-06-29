package lu.goc2022.cli.command.analyze.email;

import java.io.File;

public interface IPhishingDeciderStrategy {

	public PhishingDecision isPhishing(File emlFile);

}
