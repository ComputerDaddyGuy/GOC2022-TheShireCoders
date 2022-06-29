package lu.goc2022.rules;

import java.util.HashMap;
import java.util.Map;

/**
 * Global scoring rules.
 */
public class ScoringRules {

	private final Map<String, Score> ipAddress = new HashMap<>();
	private final Map<String, Score> fromEmail = new HashMap<>();
	private final Map<String, Score> receivedHost = new HashMap<>();
	private final Map<String, Score> subject = new HashMap<>();

	/**
	 * Scores an IP address.
	 * 
	 * @param subject
	 * @param score
	 */
	public void scoreIpAddress(String ipAddress, Score score) {
		this.ipAddress.put(ipAddress, score);
	}

	/**
	 * Scores a From Email.
	 * 
	 * @param subject
	 * @param score
	 */
	public void scoreFromEmail(String fromEmail, Score score) {
		this.fromEmail.put(fromEmail, score);
	}

	/**
	 * Scores a host that received/transfer the email.
	 * 
	 * @param subject
	 * @param score
	 */
	public void scoreReceivedHost(String receivedHost, Score score) {
		this.receivedHost.put(receivedHost, score);
	}

	/**
	 * Scores a subject.
	 * 
	 * @param subject
	 * @param score
	 */
	public void scoreSubject(String subject, Score score) {
		this.subject.put(subject, score);
	}

	/**
	 * Merges this scoring rules with the given ones, into a new scoring rules object.
	 * 
	 * @param other
	 * @return
	 */
	public ScoringRules mergeWith(ScoringRules other) {

		ScoringRules merged = new ScoringRules();

		merged.ipAddress.putAll(this.ipAddress);
		merged.fromEmail.putAll(this.fromEmail);
		merged.receivedHost.putAll(this.receivedHost);
		merged.subject.putAll(this.subject);

		if (other != null) {
			mergeMaps(merged.ipAddress, other.ipAddress);
			mergeMaps(merged.fromEmail, other.fromEmail);
			mergeMaps(merged.receivedHost, other.receivedHost);
			mergeMaps(merged.subject, other.subject);
		}

		return merged;
	}

	private void mergeMaps(Map<String, Score> mergedMap, Map<String, Score> otherMap) {
		otherMap.entrySet().stream().forEach(scoreEntry -> {
			if (mergedMap.containsKey(scoreEntry.getKey())) {
				mergedMap.put(scoreEntry.getKey(), Score.getMax(scoreEntry.getValue(), mergedMap.get(scoreEntry.getKey())));
			} else {
				mergedMap.put(scoreEntry.getKey(), scoreEntry.getValue());
			}
		});
	}

}
