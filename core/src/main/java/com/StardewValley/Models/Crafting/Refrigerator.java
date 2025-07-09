package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Player;

import java.io.Serializable;
import java.util.HashMap;

public class Refrigerator implements Serializable {
    private HashMap<Item, Integer> items;

    public Refrigerator() {
        this.items=new HashMap<>();
    }

    public void pickItem(Item item,int amount) {
        App app=App.getInstance();
        Player player=app.getCurrentGame().getCurrentPlayer();
        player.getBackPack().addItem(item,amount);
        this.items.remove(item,amount);
    }

    public boolean checkItemByName(String itemName) {
        for (Item item : items.keySet()) {
            if(item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public Item getItemByName(String itemName) {
        for (Item item : items.keySet()) {
            if(item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }


    public void putItem(Item item,int amount) {
        this.items.put(item,amount);
    }

    public boolean doesItemExist(Item item) {
        return this.items.containsKey(item);
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public boolean checkItem(Item item, int count) {
        if (!items.containsKey(item)) {
            return false;
        }
        return items.get(item) >= count;
    }

    public void removeItem(Item item, int count) {
        if (checkItem(item, count)) {
            items.put(item, items.get(item) - count);
            if (items.get(item) == 0) {
                items.remove(item);
            }
        }
    }
}
