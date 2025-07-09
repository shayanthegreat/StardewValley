package com.StardewValley.Models.Map;

import java.io.Serializable;
import java.util.ArrayList;

public class Quarry extends Building implements Serializable {

    public Quarry(ArrayList<Tile> tiles, Size size) {
        this.tiles = tiles;
        this.size = size;
    }
}
