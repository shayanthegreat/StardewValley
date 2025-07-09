package com.StardewValley.Models.Map;

import java.io.Serializable;

public enum Direction implements Serializable {
    up("u"),
    down("d"),
    left("l"),
    right("r"),
    upRight("ur"),
    downRight("dr"),
    upLeft("ul"),
    downLeft("dl");

    private String direction;
    private int dx;
    private int dy;

    Direction(String direction) {
        this.direction = direction;
        dx = 0;
        dy = 0;
        switch (direction) {
            case "u": {
                dx = -1;
                dy = 0;
                break;
            }
            case "d": {
                dx = 1;
                dy = 0;
                break;
            }
            case "l": {
                dx = 0;
                dy = -1;
                break;
            }
            case "r": {
                dx = 0;
                dy = 1;
                break;
            }
            case "ur": {
                dx = -1;
                dy = 1;
                break;
            }
            case "ul": {
                dx = -1;
                dy = -1;
                break;
            }
            case "dl": {
                dx = 1;
                dy = -1;
                break;
            }
            case "dr": {
                dx = 1;
                dy = 1;
                break;
            }
        }
    }

    public String getDirection() {
        return direction;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public static Direction getDirection(String direction) {
        for (Direction d : Direction.values()) {
            if (d.getDirection().equals(direction)) {
                return d;
            }
        }
        return null;
    }
}
