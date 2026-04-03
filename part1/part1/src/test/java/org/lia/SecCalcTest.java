package org.lia;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SecCalcTest {
    public static final double pi = 3.1415926535;

    @ParameterizedTest(name = "sec({0})")
    @DisplayName("Check main values (input, expected)")
    @CsvFileSource(resources = "/sec_values.csv")
    void checkMainValues(double x, double expected) {
        assertAll(
                () -> {
                    double actual = org.lia.SecCalc.Calc(x, 1e-16, 200);

                    if (Double.isNaN(expected)) {
                        assertTrue(Double.isNaN(actual), "expected NaN for x=" + x + " but was " + actual);
                        return;
                    }
                    if (Double.isInfinite(expected)) {
                        assertTrue(Double.isInfinite(actual), "expected Infinite for x=" + x + " but was " + actual);
                        assertEquals(Math.signum(expected), Math.signum(actual));
                        return;
                    }

                    if (Double.isFinite(expected) && Double.isFinite(actual)) {
                        double relErr = Math.abs(expected - actual) / Math.max(1.0, Math.abs(expected));
                        assertTrue(relErr < 1e-6, "error too large: " + relErr + " for x=" + x + " expected=" + expected + " actual=" + actual);
                    } else {
                        assertEquals(expected, actual);
                    }
                }
        );
    }
}
