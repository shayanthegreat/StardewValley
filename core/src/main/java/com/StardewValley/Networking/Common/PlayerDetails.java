package com.StardewValley.Networking.Common;

public class PlayerDetails {
    public String username;
    public int posX;
    public int posY;
    public boolean canSleep;
    public Reaction reaction;
    public int questCount;
    public int skillSum;
    public int gold;

    public PlayerDetails(String username) {
        this.username = username;
        posX = 0;
        posY = 0;
        canSleep = false;
        reaction = new Reaction("");
        questCount = 0;
        skillSum = 0;
        gold = 0;
    }

    public PlayerDetails() {}

    public void updateInfo() {
//        TODO: update needed info from game
    }
}
