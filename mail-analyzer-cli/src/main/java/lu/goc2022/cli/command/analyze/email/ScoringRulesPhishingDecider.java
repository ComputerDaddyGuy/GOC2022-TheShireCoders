package lu.goc2022.cli.command.analyze.email;

import java.io.File;
import java.util.List;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.converter.EmailConverter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.goc2022.cli.command.analyze.rules.ScoreDto;
import lu.goc2022.cli.command.analyze.rules.ScoringRules;

/**
 * A simple phishing decider, based on Scoring rules computed by rules-aggregator-api
 */
@AllArgsConstructor
@Slf4j
public class ScoringRulesPhishingDecider implements IPhishingDeciderStrategy {

	private static final double DELETE_THREASHOLD = 0.5;
	private static final double SANITIZE_THREASHOLD = 0.4;

	private final ScoringRules phishingRules;

	@Override
	public PhishingDecision isPhishing(File emlFile) {

		Email email = EmailConverter.emlToEmail(emlFile);

		double fromEmailScore = phishingRules.getFromEmail(email.getFromRecipient().getAddress()).map(ScoreDto::getScore).orElse(0d);
		double subjectScore = phishingRules.getSubject(email.getSubject()).map(ScoreDto::getScore).orElse(0d);
		double hostScore = phishingRules.getHost(email.getHeaders().getOrDefault("Received", List.of())).map(ScoreDto::getScore).orElse(0d);
		// TODO ip address score, not in scope of this hackathon
		// TODO we may add additional rules, and refine ponderation values

		double ponderatedScore = ponderateGlobalScore(fromEmailScore, subjectScore, hostScore);
		log.info(" --> {} - hostScore: {} /  global score: {}", emlFile.getName(), hostScore, ponderatedScore);

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
		(fromEmailScore * 1.3) //
				+ (subjectScore * 1.5) //
				+ (hostScore * 1.2) //
		) / 3;
	}

}
