package com.StardewValley.Models.Map;

import com.StardewValley.Models.Animal.Barn;
import com.StardewValley.Models.Animal.Coop;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Crafting.Material;
import com.StardewValley.Models.Crafting.MaterialType;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Store.Store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Farm implements Serializable {

    private final Position topLeft;
    private final Size size;
    private final ArrayList<Tile> tiles;
    private final Lake lake;
    private final GreenHouse greenHouse;
    private final House house;
    private final Quarry quarry;
//    private final Store RobinShop;
//    private final Store PierreShop;
//    private final Store MarineShop;
    private Barn barn;
    private Coop coop;

    public Farm(FarmMap farmMap, int turn) {
        switch (turn) {
            case 0: {
                topLeft = new Position(0, 0);
                break;
            }
            case 1: {
                topLeft = new Position(0, 150);
                break;
            }
            case 2: {
                topLeft = new Position(150, 0);
                break;
            }
            case 3: {
                topLeft = new Position(150, 150);
                break;
            }
            default: {
                topLeft = new Position(0, 0);
            }
        }
        size = farmMap.getSize();
        tiles = new ArrayList<>();
        // shops
        // TODO all Shops!
//        ArrayList<Tile> PierreTiles = new ArrayList<>();
//        ArrayList<Tile> RobinTiles = new ArrayList<>();
//        ArrayList<Tile> MarineTiles = new ArrayList<>();
        ArrayList<Tile> lakeTiles = new ArrayList<>();
        Position lakeTopLeft = new Position(farmMap.getLakePosition().x, farmMap.getLakePosition().y);
        Position lakeBottomRight = new Position(lakeTopLeft.x + farmMap.getLakeSize().height, lakeTopLeft.y + farmMap.getLakeSize().width);
        lakeTopLeft.move(topLeft);
        lakeBottomRight.move(topLeft);

        ArrayList<Tile> greenHouseTiles = new ArrayList<>();
        Position greenHouseTopLeft = new Position(farmMap.getGreenHousePosition().x, farmMap.getGreenHousePosition().y);
        Position greenHouseBottomRight = new Position(greenHouseTopLeft.x + farmMap.getGreenHouseSize().height, greenHouseTopLeft.y + farmMap.getGreenHouseSize().width);
        greenHouseTopLeft.move(topLeft);
        greenHouseBottomRight.move(topLeft);

        ArrayList<Tile> houseTiles = new ArrayList<>();
        Position houseTopLeft = new Position(farmMap.getHousePosition().x, farmMap.getHousePosition().y);
        Position houseBottomRight = new Position(houseTopLeft.x + farmMap.getHouseSize().height, houseTopLeft.y + farmMap.getHouseSize().width);
        houseTopLeft.move(topLeft);
        houseBottomRight.move(topLeft);

        ArrayList<Tile> quarryTiles = new ArrayList<>();
        Position quarryTopLeft = new Position(farmMap.getQuarryPosition().x, farmMap.getQuarryPosition().y);
        Position quarryBottomRight = new Position(quarryTopLeft.x + farmMap.getQuarrySize().height, quarryTopLeft.y + farmMap.getQuarrySize().width);
        quarryTopLeft.move(topLeft);
        quarryBottomRight.move(topLeft);

        for(int i = topLeft.x; i < topLeft.x + size.height; i++) {
            for(int j = topLeft.y; j < topLeft.y + size.width; j++) {
                Position position = new Position(i, j);
                Tile tile = new Tile(position);
                if(i >= lakeTopLeft.x && i < lakeBottomRight.x
                && j >= lakeTopLeft.y && j < lakeBottomRight.y) {
                    lakeTiles.add(tile);
                }
                else if(i >= greenHouseTopLeft.x && i < greenHouseBottomRight.x
                && j >= greenHouseTopLeft.y && j < greenHouseBottomRight.y) {
                    greenHouseTiles.add(tile);
                }
                else if(i >= houseTopLeft.x && i < houseBottomRight.x
                && j >= houseTopLeft.y && j < houseBottomRight.y) {
                    houseTiles.add(tile);
                }
                else if(i >= quarryTopLeft.x && i < quarryBottomRight.x
                && j >= quarryTopLeft.y && j < quarryBottomRight.y) {
                    quarryTiles.add(tile);
                }
                tiles.add(tile);
                if(i == topLeft.x + 2 && j == topLeft.y + 80) {
                    tile.setObject(new ShippingBin());
                }
            }
        }
        switch (turn) {
            case 0: {
                tiles.add(new Tile(new Position(99, 100)));
                tiles.add(new Tile(new Position(100, 99)));
                break;
            }
            case 1: {
                tiles.add(new Tile(new Position(99, 149)));
                tiles.add(new Tile(new Position(100, 150)));
                break;
            }
            case 2: {
                tiles.add(new Tile(new Position(149, 99)));
                tiles.add(new Tile(new Position(150, 100)));
                break;
            }
            case 3: {
                tiles.add(new Tile(new Position(149, 150)));
                tiles.add(new Tile(new Position(150, 149)));
                break;
            }
        }

        lake = new Lake(lakeTiles, farmMap.getLakeSize());
        for(Tile tile : lakeTiles) {
            tile.setBuilding(lake);
        }

        greenHouse = new GreenHouse(greenHouseTiles, farmMap.getGreenHouseSize());
        for (Tile tile : greenHouseTiles) {
            tile.setBuilding(greenHouse);
        }

        house = new House(houseTiles, farmMap.getHouseSize());
        for(Tile tile : houseTiles) {
            tile.setBuilding(house);
        }

        quarry = new Quarry(quarryTiles, farmMap.getQuarrySize());
        for (Tile tile : quarryTiles) {
            tile.setBuilding(quarry);
        }
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Size getSize() {
        return size;
    }

    public Tile getTile(Position position) {
        for (Tile tile : tiles) {
            if (position.x == tile.getPosition().x && position.y == tile.getPosition().y) {
                return tile;
            }
        }
        return null;
    }

    public void setTile(Position position, Tile newTile) {
        for (int i = 0; i < tiles.size(); i++) {
            if (position.equals(tiles.get(i).getPosition())) {
                tiles.set(i, newTile); // Actually updates the list
                break; // Optional: stop after finding the match
            }
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public Lake getLake() {
        return lake;
    }

    public House getHouse() {
        return house;
    }

    public Quarry getQuarry() {
        return quarry;
    }

    public Barn getBarn() {
        return barn;
    }

    public void setBarn(Barn barn) {
        this.barn = barn;
    }

    public Coop getCoop() {
        return coop;
    }

    public void setCoop(Coop coop) {
        this.coop = coop;
    }


}
