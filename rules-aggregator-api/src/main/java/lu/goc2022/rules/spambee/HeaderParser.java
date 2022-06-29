package lu.goc2022.rules.spambee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderParser {

	private static final HeaderParser INSTANCE = new HeaderParser();

	private HeaderParser() {

	}

	public static HeaderParser getInstance() {
		return INSTANCE;
	}

	/**
	 * Parse the <code>value</code> to extract the email headers
	 */
	public Map<String, List<String>> parseHeaders(String value) {
		Map<String, List<String>> emailHeaders = new HashMap<>();
		if (value != null) {
			List<String> lines = value.lines().toList();
			String currentHeaderName = null;
			for (String line : lines) {
				if (line.length() > 0 && !Character.isWhitespace(line.charAt(0))) {
					currentHeaderName = line.substring(0, line.indexOf(':'));
					emailHeaders.put(currentHeaderName, new ArrayList<>());
					emailHeaders.get(currentHeaderName).add(line.substring(line.indexOf(':') + 1).trim());
				} else {
					emailHeaders.get(currentHeaderName).add(line.trim());
				}
			}
		}
		return emailHeaders;
	}

}
