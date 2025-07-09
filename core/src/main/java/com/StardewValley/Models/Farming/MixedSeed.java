
package com.StardewValley.Models.Farming;

import com.StardewValley.Models.Enums.Season;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum MixedSeed implements Serializable {
    SPRING(Season.spring, new ArrayList<>(List.of(
            SeedType.cauliflower,
            SeedType.parsnip,
            SeedType.potato,
            SeedType.jazz,
            SeedType.tulipBulb
    ))),
    SUMMER(Season.summer, new ArrayList<>(List.of(
            SeedType.corn,
            SeedType.pepper,
            SeedType.radish,
            SeedType.wheat,
            SeedType.poppy,
            SeedType.sunflower,
            SeedType.spangle
    ))),
    FALL(Season.fall, new ArrayList<>(List.of(
            SeedType.artichoke,
            SeedType.corn,
            SeedType.eggplant,
            SeedType.pumpkin,
            SeedType.sunflower,
            SeedType.fairy
    ))),
    WINTER(Season.winter, new ArrayList<>(List.of(
            SeedType.powdermelon
    )));

    private final Season season;
    private final ArrayList<SeedType> seeds;

    MixedSeed(Season season, ArrayList<SeedType> seeds) {
        this.season = season;
        this.seeds = seeds;
    }

    public ArrayList<SeedType> getSeeds() {
        return seeds;
    }

    public Season getSeason() {
        return season;
    }

    public SeedType getRandomSeed() {
        return seeds.get(ThreadLocalRandom.current().nextInt(seeds.size()));
    }

    public static MixedSeed getMixedSeedBySeason(Season season) {
        for (MixedSeed seed : values()) {
            if (seed.season == season) {
                return seed;
            }
        }
        return null;
    }
}
