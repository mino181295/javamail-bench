package it.test.mail_bench;

import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import it.test.model.ConnectionManager;
import it.test.model.Mail;
import it.test.model.MailFactory;
import it.test.model.User;

public class App {
	public static void main( String[] args ) {
		User sender = new User("mino181295@gmail.com", "");
		User reciver = new User("nekup@dnsdeer.com");
		
		ConnectionManager cm = new ConnectionManager(sender, reciver);
		
		Mail tempMail = null;
		try {
			for(int i = 0; i<10; i++){
				tempMail = MailFactory.createSimpleMail(reciver, sender, i, 100);
				cm.addMail(tempMail);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		}
		try {
			cm.differentConnectionSend();
		} catch (MessagingException e) {
			e.printStackTrace();
		}		
		
		System.out.println("Media = " + TimeUnit.NANOSECONDS.toMillis(cm.getCurrentBenchmark().getAvgResult()));
		System.out.println("Massimo = " + TimeUnit.NANOSECONDS.toMillis(cm.getCurrentBenchmark().getMaxResult()));
		System.out.println("Minimo = " + TimeUnit.NANOSECONDS.toMillis(cm.getCurrentBenchmark().getMinResult()));
		System.out.println("Totale = " + TimeUnit.NANOSECONDS.toMillis(cm.getCurrentBenchmark().getTotalTime()));
		
	}
}
