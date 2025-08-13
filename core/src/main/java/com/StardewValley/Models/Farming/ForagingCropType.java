package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public enum ForagingCropType implements Serializable {
    commonMushroom(null, 40, 38, "COMMON_MUSHROOM"),
    daffodil(Season.spring, 30, 0, "DAFFODIL"),
    dandelion(Season.spring, 40, 25, "DANDELION"),
    leek(Season.spring, 60, 40, "LEEK"),
    morel(Season.spring, 150, 20, "MOREL"),
    salmonBerry(Season.spring, 5, 25, "SALMONBERRY"),
    springOnion(Season.spring, 8, 13, "SPRING_ONION"),
    wildHorseradish(Season.spring, 50, 13, "WILD_HORSERADISH"),

    fiddleHeadFern(Season.summer, 90, 25, "FIDDLEHEAD_FERN"),
    grape(Season.summer, 80, 38, "GRAPE"),
    redMushroom(Season.summer, 75, -50, "RED_MUSHROOM"),
    spiceBerry(Season.summer, 80, 25, "SPICE_BERRY"),
    sweetPea(Season.summer, 50, 0, "SWEET_PEA"),

    blackberry(Season.fall, 25, 25, "BLACKBERRY"),
    chanterelle(Season.fall, 160, 75, "CHANTERELLE"),
    hazelnut(Season.fall, 40, 38, "HAZELNUT"),
    purpleMushroom(Season.fall, 90, 30, "PURPLE_MUSHROOM"),
    wildPlum(Season.fall, 80, 25, "WILD_PLUM"),

    crocus(Season.winter, 60, 0, "CROCUS"),
    crystalFruit(Season.winter, 150, 63, "CRYSTAL_FRUIT"),
    holly(Season.winter, 80, -37, "HOLLY"),
    snowYam(Season.winter, 100, 30, "SNOW_YAM"),
    winterRoot(Season.winter, 70, 25, "WINTER_ROOT");

    private final Season season;
    private final int baseValue;
    private final int energy;
    private transient Texture texture;
    private final String textureKey;

    ForagingCropType(Season season, int baseValue, int energy, String textureKey) {
        this.season = season;
        this.baseValue = baseValue;
        this.energy = energy;
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

    public Season getSeason() {
        return season;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public int getEnergy() {
        return energy;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public static ForagingCropType getRandomInstance() {
        return values()[ThreadLocalRandom.current().nextInt(values().length)];
    }

    public Texture getTexture() {
        return texture;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.texture = loadTexture(textureKey);
    }
}
