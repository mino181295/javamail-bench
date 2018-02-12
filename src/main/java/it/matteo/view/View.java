package it.matteo.view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

public interface View {

    void addStartButtonListener(ActionListener listener);

    void addMailCheckListener(DocumentListener changeListener);

    void showErrorDialog(String errorMessage);

    void showProgressBar();

    void closeProgressBar();

    List<JLabel> getLabelList();

    List<JTextField> getTextList();

    void show();
}
