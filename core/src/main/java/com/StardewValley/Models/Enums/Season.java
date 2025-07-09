package com.StardewValley.Models.Enums;

import java.io.Serializable;

public enum Season implements Serializable {
    spring,
    summer,
    fall,
    winter,
    ;
    private int id;
    Season() {
        this.id = this.ordinal();
    }

    public int getId() {
        return id;
    }

    public Season getNext() {
        Season[] values = Season.values();
        return values[(this.ordinal() + 1) % values.length];
    }

}
