package edu.touro.mco152.bm;

/**
 * Worker is an abstraction for a SwingWorker that requires some SwingWorker-like functionality for BadBM.
 */
public interface Worker {
    void showErrorMessage();
    /**
     * abstract version of SwingWorker's execute() as applicable in BadBM. Allows for non-GUI implementation.
     */
    void startBenchmark();
    /**
     * abstract version of SwingWorker's isCancelled() as applicable in BadBM. Allows for non-GUI implementation.
     * @return is this benchmark cancelled
     */
    boolean getIsCancelled();

    /**
     * abstract version of SwingWorker's setProgress() as applicable in BadBM. Allows for non-GUI implementation.
     * @param percentComplete Completeness of benchmark
     */
    void setTheProgress(int percentComplete);

    /**
     * abstract version of SwingWorker's publish() as applicable in BadBM. Allows for non-GUI implementation.
     * @param Mark information about the benchmarking results
     */
    void doPublish(DiskMark Mark);

    /**
     * abstract version of SwingWorker's cancel() as applicable in BadBM. Allows for non-GUI implementation.
     * @param mayInterruptIfRunning mayInterruptIfRunning
     * @return
     */
    boolean cancelBenchmark(boolean mayInterruptIfRunning);
}
