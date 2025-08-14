package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Interactions.Messages.ProfileMessage;
import com.StardewValley.Models.User;
import com.StardewValley.Networking.Server.UserDAO;
import com.StardewValley.Views.MenuView;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

public class ProfileMenuController implements Controller , UserInfoController{
    private static ProfileMenuController instance;
    private ProfileMenuController() {}
    public static ProfileMenuController getInstance() {
        if (instance == null) {
            instance = new ProfileMenuController();
        }
        return instance;
    }


    @Override
    public void showError(String error, Stage stage, Skin skin) {
        final Dialog dialog = new Dialog("!!!", skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(error);
        dialog.button("OK");
        dialog.show(stage);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dialog.hide();
            }
        }, 5);
    }

    public ProfileMessage changeUsername(String newUsername) {
        App app = App.getInstance();
        if(newUsername != null && newUsername.length() < 4) {
            return new ProfileMessage(null,"new Username is too short");
        }
        if(isUsernameTaken(newUsername)) {
            return new ProfileMessage(null,"Username is already taken");
        }
        if(!isUsernameValid(newUsername)) {
            return new ProfileMessage(null,"Username is invalid");
        }
        if(app.getCurrentUser().getUsername().equals(newUsername)) {
            return new ProfileMessage(null,"New username must be different");
        }
        if(!UserDAO.updateUsername(app.getCurrentUser().getUsername(), newUsername)){
            return new ProfileMessage(null,"Error occurred");
        }

        app.getCurrentUser().setUsername(newUsername);
        return new ProfileMessage(null,"Username successfully changed");
    }

    public ProfileMessage changeNickname(String newNickname) {
        App app = App.getInstance();
        if(app.getCurrentUser().getNickname().equals(newNickname)) {
            return new ProfileMessage(null,"Your new nickname must be different");
        }
        if(!UserDAO.updateNickname(app.getCurrentUser().getUsername(), newNickname)){
            return new ProfileMessage(null,"Error occurred");
        }
        app.getCurrentUser().setNickname(newNickname);
        return new ProfileMessage(null,"Nickname successfully changed");
    }
    public ProfileMessage changePassword(String newPassword, String oldPassword) {
        App app = App.getInstance();
        if (!isPasswordValid(newPassword)) {
            return new ProfileMessage(null, "Password format is invalid");
        }

        if (!doesPasswordHaveUpperCase(newPassword)) {
            return new ProfileMessage(null, "Password doesn't have uppercase letters'");
        }

        if (!doesPasswordHaveLowerCase(newPassword)) {
            return new ProfileMessage(null, "Password doesn't have lowercase letters'");
        }

        if (!doesPasswordHaveNumber(newPassword)) {
            return new ProfileMessage(null, "Password doesn't have digits");
        }

        if (!doesPasswordHaveSpecialChar(newPassword)) {
            return new ProfileMessage(null, "Password doesn't have special characters");
        }

        if(newPassword.equals(oldPassword)) {
            return new ProfileMessage(null,"Your password must be different");
        }
        if(!UserDAO.updatePassword(app.getCurrentUser().getUsername(), newPassword)) {
            return new ProfileMessage(null,"Error occurred");
        }
        app.getCurrentUser().setPassword(newPassword);
        return new ProfileMessage(null,"Password successfully changed");
    }

    public ProfileMessage showUserInfo() {
        App app = App.getInstance();
        User currentUser = app.getCurrentUser();
        String ret = "";
        ret += "Username: " + currentUser.getUsername() + "\n";
        ret += "Nickname: " + currentUser.getNickname() + "\n";
        ret += "Max Money: " + currentUser.getMaxMoney() + "\n";
        ret += "Games Count: " + currentUser.getGamesCount();
        return new ProfileMessage(null, ret);
    }


    public ProfileMessage changeEmail(String email) {
        App app = App.getInstance();
        if(!isEmailValid(email)) {
            return new ProfileMessage(null,"Email is invalid");
        }
        if(app.getCurrentUser().getEmail().equals(email)) {
            return new ProfileMessage(null,"Your email must be different");
        }
        if(!UserDAO.updateEmail(app.getCurrentUser().getUsername(), email)) {
            return new ProfileMessage(null,"Error occurred");
        }
        app.getCurrentUser().setEmail(email);
        return new ProfileMessage(null,"Email successfully changed");
    }

    public void changeMenu(MenuView menu){
        Main.getInstance().getScreen().dispose();
        Main.getInstance().setScreen(menu);
    }


}
