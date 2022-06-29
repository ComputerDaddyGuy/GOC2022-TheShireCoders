package lu.goc2022.cli.command.analyze.rules;

import lombok.Data;

@Data
public class ScoreDto {

	private long phishingCount;
	private long totalCount;
	private double score;

}
