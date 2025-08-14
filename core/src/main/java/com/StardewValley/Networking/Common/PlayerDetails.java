package com.StardewValley.Networking.Common;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.Player;

public class PlayerDetails {
    public String username;
    public int posX;
    public int posY;
    public boolean canSleep;
    public Reaction reaction;
    public int questCount;
    public int skillSum;
    public int gold;
    public String data;
    public transient boolean isReady;

    public PlayerDetails(String username) {
        this.username = username;
        posX = 0;
        posY = 0;
        canSleep = false;
        reaction = new Reaction("");
        questCount = 0;
        skillSum = 0;
        gold = 0;
        data = "";
        isReady = false;
    }

    public PlayerDetails() {}

    public void updateInfo() {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        posX = player.getPosition().x;
        posY = player.getPosition().y;
        canSleep = (player.isInHouse() || player.isFainted());
        gold = player.getMoney();
        int x = 0;
        x+=player.getSkill(SkillType.fishing).getAmount();
        x+=player.getSkill(SkillType.farming).getAmount();
        x+=player.getSkill(SkillType.extraction).getAmount();
        x+=player.getSkill(SkillType.mining).getAmount();
        x+=player.getSkill(SkillType.foraging).getAmount();
        skillSum = x;
    }
}
