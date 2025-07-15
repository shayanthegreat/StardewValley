package com.StardewValley.Models.Tools;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Player;
import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ToolType implements Serializable {
    hoe("Hoe", Arrays.asList(
            new LevelInfo("initial", 5, 5, GameAssetManager.getInstance().HOE),
            new LevelInfo("copper", 4, 4, GameAssetManager.getInstance().COPPER_HOE),
            new LevelInfo("iron", 3, 3, GameAssetManager.getInstance().STEEL_HOE),
            new LevelInfo("gold", 2, 2, GameAssetManager.getInstance().GOLD_HOE),
            new LevelInfo("iridium", 1, 1, GameAssetManager.getInstance().IRIDIUM_HOE)
    )),

    pickaxe("Pickaxe", Arrays.asList(
            new LevelInfo("initial", 5, 4, GameAssetManager.getInstance().PICKAXE),
            new LevelInfo("copper", 4, 3, GameAssetManager.getInstance().COPPER_PICKAXE),
            new LevelInfo("iron", 3, 2, GameAssetManager.getInstance().STEEL_PICKAXE),
            new LevelInfo("gold", 2, 1, GameAssetManager.getInstance().GOLD_PICKAXE),
            new LevelInfo("iridium", 1, 0, GameAssetManager.getInstance().IRIDIUM_PICKAXE)
    )),

    axe("Axe", Arrays.asList(
            new LevelInfo("initial", 5, 4, GameAssetManager.getInstance().AXE),
            new LevelInfo("copper", 4, 3,GameAssetManager.getInstance().COPPER_AXE),
            new LevelInfo("iron", 3, 2, GameAssetManager.getInstance().STEEL_AXE),
            new LevelInfo("gold", 2, 1, GameAssetManager.getInstance().GOLD_AXE),
            new LevelInfo("iridium", 1, 0, GameAssetManager.getInstance().IRIDIUM_AXE)
    )),

    fishingPole("FishingPole", Arrays.asList(
            new LevelInfo("training", 8, 8, GameAssetManager.getInstance().BAMBOO_POLE),
            new LevelInfo("bamboo", 8, 8, GameAssetManager.getInstance().BAMBOO_POLE),
            new LevelInfo("fiberglass", 6, 6, GameAssetManager.getInstance().BAMBOO_POLE),
            new LevelInfo("iridium", 4, 4, GameAssetManager.getInstance().BAMBOO_POLE)
    )),

    scythe("Scythe", List.of(
            new LevelInfo("initial", 2, 2, GameAssetManager.getInstance().SCYTHE)
    )),

    milkPail("MilkPail", List.of(
            new LevelInfo("initial", 4, 4, GameAssetManager.getInstance().MILK_PAIL)
    )),

    shear("Shear", List.of(
            new LevelInfo("initial", 4, 4, GameAssetManager.getInstance().SHEARS)
    )),

    wateringCan("WateringCan", Arrays.asList(
            new LevelInfo("initial", 5, 5, GameAssetManager.getInstance().WATERING_CAN),
            new LevelInfo("copper", 4, 4, GameAssetManager.getInstance().COPPER_WATERING_CAN),
            new LevelInfo("iron", 3, 3, GameAssetManager.getInstance().STEEL_WATERING_CAN),
            new LevelInfo("gold", 2, 2, GameAssetManager.getInstance().GOLD_WATERING_CAN),
            new LevelInfo("iridium", 1, 1, GameAssetManager.getInstance().IRIDIUM_WATERING_CAN)
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

    public Texture getTexture() {
        return levels.get(0).texture();
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
