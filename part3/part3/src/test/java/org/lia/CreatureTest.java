package org.lia;

import org.junit.jupiter.api.Test;
import org.lia.Models.Coordinates;
import org.lia.Models.Creature;
import org.lia.Models.Question;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureTest {

    @Test
    void testCreatureCreation() {
        Creature creature = new Creature("TestCreature", new Coordinates(10, 20));
        assertEquals("TestCreature", creature.getName());
    }

    @Test
    void testSetName() {
        Creature creature = new Creature("OldName", new Coordinates(10, 20));
        creature.setName("NewName");
        assertEquals("NewName", creature.getName());
    }

    @Test
    void testBeHitten() {
        Creature creature = new Creature("Creature 1", new Coordinates(10, 20));
        Creature other = new Creature("Creature 2", new Coordinates(11, 21));
        assertTrue(other.hitOther(creature));
    }

    @Test
    void testBeHittenTooFar() {
        Creature creature = new Creature("Creature 1", new Coordinates(10, 20));
        Creature other = new Creature("Creature 2", new Coordinates(100, 200));
        assertFalse(other.hitOther(creature));
    }

    @Test
    void testBeHittenDead() {
        Creature creature = new Creature("Creature 1", new Coordinates(10, 20));
        Creature other = new Creature("Creature 2", new Coordinates(9, 19));
        for (int i = 0; i < 10; i++) {
            assertTrue(other.hitOther(creature));
        }
        assertFalse(creature.hitOther(other));
    }

    @Test
    void testSetAndSolveQuestions() {
        Creature creature = new Creature("creature", new Coordinates(10, 20));
        Question question1 = new Question("Question 1", "answer 1");
        Question question2 = new Question("Question 2", "answer 2");
        assertTrue(creature.AddQuestion(question1));
        assertTrue(creature.AddQuestion(question2));
        question1.setAnswered(true);
        assertTrue(creature.getQuestions().get(0).isAnswered());
        assertFalse(creature.getQuestions().get(1).isAnswered());
    }

    @Test
    void testTasks() {
        Creature creature = new Creature("TestCreature", new Coordinates(10, 20));
        creature.AddAction(new org.lia.Models.Action("Task 1", false));
        creature.AddAction(new org.lia.Models.Action("Task 2", true));
        assertEquals(2, creature.getActions().size());
    }

    @Test
    void testTasksIncompatibleWithQuestions() {
        Creature creature = new Creature("TestCreature", new Coordinates(10, 20));
        creature.AddAction(new org.lia.Models.Action("Task 1", false));
        assertFalse(creature.AddQuestion(new Question("Question 1", "Answer 1")));
    }

    @Test
    void testTasksCompatibleWithQuestions() {
        Creature creature = new Creature("TestCreature", new Coordinates(10, 20));
        creature.AddAction(new org.lia.Models.Action("Task 1", true));
        assertTrue(creature.AddQuestion(new Question("Question 1", "Answer 1")));
    }

    @Test
    void testTasksTerminateQuestions() {
        Creature creature = new Creature("TestCreature", new Coordinates(10, 20));
        creature.AddQuestion(new Question("Question 1", "Answer 1"));
        creature.AddAction(new org.lia.Models.Action("Task 1", false));
        assertEquals(0, creature.getQuestions().size());
    }

    @Test
    void testDeadCreature() {
        Creature creature = new Creature("TestCreature", new Coordinates(10, 20));
        Creature other = new Creature("OtherCreature", new Coordinates(10, 20));
        for (int i = 0; i < 10; i++) {
            assertTrue(other.hitOther(creature));
        }
        assertFalse(creature.AddQuestion(new Question("Question 1", "Answer 1")));
        assertFalse(creature.AddAction(new org.lia.Models.Action("Task 1", true)));
    }
}
