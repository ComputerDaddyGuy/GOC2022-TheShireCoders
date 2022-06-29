package lu.goc2022.controllers;

import lu.goc2022.rules.ScoringRules;

public interface IPhishingDataSource {

	public ScoringRules computeScoringRules();

}
