package lu.goc2022.cli;

import java.io.File;
import java.util.concurrent.Callable;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.converter.EmailConverter;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "analyze")
public class Analyze implements Callable<Integer> {

	@Parameters(paramLabel = "EML file", description = "the email to scan")
	File emlFile;

	@Override
	public Integer call() throws Exception {
		Email email = EmailConverter.emlToEmail(emlFile);

		System.out.println("getFromRecipient: " + email.getFromRecipient());
		System.out.println("getSubject: " + email.getSubject());
		email.getPlainText();
//		System.out.println("getHTMLText: " + email.getHTMLText());
		email.getAttachments();
		email.getEmbeddedImages();
		System.out.println("getId: " + email.getId());
		System.out.println("getHeaders: " + email.getHeaders());

		return 0;
	}

}
