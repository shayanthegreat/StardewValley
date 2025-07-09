package com.StardewValley.Models.Crafting;

import com.StardewValley.Models.Enums.SkillType;

import java.io.Serializable;

public class Buff implements Serializable {

    private final SkillType skillType; // null if energy;
    private final int hours;
    private final int energyAddition;

    Buff(SkillType skillType, int hours, int energyAddition) {
        this.skillType = skillType;
        this.hours = hours;
        this.energyAddition = energyAddition;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public int getHours() {
        return hours;
    }

    public int getEnergyAddition() {
        return energyAddition;
    }
}
