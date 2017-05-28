package it.test.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import it.test.view.Observer;

public class BenchmarkModel implements Model, Observable {

	User sender;
	User reciver;
	Mail mainMail;
	int mailNumber;
	double attachmentSize;
	ConnectionManager connectionManager;

	List<Observer> observersView;	
	
	public BenchmarkModel(){
		this.observersView = new LinkedList<Observer>();
	}

	public void setupData(String senderMail, String senderPassword, String reciverMail, String smtpServer, int mailNumber, double attachmentSize) throws AddressException {
		this.sender = new User(senderMail, senderPassword);
		this.reciver = new User(reciverMail);
		this.mailNumber = mailNumber;
		this.attachmentSize = attachmentSize;
		

		this.connectionManager = new ConnectionManager(sender, reciver, smtpServer);		
	}

	public void startTest() throws MessagingException  {		
		for(int i = 0; i<mailNumber; i++){
			if (attachmentSize > 0){
				mainMail = MailFactory.createMailWithAttachment(reciver, sender, i, 100, 1);
			} else {
				mainMail = MailFactory.createSimpleMail(sender, reciver, i, 100);
			}			
			connectionManager.addMail(mainMail);
		}

		
		connectionManager.sameConnectionSend();		
		Benchmark b = connectionManager.getCurrentBenchmark();
		double avg_sc = toMillis(b.getAvgResult());
		double min_sc = toMillis(b.getMinResult());
		double max_sc = toMillis(b.getMaxResult());
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append("SAME CONNECTION: \n");
		resultBuilder.append("Average time: ").append(avg_sc).append("[ms]").append("\n");
		resultBuilder.append("Max time: ").append(min_sc).append("[ms]").append("\n");
		resultBuilder.append("Min time: ").append(max_sc).append("[ms]").append("\n");
		
		connectionManager.differentConnectionSend();
		double avg_dc = toMillis(b.getAvgResult());
		double min_dc = toMillis(b.getMinResult());
		double max_dc = toMillis(b.getMaxResult());		
		resultBuilder.append("DIFFERENT CONNECTION: \n");
		resultBuilder.append("Average time: ").append(avg_dc).append("[ms]").append("\n");
		resultBuilder.append("Max time: ").append(min_dc).append("[ms]").append("\n");
		resultBuilder.append("Min time: ").append(max_dc).append("[ms]").append("\n");
		
		resultBuilder.append("Speedup: [avg = ").append(avg_dc/avg_sc)
					 .append(" ] [ min = ").append(min_dc/min_sc)
					 .append(" ] [ max = ").append(max_dc/max_sc).append(" ]");
		
		
		notifyObservers(resultBuilder.toString());
	}

	public void addObserver(Observer o) {
		observersView.add(o);
	}

	public void deleteObserver(Observer o) {
		observersView.remove(o);		
	}

	public void notifyObservers(String result) {
		for (Observer o : observersView) {
			o.updateView(result);
		}
	}
	
	private long toMillis(long nanoSeconds){
		return TimeUnit.NANOSECONDS.toMillis(nanoSeconds);
	}

}
