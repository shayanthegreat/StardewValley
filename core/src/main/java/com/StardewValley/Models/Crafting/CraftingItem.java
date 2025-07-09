package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;

import java.io.Serializable;
import java.util.HashMap;

public class CraftingItem extends Item implements Serializable {

    private  CraftingRecipe recipe;

    public CraftingItem(CraftingRecipe recipe) {
        super(recipe.getProductName(),1,false);
        this.recipe = recipe;
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
    public String getName() {
        return recipe.getProductName();
    }



    @Override
    public int getPrice() {
        return recipe.getPrice();
    }
}
