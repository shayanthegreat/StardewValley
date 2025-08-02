package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public class Seed extends Item implements Serializable {

    private SeedType type;

    public Seed(SeedType type) {
        super(type.getName(), 1, false, type.getTexture());
        this.type = type;
    }

    @Override
    public void use() {

    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(Seed.this);
    }

    @Override
    public void delete() {

    }

    @Override
    public boolean isEdible() {
        return false;
    }

    @Override
    public String getName(){
        return type.getName();
    }

    @Override
    public int getPrice() {
        return price;
    }

    public SeedType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.getName() + " Seeds";
    }

    public Texture getTexture() {
        return type.getTexture();
    }
}
