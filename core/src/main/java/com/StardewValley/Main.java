package com.StardewValley;

import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Time;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

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
        this.setScreen(new GameMenu(GameAssetManager.getInstance().getSkin()));//new RegistrationMenu(GameAssetManager.getInstance().getSkin()));
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
