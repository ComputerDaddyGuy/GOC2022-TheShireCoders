package lu.goc2022.rules.spambee.csv;

import com.opencsv.bean.CsvBindByName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class SpamBeeEvents {

	@Getter
	@Setter
	@CsvBindByName(column = "index")
	private Long index;

	@Getter
	@Setter
	@CsvBindByName(column = "id")
	private String id;

	@Getter
	@Setter
	@CsvBindByName(column = "date")
	private String date;

	@Getter
	@Setter
	@CsvBindByName(column = "attribute_count")
	private String attributeCount;

	@Getter
	@Setter
	@CsvBindByName(column = "Tag")
	private String tag;

	public boolean isPhishing() {
		return "Phishing".equals(tag);
	}

}
