package lu.goc2022.rules;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

/**
 * Global scoring rules.
 */
@ToString
public class ScoringRules {

	@Getter
	private final Map<String, Score> ipAddressMap = new HashMap<>();
	@Getter
	private final Map<String, Score> fromEmailMap = new HashMap<>();
	@Getter
	private final Map<String, Score> receivedHostMap = new HashMap<>();
	@Getter
	private final Map<String, Score> subjectMap = new HashMap<>();

	/**
	 * Scores an IP address.
	 * 
	 * @param subjectMap
	 * @param score
	 */
	public void scoreIpAddress(String ipAddress, boolean isPhishing) {
		if (ipAddress == null) {
			return;
		}
		this.ipAddressMap.put(ipAddress, ipAddressMap.getOrDefault(ipAddress, new Score()).count(isPhishing));
	}

	/**
	 * Scores a From Email.
	 * 
	 * @param subjectMap
	 * @param score
	 */
	public void scoreFromEmail(String fromEmail, boolean isPhishing) {
		if (fromEmail == null) {
			return;
		}
		this.fromEmailMap.put(fromEmail, fromEmailMap.getOrDefault(fromEmail, new Score()).count(isPhishing));
	}

	/**
	 * Scores a host that received/transfer the email.
	 * 
	 * @param subjectMap
	 * @param score
	 */
	public void scoreReceivedHost(String receivedHost, boolean isPhishing) {
		if (receivedHost == null) {
			return;
		}
		this.receivedHostMap.put(receivedHost, receivedHostMap.getOrDefault(receivedHost, new Score()).count(isPhishing));
	}

	/**
	 * Scores a subject.
	 * 
	 * @param subject
	 * @param score
	 */
	public void scoreSubject(String subject, boolean isPhishing) {
		if (subject == null) {
			return;
		}
		this.subjectMap.put(subject, subjectMap.getOrDefault(subject, new Score()).count(isPhishing));
	}

}
