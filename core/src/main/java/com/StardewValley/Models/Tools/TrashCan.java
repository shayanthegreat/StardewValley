package com.StardewValley.Models.Tools;

import com.StardewValley.Models.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class TrashCan implements Serializable {
    private final static ArrayList<Double> ratios = new ArrayList<>();
    private static final ArrayList<String> name = new ArrayList<>();
    private int level = 0;

    static {
        name.add("initial");
        name.add("copper");
        name.add("iron");
        name.add("gold");
        name.add("iridium");
        ratios.add((double) 0);
        ratios.add(0.15);
        ratios.add(0.3);
        ratios.add(0.45);
        ratios.add(0.6);
    }

    public void upgradeLevel() {
        if(level <= 3){
            level++;
        }
    }

    public double calcRatio(){
        return ratios.get(level);
    }
    public int calRefund(Item item){
//        TODO: fix this
        return 0;
    }

    public int getLevel() {
        return level;
    }

}
