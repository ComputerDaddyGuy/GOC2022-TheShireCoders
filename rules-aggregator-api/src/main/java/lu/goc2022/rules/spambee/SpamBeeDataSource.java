package lu.goc2022.rules.spambee;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lu.goc2022.rules.EmailPhishingData;
import lu.goc2022.rules.IEmailDataSource;
import lu.goc2022.rules.spambee.csv.SpamBeeAttributes;
import lu.goc2022.rules.spambee.csv.SpamBeeEvents;

@Component
@AllArgsConstructor
public class SpamBeeDataSource implements IEmailDataSource {

	private final ISpamBeeAttributesLoader spambeeAttributeLoader;
	private final ISpamBeeEventsLoader spambeeEventsLoader;

	@Override
	public List<EmailPhishingData> loadEmailData() {

		/*
		 * Real application will have to load SpamBee CSV regularly from remote location (API call, SFTP, etc.)
		 */

		System.out.println("1");
		List<SpamBeeAttributes> attr202204 = spambeeAttributeLoader.loadSpamBeeAttributes();
		System.out.println("2");
		List<SpamBeeEvents> events202204 = spambeeEventsLoader.loadSpamBeeEvents();

		List<EmailPhishingData> emailDataList = attr202204.stream() //
				.collect(Collectors.groupingBy(attr -> attr.getEventId())) // Map<String, List<SpamBeeAttributes>>
				.entrySet().stream().map((entry) -> emailAttributesToData(events202204, entry.getKey(), entry.getValue())) //
				.toList();

		return emailDataList;
	}

	private EmailPhishingData emailAttributesToData(List<SpamBeeEvents> events, String id, List<SpamBeeAttributes> attributes) {
		System.out.println("3: " + id);
		EmailPhishingData emailData = new EmailPhishingData();

		emailData.setPhishing(isPhishing(events, id));

		attributes.stream().filter(attr -> "from".equals(attr.getObjectRelation())).findFirst().ifPresent(fromAttr -> {
			emailData.setFrom(fromAttr.getValue());
		});
		attributes.stream().filter(attr -> "host".equals(attr.getObjectRelation())).toList().stream().forEach(hostAttr -> {
			emailData.addHost(hostAttr.getValue());
		});

		HeaderParser headerParser = HeaderParser.getInstance();
		attributes.stream().filter(attr -> "header".equals(attr.getObjectRelation())).findFirst().ifPresent(headerAttr -> {
			emailData.setHeaders(headerParser.parseHeaders(headerAttr.getValue()));
		});

		return emailData;
	}

	private boolean isPhishing(List<SpamBeeEvents> events202204, String id) {
		return events202204.stream() //
				.filter(event -> id.equals(event.getId())) //
				.anyMatch(event -> event.isPhishing());
	}

}
