package com.StardewValley.Models.Animal;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public enum AnimalProductType implements  Serializable {
    milk("milk", 125, true, "MILK"),
    bigMilk("big milk", 190, true, "LARGE_MILK"),
    goatMilk("goat milk", 225, true, "GOAT_MILK"),
    bigGoatMilk("big goat milk", 345, true, "LARGE_GOAT_MILK"),
    wool("wool", 250, false, "WOOL"),
    truffle("truffle", 625, false, "TRUFFLE"),
    egg("egg", 50, true, "EGG"),
    bigEgg("big egg", 95, true, "LARGE_EGG"),
    duckEgg("duck egg", 95, true, "DUCK_EGG"),
    duckFeather("duck feather", 250, false, "DUCK_FEATHER"),
    rabbitWool("rabbit wool", 340, false, "WOOL"),
    rabbitFoot("rabbit foot", 565, true, "RABBIT_S_FOOT"),
    dinosaurEgg("dinosaur egg", 350, true, "DINOSAUR_EGG");

    private String name;
    private int price;
    private boolean isEdible;

    private transient Texture texture;
    private String textureKey; // Used to reload after deserialization

    AnimalProductType(String name, int price, boolean isEdible, String textureKey) {
        this.name = name;
        this.price = price;
        this.isEdible = isEdible;
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

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.texture = loadTexture(textureKey);
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
