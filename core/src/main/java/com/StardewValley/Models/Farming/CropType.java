package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Game;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public enum CropType implements Serializable {
    blueJazz("blue jazz", SeedType.jazz, 50, 45, true, GameAssetManager.getInstance().BLUE_JAZZ),
    carrot("carrot", SeedType.carrot, 35, 75, true, GameAssetManager.getInstance().CARROT),
    cauliflower("cauliflower", SeedType.cauliflower, 175, 75, true, GameAssetManager.getInstance().CAULIFLOWER),
    coffeeBean("coffee bean", SeedType.coffeeBean, 15, 0, false, GameAssetManager.getInstance().COFFEE_BEAN),
    garlic("garlic", SeedType.garlic, 60, 20, true, GameAssetManager.getInstance().GARLIC),
    greenBean("green bean", SeedType.beanStarter, 40, 25, true, GameAssetManager.getInstance().GREEN_BEAN),
    kale("kale", SeedType.kale, 110, 50, true, GameAssetManager.getInstance().KALE),
    parsnip("parsnip", SeedType.parsnip, 35, 25, true, GameAssetManager.getInstance().PARSNIP),
    potato("potato", SeedType.potato, 80, 25, true, GameAssetManager.getInstance().POTATO),
    rhubarb("rhubarb", SeedType.rhubarb, 220, 0, false, GameAssetManager.getInstance().RHUBARB),
    strawberry("strawberry", SeedType.strawberry, 120, 50, true, GameAssetManager.getInstance().STRAWBERRY),
    tulip("tulip", SeedType.tulipBulb, 30, 45, true, GameAssetManager.getInstance().TULIP),
    unmilledRice("unmilled rice", SeedType.riceShoot, 30, 3, true, GameAssetManager.getInstance().UNMILLED_RICE),
    blueberry("blueberry", SeedType.blueberry, 50, 25, true, GameAssetManager.getInstance().BLUEBERRY),
    corn("corn", SeedType.corn, 50, 25, true, GameAssetManager.getInstance().CORN),
    hops("hops", SeedType.hopsStarter, 25, 45, true, GameAssetManager.getInstance().HOPS),
    hotPepper("hot pepper", SeedType.pepper, 40, 13, true, GameAssetManager.getInstance().HOT_PEPPER),
    melon("melon", SeedType.melon, 250, 113, true, GameAssetManager.getInstance().MELON),
    poppy("poppy", SeedType.poppy, 140, 45, true, GameAssetManager.getInstance().POPPY),
    radish("radish", SeedType.radish, 90, 45, true, GameAssetManager.getInstance().RADISH),
    redCabbage("red cabbage", SeedType.redCabbage, 260, 75, true, GameAssetManager.getInstance().RED_CABBAGE),
    starfruit("starfruit", SeedType.starfruit, 750, 125, true, GameAssetManager.getInstance().STARFRUIT),
    summerSpangle("summer spangle", SeedType.spangle, 90, 45, true, GameAssetManager.getInstance().SUMMER_SPANGLE),
    summerSquash("summer squash", SeedType.summerSquash, 45, 63, true, GameAssetManager.getInstance().SUMMER_SQUASH),
    sunflower("sunflower", SeedType.sunflower, 80, 45, true, GameAssetManager.getInstance().SUNFLOWER),
    tomato("tomato", SeedType.tomato, 60, 20, true, GameAssetManager.getInstance().TOMATO),
    wheat("wheat", SeedType.wheat, 25, 0, false, GameAssetManager.getInstance().WHEAT),
    amaranth("amaranth", SeedType.amaranth, 150, 50, true, GameAssetManager.getInstance().AMARANTH),
    artichoke("artichoke", SeedType.artichoke, 160, 30, true, GameAssetManager.getInstance().ARTICHOKE),
    beet("beet", SeedType.beet, 100, 30, true, GameAssetManager.getInstance().BEET),
    bokChoy("bok choy", SeedType.bokChoy, 80, 25, true, GameAssetManager.getInstance().BOK_CHOY),
    broccoli("broccoli", SeedType.broccoli, 70, 63, true, GameAssetManager.getInstance().BROCCOLI),
    cranberries("cranberries", SeedType.cranberry, 75, 38, true, GameAssetManager.getInstance().CRANBERRIES),
    eggplant("eggplant", SeedType.eggplant, 60, 20, true, GameAssetManager.getInstance().EGGPLANT),
    fairyRose("fairy rose", SeedType.fairy, 290, 45, true, GameAssetManager.getInstance().FAIRY_ROSE),
    grape("grape", SeedType.grapeStarter, 80, 38, true, GameAssetManager.getInstance().GRAPE),
    pumpkin("pumpkin", SeedType.pumpkin, 320, 0, false, GameAssetManager.getInstance().PUMPKIN),
    yam("yam", SeedType.yam, 160, 45, true, GameAssetManager.getInstance().YAM),
    sweetGemBerry("sweet gem berry", SeedType.rare, 3000, 0, false, GameAssetManager.getInstance().SWEET_GEM_BERRY),
    powdermelon("powdermelon", SeedType.powdermelon, 60, 63, true, GameAssetManager.getInstance().POWDERMELON),
    ancientFruit("ancient fruit", SeedType.ancient, 550, 0, false, GameAssetManager.getInstance().ANCIENT_FRUIT),
    apricot("apricot", SeedType.apricot, 59, 38, true, GameAssetManager.getInstance().APRICOT),
    cherry("cherry", SeedType.cherry, 80, 38, true, GameAssetManager.getInstance().CHERRY),
    banana("banana", SeedType.banana, 150, 75, true, GameAssetManager.getInstance().BANANA),
    mango("mango", SeedType.mango, 130, 100, true, GameAssetManager.getInstance().MANGO),
    orange("orange", SeedType.orange, 100, 38, true, GameAssetManager.getInstance().ORANGE),
    peach("peach", SeedType.peach, 140, 38, true, GameAssetManager.getInstance().PEACH),
    apple("apple", SeedType.apple, 100, 38, true, GameAssetManager.getInstance().APPLE),
    pomegranate("pomegranate", SeedType.pomegranate, 140, 38, true, GameAssetManager.getInstance().POMEGRANATE),
    oak("oak", SeedType.acorn, 150, 0, false, GameAssetManager.getInstance().OAK_RESIN),
    maple("maple", SeedType.maple, 200, 0, false, GameAssetManager.getInstance().MAPLE_STAGE_1),
    pine("pine", SeedType.pineCone, 100, 0, false, GameAssetManager.getInstance().PINE_CONE),
    mahogany("mahogany", SeedType.mahogany, 2, -2, true, GameAssetManager.getInstance().MAHOGANY_STAGE_1),
    mushroom("mushroom", SeedType.mushroomTree, 40, 38, true, GameAssetManager.getInstance().MUSHROOMTREE_STAGE_1),
    mystic("mystic", SeedType.mysticTree, 1000, 500, true, GameAssetManager.getInstance().MYSTIC_TREE_STAGE_1);

    private final String name;
    private final SeedType seed;
    private final int initialPrice;
    private final int energy;
    private final boolean isEdible;
    private final Texture texture;
    CropType(String name, SeedType seed, int initialPrice, int energy, boolean isEdible, Texture texture) {
        this.name = name;
        this.seed = seed;
        this.initialPrice = initialPrice;
        this.energy = energy;
        this.isEdible = isEdible;
        this.texture = texture;
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
}
