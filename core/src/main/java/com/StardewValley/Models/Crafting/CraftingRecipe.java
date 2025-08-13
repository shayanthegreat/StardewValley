package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Tools.BackPack;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum CraftingRecipe implements Recipe, Serializable {
    cherryBomb("Cherry Bomb", new HashMap<>() {{
        put(new Material(MaterialType.copperOre), 4);
        put(new Material(MaterialType.coal), 1);
    }}, 50, "CHERRY_BOMB"),
    bomb("Bomb", new HashMap<>() {{
        put(new Material(MaterialType.ironOre), 4);
        put(new Material(MaterialType.coal), 1);
    }}, 50, "BOMB"),
    megaBomb("Mega Bomb", new HashMap<>() {{
        put(new Material(MaterialType.goldOre), 4);
        put(new Material(MaterialType.coal), 1);
    }}, 50, "MEGA_BOMB"),
    sprinkler("Sprinkler", new HashMap<>() {{
        put(new Material(MaterialType.ironBar), 2);
    }}, 0, "SPRINKLER"),
    qualitySprinkler("Quality Sprinkler", new HashMap<>() {{
        put(new Material(MaterialType.ironBar), 1);
        put(new Material(MaterialType.goldBar), 1);
    }}, 0, "QUALITY_SPRINKLER"),
    iridiumSprinkler("Iridium Sprinkler", new HashMap<>() {{
        put(new Material(MaterialType.iridiumBar), 1);
        put(new Material(MaterialType.goldBar), 1);
    }}, 0, "IRIDIUM_SPRINKLER"),
    charcoalKlin("Charcoal Klin", new HashMap<>() {{
        put(new Material(MaterialType.wood), 20);
        put(new Material(MaterialType.copperBar), 2);
    }}, 0, "CHARCOAL_KILN"),
    furance("Furance", new HashMap<>() {{
        put(new Material(MaterialType.copperOre), 20);
        put(new Material(MaterialType.stone), 25);
    }}, 0, "FURNACE"),
    scarecrow("Scarecrow", new HashMap<>() {{
        put(new Material(MaterialType.wood), 50);
        put(new Material(MaterialType.coal), 1);
        put(new Material(MaterialType.fiber), 20);
    }}, 0, "SCARECROW"),
    deluxScarecrow("Delux Scarecrow", new HashMap<>() {{
        put(new Material(MaterialType.wood), 50);
        put(new Material(MaterialType.coal), 1);
        put(new Material(MaterialType.fiber), 20);
        put(new Material(MaterialType.iridiumOre), 1);
    }}, 0, "DELUXE_SCARECROW"),
    beeHouse("Bee House", new HashMap<>() {{
        put(new Material(MaterialType.wood), 40);
        put(new Material(MaterialType.coal), 8);
        put(new Material(MaterialType.ironBar), 1);
    }}, 0, "BEE_HOUSE"),
    cheesePress("Cheese Press", new HashMap<>() {{
        put(new Material(MaterialType.wood), 45);
        put(new Material(MaterialType.stone), 45);
    }}, 0, "CHEESE_PRESS"),
    keg("Keg", new HashMap<>() {{
        put(new Material(MaterialType.wood), 30);
        put(new Material(MaterialType.ironBar), 2);
    }}, 0, "KEG"),
    loom("Loom", new HashMap<>() {{
        put(new Material(MaterialType.wood), 60);
        put(new Material(MaterialType.fiber), 30);
    }}, 0, "LOOM"),
    mayonnaiseMachine("Mayonnaise Machine", new HashMap<>() {{
        put(new Material(MaterialType.wood), 15);
        put(new Material(MaterialType.ironBar), 15);
        put(new Material(MaterialType.ironBar), 1);
    }}, 0, "MAYONNAISE_MACHINE"),
    oilMaker("Oil Maker", new HashMap<>() {{
        put(new Material(MaterialType.wood), 100);
        put(new Material(MaterialType.ironBar), 1);
        put(new Material(MaterialType.goldBar), 1);
    }}, 0, "OIL_MAKER"),
    preservesJar("Preserves Jar", new HashMap<>() {{
        put(new Material(MaterialType.wood), 50);
        put(new Material(MaterialType.stone), 40);
        put(new Material(MaterialType.coal), 8);
    }}, 0, "PRESERVES_JAR"),
    dehydrator("Dehydrator", new HashMap<>() {{
        put(new Material(MaterialType.wood), 30);
        put(new Material(MaterialType.stone), 20);
        put(new Material(MaterialType.fiber), 30);
    }}, 0, "DEHYDRATOR"),
    grassStarter("Grass Starter", new HashMap<>() {{
        put(new Material(MaterialType.wood), 1);
        put(new Material(MaterialType.fiber), 1);
    }}, 0, "GRASS_STARTER"),
    fishSmoker("Fish Smoker", new HashMap<>() {{
        put(new Material(MaterialType.wood), 50);
        put(new Material(MaterialType.ironBar), 3);
        put(new Material(MaterialType.coal), 10);
    }}, 0, "FISH_SMOKER"),
    mysticTreeSeed("Mystic Tree Seed", new HashMap<>(), 0, "MYSTIC_TREE_SEED");

    private String productName;
    private HashMap<Item, Integer> ingredients;
    private int Price;

    private transient Texture texture;
    private String textureKey;

    CraftingRecipe(String productName, HashMap<Item, Integer> ingredients, int Price, String textureKey) {
        this.productName = productName;
        this.ingredients = ingredients;
        this.Price = Price;
        this.textureKey = textureKey;
        this.texture = loadTexture(textureKey);
    }

    private Texture loadTexture(String key) {
        GameAssetManager assets = GameAssetManager.getInstance();
        try {
            return (Texture) assets.getClass().getField(key).get(assets);
        } catch (Exception e) {
            throw new RuntimeException("Could not load texture: " + key, e);
        }
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
            if (backPack.getItemCount(item) < quantity) {
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

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.texture = loadTexture(textureKey);
    }
}

