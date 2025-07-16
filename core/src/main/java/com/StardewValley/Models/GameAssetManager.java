package com.StardewValley.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager ;
    private GameAssetManager() {}
    public static GameAssetManager getInstance() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }
    public final Texture CLOCK_ALL = new Texture("Clock/Clock_All.png");
    public final BitmapFont MAIN_FONT = new BitmapFont();
    public final TextureRegion CLOCK_MAIN = new TextureRegion(CLOCK_ALL, 0, 0, 72, 59);
    public final TextureRegion CLOCK_ARROW = new TextureRegion(CLOCK_ALL, 72, 0, 8, 18);
    public final TextureRegion[] ClOCK_MANNERS = new TextureRegion[12];
    {
        for (int i = 0; i < 12; i++) {
            ClOCK_MANNERS[i] = new TextureRegion(CLOCK_ALL, 80 + i % 4 * 13, i / 4 * 9, 13, 9);
        }
    }

    public Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

    public Texture GRASS_FLOORING = new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Flooring/Flooring_44.png");

    public Texture STONE_FLOORING = new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Flooring/Flooring_52.png");

    public Texture ABIGAIL = new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/sprites/Abigail.png");

    public Texture SAM = new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/sprites/Sam.png");

    public Texture Harvey = new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/sprites/Harvey.png");

    public Skin getSkin() {
        return skin;
    }
}
