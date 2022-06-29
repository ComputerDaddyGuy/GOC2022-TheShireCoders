package lu.goc2022.rules.ibm;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lu.goc2022.rules.EmailPhishingData;
import lu.goc2022.rules.IEmailDataSource;

@Component
@AllArgsConstructor
public class IbmTrusteerDataSource implements IEmailDataSource {

	@Override
	public List<EmailPhishingData> loadEmailData() {

		/*
		 * Real application will have to load data from Ibm Trusteer (out of scope of this hackathon)
		 */

		return List.of();
	}

}
