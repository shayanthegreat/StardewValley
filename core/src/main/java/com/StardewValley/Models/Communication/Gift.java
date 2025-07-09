package com.StardewValley.Models.Communication;

import com.StardewValley.Models.Item;

import java.io.Serializable;

public class Gift implements Serializable {
    private Item item;
    private int amount;
    private int rate;

    public Gift(Item item, int amount) {
        this.item = item;
        this.amount = amount;
        this.rate = 0;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public int getRate() {
        return rate;
    }
}
