package com.StardewValley.Models.Map;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Crafting.Material;
import com.StardewValley.Models.Crafting.MaterialType;
import com.StardewValley.Models.Farming.*;
import com.StardewValley.Models.Game;

import java.io.Serializable;
import java.util.*;

public class Map implements Serializable {
    private ArrayList<Farm> farms;
    private NPCVillage npcVillage;

    public Map(ArrayList<Farm> farms) {
        this.farms = farms;
        this.npcVillage = new NPCVillage();
        setRandomObjects();
    }

    private void setRandomObjects() {
        Random rand = new Random();
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                if(i == 50 && j == 50) {
                    continue;
                }
                Position position = new Position(i, j);
                Map map = this;
                Tile tile = map.getTile(position);
                if(tile != null) {
                    if(tile.isTotallyEmpty()) {
                        int r = rand.nextInt(3000);
                        if(r == 0) {
                            tile.setObject(new Material(MaterialType.coal));
                        }
                        if(r == 1) {
                            tile.setObject(new Material(MaterialType.wood));
                        }
                        if(r == 2) {
                            tile.setObject(new Material(MaterialType.stone));
                        }
                    }
                }
            }
        }
    }

    public void setNewDayForaging() {
        Random rand = new Random();
        for(int i = 0; i < 250; i++) {
            for(int j = 0; j < 250; j++) {
                if(i == 50 && j == 50) {
                    continue;
                }
                Position position = new Position(i, j);
                Map map = this;
                Tile tile = map.getTile(position);
                if(tile != null) {
                    if(tile.isTotallyEmpty()) {
                        int r = rand.nextInt(2000);
                        if(r == 0) {
                            ForagingCropType type = ForagingCropType.getRandomInstance();
                            ForagingCrop crop = new ForagingCrop(type);
                            tile.setObject(crop);
                        }
                        if(r == 1) {
                            SeedType type = MixedSeed.getMixedSeedBySeason(App.getInstance().getCurrentGame().getTime().getSeason()).getRandomSeed();
                            Seed seed = new Seed(type);
                            tile.setObject(seed);
                        }
                        if(r == 2) {
                            tile.setObject(new Material(MaterialType.stone));
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Tile> getPath(Position start, Position end) {
        HashMap<Tile, Tile> father = new HashMap<>();
        ArrayList<Tile> queue = new ArrayList<>();
        Tile startTile = getTile(start);
        Tile endTile = getTile(end);
        if(startTile == null || endTile == null) {
            return null;
        }
        if(App.getInstance().getCurrentGame().getCurrentPlayer().getFarm().getTile(end) == null) {
            if(!(end.x >= 100 && end.x < 150 && end.y >= 100 && end.y < 150)) {
                return null;
            }
        }
        queue.add(startTile);
        father.put(startTile, startTile);
        while (!queue.isEmpty()) {
            Tile tile = queue.removeFirst();
            Position pos = tile.getPosition();
            ArrayList<Tile> neighbors = new ArrayList<>();
            neighbors.add(getTile(new Position(pos.x + 1, pos.y)));
            neighbors.add(getTile(new Position(pos.x - 1, pos.y)));
            neighbors.add(getTile(new Position(pos.x, pos.y + 1)));
            neighbors.add(getTile(new Position(pos.x, pos.y - 1)));
            for(Tile neighbor : neighbors) {
                if(neighbor != null && neighbor.getObject() == null && (neighbor.getBuilding() == null || neighbor.getBuilding() instanceof GreenHouse) && !father.containsKey(neighbor)) {
                    father.put(neighbor, tile);
                    queue.add(neighbor);
                }
            }
            if(father.containsKey(endTile)) {
                break;
            }
        }
        Tile node = endTile;
        ArrayList<Tile> path = new ArrayList<>();
        if (!father.containsKey(node)) {
            return null;
        }
        while (!father.get(node).equals(node)) {
            path.add(node);
            node = father.get(node);
            if(father.get(node) == null) {
                return null;
            }
        }
        Collections.reverse(path);
        return path;
    }

    public Tile getTile(Position position) {
        if((position.x >= 0 && position.x < 100 && position.y >= 0 && position.y < 100)
        || ((position.x == 99 && position.y == 100) || (position.x == 100 && position.y == 99))) {
            if(farms.size() < 1) {
                return null;
            }
            return farms.get(0).getTile(position);
        }
        else if ((position.x >= 0 && position.x < 100 && position.y >= 150 && position.y < 250)
                || ((position.x == 99 && position.y == 149) || (position.x == 100 && position.y == 150))) {
            if(farms.size() < 2) {
                return null;
            }
            return farms.get(1).getTile(position);
        }
        else if((position.x >= 150 && position.x < 250 && position.y >= 0 && position.y < 100)
                || ((position.x == 149 && position.y == 99) || (position.x == 150 && position.y == 100))) {
            if(farms.size() < 3) {
                return null;
            }
            return farms.get(2).getTile(position);
        }
        else if((position.x >= 150 && position.x < 250 && position.y >= 150 && position.y < 250)
                || ((position.x == 149 && position.y == 150) || (position.x == 150 && position.y == 149))) {
            if (farms.size() < 4) {
                return null;
            }
            return farms.get(3).getTile(position);
        }
        else if(position.x >= 100 && position.x < 150 && position.y >= 100 && position.y < 150) {
            return npcVillage.getTile(position);
        }
        return null;
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public NPCVillage getNpcVillage() {
        return npcVillage;
    }
}
