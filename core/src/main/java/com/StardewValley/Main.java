package com.StardewValley;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Views.FishingMiniGameView;
import com.StardewValley.Views.MainMenu;
import com.StardewValley.Views.ProfileMenu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    private SpriteBatch batch;
    private static Main main;
    private OrthographicCamera camera;

    private Main() {
    }
    public static Main getInstance() {
        if (main == null) {
            main = new Main();
        }
        return main;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenu(GameAssetManager.getInstance().getSkin()));//new GameMenu(GameAssetManager.getInstance().getSkin()));//new RegistrationMenu(GameAssetManager.getInstance().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
