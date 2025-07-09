package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.Animal.AnimalProduct;
import com.StardewValley.Models.Animal.AnimalProductType;
import com.StardewValley.Models.Animal.Fish;
import com.StardewValley.Models.Animal.FishType;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.Farming.Crop;
import com.StardewValley.Models.Farming.CropType;
import com.StardewValley.Models.Farming.ForagingCrop;
import com.StardewValley.Models.Farming.ForagingCropType;
import com.StardewValley.Models.Item;

import java.io.Serializable;
import java.util.HashMap;

public enum CookingRecipe implements Recipe, Serializable {
    friedEgg("Fried Egg",new HashMap<>() {{
        put(new AnimalProduct(AnimalProductType.egg), 1);
    }}, 35, null, 50),
    omelet("omelet", new HashMap<>() {{
        put(new AnimalProduct(AnimalProductType.egg), 1);
        put(new AnimalProduct(AnimalProductType.milk), 1);
    }}, 125, null, 10),
    bakedFish("baked Fish", new HashMap<>(){{
        put(new Fish(FishType.sardine), 1);
        put(new Fish(FishType.salmon), 1);
        put(new Crop(CropType.wheat), 1);
    }}, 100, null, 75),
    pumpkinPie("pumpkin Pie", new HashMap<>(){{
        put(new Crop(CropType.pumpkin), 1);
        put(new Material(MaterialType.wheatFlour), 1);
        put(new AnimalProduct(AnimalProductType.milk), 1);
        put(new Material(MaterialType.sugar), 1);
    }}, 385, null, 225),
    spaghetti("spaghetti", new HashMap<>(){{
        put(new Material(MaterialType.wheatFlour), 1);
        put(new Crop(CropType.tomato), 1);
    }}, 120, null, 75),
    pizza("pizza", new HashMap<>(){{
        put(new Material(MaterialType.wheatFlour), 1);
        put(new Crop(CropType.tomato), 1);
        put(new Material(MaterialType.cheese), 1);
    }}, 300, null, 150),
    tortilla("tortilla", new HashMap<>(){{
        put(new Crop(CropType.corn), 1);
    }}, 50, null, 50),
    tripleShotEspresso("triple Shot Espresso", new HashMap<>(){{
        put(new Material(MaterialType.coffee), 3);
    }}, 450, new Buff(null,5,100), 200),
    cookie("Cookie", new HashMap<>(){{
        put(new Material(MaterialType.wheatFlour), 1);
        put(new Material(MaterialType.sugar), 1);
    }}, 140, null, 90),
    hashBrowns("hash Browns", new HashMap<>(){{
        put(new Material(MaterialType.oil), 1);
        put(new Crop(CropType.potato), 1);
    }}, 120, new Buff(SkillType.farming,5,0), 90),
    pancakes("pancakes", new HashMap<>(){{
        put(new AnimalProduct(AnimalProductType.egg), 1);
        put(new Material(MaterialType.wheatFlour), 1);
    }}, 80, new Buff(SkillType.foraging,11,0), 90),
    fruitSalad("fruit salad", new HashMap<>(){{
        put(new Crop(CropType.melon), 1);
        put(new Crop(CropType.blueberry), 1);
        put(new Crop(CropType.apricot), 1);
    }}, 450, null, 263),
    redPlate("red plate", new HashMap<>(){{
        put(new Crop(CropType.redCabbage), 1);
        put(new Crop(CropType.radish), 1);
    }}, 400, null, 240),
    bread("bread", new HashMap<>(){{
        put(new Material(MaterialType.wheatFlour), 1);
    }}, 60, null, 50),
    farmerLunch("farmer's lunch",new HashMap<>(){{
        put(new Food(CookingRecipe.omelet),1);
        put(new Crop(CropType.parsnip),1);
    }},150, new Buff(SkillType.farming,5,0), 200),
    survivalBurger("survival burger",new HashMap<>(){{
        put(new Food(CookingRecipe.bread),1);
        put(new Crop(CropType.carrot), 1);
        put(new Crop(CropType.eggplant), 1);
    }},180, new Buff(SkillType.foraging,5,0), 125),
    salad("salad", new HashMap<>(){{
        put(new ForagingCrop(ForagingCropType.leek), 1);
        put(new ForagingCrop(ForagingCropType.dandelion), 1);
    }}, 110, null, 113),
    salmonDinner("salmon dinner",new HashMap<>(){{
        put(new Fish(FishType.salmon),1);
    }},300,null,125),

    ;

    ;
    private String productName;
    private HashMap<Item, Integer> ingredients;
    private int price;
    private Buff buff;
    private int energy;

    CookingRecipe(String productName, HashMap<Item, Integer> ingredients, int price, Buff buff, int energy) {
        this.productName = productName;
        this.ingredients = ingredients;
        this.price = price;
        this.buff = buff;
        this.energy = energy;
    }

    public static CookingRecipe getCookingRecipeByName(String name) {
        for (CookingRecipe value : CookingRecipe.values()) {
            if(value.productName.equals(name)) {
                return value;
            }
        }
        return null;
    }


    public Buff getBuff() {
        return buff;
    }

    public int getEnergy() {
        return energy;
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
        return price;
    }
}
