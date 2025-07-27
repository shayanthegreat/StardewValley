package com.StardewValley.Networking.Client;

import com.StardewValley.Models.App;
import com.StardewValley.Models.User;
import com.StardewValley.Networking.Common.Connection;
import com.StardewValley.Networking.Common.ConnectionMessage;
import com.StardewValley.Networking.Common.Lobby;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static com.StardewValley.Networking.Common.Connection.TIMEOUT;

public class ClientController {
    private static ClientController instance;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    private ServerConnection connection = null;
    private ClientData data = ClientData.getInstance();

    public void initConnection(String ip, int port, String serverIp, int serverPort) {
        try {
            Socket socket = new Socket(serverIp, serverPort);
            connection = new ServerConnection(socket, ip, port);
            connection.setOtherSideIP(serverIp);
            connection.setOtherSidePort(serverPort);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (connection != null && !connection.isAlive()) {
            connection.start();
        } else {
            throw new IllegalStateException("Tracker connection thread is already running or not set");
        }
    }

    public void addUserToDB(User user) {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>(){{
            put("command", "add_user");
            put("user", ConnectionMessage.userToJson(user));
        }}, ConnectionMessage.Type.command);

        connection.sendMessage(message);
    }

    public User getUserFromDB(String username) {
        ConnectionMessage request = new ConnectionMessage(new HashMap<>(){{
            put("command", "get_user");
            put("username", username);
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);

        if(response.getFromBody("response").equals("not_found")) {
            return null;
        }
        return ConnectionMessage.userFromJson(response.getFromBody("user"));
    }

    public void informLogin(String username) {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>(){{
            put("information", "inform_login");
            put("username", username);
        }}, ConnectionMessage.Type.inform);

        connection.sendMessage(message);
    }

    public void refreshLobbies() {
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "send_lobbies");
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);

        ArrayList<String> lobbiesJson = response.getFromBody("lobbies");
        data.lobbies.clear();
        for(String json : lobbiesJson) {
            data.lobbies.add(ConnectionMessage.lobbyFromJson(json));
        }
    }

    public boolean createLobby(String name, boolean isPrivate, String password, boolean isVisible) {
        if(!isPrivate) password = "";
        String finalPassword = (isPrivate ? password : "");
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "create_lobby");
            put("name", name);
            put("isPrivate", isPrivate);
            put("password", finalPassword);
            put("isVisible", isVisible);
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);

        if(response.getFromBody("response").equals("ok")) {
            refreshLobbies();
            for(Lobby lobby : data.lobbies) {
                if(lobby.getName().equals(name)) {
                    App.getInstance().getCurrentUser().setLobby(lobby);
                }
            }
            return true;
        }
        return false;
    }

    public String joinLobby(String code) {
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "join_lobby");
            put("code", code);
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);
        if(response.getFromBody("response").equals("ok")) {
            data.lobbyCode = code;
            return "joined successfully";
        }
        else {
            return response.getFromBody("error");
        }
    }
}
