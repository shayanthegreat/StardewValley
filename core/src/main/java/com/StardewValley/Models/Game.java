package com.StardewValley.Models;

import com.StardewValley.Models.Communication.NPC;
import com.StardewValley.Models.Crafting.Material;
import com.StardewValley.Models.Crafting.MaterialType;
import com.StardewValley.Models.Animal.Animal;
import com.StardewValley.Models.Enums.InGameMenu;
import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.Enums.Weather;
import com.StardewValley.Models.Farming.Plant;
import com.StardewValley.Models.Map.*;
import com.StardewValley.Models.Store.Store;

import java.io.Serializable;
import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.Random;
import java.util.RandomAccess;

public class Game implements Serializable {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Map map;
    private Time time;
    private Weather todayWeather;
    private Weather tomrrowWeather;
    private InGameMenu inGameMenu;
    public final static Time startingTime = new Time();
    private ArrayList<Store> stores = new ArrayList<>();
    private ArrayList<NPC> npcs = new ArrayList<>();
    public Game(ArrayList<Player> players, Map map) {
        this.players = players;
        this.currentPlayer = players.getFirst();
        this.map = map;
        this.time = new Time();
        this.inGameMenu = null;
        todayWeather = Weather.getRandomWeather(Season.spring);
        tomrrowWeather = Weather.getRandomWeather(Season.spring);
        this.stores = map.getNpcVillage().getStores();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setNextPlayer() {
//        TODO: check if the player is fainted
        int index = players.indexOf(currentPlayer);
        if (index == players.size() - 1) {
            index = 0;
            nextHour();
        } else {
            index++;
        }
        setCurrentPlayer(players.get(index));
    }

    public void nextSeason() {
        time.nextSeason();
    }

    public void nextDay() {


        // todayWeather for nextDay is tomrrowWeather for today!
        todayWeather = tomrrowWeather;
        setTomorrowWeather();
        switch (todayWeather) {
            case rain -> {
                autoWaterPlant();
            }
            case storm -> {
                thor();
                autoWaterPlant();
            }
            case snow -> {
            }
        }

        if (currentPlayer.getFarm().getBarn() != null) {
            for (Animal animal : currentPlayer.getFarm().getBarn().getAnimals()) {
                if (animal.isOutside()) {
                    animal.decreaseFriendship(20);
                }
            }
        }

        if (currentPlayer.getFarm().getCoop() != null) {
            for (Animal animal : currentPlayer.getFarm().getCoop().getAnimals()) {
                if (animal.isOutside()) {
                    animal.decreaseFriendship(20);
                }
            }
        }

        if (currentPlayer.getFarm().getBarn() != null) {
            for (Animal animal : currentPlayer.getFarm().getBarn().getAnimals()) {
                if (!animal.hasBeenFedToday(time)) {
                    animal.decreaseFriendship(20);
                }
            }
        }

        if (currentPlayer.getFarm().getCoop() != null) {
            for (Animal animal : currentPlayer.getFarm().getCoop().getAnimals()) {
                if (!animal.hasBeenFedToday(time)) {
                    animal.decreaseFriendship(20);
                }
            }
        }

        if (currentPlayer.getFarm().getBarn() != null) {
            for (Animal animal : currentPlayer.getFarm().getBarn().getAnimals()) {
                if (!animal.hasBeenPetToday(time)) {
                    animal.decreaseFriendship(20);
                }
            }
        }

        if (currentPlayer.getFarm().getCoop() != null) {
            for (Animal animal : currentPlayer.getFarm().getCoop().getAnimals()) {
                if (animal.hasBeenPetToday(time)) {
                    animal.decreaseFriendship(20);
                }
            }
        }
        // check all plants
        if (time.getDay() == 28) {
            time.nextSeason();
        }
        time.nextDay();


        map.getNpcVillage().refreshShop();
        this.stores = map.getNpcVillage().getStores();

        for (int i = 0; i < 250; i++) {
            for (int j = 0; j < 250; j++) {
                Tile tile = map.getTile(new Position(i, j));
                if (tile != null) {
                    TileObject tileObject = tile.getObject();
                    if (tileObject instanceof Plant) {
                        Plant plant = (Plant) tileObject;
                        if (Math.abs(Time.getDayDifference(plant.getLastWateringTime(), App.getInstance().getCurrentGame().getTime())) > 2) {
                            tile.setObject(null);
                        } else {
                            plant.grow();
                        }
                    }
                }
            }
        }

        for (Player player : players) {
            player.resetEnergy();
        }
        resetMoney();
        crowAttack();
        map.setNewDayForaging();
    }

    public void resetMoney() {
        for (Player player : players) {
            player.addMoney(player.getFeatureMoney());
            player.resetMoney();
        }
    }

    public void nextHour() {
        if (time.getHour() == 22) {
            nextDay();
        }
        time.nextHour();
    }

    public Time getTime() {
        return time;
    }

    public void crowAttack() {
        Random rand = new Random();
        for (int i = 0; i < 250; i++) {
            for (int j = 0; j < 250; j++) {
                Tile tile = map.getTile(new Position(i, j));
                if (tile != null) {
                    if (tile.getBuilding() == null && tile.getObject() instanceof Plant) {
                        Plant plant = (Plant) tile.getObject();
                        if (plant.getGianPosition() == -1) {
                            if (rand.nextInt(64) == 0) {
                                tile.setObject(null);
                            }
                        }
                    }
                }
            }
        }
    }

    public void thor() {
        for (Player player : players) {
            for (int i = 0; i < 3; i++) {
                int x = player.getFarm().getTopLeft().x + 2 + (int) (Math.random() * 90);
                int y = player.getFarm().getTopLeft().y + 2 + (int) (Math.random() * 90);
                thor(new Position(x, y));
            }
        }
    }

    public void thor(Position position) {
        if (map.getTile(position) == null) {
            return;
        }
        Tile tile = map.getTile(position);
        TileObject tileObject = tile.getObject();
        if (tileObject instanceof Plant && tile.getBuilding() == null) {
            tile.setObject(new Material(MaterialType.coal));
        }
    }

    public Weather getTodayWeather() {
        return todayWeather;
    }

    public Weather getTomrrowWeather() {
        return tomrrowWeather;
    }

    public void setTomorrowWeather() {
        if (time.getDay() == 28) {
            tomrrowWeather = Weather.getRandomWeather(time.getSeason().getNext());
        } else {
            tomrrowWeather = Weather.getRandomWeather(time.getSeason());
        }
    }

    public void setTomorrowWeather(Weather tomorrowWeather) {
        this.tomrrowWeather = tomorrowWeather;
    }


    public InGameMenu getInGameMenu() {
        return inGameMenu;
    }

    public void setInGameMenu(InGameMenu inGameMenu) {
        this.inGameMenu = inGameMenu;
    }

    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUser().getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public void autoWaterPlant() {
        for (int i = 0; i < 250; i++) {
            for (int j = 0; j < 250; j++) {
                Tile tile = map.getTile(new Position(i, j));
                if (tile != null) {
                    TileObject tileObject = tile.getObject();
                    if (tileObject instanceof Plant && !(tile.getBuilding() instanceof GreenHouse)) {
                        ((Plant) tileObject).setLastWateringTime(new Time(time));
                    }
                }
            }
        }
    }
}
