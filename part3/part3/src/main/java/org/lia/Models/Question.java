package org.lia.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Question {

    private String questionText;
    private String answerText;
    private LocalDateTime startTime;
    private boolean isAnswered;

    public Question(String questionText, String answerText) {
        this.questionText = questionText;
        this.answerText = answerText;
        this.startTime = LocalDateTime.now();
        this.isAnswered = false;
    }
}
