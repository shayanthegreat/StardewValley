package com.StardewValley.Models.Tools;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Player;
import com.badlogic.gdx.graphics.Texture;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum ToolType implements Serializable {
    hoe("Hoe", List.of(
        new LevelInfo("initial", 5, 5, "HOE"),
        new LevelInfo("copper", 4, 4, "COPPER_HOE"),
        new LevelInfo("iron", 3, 3, "STEEL_HOE"),
        new LevelInfo("gold", 2, 2, "GOLD_HOE"),
        new LevelInfo("iridium", 1, 1, "IRIDIUM_HOE")
    )),

    pickaxe("Pickaxe", List.of(
        new LevelInfo("initial", 5, 4, "PICKAXE"),
        new LevelInfo("copper", 4, 3, "COPPER_PICKAXE"),
        new LevelInfo("iron", 3, 2, "STEEL_PICKAXE"),
        new LevelInfo("gold", 2, 1, "GOLD_PICKAXE"),
        new LevelInfo("iridium", 1, 0, "IRIDIUM_PICKAXE")
    )),

    axe("Axe", List.of(
        new LevelInfo("initial", 5, 4, "AXE"),
        new LevelInfo("copper", 4, 3, "COPPER_AXE"),
        new LevelInfo("iron", 3, 2, "STEEL_AXE"),
        new LevelInfo("gold", 2, 1, "GOLD_AXE"),
        new LevelInfo("iridium", 1, 0, "IRIDIUM_AXE")
    )),

    fishingPole("FishingPole", List.of(
        new LevelInfo("training", 8, 8, "BAMBOO_POLE"),
        new LevelInfo("bamboo", 8, 8, "BAMBOO_POLE"),
        new LevelInfo("fiberglass", 6, 6, "BAMBOO_POLE"),
        new LevelInfo("iridium", 4, 4, "BAMBOO_POLE")
    )),

    scythe("Scythe", List.of(
        new LevelInfo("initial", 2, 2, "SCYTHE")
    )),

    milkPail("MilkPail", List.of(
        new LevelInfo("initial", 4, 4, "MILK_PAIL")
    )),

    shear("Shear", List.of(
        new LevelInfo("initial", 4, 4, "SHEARS")
    )),

    wateringCan("WateringCan", List.of(
        new LevelInfo("initial", 5, 5, "WATERING_CAN"),
        new LevelInfo("copper", 4, 4, "COPPER_WATERING_CAN"),
        new LevelInfo("iron", 3, 3, "STEEL_WATERING_CAN"),
        new LevelInfo("gold", 2, 2, "GOLD_WATERING_CAN"),
        new LevelInfo("iridium", 1, 1, "IRIDIUM_WATERING_CAN")
    ));

    private final String name;
    private final ArrayList<LevelInfo> levels;

    ToolType(String name, List<LevelInfo> levels) {
        this.name = name;
        this.levels = new ArrayList<>(levels);
        reloadTextures();
    }

    private void reloadTextures() {
        for (LevelInfo info : levels) {
            info.reloadTexture();
        }
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
        switch (this) {
            case hoe, wateringCan -> {
                if (player.getSkill(SkillType.farming).getLevel() == 4) decrease = 1;
            }
            case pickaxe -> {
                if (player.getSkill(SkillType.mining).getLevel() == 4) decrease = 1;
            }
            case fishingPole -> {
                if (player.getSkill(SkillType.fishing).getLevel() == 4) decrease = 1;
            }
            case axe -> {
                if (player.getSkill(SkillType.foraging).getLevel() == 4) decrease = 1;
            }
        }
        if (success) {
            return Math.max(levels.get(level).successEnergyCost() - decrease, 0);
        } else {
            return Math.max(levels.get(level).failEnergyCost() - decrease, 0);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        reloadTextures();
    }

    public static class LevelInfo implements Serializable {
        private final String levelName;
        private final int successEnergyCost;
        private final int failEnergyCost;
        private transient Texture texture;
        private final String textureKey;

        public LevelInfo(String levelName, int successEnergyCost, int failEnergyCost, String textureKey) {
            this.levelName = levelName;
            this.successEnergyCost = successEnergyCost;
            this.failEnergyCost = failEnergyCost;
            this.textureKey = textureKey;
            this.texture = loadTexture(textureKey);
        }

        private Texture loadTexture(String key) {
            GameAssetManager assets = GameAssetManager.getInstance();
            try {
                return (Texture) assets.getClass().getField(key).get(assets);
            } catch (Exception e) {
                throw new RuntimeException("Could not load texture: " + key, e);
            }
        }

        public String getLevelName() {
            return levelName;
        }

        public void reloadTexture() {
            this.texture = loadTexture(textureKey);
        }

        public Texture texture() {
            return texture;
        }

        public int successEnergyCost() {
            return successEnergyCost;
        }

        public int failEnergyCost() {
            return failEnergyCost;
        }
    }
}
