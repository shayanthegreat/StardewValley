package com.StardewValley.Models.Animal;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;

import java.io.Serializable;

public class AnimalProduct extends Item implements Serializable {

    private AnimalProductType type ;
    private Quality quality;

    public AnimalProduct(AnimalProductType type) {
        super(type.getName(), 1, type.isEdible());
        this.type = type;
    }

    public AnimalProductType getType() {
        return type;
    }

    public Quality getQuality() {
        return quality;
    }

    @Override
    public void use() {

    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(AnimalProduct.this);
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
        return type.getPrice();
    }

    @Override
    public String toString() {
        return type.getName();
    }
}
