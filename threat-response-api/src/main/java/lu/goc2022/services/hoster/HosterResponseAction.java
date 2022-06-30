package lu.goc2022.services.hoster;

import org.springframework.stereotype.Component;

import lu.goc2022.orm.PhishingEvent;
import lu.goc2022.services.IThreatResponseAction;
import lu.goc2022.services.ThreatResponseResult;

@Component
public class HosterResponseAction implements IThreatResponseAction {

	@Override
	public ThreatResponseResult apply(PhishingEvent event) {
		/*
		 * TODO: out of the scope of the Hackathon: contact hoster of malicious website to ask to take it down
		 */
		return ThreatResponseResult.TREATED;
	}

}
