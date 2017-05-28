package it.test.model;

import it.test.view.Observer;

public interface Observable {
	
	void addObserver(Observer o);
	void deleteObserver(Observer o);
	
	void notifyObservers(String result);
	
}
