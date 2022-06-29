package lu.goc2022.rules;

import java.util.List;

public interface IEmailDataSource {

	public List<EmailPhishingData> loadEmailData();

}
