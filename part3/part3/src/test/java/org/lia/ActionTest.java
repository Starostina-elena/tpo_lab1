package org.lia;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActionTest {

    @Test
    public void testActionCreation() {
        org.lia.Models.Action action = new org.lia.Models.Action("Test Action", true);
        assertEquals("Test Action", action.getActionText());
        assertTrue(action.isCompatibleWithQuestion());
    }
}
