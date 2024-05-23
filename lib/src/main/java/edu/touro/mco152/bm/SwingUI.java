package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;

import javax.swing.*;
import java.util.List;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.dataDir;

public class SwingUI extends SwingWorker<Boolean, DiskMark> implements Worker {
    DiskWorker diskWorker;
    // Record any success or failure status returned from SwingWorker (might be us or super)
    Boolean lastStatus = null;  // so far unknown

    public void setDiskWorker(DiskWorker diskWorker) {
        this.diskWorker = diskWorker;
    }

    @Override
    public void startBenchmark() {
        execute();
    }

    /**
     * SwingUI's doInBackground is called by its hidden execute method.
     * @return was DiskWorker's startBenchmark() successful.
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        return diskWorker.startBenchmark();
    }

    @Override
    public boolean getIsCancelled() {
        return isCancelled();
    }

    @Override
    public void setTheProgress(int percentComplete) {
        setProgress(percentComplete);
    }

    @Override
    public void doPublish(DiskMark Mark) {
        publish(Mark);
    }
    @Override
    public boolean cancelBenchmark(boolean b) {
        return cancel(b);
    }

    /**
     Invoked when the doInBackground method of SwingWorker completes successfully or is aborted.
     This method, called by Swing, grants access to the get method within its scope, retrieving the computed
     result of the doInBackground method. Upon invocation, the computation within doInBackground is halted.
     */
    @Override
    protected void done() {
        // Obtain final status, might from doInBackground ret value, or SwingWorker error
        try {
            lastStatus = super.get();   // record for future access
        } catch (Exception e) {
            Logger.getLogger(App.class.getName()).warning("Problem obtaining final status: " + e.getMessage());
        }

        if (App.autoRemoveData) {
            Util.deleteDirectory(dataDir);
        }
        App.state = App.State.IDLE_STATE;
        Gui.mainFrame.adjustSensitivity();
    }

    /**
     Retrieves the most recent status of the game.
     @return The application's current status.
     */
    public Boolean getLastStatus() {
        return lastStatus;
    }

    /**
     * Process a list of 'chunks' that have been processed, ie that our thread has previously
     * published to Swing. For my info, watch Professor Cohen's video -
     * Module_6_RefactorBadBM Swing_DiskWorker_Tutorial.mp4
     * @param markList a list of DiskMark objects reflecting some completed benchmarks
     */
    @Override
    protected void process(List<DiskMark> markList) {
        markList.stream().forEach((dm) -> {
            if (dm.type == DiskMark.MarkType.WRITE) {
                Gui.addWriteMark(dm);
            } else {
                Gui.addReadMark(dm);
            }
        });
    }
}