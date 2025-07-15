package com.StardewValley.Models.Farming;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum SeedType implements Serializable {
    jazz("jazz", GameAssetManager.getInstance().JAZZ_SEEDS),
    carrot("carrot", GameAssetManager.getInstance().CARROT_SEEDS),
    cauliflower("cauliflower", GameAssetManager.getInstance().CAULIFLOWER_SEEDS),
    coffeeBean("coffee bean", GameAssetManager.getInstance().COFFEE_BEAN),
    garlic("garlic", GameAssetManager.getInstance().GARLIC_SEEDS),
    beanStarter("bean starter", GameAssetManager.getInstance().BEAN_STARTER),
    kale("kale", GameAssetManager.getInstance().KALE_SEEDS),
    parsnip("parsnip", GameAssetManager.getInstance().PARSNIP_SEEDS),
    potato("potato", GameAssetManager.getInstance().POTATO_SEEDS),
    rhubarb("rhubarb", GameAssetManager.getInstance().RHUBARB_SEEDS),
    strawberry("strawberry", GameAssetManager.getInstance().STRAWBERRY_SEEDS),
    tulipBulb("tulip bulb", GameAssetManager.getInstance().TULIP_BULB),
    riceShoot("rice shoot", GameAssetManager.getInstance().RICE_SHOOT),
    blueberry("blueberry", GameAssetManager.getInstance().BLUEBERRY_SEEDS),
    corn("corn", GameAssetManager.getInstance().CORN_SEEDS),
    hopsStarter("hops starter", GameAssetManager.getInstance().HOPS_STARTER),
    pepper("pepper", GameAssetManager.getInstance().PEPPER_SEEDS),
    melon("melon", GameAssetManager.getInstance().MELON_SEEDS),
    poppy("poppy", GameAssetManager.getInstance().POPPY_SEEDS),
    radish("radish", GameAssetManager.getInstance().RADISH_SEEDS),
    redCabbage("red cabbage", GameAssetManager.getInstance().RED_CABBAGE_SEEDS),
    starfruit("starfruit", GameAssetManager.getInstance().STARFRUIT_SEEDS),
    spangle("spangle", GameAssetManager.getInstance().SPANGLE_SEEDS),
    summerSquash("summer squash", GameAssetManager.getInstance().SUMMER_SQUASH_SEEDS),
    sunflower("sunflower", GameAssetManager.getInstance().SUNFLOWER_SEEDS),
    tomato("tomato", GameAssetManager.getInstance().TOMATO_SEEDS),
    wheat("wheat", GameAssetManager.getInstance().WHEAT_SEEDS),
    amaranth("amaranth", GameAssetManager.getInstance().AMARANTH_SEEDS),
    artichoke("artichoke", GameAssetManager.getInstance().ARTICHOKE_SEEDS),
    beet("beet", GameAssetManager.getInstance().BEET_SEEDS),
    bokChoy("bok choy", GameAssetManager.getInstance().BOK_CHOY_SEEDS),
    broccoli("broccoli", GameAssetManager.getInstance().BROCCOLI_SEEDS),
    cranberry("cranberry", GameAssetManager.getInstance().CRANBERRY_SEEDS),
    eggplant("eggplant", GameAssetManager.getInstance().EGGPLANT_SEEDS),
    fairy("fairy", GameAssetManager.getInstance().FAIRY_SEEDS),
    grapeStarter("grape starter", GameAssetManager.getInstance().GRAPE_STARTER),
    pumpkin("pumpkin", GameAssetManager.getInstance().PUMPKIN_SEEDS),
    yam("yam", GameAssetManager.getInstance().YAM_SEEDS),
    rare("rare", GameAssetManager.getInstance().RARE_SEED),
    powdermelon("powdermelon", GameAssetManager.getInstance().POWDERMELON_SEEDS),
    ancient("ancient", GameAssetManager.getInstance().ANCIENT_SEEDS),
    apricot("apricot", GameAssetManager.getInstance().APRICOT_SAPLING),
    cherry("cherry", GameAssetManager.getInstance().CHERRY_SAPLING),
    banana("banana", GameAssetManager.getInstance().BANANA_SAPLING),
    mango("mango", GameAssetManager.getInstance().MANGO_SAPLING),
    orange("orange", GameAssetManager.getInstance().ORANGE_SAPLING),
    peach("peach", GameAssetManager.getInstance().PEACH_SAPLING),
    apple("apple", GameAssetManager.getInstance().APPLE_SAPLING),
    pomegranate("pomegranate", GameAssetManager.getInstance().POMEGRANATE_SAPLING),
    acorn("acorn", GameAssetManager.getInstance().ACORN),
    maple("maple", GameAssetManager.getInstance().MAPLE_SEED),
    pineCone("pine cone", GameAssetManager.getInstance().PINE_CONE),
    mahogany("mahogany", GameAssetManager.getInstance().MAHOGANY_SEED),
    mushroomTree("mushroom tree", GameAssetManager.getInstance().MUSHROOM_TREE_SEED),
    mysticTree("mystic tree", GameAssetManager.getInstance().MYSTIC_TREE_SEED),;
    private final String name;
    private final Texture texture;
    SeedType(String name, Texture texture) {
        this.name = name;
        this.texture = texture;
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
}
