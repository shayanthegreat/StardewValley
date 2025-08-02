package com.StardewValley.Models.Animal;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;

public enum AnimalProductType implements Serializable {
    milk("milk",125, true, GameAssetManager.getInstance().MILK),
    bigMilk("big milk",190, true, GameAssetManager.getInstance().LARGE_MILK),
    goatMilk("goat milk",225, true, GameAssetManager.getInstance().GOAT_MILK),
    bigGoatMilk("big goat milk",345, true, GameAssetManager.getInstance().LARGE_GOAT_MILK),
    wool("wool",250, false, GameAssetManager.getInstance().WOOL),
    truffle("truffle",625, false, GameAssetManager.getInstance().TRUFFLE),
    egg("egg",50, true, GameAssetManager.getInstance().EGG),
    bigEgg("big egg",95, true, GameAssetManager.getInstance().LARGE_EGG),
    duckEgg("duck egg",95, true, GameAssetManager.getInstance().DUCK_EGG),
    duckFeather("duck feather",250, false, GameAssetManager.getInstance().DUCK_FEATHER),
    rabbitWool("rabbit wool",340, false, GameAssetManager.getInstance().WOOL),
    rabbitFoot("rabbit foot",565, true, GameAssetManager.getInstance().RABBIT_S_FOOT),
    dinosaurEgg("dinosaur egg",350, true, GameAssetManager.getInstance().DINOSAUR_EGG),
    ;




    private String name;
    private int price;
//    private AnimalType producer;
    private boolean isEdible;
    private Texture texture;
    AnimalProductType(String name, int price,  boolean isEdible, Texture texture) {
        this.name = name;
        this.price = price;
//        this.producer = producer;
        this.isEdible = isEdible;
        this.texture = texture;
    }

    public static AnimalProductType getProductTypeByName(String name) {
        for (AnimalProductType value : AnimalProductType.values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

//    public AnimalType getProducer() {
//        return producer;
//    }

    public boolean isEdible() {
        return isEdible;
    }

    public Texture getTexture() {
        return texture;
    }
}
