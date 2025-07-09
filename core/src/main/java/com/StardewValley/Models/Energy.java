package com.StardewValley.Models;

import java.io.Serializable;

public class Energy implements Serializable {
    public int amount = 200;
    public int maxAmount = 200;
    public boolean isFainted = false;
    public boolean isUnlimited = false;

    public void addEnergy(int amount) {
        if(this.amount + amount <= maxAmount) {
            this.amount += amount;
        }
        else {
            this.amount = maxAmount;
        }
    }

    public void decreaseEnergy(int amount) {
        if(this.amount - amount >=0){
            this.amount -= amount;
        }
        else{
            this.amount = 0;
            this.isFainted=true;
        }
    }

    public void increaseMaxAmount(int amount) {
        this.maxAmount += amount;
    }

    public void decreaseMaxAmount(int amount) {
        this.maxAmount -= amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public boolean isFainted() {
        return isFainted;
    }

    public void setFainted(boolean fainted) {
        isFainted = fainted;
    }

    public boolean isUnlimited() {
        return isUnlimited;
    }

    public void setUnlimited(boolean unlimited) {
        isUnlimited = unlimited;
    }
}
