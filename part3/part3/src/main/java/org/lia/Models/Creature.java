package org.lia.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Creature {

    private String name;

    private ArrayList<Action> actions;
    private ArrayList<Question> questions;

    private Coordinates coordinates;
    int hp;

    public Creature(String name, Coordinates coordinates) {
        this.name = name;
        actions = new ArrayList<>();
        questions = new ArrayList<>();
        this.coordinates = coordinates;
        hp = 100;
    }

    public boolean AddAction(Action action) {
        if (checkDead()) {
            return false;
        }
        actions.add(action);
        if (!action.isCompatibleWithQuestion()) {
            System.out.println("Creature " + name + " terminates solving questions");
            questions = new ArrayList<>();
            return false;
        }
        return true;
    }

    public boolean AddQuestion(Question question) {
        if (checkDead()) {
            return false;
        }
        for (Action action : actions) {
            if (!action.isCompatibleWithQuestion()) {
                System.out.println("Creature " + name + " is busy with task " + action.getActionText() + " and cannot solve questions");
                return false;
            }
        }
        questions.add(question);
        return true;
    }

    public boolean hitOther(Creature other) {
        if (checkDead()) {
            return false;
        }
        double distance = this.coordinates.computeDistanceTo(other.getCoordinates());
        if (distance < 5) {
            other.setHp(other.getHp() - 10);
            System.out.println(this.name + " hits " + other.getName() + " for 10 damage!");
            return true;
        } else {
            System.out.println(this.name + " is too far away to hit " + other.getName());
            return false;
        }
    }

    private boolean checkDead() {
        if (hp <= 0) {
            System.out.println(name + " has died.");
            questions = new ArrayList<>();
            actions = new ArrayList<>();
            return true;
        }
        return false;
    }
}
