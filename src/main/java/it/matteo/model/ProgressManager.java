package it.matteo.model;

public interface ProgressManager {

    void addProgressListener(ProgressListener p);

    void notifyProgressChanged();
}
