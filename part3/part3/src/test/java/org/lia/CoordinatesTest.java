package org.lia;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordinatesTest {

    @Test
    public void testDistanceTo() {
        org.lia.Models.Coordinates coord1 = new org.lia.Models.Coordinates(0, 0);
        org.lia.Models.Coordinates coord2 = new org.lia.Models.Coordinates(3, 4);
        double distance = coord1.computeDistanceTo(coord2);
        assertEquals(5.0, distance);
    }
}
