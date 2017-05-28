package it.test.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.mail.MessagingException;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import it.test.model.Model;
import it.test.model.Observable;
import it.test.view.Observer;
import it.test.view.View;

public class Controller {
	
	public final static String SMTP_DEFAULT = "smtp.gmail.com";

	private View benchmarkView;
	private Model benchmarkModel;

	public Controller(View benchmarkView, Model benchmarkModel) {
		this.benchmarkModel = benchmarkModel;
		this.benchmarkView = benchmarkView;
		
		if (benchmarkModel instanceof Observable &&  benchmarkView instanceof Observer){
			((Observable)benchmarkModel).addObserver((Observer)benchmarkView);
			//Done
		}		

		benchmarkView.addStartButtonListener(new StartActionListener());
		benchmarkView.addMailCheckListener(new TextChangeListener());
	}

	public void start(){
		benchmarkView.show();
	}

	private class StartActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//TODO Start the model			
			List<JTextField> textList = benchmarkView.getTextList();
			try {
				//Input check
				final String senderMail = textList.get(0).getText();
				final String smtpAddress = SMTP_DEFAULT;
				final String password = textList.get(1).getText();
				final String reciverMail = textList.get(2).getText();
				//Mail number check
				if (textList.get(3).getText().isEmpty()){
					throw new NumberFormatException("Mail number not valid");
				}
				final int mailNum = Integer.parseInt(textList.get(3).getText());
				if (mailNum <= 0) {
					throw new NumberFormatException("Mail number not valid");
				}
				//Textlength
				if (textList.get(4).getText().isEmpty()){
					throw new NumberFormatException("Text length number not valid");
				}
				final int textLength = Integer.parseInt(textList.get(4).getText());
				if (textLength < 0) {
					throw new NumberFormatException("Text length number not valid");
				}
				//Attachment size check
				if (textList.get(5).getText().isEmpty()){
					throw new NumberFormatException("Attachment size not valid");	
				}
				final double attachmentSize = Double.parseDouble(textList.get(5).getText());
				if (attachmentSize < 0) {
					throw new NumberFormatException("Attachment size not valid");
				}
				
				new Thread(new Runnable(){
					public void run() {
						try {
							benchmarkModel.setupData(senderMail, password, reciverMail, smtpAddress, mailNum, textLength, attachmentSize);
							System.out.println("Setup done");
							benchmarkView.showProgressBar();
							System.out.println("Showed");
							benchmarkModel.startTest();
							benchmarkView.closeProgressBar();							
						} catch (MessagingException e) {
							try {
								SwingUtilities.invokeAndWait(()-> benchmarkView.showErrorDialog("Connection error"));
							} catch (Exception ex) {}
						}						
					}					
				}).start();

			} catch (NumberFormatException e1) {
				benchmarkView.showErrorDialog(e1.getMessage());
			} catch (NullPointerException e2) {
				benchmarkView.showErrorDialog("Attachment size not valid");
			}
		}		
	}

	private class TextChangeListener implements DocumentListener{
		public void insertUpdate(DocumentEvent e) {}
		public void removeUpdate(DocumentEvent e) {}

		public void changedUpdate(DocumentEvent e) {
			//TODO Mail check listener
		}		
	}


}
