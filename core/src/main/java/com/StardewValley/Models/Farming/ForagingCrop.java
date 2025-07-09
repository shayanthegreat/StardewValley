package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;

import java.io.Serializable;

public class ForagingCrop extends Item implements Serializable {
    private ForagingCropType type;

    public ForagingCrop(ForagingCropType type) {
        super(type.getName(), 1, true);
        this.type = type;
    }

    @Override
    public int getPrice() {
        return type.getBaseValue();
    }

    @Override
    public void use() {

    }

    public void drop() {

    }

    @Override
    public void drop(Tile tile) {

    }

    @Override
    public void delete() {

    }
}
