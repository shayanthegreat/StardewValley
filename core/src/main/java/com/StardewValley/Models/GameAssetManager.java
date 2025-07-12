package com.StardewValley.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager = new GameAssetManager();
    public static GameAssetManager getInstance() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }
    public final Texture CLOCK_ALL = new Texture("Clock/Clock_All.png");
    public final Skin SKIN = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
    public final BitmapFont MAIN_FONT = new BitmapFont();
    public final TextureRegion CLOCK_MAIN = new TextureRegion(CLOCK_ALL, 0, 0, 72, 59);
    public final TextureRegion CLOCK_ARROW = new TextureRegion(CLOCK_ALL, 72, 0, 8, 18);
    public final TextureRegion[] ClOCK_MANNERS = new TextureRegion[12];
    public final Texture BLOCK = new Texture("Inventory/button-.png");
    public final Texture INVENTORY_TAB = new Texture("Inventory/Inventory_Tab.png");
    public final Texture CRAFTING_TAB = new Texture("Inventory/crafting_tab.png");
    public final Texture COOKING_TAB = new Texture("Inventory/cooking_tab.png");
    public final Texture MAP_TAB = new Texture("Inventory/map_tab.png");
    public final Texture SKILL_TAB = new Texture("Inventory/skill_tab.png");
    public final Texture SOCIAL_TAB = new Texture("Inventory/socail_tab.png");
    public final Texture EXIT_BUTTON = new Texture("Inventory/exit.png");
    {
        for (int i = 0; i < 12; i++) {
            ClOCK_MANNERS[i] = new TextureRegion(CLOCK_ALL, 80 + i % 4 * 13, i / 4 * 9, 13, 9);
        }
    }
}
