package lu.goc2022.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lu.goc2022.rules.ScoringRules;
import lu.goc2022.rules.ibm.IbmTrusteerDataSource;
import lu.goc2022.rules.spambee.SpamBeeDataSource;

@RestController
@AllArgsConstructor
public class AggregateController {

	/** SpamBee */
	private final SpamBeeDataSource spambeeDataSource;

	/** Other potential data sources: IBM Security Trusteer Rapport, etc. */
	private final IbmTrusteerDataSource ibmTrusteerDataSource;

	@GetMapping("/scoring-rules/aggregate")
	public void aggregateScoringRules() {

		/*
		 * Load scoring rules from each sources
		 */
		ScoringRules spambeeScoringRules = spambeeDataSource.computeScoringRules();
		ScoringRules ibmScoringRules = ibmTrusteerDataSource.computeScoringRules();

		/*
		 * Merge scoring rules together, to keep maximum score for each value
		 */
		ScoringRules mergeScoringRules = spambeeScoringRules.mergeWith(ibmScoringRules);

		/*
		 * Save scoring rules in database / json file
		 */

	}

}
