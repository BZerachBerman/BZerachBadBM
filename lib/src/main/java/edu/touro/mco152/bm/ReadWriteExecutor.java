package edu.touro.mco152.bm;

class ReadWriteExecutor {
    BenchMark benchMark;

    public void setBenchmark(BenchMark benchMark) {
        this.benchMark = benchMark;
    }


    public void runBenchMark(Worker worker) {
        benchMark.runBenchMark(worker);
    }
}
