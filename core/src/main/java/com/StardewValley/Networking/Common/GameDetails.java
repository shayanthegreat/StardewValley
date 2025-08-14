package com.StardewValley.Networking.Common;

import com.StardewValley.Networking.Server.ClientConnection;
import com.StardewValley.Networking.Server.GameDAO;
import com.StardewValley.Networking.Server.ServerMain;

import java.util.ArrayList;
import java.util.HashMap;

public class GameDetails {

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
        this.isRunning = true;
        this.chat = new Chat(usernames);
        this.gameId = GameDAO.insertGame(this);
    }

    public GameDetails(ArrayList<String> usernames, String adminUsername, int gameId) {
        players = new HashMap<>();
        for (String username : usernames) {
            players.put(username, new PlayerDetails(username));
        }
        this.adminUsername = adminUsername;
        this.gameId = gameId;
    }

    public GameDetails() {
    }

    public void sendGameDetails() {
        String json = ConnectionMessage.gameDetailsToJson(this);
        String chatJson = ConnectionMessage.newMessagesToJson(new ArrayList<>(chat.getNewMessages()));
        ConnectionMessage update = new ConnectionMessage(new HashMap<>() {{
            put("update", "update_game");
            put("json", json);
            put("game_code", gameId);
            put("new_messages", chatJson);
        }}, ConnectionMessage.Type.update);
        for (ClientConnection connection : connections) {
            if (connection.isAlive()) {
                connection.sendMessage(update);
            }
        }
    }

    public void saveAndExit() {
        ConnectionMessage command = new ConnectionMessage(new HashMap<>() {{
            put("command", "exit_game");
        }}, ConnectionMessage.Type.command);
        for (ClientConnection connection : connections) {
            if (connection.isAlive() && !connection.isEnded()) {
                connection.sendMessage(command);
                connection.setInGame(false);
                connection.setGame(null);
            }
        }

        GameDAO.updateGame(this);
    }

    public void setDefaultReadies() {
        for (PlayerDetails player : players.values()) {
            player.isReady = false;
        }
    }

    public boolean isReady() {
        for (PlayerDetails player : players.values()) {
            if (!player.isReady) {
                return false;
            }
        }
        return true;
    }

    public void setPlayerReady(String username) {
        players.get(username).isReady = true;
        if (isReady()) {
            GameDetails gameDetails = this;
            ConnectionMessage inform = new ConnectionMessage(new HashMap<>() {{
                put("information", "load_game");
                put("usernames", players.keySet());
                put("game_details", ConnectionMessage.gameDetailsToJson(gameDetails));
            }}, ConnectionMessage.Type.inform);
            connections = new ArrayList<>();
            for(String user : players.keySet()) {
                ClientConnection connection = ServerMain.getConnectionByUsername(user);
                connections.add(connection);
                if (connection.isAlive() && !connection.isEnded()) {
                    if(players.get(user).data != null) {
                        System.out.println(players.get(user).data);
                    }
                    else {
                        System.out.println("SOOOOORRRRYYYY");
                    }
                    inform.getBody().put("data", players.get(user).data);
                    connection.sendMessage(inform);
                }
            }
        }
    }

    public void setPlayerNotReady(String username) {
        players.get(username).isReady = false;
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
