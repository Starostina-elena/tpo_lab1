package org.lia;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureTest {

    @Test
    void testCreatureCreation() {
        Creature creature = new Creature("TestCreature");
        assertEquals("TestCreature", creature.getName());
    }

    @Test
    void testSetName() {
        Creature creature = new Creature("OldName");
        creature.setName("NewName");
        assertEquals("NewName", creature.getName());
    }

    @Test
    void testBeHitten() {
        Creature creature = new Creature("TestCreature");
        creature.beHitten();
    }
    
    @Test
    void testSetAndSolveQuestions() {
        Creature creature = new Creature("TestCreature");
        creature.setAndSolveQuestions();
        creature.setAndSolveQuestions();
    }

    @Test
    void testStopSolvingQuestions() {
        Creature creature = new Creature("TestCreature");
        creature.stopSolvingQuestions();
        creature.setAndSolveQuestions();
        creature.stopSolvingQuestions();
    }
}
