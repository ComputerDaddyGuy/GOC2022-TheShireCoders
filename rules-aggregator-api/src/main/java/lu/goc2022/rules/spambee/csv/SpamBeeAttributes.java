package lu.goc2022.rules.spambee.csv;

import com.opencsv.bean.CsvBindByName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class SpamBeeAttributes {

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
	@CsvBindByName(column = "event_id")
	private String eventId;

	@Getter
	@Setter
	@CsvBindByName(column = "object_relation")
	private String objectRelation;

	@Getter
	@Setter
	@CsvBindByName(column = "value")
	private String value;

}
