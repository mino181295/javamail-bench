package it.test.mail_bench;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import it.test.model.Mail;
import it.test.model.ConnectionManager;
import it.test.model.User;

public class App 
{
	public static void main( String[] args )
	{


		User sender = new User("mino181295@gmail.com", "");
		User reciver = new User("minardi.matteo@hotmail.it");
		Mail helloMail = null;

		try {
			helloMail = new Mail(reciver.getMail(), sender.getMail(), "Hello mail", "Prima mail");
		} catch (AddressException e) {
			e.printStackTrace();
		}

		ConnectionManager manager = new ConnectionManager(sender, reciver);
		manager.addMail(helloMail);
		try {
			manager.differentConnectionSend();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		System.out.println("Mail inviata");

	}
}
