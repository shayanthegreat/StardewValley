package com.StardewValley.Models.Animal;

import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public enum FishType implements Serializable {
    salmon("Salmon", 75, Season.fall, false, "SALMON"),
    sardine("Sardine", 40, Season.fall, false, "SARDINE"),
    shad("Shad", 60, Season.fall, false, "SHAD"),
    blueDiscus("BlueDiscus", 120, Season.fall, false, "BLUE_DISCUS"),
    midnightCarp("MidnightCarp", 150, Season.winter, false, "MIDNIGHT_CARP"),
    squid("Squid", 80, Season.winter, false, "SQUID"),
    tuna("Tuna", 100, Season.winter, false, "TUNA"),
    perch("Perch", 55, Season.winter, false, "PERCH"),
    flounder("Flounder", 100, Season.spring, false, "FLOUNDER"),
    lionFish("LionFish", 100, Season.spring, false, "LIONFISH"),
    herring("Herring", 30, Season.spring, false, "HERRING"),
    ghostFish("GhostFish", 45, Season.spring, false, "GHOSTFISH"),
    tilapia("Tilapia", 75, Season.summer, false, "TILAPIA"),
    dorado("Dorado", 100, Season.summer, false, "DORADO"),
    sunFish("SunFish", 30, Season.summer, false, "SUNFISH"),
    rainbowTrout("RainbowTrout", 65, Season.summer, false, "RAINBOW_TROUT"),
    legend("Legend", 5000, Season.spring, true, "LEGEND"),
    glacierFish("GlacierFish", 1000, Season.winter, true, "GLACIERFISH"),
    angler("Angler", 900, Season.fall, true, "ANGLER"),
    crimsonFish("CrimsonFish", 1500, Season.summer, true, "CRIMSONFISH");

    private String name;
    private int basePrice;
    private Season season;
    private boolean isLegendary;

    private transient Texture texture;
    private String textureKey;

    FishType(String name, int basePrice, Season season, boolean isLegendary, String textureKey) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.isLegendary = isLegendary;
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

    // Called automatically when deserializing
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.texture = loadTexture(textureKey);
    }
}
