package com.StardewValley.Models.Map;

import java.io.Serializable;

public abstract class TileObject implements Serializable {

    protected Tile placedTile;

    public Tile getPlacedTile() {
        return placedTile;
    }

    public void setPlacedTile(Tile placedTile) {
        this.placedTile = placedTile;
    }
}
