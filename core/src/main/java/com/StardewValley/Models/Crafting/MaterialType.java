package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public enum MaterialType implements Serializable {
    sugar("sugar", GameAssetManager.getInstance().SUGAR),
    cheese("cheese", GameAssetManager.getInstance().CHEESE),
    fiber("fiber", GameAssetManager.getInstance().FIBER),
    oil("oil", GameAssetManager.getInstance().OIL),
    wheatFlour("wheatFlour", GameAssetManager.getInstance().WHEAT_FLOUR),
    coffee("coffee", GameAssetManager.getInstance().COFFEE),
    bouquet("bouquet", GameAssetManager.getInstance().BOUQUET),
    weddingRing("weddingRing", GameAssetManager.getInstance().WEDDING_RING),
    wood("wood", GameAssetManager.getInstance().WOOD),
    stone("stone", GameAssetManager.getInstance().STONE),
    ironOre("iron Ore", GameAssetManager.getInstance().IRON_ORE),
    ironBar("iron Bar", GameAssetManager.getInstance().IRON_BAR),
    copperOre("copper Ore", GameAssetManager.getInstance().COPPER_ORE),
    copperBar("copper Bar", GameAssetManager.getInstance().COPPER_BAR),
    goldOre("gold Ore", GameAssetManager.getInstance().GOLD_ORE),
    goldBar("gold Bar", GameAssetManager.getInstance().GOLD_BAR),
    iridiumOre("iridium Ore", GameAssetManager.getInstance().IRIDIUM_ORE),
    iridiumBar("iridium Bar", GameAssetManager.getInstance().IRIDIUM_BAR),
    coal("coal", GameAssetManager.getInstance().COAL),
    pickle("pickle", GameAssetManager.getInstance().PICKLES),
    wine("wine", GameAssetManager.getInstance().WINE),
    hardWood("hard wood", GameAssetManager.getInstance().HARDWOOD),
    diamond("diamond", GameAssetManager.getInstance().DIAMOND),
    quartz("quartz", GameAssetManager.getInstance().QUARTZ),;

    private final String name;
    private final Texture texture;
    MaterialType(String name, Texture texture) {
        this.name = name;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public static MaterialType getMaterialTypeByName(String name) {
        for (MaterialType mt : MaterialType.values()) {
            if (mt.name.equalsIgnoreCase(name)) {
                return mt;
            }
        }
        return null;
    }

    public Texture getTexture() {
        return texture;
    }
}
