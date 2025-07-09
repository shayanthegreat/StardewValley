package com.StardewValley.Models.Communication;

import com.StardewValley.Models.Item;
import com.StardewValley.Models.Player;

import java.io.Serializable;

public class Trade implements Serializable {
    private Player player;
    private boolean type; // 0: offer 1: request
    private Item item;
    private int amount;
    private int price;
    private Item targetItem;
    private int targetAmount;

    public Trade(Player player, boolean type, Item item, int amount, int price) {
        this.player = player;
        this.type = type;
        this.item = item;
        this.amount = amount;
        this.price = price;
        this.targetItem = null;
        this.targetAmount = 0;
    }

    public Trade(Player player, boolean type, Item item, Item targetItem, int targetAmount, int amount) {
        this.player = player;
        this.type = type;
        this.item = item;
        this.targetItem = targetItem;
        this.targetAmount = targetAmount;
        this.amount = amount;
        this.price = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public Item getTargetItem() {
        return targetItem;
    }

    public int getTargetAmount() {
        return targetAmount;
    }

    public void doTrade(Player player1, Player player2) {
        if(!type){
            if(price == 0){
                player1.getBackPack().removeItem(item, amount);
                player2.getBackPack().addItem(item, amount);
                player2.getBackPack().removeItem(targetItem, targetAmount);
                player2.getBackPack().addItem(targetItem, targetAmount);
            }
            else{
                player1.getBackPack().removeItem(item, amount);
                player2.getBackPack().addItem(item, amount);
                player2.addMoney(price);
            }
        }
        else{
            player2.getBackPack().removeItem(item, amount);
            player1.getBackPack().addItem(item, amount);
            player1.decreaseMoney(price);
        }
    }

    public boolean getType(){
        return type;
    }

}
