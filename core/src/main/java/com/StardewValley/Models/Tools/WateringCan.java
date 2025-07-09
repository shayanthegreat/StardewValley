package com.StardewValley.Models.Tools;

import java.io.Serializable;
import java.util.ArrayList;

public class WateringCan extends Tool implements Serializable {
    private static ArrayList<Integer> maxCapacity = new ArrayList<>();
    private int amount;

    static {
        maxCapacity.add(40);
        maxCapacity.add(55);
        maxCapacity.add(70);
        maxCapacity.add(85);
        maxCapacity.add(100);
    }

    public WateringCan(ToolType toolType) {
        super(toolType);
        this.amount = 40;
    }

    public void fill() {
        this.amount = maxCapacity.get(level);
    }

    public int getCurrentCapacity() {
        return amount;
    }

    public void decreaseCapacity(int amount) {
        this.amount -= amount;
    }

    @Override
    public LevelInfo getLevelInfo() {
        return super.getLevelInfo();
    }

    public void upgradeLevel() {
        super.upgradeLevel();
    }
    @Override
    public void use() {
        super.use();
    }

    @Override
    public String getName() {
        return "WateringCan";
    }
}
