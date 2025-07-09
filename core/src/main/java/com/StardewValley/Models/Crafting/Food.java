package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;

import java.io.Serializable;

public class Food extends Item implements Serializable {

    private CookingRecipe recipe;

    public Food(CookingRecipe recipe) {
        super(recipe.getProductName(), 1,true);
        this.recipe = recipe;
    }

    public CookingRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void use() {

    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(Food.this);
    }

    @Override
    public void delete() {

    }

    @Override
    public String getName() {
        return recipe.getProductName();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int getPrice() {
        return recipe.getPrice();
    }
}
