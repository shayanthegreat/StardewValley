package com.StardewValley.Models.Farming;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum SeedType implements Serializable {
    jazz("jazz"),
    carrot("carrot"),
    cauliflower("cauliflower"),
    coffeeBean("coffee bean"),
    garlic("garlic"),
    beanStarter("bean starter"),
    kale("kale"),
    parsnip("parsnip"),
    potato("potato"),
    rhubarb("rhubarb"),
    strawberry("strawberry"),
    tulipBulb("tulip bulb"),
    riceShoot("rice shoot"),
    blueberry("blueberry"),
    corn("corn"),
    hopsStarter("hops starter"),
    pepper("pepper"),
    melon("melon"),
    poppy("poppy"),
    radish("radish"),
    redCabbage("red cabbage"),
    starfruit("starfruit"),
    spangle("spangle"),
    summerSquash("summer squash"),
    sunflower("sunflower"),
    tomato("tomato"),
    wheat("wheat"),
    amaranth("amaranth"),
    artichoke("artichoke"),
    beet("beet"),
    bokChoy("bok choy"),
    broccoli("broccoli"),
    cranberry("cranberry"),
    eggplant("eggplant"),
    fairy("fairy"),
    grapeStarter("grape starter"),
    pumpkin("pumpkin"),
    yam("yam"),
    rare("rare"),
    powdermelon("powdermelon"),
    ancient("ancient"),
    apricot("apricot"),
    cherry("cherry"),
    banana("banana"),
    mango("mango"),
    orange("orange"),
    peach("peach"),
    apple("apple"),
    pomegranate("pomegranate"),
    acorn("acorn"),
    maple("maple"),
    pineCone("pine cone"),
    mahogany("mahogany"),
    mushroomTree("mushroom tree"),
    mysticTree("mystic tree");

    private final String name;

    SeedType(String name) {
        this.name = name;
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
}
