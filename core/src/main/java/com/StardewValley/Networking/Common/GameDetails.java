package com.StardewValley.Networking.Common;

import com.StardewValley.Networking.Server.ClientConnection;

import java.util.ArrayList;
import java.util.HashMap;

public class GameDetails {
    private static int availableId = 0;

    private String adminUsername;
    private HashMap<String, PlayerDetails> players;
    private ArrayList<ClientConnection> connections;
    private int gameId;

    public GameDetails(ArrayList<String> usernames, String adminUsername) {
        players = new HashMap<>();
        for (String username : usernames) {
            players.put(username, new PlayerDetails(username));
        }
        this.adminUsername = adminUsername;
        this.gameId = availableId++;
    }

    public GameDetails(ArrayList<String> usernames, String adminUsername, int gameId) {
        players = new HashMap<>();
        for (String username : usernames) {
            players.put(username, new PlayerDetails(username));
        }
        this.adminUsername = adminUsername;
        this.gameId = gameId;
    }

    public PlayerDetails getPlayerByUsername(String username) {
        return players.get(username);
    }

    public void putPlayerByUsername(String username, PlayerDetails player) {
        players.put(username, player);
    }

    public int playerCount() {
        return players.size();
    }

    public HashMap<String, PlayerDetails> getPlayers() {
        return players;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public ArrayList<ClientConnection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<ClientConnection> connections) {
        this.connections = connections;
    }
}
