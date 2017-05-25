package it.test.model;

import java.util.Date;
import java.util.Optional;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {
	
	private User sendTo;
	private User sentFrom;
	
	private String objectField;
	private String textField;

	private Optional<Attachment> attachment;	

	public Mail(User sendTo, User sentFrom, String object, String text ) throws AddressException {
		this.sendTo = sendTo;
		this.sentFrom = sentFrom;		
		this.objectField = object;
		this.textField = text;
		this.attachment = Optional.empty();
	}	
	
	public Mail(String sendTo, String sentFrom, String object, String text ) throws AddressException {
		this(new User(sendTo), new User(sentFrom), object, text);
	}
	
	public Message generateMime(Session currentSession) throws MessagingException {
		if (attachment.isPresent()){
			return generateTextWithAttachment(currentSession);
		} else {
			return generateTextMime(currentSession);			
		}
	}
	
	private Message generateTextMime(Session currentSession) throws MessagingException {
		Message mime = new MimeMessage(currentSession);
		mime.setFrom(this.sentFrom.getMail());
		mime.setRecipient(Message.RecipientType.TO, sendTo.getMail());
		mime.setSubject(this.objectField);
		mime.setSentDate(new Date());
		mime.setText(this.textField);	
		mime.saveChanges();
		return mime;
		
	}
	
	private Message generateTextWithAttachment(Session currentSession) throws MessagingException {
        Message mime = new MimeMessage(currentSession);        
        mime.setFrom(this.sentFrom.getMail());
        mime.setRecipient(Message.RecipientType.TO, sendTo.getMail());
        mime.setSubject(this.objectField);        

        Multipart multipart = new MimeMultipart();
        //BodyPart
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(this.textField);        
        multipart.addBodyPart(messageBodyPart);
        
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(this.attachment.get().getAttachmentName());
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(attachment.get().getAttachmentName());
        multipart.addBodyPart(messageBodyPart);
        
        
        mime.setContent(multipart);
        mime.saveChanges();
		return mime;
	}
	
	public void setAttachment(Attachment attachment) {
		this.attachment = Optional.of(attachment);
	}

	@Override
	public String toString() {
		return "Mail [\nsendTo=" + sendTo + ",\nsentFrom=" + sentFrom + ", \nobjectField=" + objectField + ", \ntextField="
				+ textField + "\n]";
	}

}
