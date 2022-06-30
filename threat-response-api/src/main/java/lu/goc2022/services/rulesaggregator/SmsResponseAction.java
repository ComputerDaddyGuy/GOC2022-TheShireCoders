package lu.goc2022.services.rulesaggregator;

import org.springframework.stereotype.Component;

import lu.goc2022.orm.PhishingEvent;
import lu.goc2022.services.IThreatResponseAction;
import lu.goc2022.services.ThreatResponseResult;

@Component
public class SmsResponseAction implements IThreatResponseAction {

	@Override
	public ThreatResponseResult apply(PhishingEvent event) {
		/*
		 * TODO: out of the scope of the Hackathon: potentially add a new custom rule in rules-aggregator-api, to share information with others.
		 */
		return ThreatResponseResult.TREATED;
	}

}
