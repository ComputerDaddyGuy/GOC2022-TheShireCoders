package lu.goc2022.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lu.goc2022.orm.PhishingEvent;
import lu.goc2022.orm.PhishingEventRepository;

@Component
@AllArgsConstructor
@Slf4j
public class ThreatResponseScheduler {

	private final PhishingEventRepository phishingEventRepository;

	/*
	 * Implementations of response actions
	 */
	@Qualifier("smsResponseAction")
	private final IThreatResponseAction smsResponseAction;
	@Qualifier("tweetResponseAction")
	private final IThreatResponseAction tweetResponseAction;
	@Qualifier("blockCompanyDnsResponseAction")
	private final IThreatResponseAction blockCompanyDnsResponseAction;

	/**
	 * Runs every minute to react against latest detected threats.
	 */
	@Scheduled(fixedDelay = 60_000, initialDelay = 0L)
	public void reactToLatestThreats() {
		Timestamp oneMinAgo = Timestamp.from(Instant.now().minus(1, ChronoUnit.MINUTES));
		List<PhishingEvent> lastEvents = phishingEventRepository.findAllByCreationDateGreaterThan(oneMinAgo);

		log.info("{} threat event(s) occurred since {}", lastEvents.size(), oneMinAgo);

		if (!lastEvents.isEmpty()) {
			lastEvents.forEach(event -> reactToEvent(event));
		}

	}

	private void reactToEvent(PhishingEvent event) {

		/*
		 * TODO: out of scope of this Hackathon: the application need to choose which system(s) to use to react to the threat.
		 * 
		 * For examples: 
		 *  - Send a SMS to the CyberSecurity team of the company
		 *  - Call the API of the internal router/DNS of the company, to block access to the malicious website from inside the company network
		 *  - Post a Tweet so customers can be informed in real time about the malicious website
		 *  - etc.
		 * 
		 */

	}

}
