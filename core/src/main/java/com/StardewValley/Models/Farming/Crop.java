package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Time;

import java.io.Serializable;

public class Crop extends Item implements Serializable {

    private CropType type;

    public Crop(CropType type) {
        super(type.getName(), 1, true);
        this.type = type;
    }

    public CropType getType(){
        return type;
    }

    @Override
    public void use() {

    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(Crop.this);
    }

    @Override
    public void delete() {

    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getPrice() {
        return type.getInitialPrice();
    }
    @Override
    public String toString() {
        return type.getName() + " Crop";
    }
}
