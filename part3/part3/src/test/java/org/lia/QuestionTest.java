package org.lia;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionTest {

    @Test
    public void testQuestionCreation() {
        LocalDateTime startTime = LocalDateTime.now();
        org.lia.Models.Question question = new org.lia.Models.Question("What is the capital of France?", "Paris");
        assertEquals("What is the capital of France?", question.getQuestionText());
        assertEquals("Paris", question.getAnswerText());
        Duration diff = Duration.between(startTime, question.getStartTime()).abs();
        assertTrue(diff.compareTo(Duration.ofSeconds(1)) < 0);
    }
}
