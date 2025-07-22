package com.StardewValley.Networking.Server;

import com.StardewValley.Models.User;
import com.StardewValley.Networking.Common.ConnectionMessage;

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


}
