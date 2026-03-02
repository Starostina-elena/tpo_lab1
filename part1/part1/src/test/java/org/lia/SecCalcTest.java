package org.lia;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SecCalcTest {
    public static final double pi = 3.1415926535;

    @ParameterizedTest(name = "sec({0})")
    @DisplayName("Check main values")
    @ValueSource(doubles = {
            0,
            0.5,
            1,
            -0.5,
            -1,
            pi,
            -pi,
            pi / 2,
            pi / 4,
            -pi / 2,
            -pi / 4,
            Double.NaN,
            Double.POSITIVE_INFINITY,
            Double.MIN_VALUE,
    })
    void checkMainValues(double x) {
        assertAll(
                () -> {
                    double expected = 1 / Math.cos(x);
                    double actual = org.lia.SecCalc.Calc(x, 1e-16, 200);

                    if (Double.isNaN(expected) && Double.isNaN(actual)) {
                        assertTrue(true);
                        return;
                    }
                    if (Double.isInfinite(expected) && Double.isInfinite(actual)) {
                        assertEquals(Math.signum(expected), Math.signum(actual));
                        return;
                    }

                    if (Double.isFinite(expected) && Double.isFinite(actual)) {
                        double relErr = Math.abs(expected - actual) / Math.max(1.0, Math.abs(expected));
                        assertTrue(relErr < 1e-6, "error too large: " + relErr + " for x=" + x);
                    } else {
                        assertEquals(expected, actual);
                    }
                }
        );
    }
}
