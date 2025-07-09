package com.StardewValley.Models.Enums;

import java.io.Serializable;

public enum Question implements Serializable {
    first("1- What was the name of your first teacher?"),
    second("2- What was the name of your childhood pet?"),
    third("3- What was the name of your elementary school?"),
    fourth("4- In which city were you born?"),
    fifth("5- What was the first movie you saw in a cinema?");

    private final String question;
    private final int id;

    Question(String question) {
        this.question = question;
        this.id = this.ordinal() + 1;
    }

    public static Question getQuestion(int id) {
        for(Question question : values()) {
            if(question.id == id)
                return question;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.question;
    }

    public String getQuestion() {
        return question;
    }

    public int getId() {
        return id;
    }
}
