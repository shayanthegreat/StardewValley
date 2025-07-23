package com.StardewValley.Models.Map;

import com.StardewValley.Models.Crafting.Refrigerator;

import java.io.Serializable;
import java.util.ArrayList;

public class House extends Building implements Serializable {

    private Refrigerator refrigerator = new Refrigerator();
    private Position origin;

    public House(ArrayList<Tile> tiles, Size size) {
        this.tiles = tiles;
        this.size = size;
        computeOrigin();
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    private void computeOrigin() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;

        for (Tile tile : tiles) {
            Position pos = tile.getPosition();
            if (pos.x < minX || (pos.x == minX && pos.y < minY)) {
                minX = pos.x;
                minY = pos.y;
            }
        }

        this.origin = new Position(minX, minY);
    }

    public Position getOrigin() {
        return origin;
    }
}
