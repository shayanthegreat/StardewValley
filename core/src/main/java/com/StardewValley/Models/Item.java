package com.StardewValley.Models;

import com.StardewValley.Models.Animal.*;
import com.StardewValley.Models.Crafting.*;
import com.StardewValley.Models.Farming.*;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Map.TileObject;
import com.StardewValley.Models.Tools.Tool;
import com.StardewValley.Models.Tools.ToolType;

import java.io.Serializable;

public abstract class Item extends TileObject implements Serializable {

    protected final String name;
    protected final int takenSpace;
    protected final boolean isEdible;
    protected int price;
    abstract public int getPrice();

    public Item(String name, int takenSpace, boolean isEdible){
        this.name = name;
        this.takenSpace = takenSpace;
        this.isEdible = isEdible;
        this.price = 0;
    }

    public static Item getItemByName(String name){
        Item item = null;
        MaterialType materialType = MaterialType.getMaterialTypeByName(name);
        if(materialType != null){
            item = new Material(materialType);
        }
        FishType fishType = FishType.getFishTypeByName(name);
        if(fishType != null){
            item = new Fish(fishType);
        }
        CookingRecipe cookingRecipe = CookingRecipe.getCookingRecipeByName(name);
        if(cookingRecipe != null){
            item = new Food(cookingRecipe);
        }
        CraftingRecipe craftingRecipe = CraftingRecipe.getCraftingRecipeByName(name);
        if(craftingRecipe != null){
            item = new CraftingItem(craftingRecipe);
        }
        CropType cropType = CropType.getCropTypeByName(name);
        if(cropType != null){
            item = new Crop(cropType);
        }
        AnimalProductType animalProductType= AnimalProductType.getProductTypeByName(name);
        if(animalProductType != null){
            item = new AnimalProduct(animalProductType);
        }

        SeedType seedType = SeedType.getSeedTypeByName(name);
        if(seedType != null){
            item = new Seed(seedType);
        }

        ToolType toolType = ToolType.getToolTypeByName(name);
        if(toolType != null){
            item = new Tool(toolType);
        }
        return item;
    }

    public abstract void use();
    public abstract void drop(Tile tile);
    public abstract void delete();
    public String getName() {
        return name;
    }

    public int getTakenSpace() {
        return takenSpace;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item other = (Item) obj;
        if(obj instanceof Tool && this instanceof Tool) {
            boolean f1 = getName().equalsIgnoreCase(other.getName());
            boolean f2 = ((Tool)this).getLevel() == ((Tool)other).getLevel();
            return f1 && f2;
        }
        else {
            return getName().equalsIgnoreCase(other.getName());
        }
    }

    @Override
    public int hashCode() {
        return getName().toLowerCase().hashCode();
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
