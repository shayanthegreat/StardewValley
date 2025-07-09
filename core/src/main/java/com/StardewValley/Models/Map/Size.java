package com.StardewValley.Models.Map;

import java.io.Serializable;

public class Size implements Serializable {
    public int height;
    public int width;

    public Size(int height, int width) {
        this.height = height;
        this.width = width;
    }
}
