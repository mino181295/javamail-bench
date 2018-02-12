package it.matteo.model;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.SwingUtilities;

import it.matteo.view.Observer;

public class BenchmarkModel implements Model, Observable, ProgressListener {

    User sender;
    User reciver;
    Mail mainMail;
    double nBench, totalProgress, currentBench;
    int mailNumber;
    int textLenght;
    double attachmentSize;
    ConnectionManager connectionManager;

    List<Observer> observersView;

    public BenchmarkModel() {
        this.observersView = new LinkedList<Observer>();
    }

    public void setupData(String senderMail, String senderPassword, String reciverMail,
            String smtpServer, int mailNumber, int textLength, double attachmentSize)
            throws AddressException {
        this.sender = new User(senderMail, senderPassword);
        this.reciver = new User(reciverMail);
        this.mailNumber = mailNumber;
        this.textLenght = textLength;
        this.nBench = 2;
        this.currentBench = 0;
        this.attachmentSize = attachmentSize;

        this.connectionManager = new ConnectionManager(sender, reciver, smtpServer);
        this.connectionManager.addProgressListener(this);
    }

    public void startTest() throws MessagingException {
        for (int i = 0; i < mailNumber; i++) {
            if (attachmentSize > 0) {
                mainMail = MailFactory.createMailWithAttachment(reciver, sender, i, textLenght, attachmentSize);
            } else {
                mainMail = MailFactory.createSimpleMail(sender, reciver, i, textLenght);
            }
            connectionManager.addMail(mainMail);
        }

        connectionManager.sameConnectionSend();
        currentBench++;

        Benchmark b = connectionManager.getCurrentBenchmark();
        double avg_sc = toMillis(b.getAvgResult());
        double min_sc = toMillis(b.getMinResult());
        double max_sc = toMillis(b.getMaxResult());
        final StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("SAME CONNECTION: \n");
        resultBuilder.append("Average time: ").append(avg_sc).append(" [ms]").append("\n");
        resultBuilder.append("Max time: ").append(min_sc).append(" [ms]").append("\n");
        resultBuilder.append("Min time: ").append(max_sc).append(" [ms]").append("\n");

        connectionManager.differentConnectionSend();
        currentBench++;

        double avg_dc = toMillis(b.getAvgResult());
        double min_dc = toMillis(b.getMinResult());
        double max_dc = toMillis(b.getMaxResult());
        resultBuilder.append("DIFFERENT CONNECTION: \n");
        resultBuilder.append("Average time: ").append(avg_dc).append(" [ms]").append("\n");
        resultBuilder.append("Max time: ").append(min_dc).append(" [ms]").append("\n");
        resultBuilder.append("Min time: ").append(max_dc).append(" [ms]").append("\n");

        NumberFormat f = new DecimalFormat("#0.00");

        resultBuilder.append("Speedup: \n[avg = ").append(f.format(avg_dc / avg_sc))
                .append(" ] [ min = ").append(f.format(min_dc / min_sc))
                .append(" ] [ max = ").append(f.format(max_dc / max_sc)).append(" ]");

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    notifyObservers(resultBuilder.toString());
                }
            });
        } catch (InvocationTargetException e) {
            System.err.println("Error in invoke");
        } catch (InterruptedException e) {
            System.err.println("Error in invoke");
        }

    }

    public void addObserver(Observer o) {
        observersView.add(o);
    }

    public void deleteObserver(Observer o) {
        observersView.remove(o);
    }

    public void notifyObservers(String result) {
        for (Observer o : observersView) {
            o.updateView(result);
        }
    }

    public void notifyObservers(double result) {
        for (Observer o : observersView) {
            o.updateView(result);
        }
    }

    private long toMillis(long nanoSeconds) {
        return TimeUnit.NANOSECONDS.toMillis(nanoSeconds);
    }

    @Override
    public void progressChanged() {
        totalProgress = currentBench + connectionManager.getProgress();
        notifyObservers(totalProgress / nBench);
    }

    @Override
    public void deleteAttachments() {
        connectionManager.deleteMails();
    }

}
