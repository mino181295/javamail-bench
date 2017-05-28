package it.test.model;

public interface ProgressManager {
	
	void addProgressListener(ProgressListener p);
	
	void notifyProgressChanged();

}
