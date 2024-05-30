import edu.touro.mco152.bm.*;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Properties;

import static edu.touro.mco152.bm.persist.DiskRun.BlockSequence.SEQUENTIAL;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests my library of Tests
 */
public class CommandTests {
    NonSwingUI worker = new NonSwingUI();
    ReadWriteExecutor readWriteExecutor;
    int blockSize = 2048;
    DiskRun.BlockSequence blockSequence = SEQUENTIAL;
    int numOfMarks = 25;
    int numOfBlocks = 128;

    @BeforeEach
    public void setUp() {
        setupDefaultAsPerProperties();
        readWriteExecutor = new ReadWriteExecutor();
    }

    @Test
    void testRead() {
        readWriteExecutor.setBenchmark(new Read(numOfMarks, numOfBlocks, blockSize, blockSequence));
        assertTrue(readWriteExecutor.runBenchMark(worker));
    }

    @Test
    void testWrite() {
        readWriteExecutor.setBenchmark(new Write(numOfMarks, numOfBlocks, blockSize, blockSequence));
        assertTrue(readWriteExecutor.runBenchMark(worker));
    }

    void setupDefaultAsPerProperties() {
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

        App.dataDir = new File(App.locationDir.getAbsolutePath() + File.separator + App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        } else {
            App.dataDir.mkdirs(); // create data dir if not already present
        }
    }

}
