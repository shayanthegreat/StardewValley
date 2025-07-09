package com.StardewValley.Models.Artisan;

import com.StardewValley.Models.Item;

import java.util.ArrayList;

public enum ArtisanType {
    beeHouse("Bee House"),
    cheesePress("Cheese Press"),
    keg("Keg"),
    dehydrator("Dehydrator"),
    charcoalKlin("Charcoal Klin"),
    loom("Loom"),
    mayonnaiseMachine("Mayonnaise Machine"),
    oilMaker("Oil Maker"),
    preservesJar("Preserve Jar"),
    fishSmoker("Fish Smoker"),
    furnace("Furnace"),
    ;


    private String name;
    private ArrayList<Item> items;

    ArtisanType(String name) {
        this.name = name;
        items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public static ArtisanType getArtisanTypeByName(String name) {
        for(ArtisanType artisanType : ArtisanType.values()) {
            if(artisanType.getName().equalsIgnoreCase(name)) {
                return artisanType;
            }
        }
        return null;
    }
}
