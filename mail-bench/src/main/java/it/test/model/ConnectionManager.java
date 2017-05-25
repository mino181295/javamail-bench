package it.test.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import it.test.model.Benchmark.BenchmarkException;

public class ConnectionManager {

	private List<Mail> mailList;
	private Benchmark timeTracker;	

	protected User mailSender;
	private User mailReciver;

	private Session currentSession;
	private Properties props;

	public ConnectionManager(User mailSender, User mailReciver){
		this.mailReciver = mailReciver;
		this.mailSender = mailSender;

		this.mailList = new LinkedList<Mail>();
		timeTracker = new Benchmark();

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
				String username = mailSender.getMail().getAddress();
				String password = mailSender.getPass();
				return new PasswordAuthentication(username, password);	
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
	public void addMultipleMail(Mail m, int n){
		if (n > 0) {
			for(int i=0; i<n; i++){
				addMail(m);
			}
		}
	}

	public void sameConnectionSend() throws MessagingException {
		timeTracker.startBenchmark();
		Transport transport = currentSession.getTransport("smtp");
		transport.connect();
		for (Mail mail : mailList) {
			Address singleReciver [] = {mailReciver.getMail()};
			try {
				timeTracker.start();
				transport.sendMessage(mail.generateMime(currentSession), singleReciver);
				timeTracker.stopAndSave();
			} catch (BenchmarkException e) {
				e.printStackTrace();
			}
		}
		transport.close();
		timeTracker.stopBenchmark();
	}

	public void differentConnectionSend() throws MessagingException{
		timeTracker.startBenchmark();
		for (Mail mail : mailList) {
			try {
				timeTracker.start();
				Transport.send(mail.generateMime(currentSession));
				timeTracker.stopAndSave();
			} catch (BenchmarkException e) {
				e.printStackTrace();
			}
		}
		timeTracker.stopBenchmark();
	}
	
	public Benchmark getCurrentBenchmark(){
		return this.timeTracker;
	}

}
