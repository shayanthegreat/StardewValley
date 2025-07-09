package com.StardewValley;

import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Time;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends Game {
    private SpriteBatch batch;
    private static Main main;

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
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        Time time = new Time();
        time.updateBatch(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
