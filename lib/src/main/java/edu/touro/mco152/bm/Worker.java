package edu.touro.mco152.bm;

public interface Worker {
    boolean getIsCancelled();
    void setTheProgress(int percentComplete);
    void doPublish(DiskMark Mark);
    boolean cancelBenchmark(boolean b);

}
