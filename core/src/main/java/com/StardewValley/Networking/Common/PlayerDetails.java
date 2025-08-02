package com.StardewValley.Networking.Common;

public class PlayerDetails {
    public String username;
    public int posX;
    public int posY;
    public boolean canSleep;

    public PlayerDetails(String username) {
        this.username = username;
        posX = 0;
        posY = 0;
        canSleep = false;
    }

    public void updateInfo() {
//        TODO: update needed info from game
    }
}
