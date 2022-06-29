package lu.goc2022.rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data related to phishing criterias.
 *
 */
@NoArgsConstructor
public class EmailPhishingData {

	@Getter
	@Setter
	private String from;

	@Getter
	@Setter
	private Map<String, List<String>> headers = new HashMap<>();

	@Getter
	private Set<String> hosts = new HashSet<>(); // Set ensures unicity

	/*
	 * TODO add more data which could act as threat criteria
	 */

	@Getter
	@Setter
	private boolean isPhishing;

	public void addHost(String host) {
		this.hosts.add(host);
	}

	public Optional<String> getSubject() {
		return headers.getOrDefault("Subject", List.of()).stream().findFirst();
	}

	/**
	 * Loops on several known headers to extract Client IP address.
	 * 
	 * @return
	 */
	public Optional<String> getClientIpAddress() {
		return getFirstHeaderPresent( //
				"x-ms-exchange-organization-originalclientipaddress", //
				"X-XClient-IP-Addr", //
				"X-Sender-Host-Address" //
		);
	}

	private Optional<String> getFirstHeaderPresent(String... wantedHeaders) {
		for (String header : wantedHeaders) {
			if (headers.containsKey(header)) {
				return headers.get(header).stream().findFirst();
			}
		}
		return Optional.empty();
	}

}
