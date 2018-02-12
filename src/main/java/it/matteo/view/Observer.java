package it.matteo.view;

public interface Observer {

    void updateView(String result);

    void updateView(double progress);
}
