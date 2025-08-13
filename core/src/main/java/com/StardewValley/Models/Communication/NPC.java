package com.StardewValley.Models.Communication;

import com.StardewValley.Models.*;
import com.StardewValley.Models.Animal.AnimalProduct;
import com.StardewValley.Models.Animal.AnimalProductType;
import com.StardewValley.Models.Crafting.CookingRecipe;
import com.StardewValley.Models.Crafting.Food;
import com.StardewValley.Models.Crafting.Material;
import com.StardewValley.Models.Crafting.MaterialType;
import com.StardewValley.Models.Farming.Crop;
import com.StardewValley.Models.Farming.CropType;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Map.TileObject;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class NPC extends TileObject implements Serializable {
    private String name;
    private String job;
    private ArrayList<Item> favoriteItems = new ArrayList<>();
    private NPCDialogue dialogue;
    private transient Texture texture; // transient: not serialized
    private Position position;
    private NPCQuest quest;

    // Store texture key for restoration after deserialization
    private String textureKey;

    public NPC(String name) {
        this.name = name;
        this.dialogue = new NPCDialogue(this);
        this.quest = new NPCQuest(this);

        switch (name) {
            case "Sebastian" -> {
                this.job = "Merchant";
                favoriteItems.add(new AnimalProduct(AnimalProductType.wool));
                favoriteItems.add(new Food(CookingRecipe.pumpkinPie));
                favoriteItems.add(new Food(CookingRecipe.pizza));
                this.position = new Position(120, 133);
                setTextureKey("JAS");
            }
            case "Abigail" -> {
                this.job = "Miner";
                favoriteItems.add(new Material(MaterialType.stone));
                favoriteItems.add(new Material(MaterialType.ironOre));
                favoriteItems.add(new Material(MaterialType.coffee));
                this.position = new Position(124, 133);
                setTextureKey("JODI");
            }
            case "Harvey" -> {
                this.job = "GarbageSeller";
                favoriteItems.add(new Material(MaterialType.coffee));
                favoriteItems.add(new Material(MaterialType.pickle));
                favoriteItems.add(new Material(MaterialType.wine));
                this.position = new Position(128, 133);
                setTextureKey("KENT");
            }
            case "Lia" -> {
                this.job = "Chef";
                favoriteItems.add(new Crop(CropType.grape));
                favoriteItems.add(new Material(MaterialType.wine));
                this.position = new Position(132, 133);
                setTextureKey("LEO");
            }
            case "Robin" -> {
                this.job = "RandomSeller";
                favoriteItems.add(new Food(CookingRecipe.pizza));
                favoriteItems.add(new Material(MaterialType.wood));
                favoriteItems.add(new Material(MaterialType.ironBar));
                this.position = new Position(136, 133);
                setTextureKey("LEAH");
            }
        }
        restoreTexture(); // initialize texture immediately
    }

    private void setTextureKey(String key) {
        this.textureKey = key;
        this.texture = getTextureByKey(key);
    }

    private Texture getTextureByKey(String key) {
        try {
            return (Texture) GameAssetManager.getInstance().getClass().getField(key).get(GameAssetManager.getInstance());
        } catch (Exception e) {
            throw new RuntimeException("Could not load NPC texture: " + key, e);
        }
    }

    private void restoreTexture() {
        if (textureKey != null) {
            this.texture = getTextureByKey(textureKey);
        }
    }

    // Called automatically after deserialization
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        restoreTexture();
    }

    // -------------------- existing methods --------------------
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

    public String getName() { return name; }
    public String getJob() { return job; }
    public ArrayList<Item> getFavoriteItems() { return favoriteItems; }
    public boolean checkFavorite(Item item) {
        return favoriteItems.stream().anyMatch(fav -> fav.getName().equals(item.getName()));
    }

    public String getDialogue() {
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();
        int level = 0;
        for (NPCFriendship npcFriendship : player.getNPCFriendships()) {
            if (npcFriendship.getNpc().equals(this)) level = npcFriendship.getLevel();
        }
        return dialogue.GetDialogue(game.getTodayWeather(), game.getTime(), level);
    }

    public Texture getTexture() { return texture; }
    public Position getPosition() { return position; }
    public NPCQuest getQuest() { return quest; }
}
