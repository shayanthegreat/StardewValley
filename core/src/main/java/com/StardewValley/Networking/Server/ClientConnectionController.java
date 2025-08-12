package com.StardewValley.Networking.Server;

import com.StardewValley.Models.User;
import com.StardewValley.Networking.Client.ServerConnection;
import com.StardewValley.Networking.Common.ConnectionMessage;
import com.StardewValley.Networking.Common.GameDetails;
import com.StardewValley.Networking.Common.Lobby;
import com.StardewValley.Networking.Common.PlayerDetails;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientConnectionController {
    private ClientConnection connection;

    public ClientConnectionController(ClientConnection connection) {
        this.connection = connection;
    }

    public void addUser(ConnectionMessage message) {
        User user = ConnectionMessage.userFromJson(message.getFromBody("user"));
        UserDAO.insertUser(user);
    }

    public void getUser(ConnectionMessage message) {
        String username = message.getFromBody("username");
        User user = UserDAO.getUserByUsername(username);
        ConnectionMessage response;
        if (user == null) {
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "not_found");
            }}, ConnectionMessage.Type.response);
        } else {
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "ok");
                put("user", ConnectionMessage.userToJson(user));
            }}, ConnectionMessage.Type.response);
        }

        connection.sendMessage(response);
    }

    public void informLogin(ConnectionMessage message) {
        String username = message.getFromBody("username");
        connection.setUsername(username);
    }

    public void informLogout(ConnectionMessage message) {
        connection.setUsername("");
    }

    public void sendLobbies(ConnectionMessage message) {
        ArrayList<String> lobbiesJson = new ArrayList<>();
        for (Lobby lobby : ServerMain.getLobbies()) {
            lobbiesJson.add(ConnectionMessage.lobbyToJson(lobby));
        }
        ConnectionMessage response = new ConnectionMessage(new HashMap<>() {{
            put("response", "ok");
            put("lobbies", lobbiesJson);
        }}, ConnectionMessage.Type.response);

        connection.sendMessage(response);
    }

    public void createLobby(ConnectionMessage message) {
        String name = message.getFromBody("name");
        Boolean isPrivate = message.getFromBody("isPrivate");
        String password = message.getFromBody("password");
        Boolean isVisible = message.getFromBody("isVisible");

        ConnectionMessage response;
        for (Lobby lobby : ServerMain.getLobbies()) {
            if (lobby.getName().equals(name)) {
                response = new ConnectionMessage(new HashMap<>() {{
                    put("response", "not_available_name");
                }}, ConnectionMessage.Type.response);

                connection.sendMessage(response);
                return;
            }
        }
        Lobby lobby = new Lobby(name, connection.getUsername(), isPrivate, password, isVisible);
        ServerMain.addLobby(lobby);
        connection.setLobbyCode(lobby.getCode());

        response = new ConnectionMessage(new HashMap<>() {{
            put("response", "ok");
            put("code", lobby.getCode());
        }}, ConnectionMessage.Type.response);

        connection.sendMessage(response);
    }

    public void joinLobby(ConnectionMessage message) {
        String code = message.getFromBody("code");
        String username = connection.getUsername();
        String error = "";
        Lobby lobby = ServerMain.getLobbyByCode(code);
        if (!connection.getLobbyCode().isEmpty()) {
            error = "you are already in a lobby";
        } else if (lobby == null) {
            error = "lobby not found";
        } else if (lobby.getMembers().size() >= 4) {
            error = "lobby is already full";
        }

        ConnectionMessage response;
        if (error.isEmpty()) {
            lobby.addMember(username);
            connection.setLobbyCode(code);
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "ok");
            }}, ConnectionMessage.Type.response);
        } else {
            String finalError = error;
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "error");
                put("error", finalError);
            }}, ConnectionMessage.Type.response);

        }

        connection.sendMessage(response);
    }

    public void leaveLobby(ConnectionMessage message) {
        String code = connection.getLobbyCode();
        String username = connection.getUsername();
        String error = "";
        Lobby lobby = ServerMain.getLobbyByCode(code);
        if (code.isEmpty()) {
            error = "you are not in a lobby";
        }

        ConnectionMessage response;
        if (error.isEmpty()) {
            lobby.removeMember(username);
            connection.setLobbyCode("");
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "ok");
            }}, ConnectionMessage.Type.response);
        } else {
            String finalError = error;
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "error");
                put("error", finalError);
            }}, ConnectionMessage.Type.response);
        }

        connection.sendMessage(response);

    }

    public void informLobbyTermination() {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("information", "lobby_termination");
        }}, ConnectionMessage.Type.inform);

        connection.sendMessage(message);

        connection.setLobbyCode("");
    }

    public void startGame(ConnectionMessage message) {
        int mapId = message.getIntFromBody("map_id");
        String error = "";
        String code = connection.getLobbyCode();
        Lobby lobby = ServerMain.getLobbyByCode(connection.getLobbyCode());
        if (code.isEmpty() || lobby == null) {
            error = "you are not in a lobby";
        } else if (!lobby.getAdminUsername().equals(connection.getUsername())) {
            error = "you are not the admin of the lobby";
//        } else if (lobby.getMembers().size() < 1) {
//            error = "there must be at least two members";
        }

        ConnectionMessage response;
        if (!error.isEmpty()) {
            String finalError = error;
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "error");
                put("error", finalError);
            }}, ConnectionMessage.Type.response);

            connection.sendMessage(response);
            return;
        } else {
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "ok");
            }}, ConnectionMessage.Type.response);

            connection.sendMessage(response);
        }

        GameDetails gameDetails = new GameDetails(lobby.getMembers(), lobby.getAdminUsername());
        ServerMain.addGame(gameDetails);
        String json = ConnectionMessage.gameDetailsToJson(gameDetails);

        System.out.println(lobby.getMembers());
        ArrayList<ClientConnection> connections = new ArrayList<>();
        ArrayList<String> avatarPaths = new ArrayList<>();
        for(String member : lobby.getMembers()) {
            User user = UserDAO.getUserByUsername(member);
            avatarPaths.add(user.getAvatarPath());
        }
        for (String member : lobby.getMembers()) {
            ClientConnection connection = ServerMain.getConnectionByUsername(member);
            connections.add(connection);
            ConnectionMessage information = new ConnectionMessage(new HashMap<>() {{
                put("information", "start_game");
                put("game_details", json);
                put("usernames", lobby.getMembers());
                put("avatar_paths", avatarPaths);
                put("map_id", mapId);
            }}, ConnectionMessage.Type.inform);

            connection.sendMessage(information);
            connection.setInGame(true);
            connection.setGame(gameDetails);
        }

        gameDetails.setConnections(connections);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (connection.getGame().isRunning()) {
                connection.getGame().sendGameDetails();
            } else {
                scheduler.shutdown();
            }
        }, 5, 1, TimeUnit.SECONDS);
