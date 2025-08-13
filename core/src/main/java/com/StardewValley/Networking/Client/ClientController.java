package com.StardewValley.Networking.Client;

import com.StardewValley.Controllers.GameController;
import com.StardewValley.Models.App;
import com.StardewValley.Models.User;
import com.StardewValley.Networking.Common.ConnectionMessage;
import com.StardewValley.Networking.Common.Lobby;
import com.StardewValley.Networking.Common.Reaction;

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
            data.connection = connection;
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
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("command", "add_user");
            put("user", ConnectionMessage.userToJson(user));
        }}, ConnectionMessage.Type.command);

        connection.sendMessage(message);
    }

    public User getUserFromDB(String username) {
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "get_user");
            put("username", username);
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);

        if (response.getFromBody("response").equals("not_found")) {
            return null;
        }
        return ConnectionMessage.userFromJson(response.getFromBody("user"));
    }

    public void informLogin(String username) {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("information", "inform_login");
            put("username", username);
        }}, ConnectionMessage.Type.inform);

        connection.sendMessage(message);
    }

    public void informLogout() {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("information", "inform_logout");
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
        for (String json : lobbiesJson) {
            data.lobbies.add(ConnectionMessage.lobbyFromJson(json));
        }
        for (Lobby lobby : data.lobbies) {
            if (lobby.getCode().equals(data.lobbyCode)) {
                App.getInstance().getCurrentUser().setLobby(lobby);
            }
        }
    }

    public boolean createLobby(String name, boolean isPrivate, String password, boolean isVisible) {
        if (!isPrivate) password = "";
        String finalPassword = (isPrivate ? password : "");
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "create_lobby");
            put("name", name);
            put("isPrivate", isPrivate);
            put("password", finalPassword);
            put("isVisible", isVisible);
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);

        if (response.getFromBody("response").equals("ok")) {
            data.lobbyCode = response.getFromBody("code");
            refreshLobbies();

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
        if (response.getFromBody("response").equals("ok")) {
            data.lobbyCode = code;
            refreshLobbies();
            return "joined successfully";
        } else {
            return response.getFromBody("error");
        }
    }

    public String leaveLobby() {
        String code = data.lobbyCode;
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "leave_lobby");
            put("code", code);
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);
        if (response.getFromBody("response").equals("ok")) {
            data.lobbyCode = "";
            refreshLobbies();
            return "leaved successfully";
        } else {
            return response.getFromBody("error");
        }
    }

    public String startGame(int mapId) {
//        refreshLobbies();
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "start_game");
            put("map_id", mapId);
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);
        if (response.getFromBody("response").equals("ok")) {
            return "game started successfully";
        } else {
            return response.getFromBody("error");
        }
    }

    public boolean setReaction(String text) {
        if(!Reaction.isValid(text)) {
            return false;
        }
        Reaction reaction = new Reaction(text, System.currentTimeMillis());
        data.selfDetails.reaction = reaction;
        data.gameDetails.getPlayerByUsername(App.getInstance().getCurrentUser().getUsername()).reaction = reaction;
        return true;
    }

    public boolean setDefaultReaction(String text) {
        if(!Reaction.isValid(text)) {
            return false;
        }
        Reaction.addDefault(text);
        return true;
    }

    public void sendChatMessage(String text, String receiver) {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("command", "send_chat_message");
            put("text", text);
            put("sender", App.getInstance().getCurrentUser().getUsername());
            put("receiver", receiver);
        }}, ConnectionMessage.Type.command);

        connection.sendMessage(message);
    }

    public void storeItemBought(String storeName, String itemName, int count) {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("command", "store_item_bought");
            put("store", storeName);
            put("item", itemName);
            put("count", count);
        }}, ConnectionMessage.Type.command);

        connection.sendMessage(message);
    }

    public void removeLastUser(){
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("command", "remove_last_user");
        }}, ConnectionMessage.Type.command);

        connection.sendMessage(message);
    }

    public User getLastUser() {
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "get_last_user");
        }}, ConnectionMessage.Type.command);

        ConnectionMessage response = connection.sendAndWaitForResponse(request, TIMEOUT);

        if (response.getFromBody("response").equals("not_found")) {
            return null;
        }
        return ConnectionMessage.userFromJson(response.getFromBody("user"));

    }

    public void sendGift(String itemName , String receiverUsername , String senderUsername ) {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("command", "gift_send");
            put("item", itemName);
            put("receiver", receiverUsername);
            put("sender", senderUsername);
        }}, ConnectionMessage.Type.command);

        connection.sendMessage(message);
    }
}
