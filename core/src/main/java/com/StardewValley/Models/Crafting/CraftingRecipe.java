package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Tools.BackPack;
import com.StardewValley.Models.Tools.ToolType;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum CraftingRecipe implements Recipe, Serializable {
    cherryBomb("Cherry Bomb",new HashMap<>(){{
        put(new Material(MaterialType.copperOre),4);
        put(new Material(MaterialType.coal),1);
    }} , 50, GameAssetManager.getInstance().CHERRY_BOMB),
    bomb("Bomb",new HashMap<>(){{
        put(new Material(MaterialType.ironOre),4);
        put(new Material(MaterialType.coal),1);
    }} , 50, GameAssetManager.getInstance().BOMB),
    megaBomb("Mega Bomb", new HashMap<>(){{
        put(new Material(MaterialType.goldOre),4);
        put(new Material(MaterialType.coal),1);
    }}, 50, GameAssetManager.getInstance().MEGA_BOMB),
    sprinkler("Sprinkler", new HashMap<>(){{
        put(new Material(MaterialType.ironBar),2);
    }}, 0, GameAssetManager.getInstance().SPRINKLER),
    qualitySprinkler("Quality Sprinkler",new HashMap<>(){{
        put(new Material(MaterialType.ironBar),1);
        put(new Material(MaterialType.goldBar),1);
    }} , 0, GameAssetManager.getInstance().QUALITY_SPRINKLER),
    iridiumSprinkler("Iridium Sprinkler",new HashMap<>(){{
        put(new Material(MaterialType.iridiumBar),1);
        put(new Material(MaterialType.goldBar),1);
    }} , 0, GameAssetManager.getInstance().IRIDIUM_SPRINKLER),
    charcoalKlin("Charcoal Klin",new HashMap<>(){{
        put(new Material(MaterialType.wood),20);
        put(new Material(MaterialType.copperBar),2);
    }},0, GameAssetManager.getInstance().CHARCOAL_KILN),
    furance("Furance",new HashMap<>(){{
        put(new Material(MaterialType.copperOre),20);
        put(new Material(MaterialType.stone),25);
    }},0, GameAssetManager.getInstance().FURNACE),
    scarecrow("Scarecrow",new HashMap<>(){{
        put(new Material(MaterialType.wood),50);
        put(new Material(MaterialType.coal),1);
        put(new Material(MaterialType.fiber),20);
    }},0, GameAssetManager.getInstance().SCARECROW),
    deluxScarecrow("Delux Scarecrow",new HashMap<>(){{
        put(new Material(MaterialType.wood),50);
        put(new Material(MaterialType.coal),1);
        put(new Material(MaterialType.fiber),20);
        put(new Material(MaterialType.iridiumOre),1);
    }},0, GameAssetManager.getInstance().DELUXE_SCARECROW),
    beeHouse("Bee House",new HashMap<>(){{
        put(new Material(MaterialType.wood),40);
        put(new Material(MaterialType.coal),8);
        put(new Material(MaterialType.ironBar),1);
    }},0, GameAssetManager.getInstance().BEE_HOUSE),
    cheesePress("Cheese Press",new HashMap<>(){{
        put(new Material(MaterialType.wood),45);
        put(new Material(MaterialType.stone),45);
    }},0, GameAssetManager.getInstance().CHEESE_PRESS),
    keg("Keg",new HashMap<>(){{
        put(new Material(MaterialType.wood),30);
        put(new Material(MaterialType.ironBar),2);
    }},0, GameAssetManager.getInstance().KEG),
    loom("Loom",new HashMap<>(){{
        put(new Material(MaterialType.wood),60);
        put(new Material(MaterialType.fiber),30);
    }},0, GameAssetManager.getInstance().LOOM),
    mayonnaiseMachine("Mayonnaise Machine",new HashMap<>(){{
        put(new Material(MaterialType.wood),15);
        put(new Material(MaterialType.ironBar),15);
        put(new Material(MaterialType.ironBar),1);
    }},0, GameAssetManager.getInstance().MAYONNAISE_MACHINE),
    oilMaker("Oil Maker",new HashMap<>(){{
        put(new Material(MaterialType.wood),100);
        put(new Material(MaterialType.ironBar),1);
        put(new Material(MaterialType.goldBar),1);
    }},0, GameAssetManager.getInstance().OIL_MAKER),
    preservesJar("Preserves Jar",new HashMap<>(){{
        put(new Material(MaterialType.wood),50);
        put(new Material(MaterialType.stone),40);
        put(new Material(MaterialType.coal),8);
    }},0, GameAssetManager.getInstance().PRESERVES_JAR),
    dehydrator("Dehydrator",new HashMap<>(){{
        put(new Material(MaterialType.wood),30);
        put(new Material(MaterialType.stone),20);
        put(new Material(MaterialType.fiber),30);
    }},0, GameAssetManager.getInstance().DEHYDRATOR),
    grassStarter("Grass Starter",new HashMap<>(){{
        put(new Material(MaterialType.wood),1);
        put(new Material(MaterialType.fiber),1);
    }},0, GameAssetManager.getInstance().GRASS_STARTER),
    fishSmoker("Fish Smoker",new HashMap<>(){{
        put(new Material(MaterialType.wood),50);
        put(new Material(MaterialType.ironBar),3);
        put(new Material(MaterialType.coal),10);
    }},0, GameAssetManager.getInstance().FISH_SMOKER),
    mysticTreeSeed("Mystic Tree Seed",new HashMap<>(),0, GameAssetManager.getInstance().MYSTIC_TREE_SEED);
    ;

    private String productName;
    private HashMap<Item, Integer> ingredients;
    private int Price;
    private Texture texture;
    CraftingRecipe(String productName, HashMap<Item, Integer> ingredients, int Price, Texture texture) {
        this.productName = productName;
        this.ingredients = ingredients;
        this.Price = Price;
        this.texture = texture;
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

    public Texture getTexture() {
        return texture;
    }

    public boolean canCook(BackPack backPack) {
        for (Map.Entry<Item, Integer> itemIntegerEntry : this.getIngredients().entrySet()) {
            Item item = itemIntegerEntry.getKey();
            Integer quantity = itemIntegerEntry.getValue();
            if(backPack.getItemCount(item) < quantity) {
                return false;
            }
        }
        return true;
    }

    public void consume(BackPack backPack) {
        for (Map.Entry<Item, Integer> itemIntegerEntry : this.getIngredients().entrySet()) {
            Item item = itemIntegerEntry.getKey();
            Integer quantity = itemIntegerEntry.getValue();
            backPack.removeItem(item, quantity);
        }
    }
}
