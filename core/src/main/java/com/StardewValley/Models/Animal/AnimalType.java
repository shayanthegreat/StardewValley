package com.StardewValley.Models.Animal;

import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum AnimalType implements Serializable {
    cow(false,1500,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.milk) , new AnimalProduct(AnimalProductType.bigMilk))),4,new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/cow.png")),
    goat(false,4000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.goatMilk),new AnimalProduct(AnimalProductType.bigGoatMilk))),8,new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Goat.png")),
    sheep(false,8000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.wool))),12,new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Sheep.png")),
    pig(false,16000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.truffle))),8,new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Pig.png")),
    chicken(true,800,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.egg),new AnimalProduct(AnimalProductType.bigEgg))),4,new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Chicken_Statue.png")),
    duck(true,1200,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.duckEgg),new AnimalProduct(AnimalProductType.duckFeather))),8,new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Duck.png")),
    rabbit(true,8000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.rabbitWool),new AnimalProduct(AnimalProductType.rabbitFoot))),12,new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Rabbit.png")),
    dinosaur(true,14000,new ArrayList<AnimalProduct>(List.of(new AnimalProduct(AnimalProductType.dinosaurEgg))),8,new Texture("Stardew_Valley_Images-main/Stardew_Valley_Images-main/Animals/Dinosaur.png")),
    ;
    private int capacity;
    private int cost;
    private int daysTillProduct;
    private ArrayList<AnimalProduct> products;
    private boolean isInCage;
    private Texture texture;

    AnimalType(boolean isInCage,int cost,ArrayList<AnimalProduct> products,int capacity,Texture texture) {
        this.isInCage = isInCage;
        this.cost = cost;
        this.products = products;
        this.capacity = capacity;
        this.texture = texture;
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

    public Texture getTexture() {
        return texture;
    }
}
