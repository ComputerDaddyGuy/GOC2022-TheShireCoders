package lu.goc2022.services.sms;

import org.springframework.stereotype.Component;

import lu.goc2022.orm.PhishingEvent;
import lu.goc2022.services.IThreatResponseAction;
import lu.goc2022.services.ThreatResponseResult;

@Component
public class SmsResponseAction implements IThreatResponseAction {

	@Override
	public ThreatResponseResult apply(PhishingEvent event) {
		/*
		 * TODO: out of the scope of the Hackathon: send a SMS to a CyberSecurity team, IT Admins, etc.
		 */
		return ThreatResponseResult.TREATED;
	}

}
