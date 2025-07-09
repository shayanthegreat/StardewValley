
package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Enums.Season;

import java.io.Serializable;
import java.util.ArrayList;

public enum PlantType implements Serializable {
    blueJazz("blue jazz", SeedType.jazz, CropType.blueJazz, new int[]{1, 2, 2, 2}, 7, -1, new Season[]{Season.spring}, false, false),
    carrot("carrot", SeedType.carrot, CropType.carrot, new int[]{1, 1, 1}, 3, -1, new Season[]{Season.spring}, false, false),
    cauliflower("cauliflower", SeedType.cauliflower, CropType.cauliflower, new int[]{1, 2, 4, 4, 1}, 12, -1, new Season[]{Season.spring}, true, false),
    coffeeBean("coffee bean", SeedType.coffeeBean, CropType.coffeeBean, new int[]{1, 2, 2, 3, 2}, 10, 2, new Season[]{Season.spring, Season.summer}, false, false),
    garlic("garlic", SeedType.garlic, CropType.garlic, new int[]{1, 1, 1, 1}, 4, -1, new Season[]{Season.spring}, false, false),
    greenBean("green bean", SeedType.beanStarter, CropType.greenBean, new int[]{1, 1, 1, 3, 4}, 10, 3, new Season[]{Season.spring}, false, false),
    kale("kale", SeedType.kale, CropType.kale, new int[]{1, 2, 2, 1}, 6, -1, new Season[]{Season.spring}, false, false),
    parsnip("parsnip", SeedType.parsnip, CropType.parsnip, new int[]{1, 1, 1, 1}, 4, -1, new Season[]{Season.spring}, false, false),
    potato("potato", SeedType.potato, CropType.potato, new int[]{1, 1, 1, 2, 1}, 6, -1, new Season[]{Season.spring}, false, false),
    rhubarb("rhubarb", SeedType.rhubarb, CropType.rhubarb, new int[]{2, 2, 2, 3, 4}, 13, -1, new Season[]{Season.spring}, false, false),
    strawberry("strawberry", SeedType.strawberry, CropType.strawberry, new int[]{1, 1, 2, 2, 2}, 8, 4, new Season[]{Season.spring}, false, false),
    tulip("tulip", SeedType.tulipBulb, CropType.tulip, new int[]{1, 1, 2, 2}, 6, -1, new Season[]{Season.spring}, false, false),
    unmilledRice("unmilled rice", SeedType.riceShoot, CropType.unmilledRice, new int[]{1, 2, 2, 3}, 8, -1, new Season[]{Season.spring}, false, false),
    blueberry("blueberry", SeedType.blueberry, CropType.blueberry, new int[]{1, 3, 3, 4, 2}, 13, 4, new Season[]{Season.summer}, false, false),
    corn("corn", SeedType.corn, CropType.corn, new int[]{2, 3, 3, 3, 3}, 14, 4, new Season[]{Season.summer, Season.fall}, false, false),
    hops("hops", SeedType.hopsStarter, CropType.hops, new int[]{1, 1, 2, 3, 4}, 11, 1, new Season[]{Season.summer}, false, false),
    hotPepper("hot pepper", SeedType.pepper, CropType.hotPepper, new int[]{1, 1, 1, 1, 1}, 5, 3, new Season[]{Season.summer}, false, false),
    melon("melon", SeedType.melon, CropType.melon, new int[]{1, 2, 3, 3, 3}, 12, -1, new Season[]{Season.summer}, true, false),
    poppy("poppy", SeedType.poppy, CropType.poppy, new int[]{1, 2, 2, 2}, 7, -1, new Season[]{Season.summer}, false, false),
    radish("radish", SeedType.radish, CropType.radish, new int[]{2, 1, 2, 1}, 6, -1, new Season[]{Season.summer}, false, false),
    redCabbage("red cabbage", SeedType.redCabbage, CropType.redCabbage, new int[]{2, 1, 2, 2, 2}, 9, -1, new Season[]{Season.summer}, false, false),
    starfruit("starfruit", SeedType.starfruit, CropType.starfruit, new int[]{2, 3, 2, 3, 3}, 13, -1, new Season[]{Season.summer}, false, false),
    summerSpangle("summer spangle", SeedType.spangle, CropType.summerSpangle, new int[]{1, 2, 3, 1}, 8, -1, new Season[]{Season.summer}, false, false),
    summerSquash("summer squash", SeedType.summerSquash, CropType.summerSquash, new int[]{1, 1, 1, 2, 1}, 6, 3, new Season[]{Season.summer}, false, false),
    sunflower("sunflower", SeedType.sunflower, CropType.sunflower, new int[]{1, 2, 3, 2}, 8, -1, new Season[]{Season.summer, Season.fall}, false, false),
    tomato("tomato", SeedType.tomato, CropType.tomato, new int[]{2, 2, 2, 2, 3}, 11, 4, new Season[]{Season.summer}, false, false),
    wheat("wheat", SeedType.wheat, CropType.wheat, new int[]{1, 1, 1, 1}, 4, -1, new Season[]{Season.summer, Season.fall}, false, false),
    amaranth("amaranth", SeedType.amaranth, CropType.amaranth, new int[]{1, 2, 2, 2}, 7, -1, new Season[]{Season.fall}, false, false),
    artichoke("artichoke", SeedType.artichoke, CropType.artichoke, new int[]{2, 2, 1, 2, 1}, 8, -1, new Season[]{Season.fall}, false, false),
    beet("beet", SeedType.beet, CropType.beet, new int[]{1, 1, 2, 2}, 6, -1, new Season[]{Season.fall}, false, false),
    bokChoy("bok choy", SeedType.bokChoy, CropType.bokChoy, new int[]{1, 1, 1, 1}, 4, -1, new Season[]{Season.fall}, false, false),
    broccoli("broccoli", SeedType.broccoli, CropType.broccoli, new int[]{2, 2, 2, 2}, 8, 4, new Season[]{Season.fall}, false, false),
    cranberries("cranberries", SeedType.cranberry, CropType.cranberries, new int[]{1, 2, 1, 1, 2}, 7, 5, new Season[]{Season.fall}, false, false),
    eggplant("eggplant", SeedType.eggplant, CropType.eggplant, new int[]{1, 1, 1, 1}, 5, 5, new Season[]{Season.fall}, false, false),
    fairyRose("fairy rose", SeedType.fairy, CropType.fairyRose, new int[]{1, 4, 4, 3}, 12, -1, new Season[]{Season.fall}, false, false),
    grape("grape", SeedType.grapeStarter, CropType.grape, new int[]{1, 1, 2, 3, 3}, 10, 3, new Season[]{Season.fall}, false, false),
    pumpkin("pumpkin", SeedType.pumpkin, CropType.pumpkin, new int[]{1, 2, 3, 4, 3}, 13, -1, new Season[]{Season.fall}, true, false),
    yam("yam", SeedType.yam, CropType.yam, new int[]{1, 3, 3, 3}, 10, -1, new Season[]{Season.fall}, false, false),
    sweetGemBerry("sweet gem berry", SeedType.rare, CropType.sweetGemBerry, new int[]{4, 4, 4}, 28, -1, new Season[]{Season.spring, Season.summer, Season.fall, Season.winter}, true, false),
    powdermelon("powdermelon", SeedType.powdermelon, CropType.powdermelon, new int[]{1, 2, 1, 2, 1}, 7, -1, new Season[]{Season.winter}, true, false),
    ancientFruit("ancient fruit", SeedType.ancient, CropType.ancientFruit, new int[]{2, 7, 7, 7, 5}, 28, 7, new Season[]{Season.spring, Season.summer, Season.fall}, false, false),
    apricotTree("apricot tree", SeedType.apricot, CropType.apricot, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.spring}, false, true),
    cherryTree("cherry tree", SeedType.cherry, CropType.cherry, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.spring}, false, true),
    bananaTree("banana tree", SeedType.banana, CropType.banana, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.summer}, false, true),
    mangoTree("mango tree", SeedType.mango, CropType.mango, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.summer}, false, true),
    orangeTree("orange tree", SeedType.orange, CropType.orange, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.summer}, false, true),
    peachTree("peach tree", SeedType.peach, CropType.peach, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.summer}, false, true),
    appleTree("apple tree", SeedType.apple, CropType.apple, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.fall}, false, true),
    pomegranateTree("pomegranate tree", SeedType.pomegranate, CropType.pomegranate, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.fall}, false, true),
    oakTree("oak tree", SeedType.acorn, CropType.oak, new int[]{7, 7, 7, 7}, 28, 7, new Season[]{Season.spring, Season.summer, Season.fall}, false, true),
    mapleTree("maple tree", SeedType.maple, CropType.maple, new int[]{7, 7, 7, 7}, 28, 9, new Season[]{Season.spring, Season.summer, Season.fall}, false, true),
    pineTree("pine tree", SeedType.pineCone, CropType.pine, new int[]{7, 7, 7, 7}, 28, 5, new Season[]{Season.spring, Season.summer, Season.fall}, false, true),
    mahoganyTree("mahogany tree", SeedType.mahogany, CropType.mahogany, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.spring, Season.summer, Season.fall}, false, true),
    mushroomTree("mushroom tree", SeedType.mushroomTree, CropType.mushroom, new int[]{7, 7, 7, 7}, 28, 1, new Season[]{Season.spring, Season.summer, Season.fall}, false, true),
    mysticTree("mystic tree", SeedType.mysticTree, CropType.mystic, new int[]{7, 7, 7, 7}, 28, 7, new Season[]{Season.spring, Season.summer, Season.fall}, false, true);
    private String name;
    private SeedType seed;
    private CropType crop;
    private ArrayList<Integer> stages;
    private int totalTime;
    private int reGrowth;
    private ArrayList<Season> seasonsAvailable;
    private boolean canBeComeGiant;
    private boolean isTree;

    // Constructor for initializing all values
    PlantType(String name, SeedType seed, CropType crop, int[] stages, int totalTime, int reGrowth, Season[] seasonsAvailable, boolean canBeComeGiant, boolean isTree) {
        this.name = name;
        this.seed = seed;
        this.crop = crop;
        this.stages = new ArrayList<>();
        for (int stage : stages) {
            this.stages.add(stage);
        }
        this.totalTime = totalTime;
        this.reGrowth = reGrowth;
        this.seasonsAvailable = new ArrayList<>();
        for (Season season : seasonsAvailable) {
            this.seasonsAvailable.add(season);
        }
        this.canBeComeGiant = canBeComeGiant;
        this.isTree = isTree;
    }

    // Getters
    public String getName() {
        return name;
    }

    public SeedType getSeed() {
        return seed;
    }

    public CropType getCrop() {
        return crop;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getReGrowth() {
        return reGrowth;
    }

    public ArrayList<Season> getSeasonsAvailable() {
        return seasonsAvailable;
    }

    public boolean canBecomeGiant() {
        return canBeComeGiant;
    }

    public boolean isTree() {
        return isTree;
    }

    public static PlantType getPlantTypeByName(String name) {
        for (PlantType type : PlantType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static PlantType getPlantTypeBySeed(SeedType seedType) {
        for (PlantType type : PlantType.values()) {
            if (type.getSeed().equals(seedType)) {
                return type;
            }
        }
        return null;
    }

    public boolean isCanBeComeGiant() {
        return canBeComeGiant;
    }
}

