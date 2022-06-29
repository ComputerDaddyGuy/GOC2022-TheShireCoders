package lu.goc2022.rules;

import lombok.Value;

/**
 * A Score is a value between 0 (=data is NEVER associated to phishing) and 100 (=data is ALWAYS associated to phishing).
 */
@Value
public class Score {

	/**
	 * The score, between 0 and 100
	 */
	private final int value;

	/**
	 * Gets the maximum score between two scores.
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static Score getMax(Score s1, Score s2) {
		if (s2.value > s1.value) {
			return s2;
		}
		return s1;
	}

}
