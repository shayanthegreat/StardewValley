package com.StardewValley.Models.Map;

import java.io.Serializable;
import java.util.ArrayList;

public class GreenHouse extends Building implements Serializable {

    private boolean isBuilt;
    private Position origin;
    private Position topRight;

    public GreenHouse(ArrayList<Tile> tiles, Size size) {
        this.tiles = tiles;
        this.size = size;
        isBuilt = false;
        computeOrigin();
        computeTopRight();
    }

    private void computeTopRight() {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Tile tile : tiles) {
            Position pos = tile.getPosition();
            if (pos.x > maxX || (pos.x == maxX && pos.y > maxY)) {
                maxX = pos.x;
                maxY = pos.y;
            }
        }

        this.topRight = new Position(maxX, maxY);
    }

    private void computeOrigin(){
        int maxX = Integer.MAX_VALUE;
        int maxY = Integer.MAX_VALUE;

        for (Tile tile : tiles) {
            Position pos = tile.getPosition();
            if (pos.x < maxX || (pos.x == maxX && pos.y < maxY)) {
                maxX = pos.x;
                maxY = pos.y;
            }
        }

        this.origin = new Position(maxX, maxY);
    }

    public Position getTopRight() {
        return topRight;
    }

    public Position getOrigin() {
        return origin;
    }

    public void build() {
        isBuilt = true;
    }

    public boolean isBuilt() {
        return isBuilt;
    }
}
