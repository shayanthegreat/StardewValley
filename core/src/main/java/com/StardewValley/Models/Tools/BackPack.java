package com.StardewValley.Models.Tools;

import com.StardewValley.Models.Farming.Seed;
import com.StardewValley.Models.Farming.SeedType;
import com.StardewValley.Models.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BackPack implements Serializable {
    private HashMap<Item, Integer> items = new HashMap<>();
    private static ArrayList<Integer> capacity = new ArrayList<>();
    private static final ArrayList<String> name = new ArrayList<>();
    private int level;
    private int maxCapacity;
    private int amount;
    static {
        capacity.add(12);
        capacity.add(24);
        capacity.add(1000000000);
        name.add("initial");
        name.add("big");
        name.add("deluxe");
    }
    public BackPack() {
        level = 0;
        maxCapacity = 12;
        amount = 0;
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public void upgradeLevel() {
        if(level < 3){
            maxCapacity = capacity.get(level+1);
            level++;
        }
    }

    public boolean canAddItem(Item item, int count) {
        if(item.getTakenSpace() * count + getCapacity() > maxCapacity){
            return false;
        }
        return true;
    }
    public void addItem(Item item, int count) {
        if(count <= 0) {
            return;
        }
        items.put(item, items.getOrDefault(item, 0) + count);
        amount = getCapacity();
    }


    public boolean removeItem(Item item, int count) {
        if (checkItem(item, count)) {
            items.put(item, items.get(item) - count);
            if (items.get(item) == 0) {
                items.remove(item);
            }
            amount = getCapacity();
            return true;
        }
        return false;
    }

    public boolean checkItem(Item item, int count) {
        if (!items.containsKey(item)) {
            return false;
        }
        return items.get(item) >= count;
    }
    public Tool getToolByType(ToolType type) {
//        TODO: get the highest one
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item item = entry.getKey();
            if(item instanceof Tool) {
                Tool tool = (Tool) item;
                if(tool.getToolType() == type) {
                    return tool;
                }
            }
        }
        return null;
    }

    public Seed getSeedInBachPack(SeedType type) {
        for(Item item : items.keySet()) {
            if(item instanceof Seed) {
                Seed seed = (Seed) item;
                if(seed.getType().equals(type)) {
                    return seed;
                }
            }
        }
        return null;
    }

    public Item getItemByName(String name) {
        for (Item item : items.keySet()) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public int getItemCount(Item item) {
        return items.get(item);
    }


    public int getCapacity() {
        int totalCapacity = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item item = entry.getKey();
            Integer count = entry.getValue();
            totalCapacity += count * item.getTakenSpace();
        }
        return (totalCapacity);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getLevel() {
        return level;
    }

    public void increaseLevel() {
        level++;
    }


}
