package lu.goc2022.rules.spambee.csv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBeanBuilder;

import lu.goc2022.rules.spambee.ISpamBeeAttributesLoader;
import lu.goc2022.rules.spambee.SpamBeeAttributes;

@Component
public class SpamBeeAttributesCsvReader implements ISpamBeeAttributesLoader {

	@Override
	public List<SpamBeeAttributes> loadSpamBeeAttributes() {
		try (FileReader reader = new FileReader("src/main/resources/_Spambee_Attributes__2022-04.csv")) {
			List<SpamBeeAttributes> beans = new CsvToBeanBuilder<SpamBeeAttributes>(reader) //
					.withType(SpamBeeAttributes.class) //
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
