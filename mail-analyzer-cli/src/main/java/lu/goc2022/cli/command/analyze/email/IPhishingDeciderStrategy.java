package lu.goc2022.cli.command.analyze.email;

import org.simplejavamail.api.email.Email;

public interface IPhishingDeciderStrategy {

	public PhishingDecision isPhishing(Email email);

}
