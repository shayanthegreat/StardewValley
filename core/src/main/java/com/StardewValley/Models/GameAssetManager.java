package com.StardewValley.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager = new GameAssetManager();
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
}
