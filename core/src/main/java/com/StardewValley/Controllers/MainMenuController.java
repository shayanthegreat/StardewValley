package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Views.MenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

    public void changeMenu(MenuView menu) {
        Main.getInstance().getScreen().dispose();
        Main.getInstance().setScreen(menu);
    }

    public void exit(){
        Gdx.app.exit();
    }

}
