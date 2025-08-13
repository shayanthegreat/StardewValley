package com.StardewValley.Networking.Common;

import com.StardewValley.Networking.Server.ClientConnection;

import java.util.ArrayList;
import java.util.HashMap;

public class GameDetails {
    private static int availableId = 0;

    private String adminUsername;
    private HashMap<String, PlayerDetails> players;
    private transient ArrayList<ClientConnection> connections;
    private int gameId;
    private boolean isRunning;
    private transient Chat chat;

    public GameDetails(ArrayList<String> usernames, String adminUsername) {
        players = new HashMap<>();
        for (String username : usernames) {
            players.put(username, new PlayerDetails(username));
        }
        this.adminUsername = adminUsername;
        this.gameId = availableId++;
        this.isRunning = true;
        this.chat = new Chat(usernames);
    }

    public GameDetails(ArrayList<String> usernames, String adminUsername, int gameId) {
        players = new HashMap<>();
        for (String username : usernames) {
            players.put(username, new PlayerDetails(username));
        }
        this.adminUsername = adminUsername;
        this.gameId = gameId;
    }

    public GameDetails() {}

    public void sendGameDetails() {
        String json = ConnectionMessage.gameDetailsToJson(this);
        String chatJson = ConnectionMessage.newMessagesToJson(new ArrayList<>(chat.getNewMessages()));
        ConnectionMessage update = new ConnectionMessage(new HashMap<>() {{
            put("update", "update_game");
            put("json", json);
            put("game_code", gameId);
            put("new_messages", chatJson);
        }}, ConnectionMessage.Type.update);
        for(ClientConnection connection : connections) {
            connection.sendMessage(update);
        }
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

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
