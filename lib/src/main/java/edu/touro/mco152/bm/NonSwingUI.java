package edu.touro.mco152.bm;

/**
 * Soon to be replaced non-GUI version of Worker
 */
public class NonSwingUI implements Worker {
    DiskWorker diskWorker;
    int progress;

    private boolean isCancelled = false;

    @Override
    public void showErrorMessage() {
        System.out.println("For valid READ measurements please clear the disk cache by using the included RAMMap.exe" +
                " or flushmem.exe utilities.Removable drives can be disconnected and reconnected.For system drives use " +
                "the WRITE and READ operations\s independantly by doing a cold reboot after the WRITE");
    }

    @Override
    public void startBenchmark() {
        try {
        diskWorker.runBenchmark();
    }
        catch (Exception e) {throw new RuntimeException();}//not sure how to handle this. Caused by rAccFile.readFully().
    }

    @Override
    public boolean getIsCancelled() {
        return isCancelled;
    }

    @Override
    public void setTheProgress(int percentComplete) {
        progress = percentComplete;
    }

    public int getProgress() {
        return progress;
    }
    @Override
    public void doPublish(DiskMark Mark) {

    }

    @Override
    public boolean cancelBenchmark(boolean mayInterruptIfRunning) {
        isCancelled = true;
        return true;
    }
}
