package com.StardewValley.Models.Tools;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ToolType implements Serializable {
    hoe("Hoe", Arrays.asList(
            new LevelInfo("initial", 5, 5),
            new LevelInfo("copper", 4, 4),
            new LevelInfo("iron", 3, 3),
            new LevelInfo("gold", 2, 2),
            new LevelInfo("iridium", 1, 1)
    )),

    pickaxe("Pickaxe", Arrays.asList(
            new LevelInfo("initial", 5, 4),
            new LevelInfo("copper", 4, 3),
            new LevelInfo("iron", 3, 2),
            new LevelInfo("gold", 2, 1),
            new LevelInfo("iridium", 1, 0)
    )),

    axe("Axe", Arrays.asList(
            new LevelInfo("initial", 5, 4),
            new LevelInfo("copper", 4, 3),
            new LevelInfo("iron", 3, 2),
            new LevelInfo("gold", 2, 1),
            new LevelInfo("iridium", 1, 0)
    )),

    fishingPole("FishingPole", Arrays.asList(
            new LevelInfo("training", 8, 8),
            new LevelInfo("bamboo", 8, 8),
            new LevelInfo("fiberglass", 6, 6),
            new LevelInfo("iridium", 4, 4)
    )),

    scythe("Scythe", List.of(
            new LevelInfo("initial", 2, 2)
    )),

    milkPail("MilkPail", List.of(
            new LevelInfo("initial", 4, 4)
    )),

    shear("Shear", List.of(
            new LevelInfo("initial", 4, 4)
    )),

    wateringCan("WateringCan", Arrays.asList(
            new LevelInfo("initial", 5, 5),
            new LevelInfo("copper", 4, 4),
            new LevelInfo("iron", 3, 3),
            new LevelInfo("gold", 2, 2),
            new LevelInfo("iridium", 1, 1)
    ));

    private final String name;
    private final ArrayList<LevelInfo> levels;

    ToolType(String name, List<LevelInfo> levels) {
        this.name = name;
        this.levels = new ArrayList<>(levels);
    }

    public LevelInfo getLevel(int level) {
        return levels.get(level);
    }

    public String getName() {
        return name;
    }

    public static ToolType getToolTypeByName(String name) {
        for (ToolType toolType : ToolType.values()) {
            if (toolType.getName().equals(name)) {
                return toolType;
            }
        }
        return null;
    }

    public ArrayList<LevelInfo> getLevels() {
        return levels;
    }

    public int getEnergy(int level, boolean success) {
        int decrease = 0;
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        switch (this){
            case hoe:case wateringCan:{
                if(player.getSkill(SkillType.farming).getLevel() == 4){
                    decrease = 1;
                }
                break;
            }
            case pickaxe:{
                if(player.getSkill(SkillType.mining).getLevel() == 4){
                    decrease = 1;
                }
                break;
            }
            case fishingPole:{
                if(player.getSkill(SkillType.fishing).getLevel() == 4){
                    decrease = 1;
                }
                break;
            }
            case axe:{
                if(player.getSkill(SkillType.foraging).getLevel() == 4){
                    decrease = 1;
                }
                break;
            }
        }
        if(success){
            return Math.max(levels.get(level).successEnergyCost()-decrease, 0);
        }
        else{
            return Math.max(levels.get(level).failEnergyCost()-decrease, 0);
        }
    }
}
