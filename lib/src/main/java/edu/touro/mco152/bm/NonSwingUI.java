package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;

import java.io.File;
import java.util.Properties;

public class NonSwingUI implements Worker {
    DiskWorker diskWorker;

    public void setDiskWorker(DiskWorker diskWorker) {
        this.diskWorker = diskWorker;
    }

    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */
    private static void setupDefaultAsPerProperties()
    {
        /// Do the minimum of what  App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();

        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference

        // may be null when tests not run in original proj dir, so use a default area
        if (App.locationDir == null) {
            App.locationDir = new File(System.getProperty("user.home"));
        }

        App.dataDir = new File(App.locationDir.getAbsolutePath()+File.separator+App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        }
        else
        {
            App.dataDir.mkdirs(); // create data dir if not already present
        }
    }
    private boolean isCancelled;

    @Override
    public void startBenchmark() {
        try {
        diskWorker.startBenchmark();
    }
        catch (Exception e) {}//not sure how to handle this. Caused by rAccFile.readFully().
    }

    @Override
    public boolean getIsCancelled() {
        return isCancelled;
    }

    @Override
    public void setTheProgress(int percentComplete) {

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
