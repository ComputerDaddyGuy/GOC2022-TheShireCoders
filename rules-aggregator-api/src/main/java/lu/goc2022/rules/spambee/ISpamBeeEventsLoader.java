package lu.goc2022.rules.spambee;

import java.util.List;

import lu.goc2022.rules.spambee.csv.SpamBeeEvents;

public interface ISpamBeeEventsLoader {

	List<SpamBeeEvents> loadSpamBeeEvents();

}
