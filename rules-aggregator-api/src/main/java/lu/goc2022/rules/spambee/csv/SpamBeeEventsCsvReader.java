package lu.goc2022.rules.spambee.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBeanBuilder;

import lu.goc2022.rules.spambee.ISpamBeeEventsLoader;
import lu.goc2022.rules.spambee.SpamBeeEvents;

@Component
public class SpamBeeEventsCsvReader implements ISpamBeeEventsLoader {

	@Override
	public List<SpamBeeEvents> loadSpamBeeEvents() {
		try (FileReader reader = new FileReader("src/main/resources/_Spambee_Events__2022-04.csv")) {
			List<SpamBeeEvents> beans = new CsvToBeanBuilder<SpamBeeEvents>(reader) //
					.withType(SpamBeeEvents.class) //
					.build() //
					.parse();
			return beans;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return List.of();
		} catch (IOException e) {
			e.printStackTrace();
			return List.of();
		}
	}

}
