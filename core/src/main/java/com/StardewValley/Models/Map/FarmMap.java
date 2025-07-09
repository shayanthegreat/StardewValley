package com.StardewValley.Models.Map;

import java.io.Serializable;
import java.util.ArrayList;

public enum FarmMap implements Serializable {
    first(1),
    second(2),
    third(3),
    fourth(4),
    ;

    private final int id;

    private final Size size;
    private final Position housePosition;
    private final Size houseSize;
//    4*4
    private final Position greenHousePosition;
    private final Size greenHouseSize;
//    5*6
    private final Position lakePosition;
    private final Size lakeSize;
    private final Position quarryPosition;
    private final Size quarrySize;

    ArrayList<Position> storePositions = new ArrayList<>();

    FarmMap(int id) {
        this.id = id;

        size = new Size(100, 100);

        switch (id) {
            case 1: {
                housePosition = new Position(12, 10);
                houseSize = new Size(4, 4);

                greenHousePosition = new Position(8, 62);
                greenHouseSize = new Size(5, 6);

                lakePosition = new Position(54, 9);
                lakeSize = new Size(5, 5);

                quarryPosition = new Position(78, 80);
                quarrySize = new Size(6, 4);
                break;
            }
            case 2: {
                housePosition = new Position(66, 5);
                houseSize = new Size(4, 4);

                greenHousePosition = new Position(13, 73);
                greenHouseSize = new Size(5, 6);

                lakePosition = new Position(21, 14);
                lakeSize = new Size(4, 5);

                quarryPosition = new Position(83, 65);
                quarrySize = new Size(4, 5);
                break;
            }
            case 3: {
                housePosition = new Position(74, 59);
                houseSize = new Size(4, 4);

                greenHousePosition = new Position(31, 15);
                greenHouseSize = new Size(5, 6);

                lakePosition = new Position(81, 9);
                lakeSize = new Size(6, 5);

                quarryPosition = new Position(22, 68);
                quarrySize = new Size(6, 6);
                break;
            }
            case 4: {
                housePosition = new Position(21, 75);
                houseSize = new Size(4, 4);

                greenHousePosition = new Position(82, 17);
                greenHouseSize = new Size(5, 6);

                lakePosition = new Position(15, 12);
                lakeSize = new Size(6, 5);

                quarryPosition = new Position(58, 73);
                quarrySize = new Size(6, 6);
                break;
            }
            default: {
                housePosition = new Position(0, 0);
                houseSize = new Size(0, 0);
                greenHousePosition = new Position(0, 0);
                greenHouseSize = new Size(0, 0);
                lakePosition = new Position(0, 0);
                lakeSize = new Size(0, 0);
                quarryPosition = new Position(0, 0);
                quarrySize = new Size(0, 0);
                break;
            }
        }
    }

    public static FarmMap getFarmMap(int id) {
        for(FarmMap farmMap : FarmMap.values()) {
            if(id == farmMap.id) {
                return farmMap;
            }
        }
        return null;
    }

    public Size getSize() {
        return size;
    }

    public Position getHousePosition() {
        return housePosition;
    }

    public Size getHouseSize() {
        return houseSize;
    }

    public Position getGreenHousePosition() {
        return greenHousePosition;
    }

    public Size getGreenHouseSize() {
        return greenHouseSize;
    }

    public Position getLakePosition() {
        return lakePosition;
    }

    public Size getLakeSize() {
        return lakeSize;
    }

    public Position getQuarryPosition() {
        return quarryPosition;
    }

    public Size getQuarrySize() {
        return quarrySize;
    }

    public String printMap() {
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < size.height; i++) {
            for(int j = 0; j < size.width; j++) {
                char c;
                if(i >= housePosition.x && i < housePosition.x + houseSize.height
                && j >= housePosition.y && j < housePosition.y + houseSize.width) {
                    c = 'H';
                }
                else if(i >= greenHousePosition.x && i < greenHousePosition.x + greenHouseSize.height
                && j >= greenHousePosition.y && j < greenHousePosition.y + greenHouseSize.width) {
                    c = 'G';
                }
                else if(i >= lakePosition.x && i < lakePosition.x + lakeSize.height
                && j >= lakePosition.y && j < lakePosition.y + lakeSize.width) {
                    c = 'L';
                }
                else if(i >= quarryPosition.x && i < quarryPosition.x + quarrySize.height
                && j >= quarryPosition.y && j < quarryPosition.y + quarrySize.width) {
                    c = 'Q';
                }
                else {
                    c = '.';
                }
                ret.append(c);
            }
            ret.append("\n");
        }
        return ret.toString();
    }
}
