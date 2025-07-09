package com.StardewValley.Models.Store;

import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.Item;

import java.io.Serializable;

public class StoreItem implements Serializable {
    private Item item;
    private int price;
    private int dailyLimit;// 2000 if unlimited
    private Season season;
    private int seasonPrice;

    public StoreItem(Item item, int price, int dailyLimit, Season season, int seasonPrice) {
        this.item = item;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.season = season;
        this.seasonPrice = seasonPrice;
    }

    public Item getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void removeDailyLimit(int cnt) {
        this.dailyLimit -= cnt;
    }
    public Season getSeason() {
        return season;
    }

    public int getSeasonPrice() {
        return seasonPrice;
    }
}
