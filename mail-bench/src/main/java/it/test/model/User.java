package it.test.model;

import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class User {

	private InternetAddress mail;
	private Optional<String> pass;

	public User(String mail, String pass) throws AddressException{
		this.mail = new InternetAddress(mail);
		this.mail.validate();

		this.pass = Optional.of(pass);
	}

	public User(String mail) throws AddressException{
		this.mail = new InternetAddress(mail);
		this.mail.validate();

		this.pass = Optional.empty();
	}

	public InternetAddress getMail() {
		return mail;
	}

	public void setMail(String mail) throws AddressException {
		this.mail = new InternetAddress(mail);
	}
	
	public void setMail(InternetAddress mail) {
		this.mail = mail;
	}

	public String getPass() {
		return pass.isPresent() ? pass.get() : "";
	}

	@Override
	public String toString() {
		return "User [mail=" + mail + "]";
	}

	public void setPass(String pass) {
		this.pass = Optional.of(pass);
	}

}
