package com.StardewValley.Models.Communication;

import java.io.Serializable;

public class NPCFriendship implements Serializable {
    private int xp;
    private int level;
    private NPC npc;

    public NPCFriendship(NPC npc) {
        this.npc = npc;
    }

    public int getXp() {
        return xp;
    }

    public void increaseXp(int amount) {
        xp += amount;
        if(level == 0 && xp >= 200){
            level = 1;
        }
        else if(level == 1 && xp >= 400){
            level = 2;
        }
        else if(level == 2 && xp >= 600){
            level = 3;
        }
        else if(xp >= 799){
         xp = 799;
        }
    }

    public void decreaseXp(int amount) {
        xp -= amount;
        if(this.level==1 && xp < 200 ) {
            this.level=0;
        }
        else if( this.level==2 && xp >= 200 && xp < 400 ) {
            this.level=1;
        }
        else if( this.level==3 && xp >= 400 && xp < 600 ) {
            this.level=2;
        }
        else if(xp < 0){
            xp = 0;
        }
    }

    public int getLevel() {
        return level;
    }

    public NPC getNpc() {
        return npc;
    }

    public void addLevel(){
        if(level == 3){
            xp = 799;
        }
        else{
            level++;
            this.xp = 200 * level;
        }
    }
}
