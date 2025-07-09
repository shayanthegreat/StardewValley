package com.StardewValley.Models.Farming;

import java.io.Serializable;

public enum CropType implements Serializable {
    blueJazz("blue jazz", SeedType.jazz, 50, 45, true),
    carrot("carrot", SeedType.carrot, 35, 75, true),
    cauliflower("cauliflower", SeedType.cauliflower, 175, 75, true),
    coffeeBean("coffee bean", SeedType.coffeeBean, 15, 0, false),
    garlic("garlic", SeedType.garlic, 60, 20, true),
    greenBean("green bean", SeedType.beanStarter, 40, 25, true),
    kale("kale", SeedType.kale, 110, 50, true),
    parsnip("parsnip", SeedType.parsnip, 35, 25, true),
    potato("potato", SeedType.potato, 80, 25, true),
    rhubarb("rhubarb", SeedType.rhubarb, 220, 0, false),
    strawberry("strawberry", SeedType.strawberry, 120, 50, true),
    tulip("tulip", SeedType.tulipBulb, 30, 45, true),
    unmilledRice("unmilled rice", SeedType.riceShoot, 30, 3, true),
    blueberry("blueberry", SeedType.blueberry, 50, 25, true),
    corn("corn", SeedType.corn, 50, 25, true),
    hops("hops", SeedType.hopsStarter, 25, 45, true),
    hotPepper("hot pepper", SeedType.pepper, 40, 13, true),
    melon("melon", SeedType.melon, 250, 113, true),
    poppy("poppy", SeedType.poppy, 140, 45, true),
    radish("radish", SeedType.radish, 90, 45, true),
    redCabbage("red cabbage", SeedType.redCabbage, 260, 75, true),
    starfruit("starfruit", SeedType.starfruit, 750, 125, true),
    summerSpangle("summer spangle", SeedType.spangle, 90, 45, true),
    summerSquash("summer squash", SeedType.summerSquash, 45, 63, true),
    sunflower("sunflower", SeedType.sunflower, 80, 45, true),
    tomato("tomato", SeedType.tomato, 60, 20, true),
    wheat("wheat", SeedType.wheat, 25, 0, false),
    amaranth("amaranth", SeedType.amaranth, 150, 50, true),
    artichoke("artichoke", SeedType.artichoke, 160, 30, true),
    beet("beet", SeedType.beet, 100, 30, true),
    bokChoy("bok choy", SeedType.bokChoy, 80, 25, true),
    broccoli("broccoli", SeedType.broccoli, 70, 63, true),
    cranberries("cranberries", SeedType.cranberry, 75, 38, true),
    eggplant("eggplant", SeedType.eggplant, 60, 20, true),
    fairyRose("fairy rose", SeedType.fairy, 290, 45, true),
    grape("grape", SeedType.grapeStarter, 80, 38, true),
    pumpkin("pumpkin", SeedType.pumpkin, 320, 0, false),
    yam("yam", SeedType.yam, 160, 45, true),
    sweetGemBerry("sweet gem berry", SeedType.rare, 3000, 0, false),
    powdermelon("powdermelon", SeedType.powdermelon, 60, 63, true),
    ancientFruit("ancient fruit", SeedType.ancient, 550, 0, false),
    apricot("apricot", SeedType.apricot, 59, 38, true),
    cherry("cherry", SeedType.cherry, 80, 38, true),
    banana("banana", SeedType.banana, 150, 75, true),
    mango("mango", SeedType.mango, 130, 100, true),
    orange("orange", SeedType.orange, 100, 38, true),
    peach("peach", SeedType.peach, 140, 38, true),
    apple("apple", SeedType.apple, 100, 38, true),
    pomegranate("pomegranate", SeedType.pomegranate, 140, 38, true),
    oak("oak", SeedType.acorn, 150, 0, false),
    maple("maple", SeedType.maple, 200, 0, false),
    pine("pine", SeedType.pineCone, 100, 0, false),
    mahogany("mahogany", SeedType.mahogany, 2, -2, true),
    mushroom("mushroom", SeedType.mushroomTree, 40, 38, true),
    mystic("mystic", SeedType.mysticTree, 1000, 500, true);

    private final String name;
    private final SeedType seed;
    private final int initialPrice;
    private final int energy;
    private final boolean isEdible;

    CropType(String name, SeedType seed, int initialPrice, int energy, boolean isEdible) {
        this.name = name;
        this.seed = seed;
        this.initialPrice = initialPrice;
        this.energy = energy;
        this.isEdible = isEdible;
    }

    public String getName() {
        return name;
    }

    public SeedType getSeed() {
        return seed;
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
