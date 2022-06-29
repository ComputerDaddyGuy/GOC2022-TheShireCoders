package lu.goc2022.rules;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ScoringRulesCalculator {

	public ScoringRules calculateScoringRules(List<EmailPhishingData> allEmailData) {
		ScoringRules scoringRules = new ScoringRules();

		allEmailData.forEach(emailData -> {
			emailData.getClientIpAddress().ifPresent(ipAddress -> scoringRules.scoreIpAddress(ipAddress, emailData.isPhishing()));
			scoringRules.scoreFromEmail(emailData.getFrom(), emailData.isPhishing());
			emailData.getSubject().ifPresent(subject -> scoringRules.scoreSubject(subject, emailData.isPhishing()));
			emailData.getHosts().forEach(host -> scoringRules.scoreReceivedHost(host, emailData.isPhishing()));
		});

		return scoringRules;
	}

}
