package lu.goc2022.cli.command.analyze.email;

/**
 * A decision taken after phishing status evaluation.
 */
public enum PhishingDecision {

	/**
	 * Delete the email, as it's marked as dangerous phishing.
	 */
	DELETE,

	/**
	 * Keep the email but sanitize its content (remove the URL, etc.).
	 */
	SANITIZE,

	/**
	 * Do nothing about the email, as it's considered safe.
	 */
	OK

}
