package com.StardewValley.Models.Map;

import com.StardewValley.Models.Crafting.Refrigerator;

import java.io.Serializable;
import java.util.ArrayList;

public class House extends Building implements Serializable {

    private Refrigerator refrigerator = new Refrigerator();

    public House(ArrayList<Tile> tiles, Size size) {
        this.tiles = tiles;
        this.size = size;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }
}
