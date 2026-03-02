package org.lia;

public class Creature {

    private String name;
    private boolean solvingQuestionsStatus;

    public Creature(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void beHitten() {
        System.out.println("Ouch! " + name + " was hitten!");
    }

    public void setAndSolveQuestions() {
        if (solvingQuestionsStatus) {
            System.out.println(name + " is already solving questions.");
        } else {
            solvingQuestionsStatus = true;
            System.out.println(name + " starts solving questions.");
        }
    }

    public void stopSolvingQuestions() {
        if (solvingQuestionsStatus) {
            solvingQuestionsStatus = false;
            System.out.println(name + " stops solving questions.");
        } else {
            System.out.println(name + " is not solving questions yet.");
        }
    }
}
