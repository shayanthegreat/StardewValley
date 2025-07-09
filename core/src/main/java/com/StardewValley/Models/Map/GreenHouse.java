package com.StardewValley.Models.Map;

import java.io.Serializable;
import java.util.ArrayList;

public class GreenHouse extends Building implements Serializable {

    private boolean isBuilt;

    public GreenHouse(ArrayList<Tile> tiles, Size size) {
        this.tiles = tiles;
        this.size = size;
        isBuilt = false;
    }

    public void build() {
        isBuilt = true;
    }

    public boolean isBuilt() {
        return isBuilt;
    }
}
