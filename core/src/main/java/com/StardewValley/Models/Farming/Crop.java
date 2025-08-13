package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Time;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Crop extends Item implements Serializable {

    private CropType type;

    public Crop(CropType type) {
        super(type.getName(), 1, true);
        this.type = type;
        texture = type.getTexture();
        sprite = new Sprite(texture);
    }

    private void readObject(ObjectInputStream ois)
        throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        texture = type.getTexture();
        sprite = new Sprite(texture);
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
    public boolean isEdible() {
        return this.type.isEdible();
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

    public Texture getTexture() {
        return type.getTexture();
    }
}
