package it.test.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;

public class BenchmarkView implements View, Observer{

	private final static String TITLE = "SMTP Benchmark";

	private JLabel titleLabel;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel setupPanel;
	//	private ProgressMonitor progressBar;
	//	private JDialog progressBarDialog;
	//	private JProgressBar progressBar;
	private JTextArea resultArea;

	String [] labelNames = {"Sender mail", "Sender SMTP Server", "Password", "Reciver mail", "Mail number to test", "Attachment Size"};
	List<JLabel> labelComponents;
	List<JTextField> textComponents;

	JButton startButton;

	public BenchmarkView(){
		mainFrame = new JFrame();

		titleLabel = new JLabel();
		resultArea = new JTextArea();

		mainPanel = new JPanel();
		centerPanel = new JPanel();
		setupPanel = new JPanel();

		startButton = new JButton();

		labelComponents = new LinkedList<JLabel>();
		textComponents = new LinkedList<JTextField>();		
		for (String string : labelNames) {
			labelComponents.add(new JLabel(string+": ", JLabel.TRAILING));
			if (string.equals("Password")){
				textComponents.add(new JPasswordField(20));
			} else {
				textComponents.add(new JTextField(20));
			}
		}
		
		setupFrame();
		setupPanel();
		setupLabel();
		setupButton();
		setupResultArea();

		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
	}

	private void setupResultArea() {
		resultArea.setEditable(false);
		resultArea.setRows(10);

	}

	public void show(){
		mainFrame.setVisible(true);
	}

	private void setupFrame(){
		mainFrame.setTitle(TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setupPanel() {
		//Creating main panel layout.
		BorderLayout mainPanelLayout = new BorderLayout();
		mainPanel.setLayout(mainPanelLayout);
		mainPanel.setBorder(new EmptyBorder(6, 6, 6, 6));
		mainPanel.add(titleLabel, BorderLayout.NORTH);
		//Creating the center of the main panel.		
		BorderLayout centerLayout = new BorderLayout();
		centerPanel.setLayout(centerLayout);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		//Creating the grid of the data.
		GridLayout dataLayout = new GridLayout(labelNames.length,1, 10, 10);
		setupPanel.setLayout(dataLayout);
		setupPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		for (int i=0; i<labelNames.length; i++){
			setupPanel.add(labelComponents.get(i));
			setupPanel.add(textComponents.get(i));
		}		
		centerPanel.add(setupPanel, BorderLayout.NORTH);
		//Adding the result area.
		centerPanel.add(resultArea, BorderLayout.SOUTH);
		//Adding the main panel to the content pane.
		mainFrame.getContentPane().add(mainPanel);
	}

	private void setupLabel(){
		titleLabel.setText(TITLE);
		titleLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
	}

	private void setupButton() {
		startButton.setText("Start benchmark");
		mainPanel.add(startButton, BorderLayout.SOUTH);
	}

	private int findIndexOf(String component){
		for(int i = 0; i<labelNames.length; i++){
			if (labelNames[i].equals(component)){
				return i;
			}
		}
		return -1;
	}

	public void addStartButtonListener(ActionListener listener){
		startButton.addActionListener(listener);
	}

	public void addMailCheckListener(DocumentListener changeListener){
		this.textComponents.get(findIndexOf("Sender mail")).getDocument().addDocumentListener(changeListener);
	}

	public void showErrorDialog(String errorMessage){
		JOptionPane.showMessageDialog(mainFrame,
				errorMessage,
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public void updateView(String result) {
		resultArea.setText(result);		
	}

	public List<JLabel> getLabelList() {		
		return this.labelComponents;
	}

	public List<JTextField> getTextList() {
		return this.textComponents;
	}

}
