package org.lia;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTest {

    @Test
    public void testQuestionCreation() {
        Instant fixedInstant = Instant.parse("2026-04-03T12:23:34Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneOffset.UTC);
        LocalDateTime expected = LocalDateTime.ofInstant(fixedInstant, ZoneOffset.UTC);

        org.lia.Models.Question question = new org.lia.Models.Question("What is the capital of France?", "Paris", fixedClock);
        assertEquals("What is the capital of France?", question.getQuestionText());
        assertEquals("Paris", question.getAnswerText());
        assertEquals(expected, question.getStartTime());
    }
}
