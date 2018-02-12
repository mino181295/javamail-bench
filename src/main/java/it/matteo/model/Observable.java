package it.matteo.model;

import it.matteo.view.Observer;

public interface Observable {

    void addObserver(Observer o);

    void deleteObserver(Observer o);

    void notifyObservers(String result);

}
