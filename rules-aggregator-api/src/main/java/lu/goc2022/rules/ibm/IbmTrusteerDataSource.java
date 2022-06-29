package lu.goc2022.rules.ibm;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lu.goc2022.controllers.IPhishingDataSource;
import lu.goc2022.rules.ScoringRules;

@Component
@AllArgsConstructor
public class IbmTrusteerDataSource implements IPhishingDataSource {

	@Override
	public ScoringRules computeScoringRules() {

		/*
		 * Real application will have to load data from Ibm Trusteer (out of scope of this hackathon)
		 */

		return new ScoringRules();
	}

}
