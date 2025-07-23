package com.StardewValley.Models;

import com.StardewValley.Models.Communication.FriendShip;
import com.StardewValley.Models.Communication.NPC;
import com.StardewValley.Models.Communication.NPCFriendship;
import com.StardewValley.Models.Communication.NPCQuest;
import com.StardewValley.Models.Crafting.Buff;
import com.StardewValley.Models.Crafting.CookingRecipe;
import com.StardewValley.Models.Crafting.CraftingRecipe;
import com.StardewValley.Models.Enums.AvatarType;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.Farming.Seed;
import com.StardewValley.Models.Farming.SeedType;
import com.StardewValley.Models.Map.Farm;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Store.Store;
import com.StardewValley.Models.Tools.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Player implements Serializable {
    private int money;
    private int featureMoney;
    private User user;
    private Farm farm;
    private Position position;
    private Energy energy;
    private boolean isFainted = false;
    private final BackPack backPack;
    private final TrashCan trashCan;
    private final HashMap<SkillType, Skill> skills ;
    private final ArrayList<CraftingRecipe> knownCraftingRecipes;
    private final ArrayList<CookingRecipe> knownCookingRecipes;
    private Time[] lastBuffTime; // 0:farming, 1: extraction, 2: foraging, 3: fishing, 4: energy;
    private int buffedEnergy;
    private ArrayList<FriendShip> friendShips;
    private Tool currentTool = null;
    private ArrayList<NPC> npcs;
    private Store currentStore;
    private int Hay;
    private ArrayList<NPCFriendship> NPCFriendships;
    private ArrayList<NPCQuest> NPCQuests;
    private AvatarType avatarType;


    public Player(User user, Farm farm) {
        this.money = 100;
        this.user = user;
        this.farm = farm;
        this.position = new Position(farm.getTopLeft().x + 50, farm.getTopLeft().y + 50);
        this.energy = new Energy();
        backPack = new BackPack();
        Tool scythe = new Tool(ToolType.scythe);
        WateringCan wateringCan = new WateringCan(ToolType.wateringCan);
        Tool axe = new Tool(ToolType.axe);
        Tool shear = new Tool(ToolType.shear);
        Tool hoe = new Tool(ToolType.hoe);
        backPack.addItem(scythe, 1);
        backPack.addItem(wateringCan, 1);
        backPack.addItem(axe, 1);
        backPack.addItem(hoe, 1);
        backPack.addItem(shear, 1);
        // add this bullshit to backpack
        backPack.addItem(new Seed(SeedType.carrot), 8);
        trashCan = new TrashCan();
        skills = new HashMap<>();
        skills.put(SkillType.mining, new Skill(SkillType.mining));
        skills.put(SkillType.farming, new Skill(SkillType.farming));
        skills.put(SkillType.fishing, new Skill(SkillType.fishing));
        skills.put(SkillType.foraging, new Skill(SkillType.foraging));
        knownCraftingRecipes = new ArrayList<>();
        knownCookingRecipes = new ArrayList<>();
        lastBuffTime = new Time[5];
        for(int i = 0; i < 5; i++) {
            lastBuffTime[i] = Game.startingTime;
        }
        buffedEnergy = 0;
        friendShips = new ArrayList<>();
        npcs = new ArrayList<>();
        npcs.add(new NPC("Sebastian"));
        npcs.add(new NPC("Abigail"));
        npcs.add(new NPC("Harvey"));
        npcs.add(new NPC("Lia"));
        npcs.add(new NPC("Robin"));
        for (SkillType skillType : SkillType.values()) {
            skills.put(skillType, new Skill(skillType));
        }
        friendShips = new ArrayList<>();
        this.NPCFriendships = new ArrayList<>();
        this.NPCFriendships.add(new NPCFriendship(npcs.get(0)));
        this.NPCFriendships.add(new NPCFriendship(npcs.get(1)));
        this.NPCFriendships.add(new NPCFriendship(npcs.get(2)));
        this.NPCFriendships.add(new NPCFriendship(npcs.get(3)));
        this.NPCFriendships.add(new NPCFriendship(npcs.get(4)));
        this.Hay = 100;
        this.avatarType = AvatarType.ABIGAIL;
        this.NPCQuests = new ArrayList<>();
        this.NPCQuests.add(new NPCQuest(new NPC("Sebastian")));
        this.NPCQuests.add(new NPCQuest(new NPC("Abigail")));
        this.NPCQuests.add(new NPCQuest(new NPC("Harvey")));
        this.NPCQuests.add(new NPCQuest(new NPC("Lia")));
        this.NPCQuests.add(new NPCQuest(new NPC("Robin")));
    }

    public ArrayList<FriendShip> getFriendShips() {
        return friendShips;
    }

    public User getUser() {
        return user;
    }

    public Farm getFarm() {
        return farm;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isFainted() {
        return isFainted;
    }

    public Energy getEnergy() {
        return energy;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void addEnergy(int amount) {
            this.energy.addEnergy(amount);
    }

    public void decreaseEnergy(float amount) {
        this.energy.decreaseEnergy(amount);
    }

    public void setFainted(boolean fainted) {
        isFainted = fainted;
    }

    public void addFriendShip(FriendShip friendShip) {
        friendShips.add(friendShip);
    }

    public void Walk(Position position) {

    }

    public BackPack getBackPack() {
        return backPack;
    }

    public TrashCan getTrashCan() {
        return trashCan;
    }

    public Skill getSkill(SkillType skillType) {
        return skills.get(skillType);
    }

    public boolean knowCraftingRecipe(CraftingRecipe recipe) {
        return knownCraftingRecipes.contains(recipe);
    }

    public void addKnownCraftingRecipe(CraftingRecipe recipe) {
        this.knownCraftingRecipes.add(recipe);
    }

    public ArrayList<CookingRecipe> getKnownCookingRecipes() {
        return knownCookingRecipes;
    }

    public ArrayList<CraftingRecipe> getKnownCraftingRecipes() {
        return knownCraftingRecipes;
    }

    public boolean knowCookingRecipe(CookingRecipe recipe) {
        return knownCookingRecipes.contains(recipe);
    }

    public void addKnownCookingRecipe(CookingRecipe recipe) {
        this.knownCookingRecipes.add(recipe);
    }

    public void applyBuff(Buff buff) {
        App app = App.getInstance();
        Game game = app.getCurrentGame();
        Time time = game.getTime();
        switch (buff.getSkillType()){
            case farming :{
                lastBuffTime[0] = Time.addHour(time,buff.getHours());
                break;
            }
            case extraction:{
                lastBuffTime[1] = Time.addHour(time,buff.getHours());
                break;
            }
            case foraging:{
                lastBuffTime[2] = Time.addHour(time,buff.getHours());
                break;
            }
            case fishing:{
                lastBuffTime[3] = Time.addHour(time,buff.getHours());
                break;
            }
            default:{
                lastBuffTime[4] = Time.addHour(time,buff.getHours());
                break;
            }
        }
    }

    public boolean isBuffed(SkillType skillType) {
//        TODO: fix this
        return true;
    }

    public void disableEnergyBuff() {
//        decrease energy capacity if the buff was over
    }
    public FriendShip getFriendShipByPlayer(Player player) {
        for (FriendShip friendShip : this.friendShips) {
            if(friendShip.getPlayer().equals(player)) {
                return friendShip;
            }
        }
        return null;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void decreaseMoney(int amount) {
        this.money -= amount;
    }

    public int getFeatureMoney() {
        return featureMoney;
    }

    public void setFeatureMoney(int featureMoney) {
        this.featureMoney += featureMoney;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public void setCurrentStore(Store currentStore) {
        this.currentStore = currentStore;
    }

    public Store getCurrentStore() {
        return currentStore;
    }

    public int getHay(){
        return Hay;
    }

    public void addHay(int amount) {
        this.Hay += amount;
    }

    public void decreaseHay(int amount) {
        this.Hay -= amount;
    }
    public void resetMoney(){
        this.featureMoney = 0;
    }

    public void resetEnergy(){
        if(!energy.isFainted()) {
            energy.amount = energy.maxAmount;
        }
        else {
            energy.isFainted = false;
            energy.amount = (int)(energy.maxAmount * 0.75);
        }
    }

    public NPCFriendship getNpcFriendshipByName(String name) {
        for (NPCFriendship npcFriendship : this.NPCFriendships) {
            if(npcFriendship.getNpc().getName().equals(name)) {
                return npcFriendship;
            }
        }
        return null;
    }

    public ArrayList<NPCFriendship> getNPCFriendships() {
        return NPCFriendships;
    }

    public ArrayList<NPCQuest> getNPCQuests() {
        return NPCQuests;
    }

    public void setAvatarType(AvatarType avatarType) {
        this.avatarType = avatarType;
    }

    public AvatarType getAvatarType() {
        return avatarType;
    }
}
