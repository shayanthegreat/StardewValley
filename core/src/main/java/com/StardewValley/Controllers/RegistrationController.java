package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.Menu;
import com.StardewValley.Models.Enums.Question;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Interactions.Commands.RegistrationCommand;
import com.StardewValley.Models.Interactions.Messages.RegistrationMessage;
import com.StardewValley.Models.User;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Views.LobbyView;
import com.StardewValley.Views.MainMenu;
import com.StardewValley.Views.MenuView;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RegistrationController implements UserInfoController , Controller{

    private static RegistrationController registrationController;
    private RegistrationController() {}
    public static RegistrationController getInstance() {
        if (registrationController == null) {
            registrationController = new RegistrationController();
        }
        return registrationController;
    }

    public RegistrationMessage Register(String username, String password, String passwordConfirm, String nickName, String email, String gender) {
        App app = App.getInstance();

        if (isUsernameTaken(username)) {
            return new RegistrationMessage(null, "this username is already taken\nthis is a recommended username: " + username + "-9");
        }

        if (!isUsernameValid(username)) {
            return new RegistrationMessage(null, "Username format is invalid");
        }

        if (!isEmailValid(email)) {
            return new RegistrationMessage(null, "Email format is invalid");
        }

        if(!gender.equals("male") && !gender.equals("female")) {
            return new RegistrationMessage(null, "Gender is invalid (male / female)");
        }
        if (password.equals("random")) {
            String randomPassword = generateRandomPassword();
            String codedRandomPassword = sha256(randomPassword);
            User newUser = new User(username, codedRandomPassword, nickName, email, gender);
            ClientController.getInstance().addUserToDB(newUser);
            return new RegistrationMessage(RegistrationCommand.askForPassword, "This is a random password: " + randomPassword + "\ndo you want to set this as your password?");
        }

        if(password.length() < 5) {
            return new RegistrationMessage(null, "Password must be at least 5 characters");
        }

        if (!isPasswordValid(password)) {
            return new RegistrationMessage(null, "Password format is invalid");
        }

        if (!doesPasswordHaveUpperCase(password)) {
            return new RegistrationMessage(null, "Password doesn't have uppercase letters'");
        }

        if (!doesPasswordHaveLowerCase(password)) {
            return new RegistrationMessage(null, "Password doesn't have lowercase letters'");
        }

        if (!doesPasswordHaveNumber(password)) {
            return new RegistrationMessage(null, "Password doesn't have digits");
        }

        if (!doesPasswordHaveSpecialChar(password)) {
            return new RegistrationMessage(null, "Password doesn't have special characters");
        }

        if (!isPasswordConfirmCorrect(password, passwordConfirm)) {
            return new RegistrationMessage(null, "Your Password confirmation is incorrect");
        }
//        String codedPassword = sha256(password);
        User newUser = new User(username, password, nickName, email, gender);
        ClientController.getInstance().addUserToDB(newUser);
        return new RegistrationMessage(RegistrationCommand.askQuestion, "You are successfully registered");

    }

    private String generateRandomPassword() {
        Random randomLength = new Random();
        int length = randomLength.nextInt(10) + 8;
        String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        String DIGITS = "0123456789";
        String SPECIALS = "?><,\"';:\\/|][}{+=)(*&^%$#!";
        String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIALS;
        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();


        passwordChars.add(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        passwordChars.add(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SPECIALS.charAt(random.nextInt(SPECIALS.length())));

        for (int i = passwordChars.size(); i < length; i++) {
            passwordChars.add(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }
        Collections.shuffle(passwordChars);
        StringBuilder ret = new StringBuilder();
        for(Character c : passwordChars) {
            ret.append(c);
        }
        return ret.toString();
    }

    public RegistrationMessage acceptRandomPassword(boolean accept) {
        if (accept) {
            return new RegistrationMessage(null, "You are successfully registered");
        } else {
            App app = App.getInstance();
            ClientController.getInstance().removeLastUser();
            return new RegistrationMessage(RegistrationCommand.askForPassword, "Ok you can try again");
        }
    }

   public void changeMenu(MenuView menu){
        Main.getInstance().getScreen().dispose();
        Main.getInstance().setScreen(menu);
   }

    public RegistrationMessage pickQuestion(int id, String answer) {
        App app = App.getInstance();
        Question question = Question.getQuestion(id);
        ClientController.getInstance().getLastUser().setQuestion(question);
        ClientController.getInstance().getLastUser().setAnswer(answer);
        return new RegistrationMessage(null, "Your question and answer successfully saved");
    }

    @Override
    public void showError(String message, Stage stage, Skin skin) {
        final Dialog dialog = new Dialog("!!!", skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dialog.hide();
            }
        }, 5);
    }

    public RegistrationMessage forgetPassword(String username) {
        if (!isUsernameTaken(username)) {
            return new RegistrationMessage(null, "Wrong Username");
        }
        App app = App.getInstance();
//        TODO: look at DB
        return new RegistrationMessage(RegistrationCommand.answerQuestion, ClientController.getInstance().getUserFromDB(username).getQuestion().toString().substring(3));
    }

    public RegistrationMessage checkAnswer(String username, String answer) {
        App app = App.getInstance();
        User user = ClientController.getInstance().getUserFromDB(username);
        if (user.getAnswer().equals(answer)) {
            return new RegistrationMessage(null, user.getPassword());
        }
        return new RegistrationMessage(null, "Wrong Answer");
    }

    public RegistrationMessage login(String username, String password, boolean stayLoggedIn) {

        App app = App.getInstance();
        User user = ClientController.getInstance().getUserFromDB(username);
        if (!isUsernameTaken(username)) {
            return new RegistrationMessage(null, "Wrong Username");
        }
        String codedPassword = sha256(password);
        if (!user.getPassword().equals(password)) {
            System.out.println("hello");
            return new RegistrationMessage(null, "Wrong Password");
        }
        if (stayLoggedIn) {
            app.setStayLoggedIn(true);
        } else {
            app.setStayLoggedIn(false);
        }

        app.setCurrentUser(user);
        Main.getInstance().getScreen().dispose();
        Main.getInstance().setScreen(new LobbyView());
        ClientController.getInstance().informLogin(username);
        return new RegistrationMessage(null, "You logged in successfully! you are now in main menu");
    }


}
