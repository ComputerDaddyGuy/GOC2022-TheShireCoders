package lu.goc2022.cli.exceptions;

public class MailAnalyzerException extends RuntimeException {

	public MailAnalyzerException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailAnalyzerException(String message) {
		super(message);
	}

	public MailAnalyzerException(Throwable cause) {
		super(cause);
	}

}
