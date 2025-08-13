package com.StardewValley.Controllers;

import com.StardewValley.Models.App;
import com.StardewValley.Networking.Client.ClientController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface UserInfoController {

    public default boolean isUsernameTaken(String username) {
        App app = App.getInstance();
        if (ClientController.getInstance().getUserFromDB(username) != null) {
            return true;
        }
        return false;
    }

    public default boolean isUsernameValid(String username) {
        String regex = "^[a-zA-Z0-9-]+$";
        if (username.matches(regex)) {
            return true;
        }
        return false;
    }

    public default boolean isEmailValid(String email) {
        String regex = "^\\s*(?!.*\\.\\.)[a-zA-Z0-9](?:[a-zA-Z0-9._-]*[a-zA-Z0-9])?@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?(?:\\.[a-zA-Z]{2,})+\\s*$";
        if (email.matches(regex)) {
            return true;
        }
        return false;
    }

    public default boolean isPasswordValid(String password) {
        String regex = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]+$";
        if (password.matches(regex)) {
            return true;
        }
        return false;
    }

    public default boolean doesPasswordHaveSpecialChar(String password) {
        String specialChars = "!@#$%^&*()_+-=[]{},.<>?/\\|\"':;`~";
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (specialChars.indexOf(ch) != -1) {
                return true;
            }
        }
        return false;
    }

    public default boolean doesPasswordHaveUpperCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    public default boolean doesPasswordHaveLowerCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isLowerCase(ch)) {
                return true;
            }
        }
        return false;
    }

    public default boolean doesPasswordHaveNumber(String password) {
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isDigit(ch)) {
                return true;
            }
        }
        return false;
    }

    public default boolean isPasswordConfirmCorrect(String password, String passwordConfirm) {
        if (password.equals(passwordConfirm)) {
            return true;
        }
        return false;
    }

    public default String sha256(String input) {
        if (input == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            return input;
        }
    }
}
