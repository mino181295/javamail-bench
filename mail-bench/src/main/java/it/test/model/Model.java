package it.test.model;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface Model {

	void setupData(String senderMail, String senderPassword, String reciverMail,
				   String smtpServer, int mailNumber, int textLength, double attachmentSize) throws AddressException;
	
	void startTest() throws MessagingException;
	
}
