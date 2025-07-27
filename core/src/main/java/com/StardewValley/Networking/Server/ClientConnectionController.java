package com.StardewValley.Networking.Server;

import com.StardewValley.Models.User;
import com.StardewValley.Networking.Common.ConnectionMessage;
import com.StardewValley.Networking.Common.Lobby;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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
        if(user == null) {
            response = new ConnectionMessage(new HashMap<>(){{
                put("response", "not_found");
            }}, ConnectionMessage.Type.response);
        }
        else {
            response = new ConnectionMessage(new HashMap<>(){{
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

    public void sendLobbies(ConnectionMessage message) {
        ArrayList<String> lobbiesJson = new ArrayList<>();
        for(Lobby lobby : ServerMain.getLobbies()) {
            lobbiesJson.add(ConnectionMessage.lobbyToJson(lobby));
        }
        ConnectionMessage response = new ConnectionMessage(new HashMap<>(){{
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
        for(Lobby lobby : ServerMain.getLobbies()) {
            if(lobby.getName().equals(name)) {
                response = new ConnectionMessage(new HashMap<>(){{
                    put("response", "not_available_name");
                }}, ConnectionMessage.Type.response);

                connection.sendMessage(response);
                return;
            }
        }
        Lobby lobby = new Lobby(name, connection.getUsername(), isPrivate, password, isVisible);
        ServerMain.addLobby(lobby);

        response = new ConnectionMessage(new HashMap<>(){{
            put("response", "ok");
        }}, ConnectionMessage.Type.response);

        connection.sendMessage(response);
    }

    public void joinLobby(ConnectionMessage message) {

    }


}
