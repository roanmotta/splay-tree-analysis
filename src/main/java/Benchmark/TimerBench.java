package Benchmark;

public class TimerBench {
    private long startTime;
    private long totalTime;

    public void reset() {
        this.totalTime = 0;
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void stop() {
        long endTime = System.nanoTime();
        this.totalTime += (endTime - this.startTime);
    }


    public double getTotal() {
        return this.totalTime / 1000000.0;
    }
}