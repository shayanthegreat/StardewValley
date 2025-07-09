package com.StardewValley.Models.Map;

import com.StardewValley.Models.Farming.Fertilizer;
import com.StardewValley.Models.Farming.Plant;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Time;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Tile implements Serializable {

    private final Position position;
    private TileObject object;
    private Building building;
    private boolean isPlowed;
    private boolean containsPlant;

    public Tile(Position position) {
        this.position = position;
        isPlowed = false;
        containsPlant = false;
    }

    public boolean isTotallyEmpty() {
        return object == null && building == null;
    }

    public boolean isBuildingPlantable() {
        if(building == null) {
            return true;
        }
        if(building instanceof GreenHouse && ((GreenHouse) building).isBuilt()) {
            return true;
        }
        return false;
    }

    public Position getPosition() {
        return position;
    }

    public TileObject getObject() {
        return object;
    }

    public void setObject(TileObject object) {
        this.object = object;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }


    public void setContainsPlant(boolean containsPlant) {
        this.containsPlant = containsPlant;
//        TODO: when there is no more plant change it to false
    }



    public boolean containsPlant() {
        return containsPlant;
    }

    public void fertilize(Fertilizer fertilizer) {}

    public void water() {
//        TODO: also set lastWateringTime
    }
}
