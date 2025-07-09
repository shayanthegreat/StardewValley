package com.StardewValley.Models.Communication;

import com.StardewValley.Models.Animal.AnimalProduct;
import com.StardewValley.Models.Animal.AnimalProductType;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Crafting.CookingRecipe;
import com.StardewValley.Models.Crafting.Food;
import com.StardewValley.Models.Crafting.Material;
import com.StardewValley.Models.Crafting.MaterialType;
import com.StardewValley.Models.Farming.Crop;
import com.StardewValley.Models.Farming.CropType;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.TileObject;
import com.StardewValley.Models.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class NPC extends TileObject implements Serializable {
    private String name;
    private String job;
    private ArrayList<Item> favoriteItems = new ArrayList<>();
    private NPCDialogue dialogue;
    //private NPCFriendship friendship;
    private NPCQuest quest;
    public NPC(String name) {
        switch (name){
            case "Sebastian":{
                this.name = "Sebastian";
                this.job = "Merchant";
                favoriteItems.add(new AnimalProduct(AnimalProductType.wool));
                favoriteItems.add(new Food(CookingRecipe.pumpkinPie));
                favoriteItems.add(new Food(CookingRecipe.pizza));
                break;
            }
            case "Abigail":{
                this.name = "Abigail";
                this.job = "Miner";
                favoriteItems.add(new Material(MaterialType.stone));
                favoriteItems.add(new Material(MaterialType.ironOre));
                favoriteItems.add(new Material(MaterialType.coffee));
                break;
            }
            case "Harvey":{
                this.name = "Harvey";
                this.job = "GarbageSeller";
                favoriteItems.add(new Material(MaterialType.coffee));
                favoriteItems.add(new Material(MaterialType.pickle));
                favoriteItems.add(new Material(MaterialType.wine));
                break;
            }
            case "Lia":{
                this.name = "Lia";
                this.job = "Chef";
                favoriteItems.add(new Crop(CropType.grape));
                favoriteItems.add(new Material(MaterialType.wine));
                break;
            }
            case "Robin":{
                this.name = "Robin";
                this.job = "RandomSeller";
                favoriteItems.add(new Food(CookingRecipe.pizza));
                favoriteItems.add(new Material(MaterialType.wood));
                favoriteItems.add(new Material(MaterialType.ironBar));
                break;
            }
        }
        this.dialogue = new NPCDialogue(this);
        this.quest = new NPCQuest(this);
    }

    public static NPC getNPCByName(String name){
        ArrayList<NPC> npcs = App.getInstance().getCurrentGame().getCurrentPlayer().getNpcs();
        return switch (name) {
            case "Sebastian" -> npcs.get(0);
            case "Abigail" -> npcs.get(1);
            case "Harvey" -> npcs.get(2);
            case "Lia" -> npcs.get(3);
            case "Robin" -> npcs.get(4);
            default -> null;
        };
    }
    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public ArrayList<Item> getFavoriteItems() {
        return favoriteItems;
    }

    public boolean checkFavorite(Item item){
        for (Item favoriteItem : favoriteItems) {
            if(favoriteItem.getName().equals(item.getName())){
                return true;
            }
        }
        return false;
    }

    public String getDialogue() {
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();
        int level = 0;
        for (NPCFriendship npcFriendship : player.getNPCFriendships()) {
            if (npcFriendship.getNpc().equals(this)) {
                level = npcFriendship.getLevel();
            }
        }
        return dialogue.GetDialogue(game.getTodayWeather(), game.getTime(), level);
    }


    public NPCQuest getQuest() {
        return quest;
    }
}
