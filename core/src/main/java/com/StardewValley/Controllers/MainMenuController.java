package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Views.LoginMenu;
import com.StardewValley.Views.MenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;

public class MainMenuController implements Controller {
    private static MainMenuController instance;
    private MainMenuController() {}
    public static MainMenuController getInstance() {
        if (instance == null) {
            instance = new MainMenuController();
        }
        return instance;
    }

    @Override
    public void showError(String error, Stage stage, Skin skin) {

    }

    public void logout(){
        App.getInstance().setCurrentUser(null);
        ClientController.getInstance().informLogout();
        File file = new File("username.txt");
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {}
        }
        changeMenu(new LoginMenu(GameAssetManager.getInstance().getSkin()));
    }

    public void changeMenu(MenuView menu) {
        Main.getInstance().getScreen().dispose();
        Main.getInstance().setScreen(menu);
    }

    public void exit(){
        Gdx.app.exit();
    }

}
