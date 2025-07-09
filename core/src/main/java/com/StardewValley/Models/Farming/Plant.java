package com.StardewValley.Models.Farming;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Map.TileObject;
import com.StardewValley.Models.Time;

import java.io.Serializable;
import java.util.ArrayList;

public class Plant extends TileObject implements Serializable {

    private PlantType type;
    private Time plantingTime;
    private int regrownTimes;
    private int gianPosition;
    //    0 1
//    2 3 and -1 if not gianted
    private ArrayList<Plant> gianPlants;
    private Time lastWateringTime;
    private int currentStage;

    public Plant(PlantType type, Time plantingTime) {
        super();
        this.type = type;
        this.plantingTime = plantingTime;
        this.lastWateringTime = plantingTime;
        this.regrownTimes = 0;
        this.gianPosition = -1;
        this.currentStage = 1;
    }

    public PlantType getType() {
        return type;
    }

    public Time getPlantingTime() {
        return plantingTime;
    }


    public int getRegrownTimes() {
        return regrownTimes;
    }

    public void addRegrownTimes() {
        regrownTimes++;
    }

    public int getGianPosition() {
        return gianPosition;
    }

    public void setPlantingTime(Time plantingTime) {
        this.plantingTime = plantingTime;
    }

    public void setRegrownTimes(int regrownTimes) {
        this.regrownTimes = regrownTimes;
    }

    public void setGianPosition(int gianPosition) {
        this.gianPosition = gianPosition;
    }

    public ArrayList<Plant> getGianPlants() {
        return gianPlants;
    }

    public void setGianPlants(ArrayList<Plant> gianPlants) {
        this.gianPlants = gianPlants;
    }

    public void harvest() {}

    public Time getLastWateringTime() {
        return lastWateringTime;
    }

    public void setLastWateringTime(Time lastWateringTime) {
        this.lastWateringTime = lastWateringTime;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public void grow(){
//        TODO: implement this if needed current stage
//        int diff = Time.getDayDifference(plantingTime, App.getInstance().getCurrentGame().getTime());
//        if(type.getReGrowth() > regrownTimes){
//            diff %= type.getTotalTime() + 2;
//        }
//        int sum = 0;
//        this.currentStage = type.getStages().size()+1;
//        for(int i = 0; i < type.getStages().size(); i++){
//            if(type.getStages().get(i) + sum >= diff){
//                currentStage = i+1;
//                break;
//            }
//            sum += type.getStages().get(i);
//        }
//        if(diff > type.getTotalTime()){
//            regrownTimes++;
//        }
    }
    public boolean isDestroyed(){
        if(regrownTimes == 1 && type.getReGrowth() == -1)
            return true;
        else if(regrownTimes >= type.getReGrowth()){
            return true;
        }
        if(Math.abs(Time.getDayDifference(lastWateringTime, App.getInstance().getCurrentGame().getTime())) > 2){
            return true;
        }
        return false;
    }

    public void destroy() {
        if(gianPosition == -1) {
            Tile tile = getPlacedTile();
            tile.setObject(null);
        }
        else {
            ArrayList<Tile> tiles = new ArrayList<>();
            for(Plant plant : gianPlants) {
                tiles.add(plant.getPlacedTile());
            }
            for(Tile tile : tiles) {
                tile.setObject(null);
            }
        }
    }
}
