package lu.goc2022.services.dns;

import org.springframework.stereotype.Component;

import lu.goc2022.orm.PhishingEvent;
import lu.goc2022.services.IThreatResponseAction;
import lu.goc2022.services.ThreatResponseResult;

@Component
public class BlockCompanyDnsResponseAction implements IThreatResponseAction {

	@Override
	public ThreatResponseResult apply(PhishingEvent event) {
		/*
		 * TODO: out of the scope of the Hackathon: call an HTTP API of the company DNS to block access to website from internal network.
		 */
		return ThreatResponseResult.TREATED;
	}

}
