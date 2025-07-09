package com.StardewValley.Models.Map;

import java.io.Serializable;

public class Position implements Serializable {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void move(Position p) {
        move(p.x, p.y);
    }

    public void move(Direction direction) {
        int dx = direction.getDx();
        int dy = direction.getDy();
        move(dx, dy);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position) {
            Position p = (Position)obj;
            return p.x == x && p.y == y;
        }
        return false;
    }
}
