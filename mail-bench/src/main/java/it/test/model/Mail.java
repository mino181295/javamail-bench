package it.test.model;

import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	
	private InternetAddress sendTo;
	private InternetAddress sentFrom;
	
	private String objectField;
	private String textField;

	private Attachment attachment;	

	public Mail(String sendTo, String sentFrom, String object, String text ) throws AddressException {
		
		this.sendTo = new InternetAddress(sendTo);
		this.sentFrom = new InternetAddress(sentFrom);
		
		this.objectField = object;
		this.textField = text;
	}
	
	public Message generateMime(Session currentSession) throws MessagingException {
		Message mime = new MimeMessage(currentSession);
		mime.setFrom(this.sentFrom);
		mime.setRecipient(Message.RecipientType.TO, sendTo);
		mime.setSubject(this.objectField);
		mime.setSentDate(new Date());
		mime.setText(this.textField);		
		return mime;
	}
	
	public InternetAddress getSendTo() {
		return sendTo;
	}

	public void setSendTo(InternetAddress sendTo) {
		this.sendTo = sendTo;
	}

	public InternetAddress getSentFrom() {
		return sentFrom;
	}

	public void setSentFrom(InternetAddress sentFrom) {
		this.sentFrom = sentFrom;
	}
	
	public String getObjectField() {
		return objectField;
	}

	public void setObjectField(String objectField) {
		this.objectField = objectField;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}
	

}
