package lu.goc2022.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lu.goc2022.controllers.dto.ScoreDto;
import lu.goc2022.controllers.dto.ScoringRuleDto;
import lu.goc2022.controllers.dto.ScoringRulesDto;
import lu.goc2022.rules.EmailPhishingData;
import lu.goc2022.rules.IEmailDataSource;
import lu.goc2022.rules.Score;
import lu.goc2022.rules.ScoringRules;
import lu.goc2022.rules.ScoringRulesCalculator;
import lu.goc2022.rules.ibm.IbmTrusteerDataSource;
import lu.goc2022.rules.spambee.SpamBeeDataSource;

@RestController
@AllArgsConstructor
public class AggregateController {

	/** SpamBee */
	private final SpamBeeDataSource spambeeDataSource;

	/** Other potential data sources: IBM Security Trusteer Rapport, etc. */
	private final IbmTrusteerDataSource ibmTrusteerDataSource;

	private final ScoringRulesCalculator scoringRulesCalculator;

	@GetMapping("/scoring-rules/aggregate")
	public ScoringRulesDto aggregateScoringRules() {

		/*
		 * Parallel load email data from each sources, and merge them once they're all completed
		 */
		CompletableFuture<List<EmailPhishingData>> spambeeEmailData = loadEmailDataParallel(spambeeDataSource);
		CompletableFuture<List<EmailPhishingData>> ibmEmailData = loadEmailDataParallel(ibmTrusteerDataSource);

		List<EmailPhishingData> allEmailData = new ArrayList<>();
		CompletableFuture.allOf(spambeeEmailData, ibmEmailData) // Wait for all async loadings to be completed...
				.thenAccept((nothing) -> {
					// ... then add them all in global list
					allEmailData.addAll(spambeeEmailData.join());
					allEmailData.addAll(ibmEmailData.join());
				}).join();

		/*
		 * Calculate scoring rules
		 */
		ScoringRules mergeScoringRules = scoringRulesCalculator.calculateScoringRules(allEmailData);

		/*
		 * Save scoring rules in database / json file
		 */
		return toDto(mergeScoringRules);
	}

	/**
	 * Loads email data from source, in separate thread.
	 * 
	 * @param source
	 * @return
	 */
	private CompletableFuture<List<EmailPhishingData>> loadEmailDataParallel(IEmailDataSource source) {
		return CompletableFuture.supplyAsync(() -> {
			return source.loadEmailData();
		}).exceptionally(t -> {
			throw new RuntimeException(t);
		});
	}

	private ScoringRulesDto toDto(ScoringRules obj) {
		ScoringRulesDto dto = new ScoringRulesDto();

		dto.setIpAddresses(toDto(obj.getIpAddressMap()));
		dto.setFromEmails(toDto(obj.getFromEmailMap()));
		dto.setHosts(toDto(obj.getReceivedHostMap()));
		dto.setSubjects(toDto(obj.getSubjectMap()));
		return dto;
	}

	private List<ScoringRuleDto> toDto(Map<String, Score> map) {
		List<ScoringRuleDto> dtoList = new ArrayList<>();
		map.forEach((value, score) -> dtoList.add(toDto(value, score)));
		return dtoList;
	}

	private ScoringRuleDto toDto(String value, Score score) {
		ScoringRuleDto dto = new ScoringRuleDto();
		dto.setValue(value);
		dto.setScore(toDto(score));
		return dto;
	}

	private ScoreDto toDto(Score score) {
		ScoreDto dto = new ScoreDto();
		dto.setPhishingCount(score.getPhishingCount());
		dto.setTotalCount(score.getTotalCount());
		dto.setScore(score.getScore());
		return dto;
	}
}
