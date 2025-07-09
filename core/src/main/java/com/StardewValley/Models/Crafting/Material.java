package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;

import java.io.Serializable;

public class Material extends Item implements Serializable {
    private MaterialType type;

    public Material(MaterialType type) {
        super(type.getName(), 0, false);
        this.type = type;
    }

    public MaterialType getType() {
        return type;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public void use() {

    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(Material.this);
    }

    @Override
    public void delete() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return type.getName();
    }
}
