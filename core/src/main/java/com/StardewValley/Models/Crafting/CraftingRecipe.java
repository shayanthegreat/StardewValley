package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Tools.ToolType;

import java.io.Serializable;
import java.util.HashMap;

public enum CraftingRecipe implements Recipe, Serializable {
    cherryBomb("Cherry Bomb",new HashMap<>(){{
        put(new Material(MaterialType.copperOre),4);
        put(new Material(MaterialType.coal),1);
    }} , 50),
    bomb("Bomb",new HashMap<>(){{
        put(new Material(MaterialType.ironOre),4);
        put(new Material(MaterialType.coal),1);
    }} , 50),
    megaBomb("Mega Bomb", new HashMap<>(){{
        put(new Material(MaterialType.goldOre),4);
        put(new Material(MaterialType.coal),1);
    }}, 50),
    sprinkler("Sprinkler", new HashMap<>(){{
        put(new Material(MaterialType.ironBar),2);
    }}, 0),
    qualitySprinkler("Quality Sprinkler",new HashMap<>(){{
        put(new Material(MaterialType.ironBar),1);
        put(new Material(MaterialType.goldBar),1);
    }} , 0),
    iridiumSprinkler("Iridium Sprinkler",new HashMap<>(){{
        put(new Material(MaterialType.iridiumBar),1);
        put(new Material(MaterialType.goldBar),1);
    }} , 0),
    charcoalKlin("Charcoal Klin",new HashMap<>(){{
        put(new Material(MaterialType.wood),20);
        put(new Material(MaterialType.copperBar),2);
    }},0),
    furance("Furance",new HashMap<>(){{
        put(new Material(MaterialType.copperOre),20);
        put(new Material(MaterialType.stone),25);
    }},0),
    scarecrow("Scarecrow",new HashMap<>(){{
        put(new Material(MaterialType.wood),50);
        put(new Material(MaterialType.coal),1);
        put(new Material(MaterialType.fiber),20);
    }},0),
    deluxScarecrow("Delux Scarecrow",new HashMap<>(){{
        put(new Material(MaterialType.wood),50);
        put(new Material(MaterialType.coal),1);
        put(new Material(MaterialType.fiber),20);
        put(new Material(MaterialType.iridiumOre),1);
    }},0),
    beeHouse("Bee House",new HashMap<>(){{
        put(new Material(MaterialType.wood),40);
        put(new Material(MaterialType.coal),8);
        put(new Material(MaterialType.ironBar),1);
    }},0),
    cheesePress("Cheese Press",new HashMap<>(){{
        put(new Material(MaterialType.wood),45);
        put(new Material(MaterialType.stone),45);
    }},0),
    keg("Keg",new HashMap<>(){{
        put(new Material(MaterialType.wood),30);
        put(new Material(MaterialType.ironBar),2);
    }},0),
    loom("Loom",new HashMap<>(){{
        put(new Material(MaterialType.wood),60);
        put(new Material(MaterialType.fiber),30);
    }},0),
    mayonnaiseMachine("Mayonnaise Machine",new HashMap<>(){{
        put(new Material(MaterialType.wood),15);
        put(new Material(MaterialType.ironBar),15);
        put(new Material(MaterialType.ironBar),1);
    }},0),
    oilMaker("Oil Maker",new HashMap<>(){{
        put(new Material(MaterialType.wood),100);
        put(new Material(MaterialType.ironBar),1);
        put(new Material(MaterialType.goldBar),1);
    }},0),
    preservesJar("Preserves Jar",new HashMap<>(){{
        put(new Material(MaterialType.wood),50);
        put(new Material(MaterialType.stone),40);
        put(new Material(MaterialType.coal),8);
    }},0),
    dehydrator("Dehydrator",new HashMap<>(){{
        put(new Material(MaterialType.wood),30);
        put(new Material(MaterialType.stone),20);
        put(new Material(MaterialType.fiber),30);
    }},0),
    grassStarter("Grass Starter",new HashMap<>(){{
        put(new Material(MaterialType.wood),1);
        put(new Material(MaterialType.fiber),1);
    }},0),
    fishSmoker("Fish Smoker",new HashMap<>(){{
        put(new Material(MaterialType.wood),50);
        put(new Material(MaterialType.ironBar),3);
        put(new Material(MaterialType.coal),10);
    }},0),
    mysticTreeSeed("Mystic Tree Seed",new HashMap<>(),0);
    ;

    private String productName;
    private HashMap<Item, Integer> ingredients;
    private int Price;

    CraftingRecipe(String productName, HashMap<Item, Integer> ingredients, int Price) {
        this.productName = productName;
        this.ingredients = ingredients;
        this.Price = Price;
    }

    public static CraftingRecipe getCraftingRecipeByName(String name) {
        for (CraftingRecipe value : CraftingRecipe.values()) {
            if (value.productName.equals(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public HashMap<Item, Integer> getIngredients() {
        return ingredients;
    }

    @Override
    public int getPrice() {
        return Price;
    }
}
