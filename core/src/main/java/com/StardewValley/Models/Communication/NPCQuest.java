package com.StardewValley.Models.Communication;

import com.StardewValley.Models.Animal.AnimalProduct;
import com.StardewValley.Models.Animal.Fish;
import com.StardewValley.Models.Animal.FishType;
import com.StardewValley.Models.Crafting.CookingRecipe;
import com.StardewValley.Models.Crafting.Food;
import com.StardewValley.Models.Crafting.Material;
import com.StardewValley.Models.Crafting.MaterialType;
import com.StardewValley.Models.Farming.Crop;
import com.StardewValley.Models.Farming.CropType;
import com.StardewValley.Models.Farming.Plant;
import com.StardewValley.Models.Farming.PlantType;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class NPCQuest implements Serializable {
    private ArrayList<Pair<Item, Integer> > requests;
    private ArrayList<Pair<Item, Integer> > rewards;
    private NPC npc;
    private int activeQuest;
    private boolean isDone = false;
    public NPCQuest(NPC npc) {
        this.npc = npc;
        requests = new ArrayList<>();
        rewards = new ArrayList<>();
        activeQuest = 0;
        switch (npc.getName()){
            case "Sebastian":{
                requests.add(new Pair<>(new Material(MaterialType.ironOre), 50));
                requests.add(new Pair<>(new Food(CookingRecipe.pumpkinPie), 1));
                requests.add(new Pair<>(new Material(MaterialType.stone), 150));
                break;
            }
            case "Abigail":{
                requests.add(new Pair<>(new Material(MaterialType.goldBar), 1));
                requests.add(new Pair<>(new Crop(CropType.pumpkin), 1));
                requests.add(new Pair<>(new Crop(CropType.wheat), 50));
                break;
            }
            case "Harvey":{
                requests.add(new Pair<>(new Crop(CropType.pumpkin), 12));
                requests.add(new Pair<>(new Fish(FishType.salmon), 1));
                requests.add(new Pair<>(new Material(MaterialType.wine), 1));
                break;
            }
            case "Lia":{
                requests.add(new Pair<>(new Material(MaterialType.hardWood), 10));
                requests.add(new Pair<>(new Fish(FishType.salmon), 1));
                requests.add(new Pair<>(new Material(MaterialType.wood), 200));
                break;
            }
            case "Robin":{
                requests.add(new Pair<>(new Material(MaterialType.wood), 80));
                requests.add(new Pair<>(new Material(MaterialType.ironBar), 10));
                requests.add(new Pair<>(new Material(MaterialType.wood), 1000));
                break;
            }
        }
        this.activeQuest = 0;
    }

    public int getActiveQuest() {
        return activeQuest;
    }

    public void addActiveQuest() {
        this.activeQuest++;
        this.activeQuest = Math.max(this.activeQuest, 2);
    }

    public NPC getNpc() {
        return npc;
    }

    public ArrayList<Pair<Item, Integer>> getRequests() {
        return requests;
    }

    public ArrayList<Pair<Item, Integer>> getRewards() {
        return rewards;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }
}
