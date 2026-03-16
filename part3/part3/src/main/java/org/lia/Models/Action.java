package org.lia.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Action {

    private String actionText;
    private boolean compatibleWithQuestion;

    public Action(String actionText, boolean compatibleWithQuestion) {
        this.actionText = actionText;
        this.compatibleWithQuestion = compatibleWithQuestion;
    }
}