//        TODO: do other stuff for game
    }

    public void updateSelf(ConnectionMessage message) {
        String json = message.getFromBody("json");
        PlayerDetails newSelf = ConnectionMessage.playerDetailsFromJson(json);
        newSelf.username = connection.getUsername();
        String username = newSelf.username;
        GameDetails game = connection.getGame();
        if(game.isRunning()){
            game.putPlayerByUsername(username, newSelf);
        }
    }

    public void sendChatMessage(ConnectionMessage message) {
        String text = message.getFromBody("text");
        String sender = message.getFromBody("sender");
        String receiver = message.getFromBody("receiver");
        connection.getGame().getChat().registerNewMessage(text, sender, receiver);
    }

    public void storeItemBought(ConnectionMessage message) {
        String store = message.getFromBody("store");
        String item = message.getFromBody("item");
        int count = message.getIntFromBody("count");

        ConnectionMessage inform = new ConnectionMessage(new HashMap<>() {{
            put("information", "store_item_bought");
            put("store", store);
            put("item", item);
            put("count", count);
        }}, ConnectionMessage.Type.inform);

        for(ClientConnection conn : connection.getGame().getConnections()) {
            if(conn == connection) {
                continue;
            }
            conn.sendMessage(inform);
        }
    }

    public void removeLastUser() {
        UserDAO.removeLastInsertedUser();
    }

    public void getLastUser() {
        User user = UserDAO.getLastUser();
        ConnectionMessage response;
        if (user == null) {
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "not_found");
            }}, ConnectionMessage.Type.response);
        } else {
            response = new ConnectionMessage(new HashMap<>() {{
                put("response", "ok");
                put("user", ConnectionMessage.userToJson(user));
            }}, ConnectionMessage.Type.response);
        }
        connection.sendMessage(response);
    }

    public void sendGift(ConnectionMessage message) {
        String item = message.getFromBody("item");
        String receiver = message.getFromBody("receiver");
        String sender = message.getFromBody("sender");
        ClientConnection connection = ServerMain.getConnectionByUsername(receiver);
        if(connection == null) {
            return;
        }
        ConnectionMessage inform = new ConnectionMessage(new HashMap<>() {{
            put("information", "gift_send");
            put("item", item);
            put("receiver", receiver);
            put("sender", sender);
        }}, ConnectionMessage.Type.inform);
        connection.sendMessage(inform);
    }
}



