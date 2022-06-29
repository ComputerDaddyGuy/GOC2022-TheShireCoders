package lu.goc2022.controllers.dto;

import java.util.List;

import lombok.Data;

@Data
public class ScoringRulesDto {

	private List<ScoringRuleDto> ipAddresses;
	private List<ScoringRuleDto> fromEmails;
	private List<ScoringRuleDto> hosts;
	private List<ScoringRuleDto> subjects;

}
