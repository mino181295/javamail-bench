package it.test.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class ConnectionManager {
	
	private List<Mail> mailList;	
	private Properties props;
	
	protected User mailSender;
	private User mailReciver;
	
	private Session currentSession;
	
	public ConnectionManager(User mailSender, User mailReciver){
		this.mailReciver = mailReciver;
		this.mailSender = mailSender;
		
		this.mailList = new LinkedList<Mail>();
		
		this.props = new Properties();
		this.propSetup();		
		this.generateAuthSession(mailSender);
	}
	
	private void propSetup(){
		this.props.put("mail.smtp.auth", "true");
		this.props.put("mail.smtp.starttls.enable", "true");
		this.props.put("mail.smtp.host", "smtp.gmail.com");
		this.props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		this.props.put("mail.smtp.port", "587");
	}	
	
	private void generateAuthSession(User u) {
		this.currentSession = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailSender.getMail(), mailSender.getPass());	
			}
		});
	}	
	
	public void addMail(Mail m){
		this.mailList.add(m);
	}
	
	public void addMails(List<Mail> mailList){
		for (Mail curr : mailList) {
			this.addMail(curr);
		}
	}
	
	public void sameConnectionSend() throws MessagingException {
		//TODO
	}
	
	public void differentConnectionSend() throws MessagingException{
		for (Mail mail : mailList) {
	    	Transport.send(mail.generateMime(currentSession));
	    }
	}

}
