package com.StardewValley.Models.Artisan;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public enum ArtisanType {
    beeHouse("Bee House", GameAssetManager.getInstance().BEE_HOUSE),
    cheesePress("Cheese Press", GameAssetManager.getInstance().CHEESE_PRESS),
    keg("Keg", GameAssetManager.getInstance().KEG),
    dehydrator("Dehydrator", GameAssetManager.getInstance().DEHYDRATOR),
    charcoalKlin("Charcoal Klin", GameAssetManager.getInstance().CHARCOAL_KILN),
    loom("Loom", GameAssetManager.getInstance().LOOM),
    mayonnaiseMachine("Mayonnaise Machine", GameAssetManager.getInstance().MAYONNAISE_MACHINE),
    oilMaker("Oil Maker", GameAssetManager.getInstance().OIL_MAKER),
    preservesJar("Preserve Jar", GameAssetManager.getInstance().PRESERVES_JAR),
    fishSmoker("Fish Smoker", GameAssetManager.getInstance().FISH_SMOKER),
    furnace("Furnace", GameAssetManager.getInstance().FURNACE),
    ;


    private String name;
    private Texture texture;
    private ArrayList<Item> items;

    ArtisanType(String name, Texture texture) {
        this.name = name;
        this.texture = texture;
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

    public Texture getTexture() {
        return texture;
    }
}
