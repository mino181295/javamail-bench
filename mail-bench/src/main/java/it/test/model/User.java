package it.test.model;

import java.util.Optional;

public class User {

	private String mail;
	private Optional<String> pass;

	public User(String mail, String pass){
		this.mail = mail;
		this.pass = Optional.of(pass);
	}

	public User(String mail){
		this.mail = mail;
		this.pass = Optional.empty();
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPass() {
		return pass.isPresent() ? pass.get() : "";
	}

	public void setPass(String pass) {
		this.pass = Optional.of(pass);
	}


}
