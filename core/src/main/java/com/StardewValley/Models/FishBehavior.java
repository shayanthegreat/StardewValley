package com.StardewValley.Models;

import java.util.concurrent.ThreadLocalRandom;

public class FishBehavior {
    private final int id;
    private float lastVelocity = 0f;

    public FishBehavior(int id) {
        this.id = id % 6;
    }

    public float behavior() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        switch (id) {
            case 1:
                int choice = rand.nextInt(3);
                if (choice == 0) return -60f;
                else if (choice == 2) return 60f;
                else return 0f;

            case 2:
                float newVelocity = lastVelocity + rand.nextFloat(-10f, 10f);
                newVelocity = clamp(newVelocity, -40f, 40f);
                lastVelocity = newVelocity;
                return newVelocity;

            case 3:
                return rand.nextFloat(-60f, -20f);

            case 4:
                return rand.nextFloat(20f, 60f);

            case 5:
                return rand.nextFloat(-150f, 150f);


            default:
                return 0f;
        }
    }

    private float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
