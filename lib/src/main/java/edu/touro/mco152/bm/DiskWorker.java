package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;

/**
 * Run the disk benchmarking as a Swing-compliant thread (only one of these threads can run at
 * once.) Cooperates with Swing to provide and make use of interim and final progress and
 * information, which is also recorded as needed to the persistence store, and log.
 * <p>
 * Depends on static values that describe the benchmark to be done having been set in App and Gui classes.
 * The DiskRun class is used to keep track of and persist info about each benchmark at a higher level (a run),
 * while the DiskMark class described each iteration's result, which is displayed by the UI as the benchmark run
 * progresses.
 * <p>
 * This class only knows how to do 'read' or 'write' disk benchmarks. It is instantiated by the
 * startBenchmark() method.
 * <p>
 * To be Swing compatible, this class declares that its final return (when
 * startBenchmark() is finished) is of type Boolean.
 */

public class DiskWorker  {
    Worker worker;
    public DiskWorker(Worker worker) {
        this.worker = worker;
    }


    /**

     The startBenchmark method facilitates continuous execution of complex operations on a separate thread,
     preventing program slowdowns. Invoked by a Worker, startBenchmark manages the following tasks:
     Logging information about the ongoing benchmark.
     Writing messages to the MainFrame through the app class.
     Additionally, this method performs the following operations in sequence:
     Retrieves the user-selected number of blocks to execute, crucial for benchmark tracking. The number of blocks may vary
     based on whether the user opted for both read and write operations in a single benchmarking session. This count determines
     the total iterations required for computing data to generate the benchmark graph.
     Depending on the user's selection of read, write, or combined operations, subsequent stages vary:
     A loop iterates based on the chosen number of blocks, tracking benchmark durations per iteration. At the end of each iteration,
     the total time and write count are recorded and published using Worker's doPublish() method.
     A nested loop calculates the total number of units and bytes written within the current iteration, updating the progress
     via Worker's setTheProgress() method. This entire process repeats if the user selected both read and write operations.
     @return Boolean successfully ran
     */

    public Boolean runBenchmark() throws Exception{

        /*
          We 'got here' because: 1: End-user clicked 'Start' on the benchmark UI,
          which triggered the start-benchmark event associated with the App::startBenchmark()
          method.  2: startBenchmark() then instantiated a Worker, and called
          its execute() method, causing Swing to eventually
          call its doInBackground() method, which called DiskWorker's startBenchmark() method.
         */
        Logger.getLogger(App.class.getName()).log(Level.INFO, "*** New worker thread started ***");
        msg("Running readTest " + App.readTest + "   writeTest " + App.writeTest);
        msg("num files: " + App.numOfMarks + ", num blks: " + App.numOfBlocks
                + ", blk size (kb): " + App.blockSizeKb + ", blockSequence: " + App.blockSequence);

        /*
          init local vars that keep track of benchmarks and a large read/write buffer
         */
        int wUnitsTotal = App.writeTest ? numOfBlocks * numOfMarks : 0;
        int rUnitsTotal = App.readTest ? numOfBlocks * numOfMarks : 0;

        int blockSize = blockSizeKb * KILOBYTE;
        byte[] blockArr = new byte[blockSize];
        for (int b = 0; b < blockArr.length; b++) {
            if (b % 2 == 0) {
                blockArr[b] = (byte) 0xFF;
            }
        }

        Gui.updateLegend();  // init chart legend info

        if (App.autoReset) {
            App.resetTestData();
            Gui.resetTestData();
        }

        /*
          The GUI allows a Write, Read, or both types of BMs to be started. They are done serially.
         */
        if (App.writeTest) {
            ReadWriteExecutor invoker = new ReadWriteExecutor();
            invoker.setBenchmark(new Write(App.numOfMarks, App.numOfBlocks, App.blockSizeKb, App.blockSequence));
            invoker.runBenchMark(worker);
        }

        /*
          Most benchmarking systems will try to do some cleanup in between 2 benchmark operations to
          make it more 'fair'. For example a networking benchmark might close and re-open sockets,
          a memory benchmark might clear or invalidate the Op Systems TLB or other caches, etc.
         */

        // try renaming all files to clear catch
        if (App.readTest && App.writeTest && !worker.getIsCancelled()) {
            worker.showErrorMessage();
        }

        // Same as above, just for Read operations instead of Writes.
        if (App.readTest) {
            ReadWriteExecutor invoker = new ReadWriteExecutor();
            invoker.setBenchmark(new Read(App.numOfMarks, App.numOfBlocks, App.blockSizeKb, App.blockSequence));
            invoker.runBenchMark(worker);
        }

        App.nextMarkNumber += App.numOfMarks;
        return true;
    }
}