package edu.touro.mco152.bm;

/**
 * ReadWriteExecutor is an invoker that allows DiskWorker to follow the Command Pattern
 * by passing in a BenchMarking class and then calling ReadWriteExecutor's runBenchMark.
 */
public class ReadWriteExecutor {
    BenchMark benchMark;

    public void setBenchmark(BenchMark benchMark) {
        this.benchMark = benchMark;
    }

    public boolean runBenchMark(Worker worker) {
        return benchMark.runBenchMark(worker);
    }
}
