package it.matteo.model;

import java.util.LinkedList;
import java.util.List;

public class Benchmark {

    private long initialTime;
    private long finalTime;

    private long totalTime;

    private boolean isRunning = false;

    private List<Long> capturedTimes;

    private long maxResult, minResult, avgResult;

    public Benchmark() {
        this.capturedTimes = new LinkedList<Long>();
    }

    public void startBenchmark() {
        this.capturedTimes.clear();
        this.totalTime = System.nanoTime();
        maxResult = 0;
        minResult = 0;
        avgResult = 0;
    }

    public void stopBenchmark() {
        this.totalTime = System.nanoTime() - this.totalTime;
        if (capturedTimes.size() > 0) {
            maxResult = capturedTimes.get(0);
            minResult = capturedTimes.get(0);
            for (Long n : capturedTimes) {
                avgResult += n.longValue();
                if (n > maxResult) {
                    maxResult = n;
                }
                if (n < minResult) {
                    minResult = n;
                }
            }
            avgResult = avgResult / capturedTimes.size();
        }
    }

    public void start() throws BenchmarkException {
        if (!this.isRunning) {
            this.initialTime = System.nanoTime();
            this.isRunning = true;
        } else {
            throw new BenchmarkException("Benchmark not started");
        }
    }

    private void stop() throws BenchmarkException {
        if (this.isRunning) {
            this.finalTime = System.nanoTime();
            this.isRunning = false;
        } else {
            throw new BenchmarkException("Benchmark not started");
        }
    }

    private long getResult() throws BenchmarkException {
        if (!this.isRunning) {
            return finalTime - initialTime;
        } else {
            throw new BenchmarkException("Benchmark not ended");
        }
    }

    public void stopAndSave() throws BenchmarkException {
        this.stop();
        this.capturedTimes.add(this.getResult());
    }

    public long getMaxResult() {
        return maxResult;
    }

    public long getMinResult() {
        return minResult;
    }

    public long getAvgResult() {
        return avgResult;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public class BenchmarkException extends Exception {

        private static final long serialVersionUID = 1L;

        public BenchmarkException(String e) {
            super(e);
        }
    }

}
