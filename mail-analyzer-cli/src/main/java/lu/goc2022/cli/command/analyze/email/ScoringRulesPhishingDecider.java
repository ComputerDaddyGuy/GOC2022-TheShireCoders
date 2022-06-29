package lu.goc2022.cli.command.analyze.email;

import java.util.List;

import org.simplejavamail.api.email.Email;

import lombok.AllArgsConstructor;
import lu.goc2022.cli.command.analyze.rules.ScoreDto;
import lu.goc2022.cli.command.analyze.rules.ScoringRules;

/**
 * A simple phishing decider, based on Scoring rules computed by rules-aggregator-api
 */
@AllArgsConstructor
public class ScoringRulesPhishingDecider implements IPhishingDeciderStrategy {

	private static final double DELETE_THREASHOLD = 0.8;
	private static final double SANITIZE_THREASHOLD = 0.4;

	private final ScoringRules phishingRules;

	@Override
	public PhishingDecision isPhishing(Email email) {

		double fromEmailScore = phishingRules.getFromEmail(email.getFromRecipient().getAddress()).map(ScoreDto::getScore).orElse(0d);
		double subjectScore = phishingRules.getSubject(email.getSubject()).map(ScoreDto::getScore).orElse(0d);
		double hostScore = phishingRules.getHost(email.getHeaders().getOrDefault("Received", List.of())).map(ScoreDto::getScore).orElse(0d);
		// TODO ip address score, not in scope of this hackathon

		double ponderatedScore = ponderateGlobalScore(fromEmailScore, subjectScore, hostScore);

		if (ponderatedScore >= DELETE_THREASHOLD) {
			return PhishingDecision.DELETE;
		}
		if (ponderatedScore >= SANITIZE_THREASHOLD) {
			return PhishingDecision.SANITIZE;
		}
		return PhishingDecision.OK;
	}

	private double ponderateGlobalScore(double fromEmailScore, double subjectScore, double hostScore) {
		return ( //
		(fromEmailScore * 0.8) //
				+ (subjectScore * 0.4) //
				+ (hostScore * 0.3) //
		) / 3;
	}

}
