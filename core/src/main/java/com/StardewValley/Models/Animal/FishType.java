package com.StardewValley.Models.Animal;

import com.StardewValley.Models.Enums.Season;

import java.io.Serializable;

public enum FishType implements Serializable {
    salmon("Salmon",75,Season.fall,false),
    sardine("Sardine",40,Season.fall,false),
    shad("Shad",60,Season.fall,false),
    blueDiscus("BlueDiscus",120,Season.fall,false),
    midnightCarp("MidnightCarp",150,Season.winter,false),
    squid("Squid",80,Season.winter,false),
    tuna("Tuna",100,Season.winter,false),
    perch("Perch",55,Season.winter,false),
    flounder("Flounder",100,Season.spring,false),
    lionFish("LionFish",100,Season.spring,false),
    herring("Herring",30,Season.spring,false),
    ghostFish("GhostFish",45,Season.spring,false),
    tilapia("Tilapia",75,Season.summer,false),
    dorado("Dorado",100,Season.summer,false),
    sunFish("SunFish",30,Season.summer,false),
    rainbowTrout("RainbowTrout",65,Season.summer,false),
    legend("Legend",5000,Season.spring,true),
    glacierFish("GlacierFish",1000,Season.winter,true),
    angler("Angler",900,Season.fall,true),
    crimsonFish("CrimsonFish",1500,Season.summer,true),
    ;

    private String name;
    private int basePrice;
    private Season season;
    private boolean isLegendary;

    FishType(String name, int basePrice, Season season, boolean isLegendary) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.isLegendary = isLegendary;
    }

    public static FishType getFishTypeByName(String name) {
        for (FishType value : FishType.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public Season getSeason() {
        return season;
    }

    public boolean isLegendary() {
        return isLegendary;
    }
}
