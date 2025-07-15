package com.StardewValley.Models.Animal;

import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public enum FishType implements Serializable {
    salmon("Salmon",75,Season.fall,false, GameAssetManager.getInstance().SALMON),
    sardine("Sardine",40,Season.fall,false, GameAssetManager.getInstance().SARDINE),
    shad("Shad",60,Season.fall,false, GameAssetManager.getInstance().SHAD),
    blueDiscus("BlueDiscus",120,Season.fall,false, GameAssetManager.getInstance().BLUE_DISCUS),
    midnightCarp("MidnightCarp",150,Season.winter,false, GameAssetManager.getInstance().MIDNIGHT_CARP),
    squid("Squid",80,Season.winter,false, GameAssetManager.getInstance().SQUID),
    tuna("Tuna",100,Season.winter,false, GameAssetManager.getInstance().TUNA),
    perch("Perch",55,Season.winter,false, GameAssetManager.getInstance().PERCH),
    flounder("Flounder",100,Season.spring,false, GameAssetManager.getInstance().FLOUNDER),
    lionFish("LionFish",100,Season.spring,false, GameAssetManager.getInstance().LIONFISH),
    herring("Herring",30,Season.spring,false, GameAssetManager.getInstance().HERRING),
    ghostFish("GhostFish",45,Season.spring,false, GameAssetManager.getInstance().GHOSTFISH),
    tilapia("Tilapia",75,Season.summer,false, GameAssetManager.getInstance().TILAPIA),
    dorado("Dorado",100,Season.summer,false, GameAssetManager.getInstance().DORADO),
    sunFish("SunFish",30,Season.summer,false, GameAssetManager.getInstance().SUNFISH),
    rainbowTrout("RainbowTrout",65,Season.summer,false, GameAssetManager.getInstance().RAINBOW_TROUT),
    legend("Legend",5000,Season.spring,true, GameAssetManager.getInstance().LEGEND),
    glacierFish("GlacierFish",1000,Season.winter,true, GameAssetManager.getInstance().GLACIERFISH),
    angler("Angler",900,Season.fall,true, GameAssetManager.getInstance().ANGLER),
    crimsonFish("CrimsonFish",1500,Season.summer,true, GameAssetManager.getInstance().CRIMSONFISH),
    ;

    private String name;
    private int basePrice;
    private Season season;
    private boolean isLegendary;
    private Texture texture;
    FishType(String name, int basePrice, Season season, boolean isLegendary, Texture texture) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.isLegendary = isLegendary;
        this.texture = texture;
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

    public Texture getTexture() {
        return texture;
    }
}
