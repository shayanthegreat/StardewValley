package com.StardewValley.Models.Farming;

import java.io.Serializable;

public class Fertilizer implements Serializable {
    private final String name;

    public Fertilizer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
