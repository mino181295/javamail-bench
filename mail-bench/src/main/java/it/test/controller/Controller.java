package it.test.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import it.test.model.Model;
import it.test.model.Observable;
import it.test.view.Observer;
import it.test.view.View;

public class Controller {

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
				String senderMail = textList.get(0).getText();
				String smtpAddress = textList.get(1).getText().isEmpty() ? "smtp.gmail.com" : textList.get(0).getText();
				String password = textList.get(2).getText();
				String reciverMail = textList.get(3).getText();
				//Mail number check
				if (textList.get(4).getText().isEmpty()){
					throw new NumberFormatException("Mail number not valid");
				}
				int mailNum = Integer.parseInt(textList.get(4).getText());
				if (mailNum <= 0) {
					throw new NumberFormatException("Mail number not valid");
				}
				//Attachment size check
				if (textList.get(5).getText().isEmpty()){
					throw new NumberFormatException("Attachment size not valid");	
				}
				double attachmentSize = Double.parseDouble(textList.get(5).getText());
				if (attachmentSize < 0) {
					throw new NumberFormatException("Attachment size not valid");
				}
				
				benchmarkModel.setupData(senderMail, password, reciverMail, smtpAddress, mailNum, attachmentSize);
				benchmarkModel.startTest();

			} catch (NumberFormatException e1) {
				benchmarkView.showErrorDialog(e1.getMessage());
			} catch (NullPointerException e2) {
				benchmarkView.showErrorDialog("Attachment size not valid");
			} catch (AddressException e1) {
				benchmarkView.showErrorDialog("Mail address not valid");
			} catch (MessagingException e1) {
				benchmarkView.showErrorDialog("Connection error");
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
