package com.StardewValley.Models.Farming;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum SeedType implements Serializable {
    jazz("jazz", "JAZZ_SEEDS"),
    carrot("carrot", "CARROT_SEEDS"),
    cauliflower("cauliflower", "CAULIFLOWER_SEEDS"),
    coffeeBean("coffee bean", "COFFEE_BEAN"),
    garlic("garlic", "GARLIC_SEEDS"),
    beanStarter("bean starter", "BEAN_STARTER"),
    kale("kale", "KALE_SEEDS"),
    parsnip("parsnip", "PARSNIP_SEEDS"),
    potato("potato", "POTATO_SEEDS"),
    rhubarb("rhubarb", "RHUBARB_SEEDS"),
    strawberry("strawberry", "STRAWBERRY_SEEDS"),
    tulipBulb("tulip bulb", "TULIP_BULB"),
    riceShoot("rice shoot", "RICE_SHOOT"),
    blueberry("blueberry", "BLUEBERRY_SEEDS"),
    corn("corn", "CORN_SEEDS"),
    hopsStarter("hops starter", "HOPS_STARTER"),
    pepper("pepper", "PEPPER_SEEDS"),
    melon("melon", "MELON_SEEDS"),
    poppy("poppy", "POPPY_SEEDS"),
    radish("radish", "RADISH_SEEDS"),
    redCabbage("red cabbage", "RED_CABBAGE_SEEDS"),
    starfruit("starfruit", "STARFRUIT_SEEDS"),
    spangle("spangle", "SPANGLE_SEEDS"),
    summerSquash("summer squash", "SUMMER_SQUASH_SEEDS"),
    sunflower("sunflower", "SUNFLOWER_SEEDS"),
    tomato("tomato", "TOMATO_SEEDS"),
    wheat("wheat", "WHEAT_SEEDS"),
    amaranth("amaranth", "AMARANTH_SEEDS"),
    artichoke("artichoke", "ARTICHOKE_SEEDS"),
    beet("beet", "BEET_SEEDS"),
    bokChoy("bok choy", "BOK_CHOY_SEEDS"),
    broccoli("broccoli", "BROCCOLI_SEEDS"),
    cranberry("cranberry", "CRANBERRY_SEEDS"),
    eggplant("eggplant", "EGGPLANT_SEEDS"),
    fairy("fairy", "FAIRY_SEEDS"),
    grapeStarter("grape starter", "GRAPE_STARTER"),
    pumpkin("pumpkin", "PUMPKIN_SEEDS"),
    yam("yam", "YAM_SEEDS"),
    rare("rare", "RARE_SEED"),
    powdermelon("powdermelon", "POWDERMELON_SEEDS"),
    ancient("ancient", "ANCIENT_SEEDS"),
    apricot("apricot", "APRICOT_SAPLING"),
    cherry("cherry", "CHERRY_SAPLING"),
    banana("banana", "BANANA_SAPLING"),
    mango("mango", "MANGO_SAPLING"),
    orange("orange", "ORANGE_SAPLING"),
    peach("peach", "PEACH_SAPLING"),
    apple("apple", "APPLE_SAPLING"),
    pomegranate("pomegranate", "POMEGRANATE_SAPLING"),
    acorn("acorn", "ACORN"),
    maple("maple", "MAPLE_SEED"),
    pineCone("pine cone", "PINE_CONE"),
    mahogany("mahogany", "MAHOGANY_SEED"),
    mushroomTree("mushroom tree", "MUSHROOM_TREE_SEED"),
    mysticTree("mystic tree", "MYSTIC_TREE_SEED");

    private final String name;
    private transient Texture texture;
    private final String textureKey;

    SeedType(String name, String textureKey) {
        this.name = name;
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

    private static final Map<String, SeedType> NAME_LOOKUP = new HashMap<>();
    static {
        for (SeedType type : values()) {
            NAME_LOOKUP.put(type.name(), type);       // raw enum name
            NAME_LOOKUP.put(type.getName(), type);    // readable name
        }
    }

    public static SeedType getSeedTypeOrNull(String seedName) {
        return NAME_LOOKUP.getOrDefault(seedName, null);
    }

    public static SeedType getSeedTypeByName(String name) {
        return NAME_LOOKUP.get(name);
    }

    public Texture getTexture() {
        return texture;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.texture = loadTexture(textureKey);
    }
}
