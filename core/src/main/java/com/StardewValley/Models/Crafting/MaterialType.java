package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public enum MaterialType implements Serializable {
    sugar("Sugar", "SUGAR"),
    cheese("Cheese", "CHEESE"),
    fiber("Fiber", "FIBER"),
    oil("Oil", "OIL"),
    wheatFlour("Wheat Flour", "WHEAT_FLOUR"),
    coffee("Coffee", "COFFEE"),
    bouquet("Bouquet", "BOUQUET"),
    weddingRing("Wedding Ring", "WEDDING_RING"),
    wood("Wood", "WOOD"),
    stone("Stone", "STONE"),
    ironOre("Iron Ore", "IRON_ORE"),
    ironBar("Iron Bar", "IRON_BAR"),
    copperOre("Copper Ore", "COPPER_ORE"),
    copperBar("Copper Bar", "COPPER_BAR"),
    goldOre("Gold Ore", "GOLD_ORE"),
    goldBar("Gold Bar", "GOLD_BAR"),
    iridiumOre("Iridium Ore", "IRIDIUM_ORE"),
    iridiumBar("Iridium Bar", "IRIDIUM_BAR"),
    coal("Coal", "COAL"),
    pickle("Pickle", "PICKLES"),
    wine("Wine", "WINE"),
    hardWood("Hard Wood", "HARDWOOD"),
    diamond("Diamond", "DIAMOND"),
    quartz("Quartz", "QUARTZ");

    private final String name;
    private transient Texture texture;
    private final String textureKey;

    MaterialType(String name, String textureKey) {
        this.name = name;
        this.textureKey = textureKey;
        this.texture = loadTexture(textureKey);
    }

    private Texture loadTexture(String key) {
        GameAssetManager assets = GameAssetManager.getInstance();
        try {
            return (Texture) assets.getClass().getField(key).get(assets);
        } catch (Exception e) {
            throw new RuntimeException("Could not load texture: " + key, e);
        }
    }

    public String getName() {
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public static MaterialType getMaterialTypeByName(String name) {
        for (MaterialType mt : MaterialType.values()) {
            if (mt.name.equalsIgnoreCase(name)) {
                return mt;
            }
        }
        return null;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.texture = loadTexture(textureKey);
    }
}
