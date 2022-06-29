package lu.goc2022.rules.spambee;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lu.goc2022.controllers.IPhishingDataSource;
import lu.goc2022.rules.ScoringRules;

@Component
@AllArgsConstructor
public class SpamBeeDataSource implements IPhishingDataSource {

	private final ISpamBeeAttributesLoader spambeeAttributeLoader;
	private final ISpamBeeEventsLoader spambeeEventsLoader;

	@Override
	public ScoringRules computeScoringRules() {

		/*
		 * Real application will have to load SpamBee CSV regularly from remote location (API call, SFTP, etc.)
		 */

		List<SpamBeeAttributes> attr202204 = spambeeAttributeLoader.loadSpamBeeAttributes();
		List<SpamBeeEvents> events202204 = spambeeEventsLoader.loadSpamBeeEvents();

		ScoringRules scoringRules = new ScoringRules();

		return scoringRules;
	}

	private boolean isPhishing(List<SpamBeeEvents> events202204, String id) {
		return events202204.stream() //
				.filter(event -> id.equals(event.getId())) //
				.anyMatch(event -> event.isPhishing());
	}

}
