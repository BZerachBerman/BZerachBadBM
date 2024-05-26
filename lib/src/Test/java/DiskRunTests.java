import edu.touro.mco152.bm.persist.DiskRun;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DiskRunTests {
    DiskRun diskRun;

    @BeforeEach
    void setUp() {
        diskRun = new DiskRun();
    }

    /**
     * This test will pass most of the time, as it is only considered inaccurate if the seconds are off.
     * This is admittedly a flawed way to do this, but it works.
     */
    @Test
    void getStartTimeString_Normal_ReturnsString() {
        String x = diskRun.getStartTimeString();
        DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, MMM d HH:mm:ss");
        assertEquals(DATE_FORMAT.format(new Date()), x);
    }

    @Test
    void setAndGetBlockSize_Normal_ReturnsInt() {
        int x = 16;
        diskRun.setBlockSize(x);
        assertEquals(x, diskRun.getBlockSize());
    }

    /**
     * Testing execution Time from CORRECT as well as Performance
     */
    @Test
    void setBlockSize_SpeedTest_GoesFast() {
        long start = System.nanoTime();
        diskRun.setBlockSize(10);
        long end = System.nanoTime();
        long runTime = end - start;
        assertTrue(runTime < 1000000);
    }

    /**
     * Testing for proper input validation of the setter methods
     */
    @Test
    void setBlockSize_ForceError_HandlesWell() {
        assertThrows(Exception.class, () -> diskRun.setBlockSize(-10));
    }
}
