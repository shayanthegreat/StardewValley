package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public enum ForagingCropType implements Serializable {
    commonMushroom(null, 40, 38, GameAssetManager.getInstance().COMMON_MUSHROOM),
    daffodil(Season.spring, 30, 0, GameAssetManager.getInstance().DAFFODIL),
    dandelion(Season.spring, 40, 25, GameAssetManager.getInstance().DANDELION),
    leek(Season.spring, 60, 40, GameAssetManager.getInstance().LEEK),
    morel(Season.spring, 150, 20, GameAssetManager.getInstance().MOREL),
    salmonBerry(Season.spring, 5, 25, GameAssetManager.getInstance().SALMONBERRY),
    springOnion(Season.spring, 8, 13, GameAssetManager.getInstance().SPRING_ONION),
    wildHorseradish(Season.spring, 50, 13, GameAssetManager.getInstance().WILD_HORSERADISH),

    fiddleHeadFern(Season.summer, 90, 25, GameAssetManager.getInstance().FIDDLEHEAD_FERN),
    grape(Season.summer, 80, 38, GameAssetManager.getInstance().GRAPE),
    redMushroom(Season.summer, 75, -50, GameAssetManager.getInstance().RED_MUSHROOM),
    spiceBerry(Season.summer, 80, 25, GameAssetManager.getInstance().SPICE_BERRY),
    sweetPea(Season.summer, 50, 0, GameAssetManager.getInstance().SWEET_PEA),

    blackberry(Season.fall, 25, 25, GameAssetManager.getInstance().BLACKBERRY),
    chanterelle(Season.fall, 160, 75, GameAssetManager.getInstance().CHANTERELLE),
    hazelnut(Season.fall, 40, 38, GameAssetManager.getInstance().HAZELNUT),
    purpleMushroom(Season.fall, 90, 30, GameAssetManager.getInstance().PURPLE_MUSHROOM),
    wildPlum(Season.fall, 80, 25, GameAssetManager.getInstance().WILD_PLUM),

    crocus(Season.winter, 60, 0, GameAssetManager.getInstance().CROCUS),
    crystalFruit(Season.winter, 150, 63, GameAssetManager.getInstance().CRYSTAL_FRUIT),
    holly(Season.winter, 80, -37, GameAssetManager.getInstance().HOLLY),
    snowYam(Season.winter, 100, 30, GameAssetManager.getInstance().SNOW_YAM),
    winterRoot(Season.winter, 70, 25, GameAssetManager.getInstance().WINTER_ROOT);

    private final Season season;
    private final int baseValue;
    private final int energy;
    private final Texture texture;
    ForagingCropType(Season season, int baseValue, int energy, Texture texture) {
        this.season = season;
        this.baseValue = baseValue;
        this.energy = energy;
        this.texture = texture;
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
    public String getName(){
        return name().toLowerCase();
    }

    public static ForagingCropType getRandomInstance() {
        return values()[ThreadLocalRandom.current().nextInt(values().length)];
    }

    public Texture getTexture() {
        return texture;
    }
}
