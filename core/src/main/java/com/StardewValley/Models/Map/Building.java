package com.StardewValley.Models.Map;

import java.util.ArrayList;

public abstract class Building {
    protected ArrayList<Tile> tiles;
    protected Size size;

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public Size getSize() {
        return size;
    }
}
