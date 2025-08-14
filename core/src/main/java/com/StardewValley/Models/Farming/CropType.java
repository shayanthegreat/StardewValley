package com.StardewValley.Models.Farming;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public enum CropType implements Serializable {
    blueJazz("Blue Jazz", SeedType.jazz, 50, 45, true, "BLUE_JAZZ"),
    carrot("Carrot", SeedType.carrot, 35, 75, true, "CARROT"),
    cauliflower("Cauliflower", SeedType.cauliflower, 175, 75, true, "CAULIFLOWER"),
    coffeeBean("Coffee Bean", SeedType.coffeeBean, 15, 0, false, "COFFEE_BEAN"),
    garlic("Garlic", SeedType.garlic, 60, 20, true, "GARLIC"),
    greenBean("Green Bean", SeedType.beanStarter, 40, 25, true, "GREEN_BEAN"),
    kale("Kale", SeedType.kale, 110, 50, true, "KALE"),
    parsnip("Parsnip", SeedType.parsnip, 35, 25, true, "PARSNIP"),
    potato("Potato", SeedType.potato, 80, 25, true, "POTATO"),
    rhubarb("Rhubarb", SeedType.rhubarb, 220, 0, false, "RHUBARB"),
    strawberry("Strawberry", SeedType.strawberry, 120, 50, true, "STRAWBERRY"),
    tulip("Tulip", SeedType.tulipBulb, 30, 45, true, "TULIP"),
    unmilledRice("Unmilled Rice", SeedType.riceShoot, 30, 3, true, "UNMILLED_RICE"),
    blueberry("Blueberry", SeedType.blueberry, 50, 25, true, "BLUEBERRY"),
    corn("Corn", SeedType.corn, 50, 25, true, "CORN"),
    hops("Hops", SeedType.hopsStarter, 25, 45, true, "HOPS"),
    hotPepper("Hot Pepper", SeedType.pepper, 40, 13, true, "HOT_PEPPER"),
    melon("Melon", SeedType.melon, 250, 113, true, "MELON"),
    poppy("Poppy", SeedType.poppy, 140, 45, true, "POPPY"),
    radish("Radish", SeedType.radish, 90, 45, true, "RADISH"),
    redCabbage("Red Cabbage", SeedType.redCabbage, 260, 75, true, "RED_CABBAGE"),
    starfruit("Starfruit", SeedType.starfruit, 750, 125, true, "STARFRUIT"),
    summerSpangle("Summer Spangle", SeedType.spangle, 90, 45, true, "SUMMER_SPANGLE"),
    summerSquash("Summer Squash", SeedType.summerSquash, 45, 63, true, "SUMMER_SQUASH"),
    sunflower("Sunflower", SeedType.sunflower, 80, 45, true, "SUNFLOWER"),
    tomato("Tomato", SeedType.tomato, 60, 20, true, "TOMATO"),
    wheat("Wheat", SeedType.wheat, 25, 0, false, "WHEAT"),
    amaranth("Amaranth", SeedType.amaranth, 150, 50, true, "AMARANTH"),
    artichoke("Artichoke", SeedType.artichoke, 160, 30, true, "ARTICHOKE"),
    beet("Beet", SeedType.beet, 100, 30, true, "BEET"),
    bokChoy("Bok Choy", SeedType.bokChoy, 80, 25, true, "BOK_CHOY"),
    broccoli("Broccoli", SeedType.broccoli, 70, 63, true, "BROCCOLI"),
    cranberries("Cranberries", SeedType.cranberry, 75, 38, true, "CRANBERRIES"),
    eggplant("Eggplant", SeedType.eggplant, 60, 20, true, "EGGPLANT"),
    fairyRose("Fairy Rose", SeedType.fairy, 290, 45, true, "FAIRY_ROSE"),
    grape("Grape", SeedType.grapeStarter, 80, 38, true, "GRAPE"),
    pumpkin("Pumpkin", SeedType.pumpkin, 320, 0, false, "PUMPKIN"),
    yam("Yam", SeedType.yam, 160, 45, true, "YAM"),
    sweetGemBerry("Sweet Gem Berry", SeedType.rare, 3000, 0, false, "SWEET_GEM_BERRY"),
    powdermelon("Powdermelon", SeedType.powdermelon, 60, 63, true, "POWDERMELON"),
    ancientFruit("Ancient Fruit", SeedType.ancient, 550, 0, false, "ANCIENT_FRUIT"),
    apricot("Apricot", SeedType.apricot, 59, 38, true, "APRICOT"),
    cherry("Cherry", SeedType.cherry, 80, 38, true, "CHERRY"),
    banana("Banana", SeedType.banana, 150, 75, true, "BANANA"),
    mango("Mango", SeedType.mango, 130, 100, true, "MANGO"),
    orange("Orange", SeedType.orange, 100, 38, true, "ORANGE"),
    peach("Peach", SeedType.peach, 140, 38, true, "PEACH"),
    apple("Apple", SeedType.apple, 100, 38, true, "APPLE"),
    pomegranate("Pomegranate", SeedType.pomegranate, 140, 38, true, "POMEGRANATE"),
    oak("Oak", SeedType.acorn, 150, 0, false, "OAK_RESIN"),
    maple("Maple", SeedType.maple, 200, 0, false, "MAPLE_STAGE_1"),
    pine("Pine", SeedType.pineCone, 100, 0, false, "PINE_CONE"),
    mahogany("Mahogany", SeedType.mahogany, 2, -2, true, "MAHOGANY_STAGE_1"),
    mushroom("Mushroom", SeedType.mushroomTree, 40, 38, true, "MUSHROOMTREE_STAGE_1"),
    mystic("Mystic", SeedType.mysticTree, 1000, 500, true, "MYSTIC_TREE_STAGE_1");

    private final String name;
    private final SeedType seed;
    private final int initialPrice;
    private final int energy;
    private final boolean isEdible;
    private transient Texture texture;
    private final String textureKey;

    CropType(String name, SeedType seed, int initialPrice, int energy, boolean isEdible, String textureKey) {
        this.name = name;
        this.seed = seed;
        this.initialPrice = initialPrice;
        this.energy = energy;
        this.isEdible = isEdible;
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

    public SeedType getSeed() {
        return seed;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public static CropType getCropTypeByName(String name) {
        for (CropType cropType : values()) {
            if (cropType.getName().equalsIgnoreCase(name)) {
                return cropType;
            }
        }
        return null;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.texture = loadTexture(textureKey);
    }
}
