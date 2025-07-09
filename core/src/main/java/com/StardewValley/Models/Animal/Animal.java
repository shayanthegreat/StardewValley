package com.StardewValley.Models.Animal;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.Time;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Animal  implements Serializable {

    private final String name;
    private final AnimalType type;
    private final Player owner;
    private int friendship;
    private Position position;
    private boolean isOutside;
    private Time lastFeedingTime;
    private Time lastPetingTime;
    private Time lastProducingTime;
    private boolean isCollected;
    private AnimalProduct currentProduct;


    public Animal(AnimalType type, Player owner, String name) {
        this.type = type;
        this.owner = owner;
        this.name = name;
        this.isOutside = false;
        this.isCollected=true;
//        this.lastPetingTime = App.getInstance().getCurrentGame().getTime();
    }

    public AnimalType getType() {
        return type;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public AnimalProduct whichProduct(){
        if(this.currentProduct!=null){
            return this.currentProduct;
        }
        if(this.type.getProducts().size() == 1 ){
            this.currentProduct = this.type.getProducts().get(0);
            return type.getProducts().get(0);
        }
        if(this.friendship>=100){
            double x =((this.friendship+(150* ThreadLocalRandom.current().nextDouble(0.5, 1.5)))/100);
            double roll=Math.random();
            if(roll < x){
                this.currentProduct = this.type.getProducts().get(1);
                return type.getProducts().get(1);
            }
            else{
                this.currentProduct = this.type.getProducts().get(0);
                return type.getProducts().get(0);
            }
        }
        else{
            this.currentProduct = this.type.getProducts().get(0);
            return type.getProducts().get(0);
        }
    }

    public AnimalProduct getCurrentProduct() {
        return currentProduct;
    }

    public void increaseFriendship(int amount) {
        if(friendship+amount<1000){
            friendship+=amount;
        }
    }

    public void decreaseFriendship(int amount) {}

    public int getFriendship() { return friendship; }

    public void setFriendship(int friendship) { this.friendship = friendship; }

    public Player getOwner() { return owner; }

    public Position getPosition() { return position; }

    public void move(Tile newTile) {
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }

    public boolean isOutside() {
        return isOutside;
    }

    public void setCurrentProduct(AnimalProduct currentProduct) {
        this.currentProduct = currentProduct;
    }

    public boolean doesProduce() {
        App app = App.getInstance();
        Game game=app.getCurrentGame();
        if(lastFeedingTime == null){
            return false;
        }
        if(lastFeedingTime.getSeason() == game.getTime().getSeason()){
            if(lastFeedingTime.getDay() - game.getTime().getDay()==-1){
                return true;
            }
        }
        else if(lastFeedingTime.getSeason().getNext() == game.getTime().getSeason()){
            if(lastFeedingTime.getDay()==28 && game.getTime().getDay()==1){
                return true;
            }
        }
        else if(!isCollected){
            return true;
        }
        return false;
    }

    public void collect() {}

    public int calSellingPrice() {
        return (int)(this.type.getCost()*((double)this.friendship/100 + 0.3));
    }

    public String getName() {
        return name;
    }

    public void setLastFeedingTime(Time lastFeedingTime) {
        this.lastFeedingTime = new Time(lastFeedingTime);
    }

    public void setLastPettingTime(Time lastPetingTime) {
        this.lastPetingTime = lastPetingTime;
    }

    public void setLastProducingTime(Time lastProducingTime) {
        this.lastProducingTime = lastProducingTime;
    }

    public Time getLastFeedingTime() {
        return lastFeedingTime;
    }

    public Time getLastPetingTime() {
        return lastPetingTime;
    }

    public Time getLastProducingTime() {
        return lastProducingTime;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public boolean hasBeenFedToday(Time time) {
        if(lastFeedingTime==null){
            return false;
        }
        if(lastFeedingTime.getSeason()==time.getSeason()){
            if(lastFeedingTime.getDay()==time.getDay()){
                return true;
            }
        }
        return false;
    }

    public boolean hasBeenPetToday(Time time) {
        if(lastFeedingTime==null){
            return false;
        }
        if(lastFeedingTime.getSeason()==time.getSeason()){
            if(lastFeedingTime.getDay()==time.getDay()){
                return true;
            }
        }
        return false;
    }

}
