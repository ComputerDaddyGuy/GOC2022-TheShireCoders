package lu.goc2022.cli.command.analyze.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.Data;

@Data
public class ScoringRules {

	private List<ScoringRuleDto> ipAddresses = new ArrayList<>();
	private List<ScoringRuleDto> fromEmails = new ArrayList<>();
	private List<ScoringRuleDto> hosts = new ArrayList<>();
	private List<ScoringRuleDto> subjects = new ArrayList<>();

	public Optional<ScoreDto> getIpAddress(String ipAddress) {
		return ipAddresses.stream().filter(rule -> rule.getValue() != null && rule.getValue().equals(ipAddress)).findFirst().map(rule -> rule.getScore());
	}

	public Optional<ScoreDto> getFromEmail(String from) {
		return fromEmails.stream().filter(rule -> rule.getValue() != null && rule.getValue().equals(from)).findFirst().map(rule -> rule.getScore());
	}

	public Optional<ScoreDto> getHost(Collection<String> recipientHeaders) {
		return hosts.stream().filter(rule -> rule.getValue() != null && recipientHeaders.stream().anyMatch(recipient -> recipient.contains(rule.getValue()))).findFirst().map(rule -> rule.getScore());
	}

	public Optional<ScoreDto> getSubject(String subject) {
		return subjects.stream().filter(rule -> rule.getValue() != null && rule.getValue().equals(subject)).findFirst().map(rule -> rule.getScore());
	}

}
