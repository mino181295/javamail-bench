package it.test.mail_bench;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import it.test.model.ConnectionManager;
import it.test.model.Mail;
import it.test.model.MailFactory;
import it.test.model.User;

public class App {
	public static void main( String[] args ) {
		User sender = new User("mino181295@gmail.com", "");
		User reciver = new User("minardi.matteo@hotmail.it");
		
		ConnectionManager cm = new ConnectionManager(sender, reciver);
		
		Mail firstAttachmentMail = null;
		try {
			firstAttachmentMail = MailFactory.createMailWithAttachment(reciver, sender, 1, 10, 1);
		} catch (AddressException e) {
			e.printStackTrace();
		}
		
		cm.addMail(firstAttachmentMail);
		try {
			cm.differentConnectionSend();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done");
	}
}
