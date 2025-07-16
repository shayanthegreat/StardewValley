package com.StardewValley.Models.Enums;

public enum Texts {
    SignUp("Sign Up"),
    Login("Login"),
    LoginMenu("Login Menu"),
    Random("Random"),
    ;
    private String text;
    Texts(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
