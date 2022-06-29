package lu.goc2022.rules.spambee;

import java.util.List;

import lu.goc2022.rules.spambee.csv.SpamBeeAttributes;

public interface ISpamBeeAttributesLoader {

	List<SpamBeeAttributes> loadSpamBeeAttributes();

}
