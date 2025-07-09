package com.StardewValley.Models.Crafting;

import java.io.Serializable;

public enum MaterialType implements Serializable {
    sugar("sugar"),
    cheese("cheese"),
    fiber("fiber"),
    oil("oil"),
    wheatFlour("wheatFlour"),
    coffee("coffee"),
    bouquet("bouquet"),
    weddingRing("weddingRing"),
    wood("wood"),
    stone("stone"),
    ironOre("iron Ore"),
    ironBar("iron Bar"),
    copperOre("copper Ore"),
    copperBar("copper Bar"),
    goldOre("gold Ore"),
    goldBar("gold Bar"),
    iridiumOre("iridium Ore"),
    iridiumBar("iridium Bar"),
    coal("coal"),
    pickle("pickle"),
    wine("wine"),
    hardWood("hard wood"),
    diamond("diamond"),
    quartz("quartz");

    private final String name;

    MaterialType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MaterialType getMaterialTypeByName(String name) {
        for (MaterialType mt : MaterialType.values()) {
            if (mt.name.equalsIgnoreCase(name)) {
                return mt;
            }
        }
        return null;
    }
}
