package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Enums.Season;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public enum ForagingCropType implements Serializable {
    commonMushroom(null, 40, 38),
    daffodil(Season.spring, 30, 0),
    dandelion(Season.spring, 40, 25),
    leek(Season.spring, 60, 40),
    morel(Season.spring, 150, 20),
    salmonBerry(Season.spring, 5, 25),
    springOnion(Season.spring, 8, 13),
    wildHorseradish(Season.spring, 50, 13),

    fiddleHeadFern(Season.summer, 90, 25),
    grape(Season.summer, 80, 38),
    redMushroom(Season.summer, 75, -50),
    spiceBerry(Season.summer, 80, 25),
    sweetPea(Season.summer, 50, 0),

    blackberry(Season.fall, 25, 25),
    chanterelle(Season.fall, 160, 75),
    hazelnut(Season.fall, 40, 38),
    purpleMushroom(Season.fall, 90, 30),
    wildPlum(Season.fall, 80, 25),

    crocus(Season.winter, 60, 0),
    crystalFruit(Season.winter, 150, 63),
    holly(Season.winter, 80, -37),
    snowYam(Season.winter, 100, 30),
    winterRoot(Season.winter, 70, 25);

    private final Season season;
    private final int baseValue;
    private final int energy;

    ForagingCropType(Season season, int baseValue, int energy) {
        this.season = season;
        this.baseValue = baseValue;
        this.energy = energy;
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


}
