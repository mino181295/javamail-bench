package it.test.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class View {

	JFrame mainFrame;
	JPanel mainPanel;
	JPanel setupPanel;

	String [] labelNames = {"Sender mail", "Password", "Reciver mail"};
	List<JLabel> labelComponents;
	List<JTextField> textComponents;

	JButton startButton;

	public View(){
		mainFrame = new JFrame();

		mainPanel = new JPanel();
		setupPanel = new JPanel();

		startButton = new JButton();

		labelComponents = new LinkedList<JLabel>();
		textComponents = new LinkedList<JTextField>();		
		for (String string : labelNames) {
			labelComponents.add(new JLabel(string, JLabel.TRAILING));
			textComponents.add(new JTextField());
		}		

		setupFrame();
		setupPanel();
		setupButton();
		
		mainFrame.pack();
	}

	public void show(){
		mainFrame.setVisible(true);
	}

	private void setupFrame(){
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setupButton() {
		startButton.setText("Start benchmark");
		mainPanel.add(startButton, BorderLayout.SOUTH);
	}

	private void setupPanel() {
		BorderLayout borderLayout = new BorderLayout();
		mainPanel.setLayout(borderLayout);

		GridLayout gridLayout = new GridLayout(4,1, 10, 10);
		setupPanel.setLayout(gridLayout);
		for (int i=0; i<labelNames.length; i++){
			setupPanel.add(labelComponents.get(i));
			setupPanel.add(textComponents.get(i));
		}
		mainPanel.add(setupPanel, BorderLayout.CENTER);
		mainFrame.getContentPane().add(mainPanel);
	}
}
