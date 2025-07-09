package com.StardewValley.Models.Communication;

import com.StardewValley.Models.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendShip implements Serializable {
    private Player player;
    private int xp;
    private int level;
    private ArrayList<String> talkLog;
    private ArrayList<Gift> giftLog;
    private ArrayList<Trade> tradeLog;
    private ArrayList<Trade> tradeOffers;
    private String MarriageRequest;
    public FriendShip(Player player) {
        this.player = player;
        this.xp = 0;
        this.level = 0;
        this.talkLog = new ArrayList<>();
        this.giftLog = new ArrayList<>();
        this.tradeLog = new ArrayList<>();
        this.tradeOffers = new ArrayList<>();
    }


    public void setLevel(int level) {
        switch (level){
            case 0:
                this.xp = 0;
                break;
            case 1:
                if(this.level != 1)
                    this.xp = 100;
                break;
            case 2:
                if(this.level != 2)
                    this.xp = 200;
                break;
            case 3:
                if(this.level != 3)
                    this.xp = 300;
                break;
            case 4:
                if(this.level != 4)
                    this.xp = 400;
                break;
        }
        this.level = level;
    }

    public void addTradeOffer(Trade trade) {
        this.tradeOffers.add(trade);
    }

    public Player getPlayer() {
        return player;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public void increaseXp(int amount) {
        xp += amount;
        if( this.level==0&& xp >= 100 && xp <= 200 ) {
            this.level=1;
        }
        else if( this.level==1&&xp >= 200 && xp <= 300 ) {
            this.level=2;
        }
        else if(this.level==2 && xp >= 300 && xp <= 400) {
            this.level=3;
        }
        else if(this.level==3 && xp >= 400 ) {
            this.level=4;
        }
    }

    public void decreaseXp(int amount) {
        xp -= amount;
        if(this.level==1 && xp < 100 ) {
            this.level=0;
        }
        else if( this.level==2 && xp >= 100 && xp < 200 ) {
            this.level=1;
        }
        else if( this.level==3 && xp >= 200 && xp < 300 ) {
            this.level=2;
        }
        else if( this.level==4 && xp >= 300 && xp < 400 ) {
            this.level=3;
        }
    }

    public void addMessage(String message) {
        this.talkLog.add(message);
    }
    public ArrayList<String> getMessageLog() {
        return talkLog;
    }

    public void addGift(Gift gift) {
        this.giftLog.add(gift);
    }
    public ArrayList<Gift> getGiftLog() {
        return giftLog;
    }

    public Trade getById(int id) {
        return this.tradeOffers.get(id-1);
    }
    public void addTrade(Trade trade) {
        this.tradeLog.add(trade);
    }
    public ArrayList<Trade> getTradeLog() {
        return tradeLog;
    }

    public ArrayList<String> getTalkLog() {
        return talkLog;
    }

    public ArrayList<Trade> getTradeOffers() {
        return tradeOffers;
    }

    public void removeTradeOffer(Trade trade) {
        this.tradeOffers.remove(trade);
    }

    public int getTradeId(Trade trade) {
        return (this.tradeOffers.indexOf(trade)+1);
    }

    public void addMarriageRequest(String request) {
        this.MarriageRequest=(request);
    }

    public String getMarriageRequest() {
        return MarriageRequest;
    }
}
