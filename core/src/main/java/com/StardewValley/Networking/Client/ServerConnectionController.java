package com.StardewValley.Networking.Client;

import com.StardewValley.Networking.Common.ConnectionMessage;

import java.util.HashMap;

public class ServerConnectionController {
    private static ServerConnectionController instance;
    private ServerConnection connection;

    private ServerConnectionController() {
    }

    public static ServerConnectionController getInstance() {
        if (instance == null) {
            instance = new ServerConnectionController();
        }
        return instance;
    }

    public ConnectionMessage status() {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("command", "status");
            put("response", "ok");
            put("client_ip", connection.getIp());
            put("client_port", connection.getPort());
        }}, ConnectionMessage.Type.response);

        return message;
    }

    public void setConnection(ServerConnection connection) {
        this.connection = connection;
    }
}
