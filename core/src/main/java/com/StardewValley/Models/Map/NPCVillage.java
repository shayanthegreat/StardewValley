package com.StardewValley.Models.Map;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Store.Store;

import java.io.Serializable;
import java.util.ArrayList;

public class NPCVillage implements Serializable {
    private final Position topLeft;
    private final Size size;
    private final ArrayList<Tile> tiles;
    private ArrayList<Store> stores;
    private final ArrayList<Position> storePositions;

    public NPCVillage() {
        stores = new ArrayList<>();
        stores.add(new Store("Clint"));
        stores.add(new Store("Morris"));
        stores.add(new Store("Pierre"));
        stores.add(new Store("Robin"));
        stores.add(new Store("Willy"));
        stores.add(new Store("Marnie"));
        stores.add(new Store("Gus"));

        storePositions = new ArrayList<>();
        storePositions.add(new Position(105, 125));
        storePositions.add(new Position(110, 125));
        storePositions.add(new Position(115, 125));
        storePositions.add(new Position(120, 125));
        storePositions.add(new Position(125, 125));
        storePositions.add(new Position(130, 125));
        storePositions.add(new Position(135, 125));

        topLeft = new Position(100, 100);
        size = new Size(50, 50);
        tiles = new ArrayList<>();
        for(int i = 100; i < 150; i++) {
            for(int j = 100; j < 150; j++) {
                Tile tile = new Tile(new Position(i, j));
                for(int k = 0; k < stores.size(); k++) {
                    if(i == storePositions.get(k).x && j == storePositions.get(k).y) {
                        tile.setBuilding(stores.get(k));
                    }
                }
                tiles.add(tile);
            }
        }
    }

    public void refreshShop() {
        ArrayList<Store> newStores = new ArrayList<>();
        newStores.add(new Store("Clint"));
        newStores.add(new Store("Morris"));
        newStores.add(new Store("Pierre"));
        newStores.add(new Store("Robin"));
        newStores.add(new Store("Willy"));
        newStores.add(new Store("Marnie"));
        newStores.add(new Store("Gus"));

        stores = newStores;

        for(int i = 0; i < stores.size(); i++) {
            getTile(storePositions.get(i)).setBuilding(stores.get(i));
        }
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Size getSize() {
        return size;
    }

    public Tile getTile(Position position) {
        for(Tile tile : tiles) {
            if(tile.getPosition().equals(position)) {
                return tile;
            }
        }
        return null;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public ArrayList<Position> getStorePositions() {
        return storePositions;
    }
}
