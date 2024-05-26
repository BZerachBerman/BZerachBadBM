import edu.touro.mco152.bm.Util;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTests {

    /**
     * Tests Range and Conformance of CORRECT.
     * Also uses slight cross-checking by going directly to DF.format()
     *
     */
    @ParameterizedTest
    @ValueSource(doubles = { (double)Integer.MAX_VALUE, (double)Integer.MIN_VALUE, 50, 0, -20, Math.PI })
    void displayString_Normal_ConvertsToString(double x) {
        DecimalFormat DF = new DecimalFormat("###.##");
        String y = DF.format(x);
        assertEquals(Util.displayString(x), y);
    }

}
