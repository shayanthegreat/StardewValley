package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.Animal.*;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.Farming.*;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Tools.BackPack;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum CookingRecipe implements Recipe, Serializable {
    friedEgg("Fried Egg", new HashMap<>() {{
        put(new AnimalProduct(AnimalProductType.egg), 1);
    }}, 35, null, 50, "FRIED_EGG"),
    omelet("Omelet", new HashMap<>() {{
        put(new AnimalProduct(AnimalProductType.egg), 1);
        put(new AnimalProduct(AnimalProductType.milk), 1);
    }}, 125, null, 10, "OMELET"),
    bakedFish("Baked Fish", new HashMap<>() {{
        put(new Fish(FishType.sardine), 1);
        put(new Fish(FishType.salmon), 1);
        put(new Crop(CropType.wheat), 1);
    }}, 100, null, 75, "BAKED_FISH"),
    pumpkinPie("Pumpkin Pie", new HashMap<>() {{
        put(new Crop(CropType.pumpkin), 1);
        put(new Material(MaterialType.wheatFlour), 1);
        put(new AnimalProduct(AnimalProductType.milk), 1);
        put(new Material(MaterialType.sugar), 1);
    }}, 385, null, 225, "PUMPKIN_PIE"),
    spaghetti("Spaghetti", new HashMap<>() {{
        put(new Material(MaterialType.wheatFlour), 1);
        put(new Crop(CropType.tomato), 1);
    }}, 120, null, 75, "SPAGHETTI"),
    pizza("Pizza", new HashMap<>() {{
        put(new Material(MaterialType.wheatFlour), 1);
        put(new Crop(CropType.tomato), 1);
        put(new Material(MaterialType.cheese), 1);
    }}, 300, null, 150, "PIZZA"),
    tortilla("Tortilla", new HashMap<>() {{
        put(new Crop(CropType.corn), 1);
    }}, 50, null, 50, "TORTILLA"),
    tripleShotEspresso("Triple Shot Espresso", new HashMap<>() {{
        put(new Material(MaterialType.coffee), 3);
    }}, 450, new Buff(null, 5, 100), 200, "TRIPLE_SHOT_ESPRESSO"),
    cookie("Cookie", new HashMap<>() {{
        put(new Material(MaterialType.wheatFlour), 1);
        put(new Material(MaterialType.sugar), 1);
    }}, 140, null, 90, "COOKIE"),
    hashBrowns("Hash Browns", new HashMap<>() {{
        put(new Material(MaterialType.oil), 1);
        put(new Crop(CropType.potato), 1);
    }}, 120, new Buff(SkillType.farming, 5, 0), 90, "HASHBROWNS"),
    pancakes("Pancakes", new HashMap<>() {{
        put(new AnimalProduct(AnimalProductType.egg), 1);
        put(new Material(MaterialType.wheatFlour), 1);
    }}, 80, new Buff(SkillType.foraging, 11, 0), 90, "PANCAKES"),
    fruitSalad("Fruit Salad", new HashMap<>() {{
        put(new Crop(CropType.melon), 1);
        put(new Crop(CropType.blueberry), 1);
        put(new Crop(CropType.apricot), 1);
    }}, 450, null, 263, "FRUIT_SALAD"),
    redPlate("Red Plate", new HashMap<>() {{
        put(new Crop(CropType.redCabbage), 1);
        put(new Crop(CropType.radish), 1);
    }}, 400, null, 240, "RED_PLATE"),
    bread("Bread", new HashMap<>() {{
        put(new Material(MaterialType.wheatFlour), 1);
    }}, 60, null, 50, "BREAD"),
    farmerLunch("Farmer's Lunch", new HashMap<>() {{
        put(new Food(CookingRecipe.omelet), 1);
        put(new Crop(CropType.parsnip), 1);
    }}, 150, new Buff(SkillType.farming, 5, 0), 200, "FARMER_S_LUNCH"),
    survivalBurger("Survival Burger", new HashMap<>() {{
        put(new Food(CookingRecipe.bread), 1);
        put(new Crop(CropType.carrot), 1);
        put(new Crop(CropType.eggplant), 1);
    }}, 180, new Buff(SkillType.foraging, 5, 0), 125, "SURVIVAL_BURGER"),
    salad("Salad", new HashMap<>() {{
        put(new ForagingCrop(ForagingCropType.leek), 1);
        put(new ForagingCrop(ForagingCropType.dandelion), 1);
    }}, 110, null, 113, "SALAD"),
    salmonDinner("Salmon Dinner", new HashMap<>() {{
        put(new Fish(FishType.salmon), 1);
    }}, 300, null, 125, "SALMON_DINNER");

    private String productName;
    private HashMap<Item, Integer> ingredients;
    private int price;
    private Buff buff;
    private int energy;

    private transient Texture texture;
    private String textureKey;

    CookingRecipe(String productName, HashMap<Item, Integer> ingredients, int price, Buff buff, int energy, String textureKey) {
        this.productName = productName;
        this.ingredients = ingredients;
        this.price = price;
        this.buff = buff;
        this.energy = energy;
        this.textureKey = textureKey;
        this.texture = loadTexture(textureKey);
    }

    private Texture loadTexture(String key) {
        if (key == null) return null;
        GameAssetManager assets = GameAssetManager.getInstance();
        try {
            return (Texture) assets.getClass().getField(key).get(assets);
        } catch (Exception e) {
            throw new RuntimeException("Could not load texture: " + key, e);
        }
    }

    public static CookingRecipe getCookingRecipeByName(String name) {
        for (CookingRecipe value : CookingRecipe.values()) {
            if (value.productName.equals(name)) return value;
        }
        return null;
    }

    public Buff getBuff() { return buff; }
    public int getEnergy() { return energy; }
    @Override public String getProductName() { return productName; }
    @Override public HashMap<Item, Integer> getIngredients() { return ingredients; }
    @Override public int getPrice() { return price; }
    public Texture getTexture() { return texture; }

    public boolean canCook(BackPack backPack) {
        for (Map.Entry<Item, Integer> entry : ingredients.entrySet()) {
            if (backPack.getItemCount(entry.getKey()) < entry.getValue()) return false;
        }
        return true;
    }

    public void consume(BackPack backPack) {
        for (Map.Entry<Item, Integer> entry : ingredients.entrySet()) {
            backPack.removeItem(entry.getKey(), entry.getValue());
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.texture = loadTexture(textureKey);
    }
}
