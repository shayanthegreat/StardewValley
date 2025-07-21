package com.StardewValley.Networking.Client;

import java.net.Socket;

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

    public void initConnection(String ip, int port, String serverIp, int serverPort) {
        try {
            Socket socket = new Socket(serverIp, serverPort);
            connection = new ServerConnection(socket, ip, port, serverIp, serverPort);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (connection != null && !connection.isAlive()) {
            connection.start();
        } else {
            throw new IllegalStateException("Tracker connection thread is already running or not set");
        }
    }
}
