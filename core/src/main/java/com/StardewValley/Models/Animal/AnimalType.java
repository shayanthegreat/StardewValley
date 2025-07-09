package com.StardewValley.Models.Animal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum AnimalType implements Serializable {
    cow(false,1500,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.milk) , new AnimalProduct(AnimalProductType.bigMilk))),4),
    goat(false,4000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.goatMilk),new AnimalProduct(AnimalProductType.bigGoatMilk))),8),
    sheep(false,8000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.wool))),12),
    pig(false,16000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.truffle))),8),
    chicken(true,800,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.egg),new AnimalProduct(AnimalProductType.bigEgg))),4),
    duck(true,1200,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.duckEgg),new AnimalProduct(AnimalProductType.duckFeather))),8),
    rabbit(true,8000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.rabbitWool),new AnimalProduct(AnimalProductType.rabbitFoot))),12),
    dinosaur(true,14000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.dinosaurEgg))),8),
    ;
    private int capacity;
    private int cost;
    private int daysTillProduct;
    private ArrayList<AnimalProduct> products;
    private boolean isInCage;

    AnimalType(boolean isInCage,int cost,ArrayList<AnimalProduct> products,int capacity) {
        this.isInCage = isInCage;
        this.cost = cost;
        this.products = products;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getDaysTillProduct() {
        return daysTillProduct;
    }

    public ArrayList<AnimalProduct> getProducts() {
        return products;
    }

    public static AnimalType getAnimalTypeByName(String name) {
        if(name.equals("cow")) return cow;
        else if(name.equals("goat")) return goat;
        else if(name.equals("sheep")) return sheep;
        else if(name.equals("pig")) return pig;
        else if(name.equals("chicken")) return chicken;
        else if(name.equals("duck")) return duck;
        else if(name.equals("rabbit")) return rabbit;
        else if(name.equals("dinosaur")) return dinosaur;
        else return null;
    }

    public boolean isInCage() {
        return isInCage;
    }

    public int getCost() {
        return cost;
    }
}
