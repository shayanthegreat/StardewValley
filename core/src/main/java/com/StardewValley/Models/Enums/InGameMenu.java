package com.StardewValley.Models.Enums;

import java.io.Serializable;

public enum InGameMenu implements Serializable {
    tradeMenu("trade"),
    houseMenu("house"),
    craftingMenu("crafting");

    private final String name;

    InGameMenu(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + " menu";
    }
}
