package org.lia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinatesTest {

    @ParameterizedTest(name = "distance between ({0},{1}) and ({2},{3}) => {4}")
    @CsvFileSource(resources = "/coordinates.csv")
    public void testDistanceTo(double x1, double y1, double x2, double y2, double expected) {
        org.lia.Models.Coordinates coord1 = new org.lia.Models.Coordinates(x1, y1);
        org.lia.Models.Coordinates coord2 = new org.lia.Models.Coordinates(x2, y2);
        double distance = coord1.computeDistanceTo(coord2);
        assertEquals(expected, distance, 1e-6);
    }
}
