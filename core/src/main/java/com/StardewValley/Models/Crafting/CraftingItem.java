package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

public class CraftingItem extends Item implements Serializable {

    private CraftingRecipe recipe;

    public CraftingItem(CraftingRecipe recipe) {
        super(recipe.getProductName(),1,false);
        this.recipe = recipe;
        texture = recipe.getTexture();
        sprite = new Sprite(texture);
    }

    private void readObject(ObjectInputStream ois)
        throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        texture = recipe.getTexture();
        sprite = new Sprite(texture);
    }

    public CraftingRecipe getRecipe() { return recipe; }

    @Override
    public void use() {
//        TODO: check for different crafting recipes
    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(CraftingItem.this);
    }

    @Override
    public void delete() {

    }

    @Override
    public boolean isEdible() {
        return false;
    }

    @Override
    public String getName() {
        return recipe.getProductName();
    }



    @Override
    public int getPrice() {
        return recipe.getPrice();
    }
}
