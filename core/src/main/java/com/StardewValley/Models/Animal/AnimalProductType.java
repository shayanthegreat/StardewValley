package com.StardewValley.Models.Animal;

import java.io.Serializable;

public enum AnimalProductType implements Serializable {
    milk("milk",125, true),
    bigMilk("big milk",190, true),
    goatMilk("goat milk",225, true),
    bigGoatMilk("big goat milk",345, true),
    wool("wool",250, false),
    truffle("truffle",625, false),
    egg("egg",50, true),
    bigEgg("big egg",95, true),
    duckEgg("duck egg",95, true),
    duckFeather("duck feather",250, false),
    rabbitWool("rabbit wool",340, false),
    rabbitFoot("rabbit foot",565, true),
    dinosaurEgg("dinosaur egg",350, true),
    ;




    private String name;
    private int price;
//    private AnimalType producer;
    private boolean isEdible;
    AnimalProductType(String name, int price,  boolean isEdible) {
        this.name = name;
        this.price = price;
//        this.producer = producer;
        this.isEdible = isEdible;
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
}
