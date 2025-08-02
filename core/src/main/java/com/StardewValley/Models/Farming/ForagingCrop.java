package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public class ForagingCrop extends Item implements Serializable {
    private ForagingCropType type;

    public ForagingCrop(ForagingCropType type) {
        super(type.getName(), 1, true, type.getTexture());
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

    @Override
    public boolean isEdible() {
        return false;
    }

    public Texture getTexture() {
        return type.getTexture();
    }
}
