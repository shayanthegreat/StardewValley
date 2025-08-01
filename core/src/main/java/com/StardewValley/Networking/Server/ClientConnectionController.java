package com.StardewValley.Networking.Server;

import com.StardewValley.Models.User;
import com.StardewValley.Networking.Client.ServerConnection;
import com.StardewValley.Networking.Common.ConnectionMessage;
import com.StardewValley.Networking.Common.GameDetails;
import com.StardewValley.Networking.Common.Lobby;

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

        response = new ConnectionMessage(new HashMap<>() {{
            put("response", "ok");
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
        String error = "";
        String code = connection.getLobbyCode();
        Lobby lobby = ServerMain.getLobbyByCode(connection.getLobbyCode());
        if (code.isEmpty() || lobby == null) {
            error = "you are not in a lobby";
        } else if (!lobby.getAdminUsername().equals(connection.getUsername())) {
            error = "you are not the admin of the lobby";
        } else if (lobby.getMembers().size() <= 1) {
            error = "there must be at least two members";
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

        ArrayList<ClientConnection> connections = new ArrayList<>();
        for (String member : lobby.getMembers()) {
            ClientConnection connection = ServerMain.getConnectionByUsername(member);
            connections.add(connection);
            ConnectionMessage information = new ConnectionMessage(new HashMap<>() {{
                put("information", "start_game");
                put("members", lobby.getMembers());
                put("admin", lobby.getAdminUsername());
            }}, ConnectionMessage.Type.inform);

            connection.sendMessage(information);
            connection.setInGame(true);
        }

        gameDetails.setConnections(connections);

//        TODO: do other stuff for game
    }
}



