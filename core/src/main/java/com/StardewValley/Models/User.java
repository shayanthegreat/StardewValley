package com.StardewValley.Models;

import com.StardewValley.Models.Enums.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private final String gender;
    private Question question;
    private String answer;
    private Game currentGame;
    private ArrayList<Integer> gamesMoney;

    public User(String username, String password, String nickname, String email, String gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.gamesMoney = new ArrayList<>();
        this.question = Question.first;
        this.answer = "none";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAnswerCorrect(String answer) {
        return this.answer.equals(answer);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void addGamesMoney(int money) {
        this.gamesMoney.add(money);
    }

    public int getGamesCount() {
        return gamesMoney.size();
    }

    public int getMaxMoney() {
        int maxMoney = 0;
        for (Integer money : gamesMoney) {
            maxMoney = maxMoney < money ? maxMoney : money;
        }
        return maxMoney;
    }

}
