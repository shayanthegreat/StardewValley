package com.StardewValley.Controllers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface Controller {
    public void showError(String error, Stage stage, Skin skin);
}
