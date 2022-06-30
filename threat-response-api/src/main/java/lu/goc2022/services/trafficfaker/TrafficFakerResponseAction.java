package lu.goc2022.services.trafficfaker;

import org.springframework.stereotype.Component;

import lu.goc2022.orm.PhishingEvent;
import lu.goc2022.services.IThreatResponseAction;
import lu.goc2022.services.ThreatResponseResult;

@Component
public class TrafficFakerResponseAction implements IThreatResponseAction {

	@Override
	public ThreatResponseResult apply(PhishingEvent event) {
		/*
		 * TODO: out of the scope of the Hackathon: generate fake traffic on malicious website, using our "traffic-faker" application
		 */
		return ThreatResponseResult.TREATED;
	}

}
