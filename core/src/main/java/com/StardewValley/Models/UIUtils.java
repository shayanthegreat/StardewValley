package com.StardewValley.Models;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class UIUtils {
    public static void showTopMessage(Stage stage, Skin skin, String message) {
        Label label = new Label(message, skin);
        label.setColor(1, 1, 1, 0); // Start invisible
        label.setFontScale(4.5f);
        // Center horizontally, set top position (e.g. 90% height)
        label.setPosition(
            (stage.getWidth() - label.getWidth()) / 2f,
            stage.getHeight() * 0.9f
        );

        // Add label to stage and animate
        stage.addActor(label);
        label.addAction(Actions.sequence(
            Actions.fadeIn(0.3f),
            Actions.delay(2f),
            Actions.fadeOut(0.5f),
            Actions.run(label::remove)
        ));
    }
}
