package lu.goc2022.rules;

/**
 * A Score is a value between 0 (=data is NEVER associated to phishing) and 1 (=data is ALWAYS associated to phishing).
 */
public class Score {

	/**
	 * The score, between 0 and 100
	 */
	private int phishingCount;
	private int totalCount;

	public Score() {

	}

	public Score count(boolean isPhishing) {
		if (isPhishing) {
			phishingCount++;
		}
		totalCount++;
		return this;
	}

	public float getScore() {
		return (float) phishingCount / (float) totalCount;
	}

	public int getPhishingCount() {
		return phishingCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public String toString() {
		return phishingCount + "/" + totalCount + ":" + getScore();
	}
}
