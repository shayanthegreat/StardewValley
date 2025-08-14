package com.StardewValley.Networking.Client;

import com.StardewValley.Networking.Common.Connection;
import com.StardewValley.Networking.Common.ConnectionMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerConnection extends Connection {
    private String ip;
    private int port;
    private ServerConnectionController controller = ServerConnectionController.getInstance();

    private AtomicBoolean exitFlag = new AtomicBoolean(false);

    protected ServerConnection(Socket socket, String ip, int port) throws IOException {
        super(socket);
        this.port = port;
        this.ip = ip;
        controller.setConnection(this);
    }

    @Override
    public boolean initialHandshake() {
        try {
            readFrame();
            sendMessage(controller.status());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected synchronized boolean handleMessage(ConnectionMessage message) {
        System.out.println(message.getBody());
        if (message.getType().equals(ConnectionMessage.Type.command)) {
            if (message.getFromBody("command").equals("status")) {
                sendMessage(controller.status());
                return true;
            }
            if(message.getFromBody("command").equals("file_meta")) {
                super.startFileReceiving(message);
                return true;
            }
            if(message.getFromBody("command").equals("file_complete")) {
                super.endFileReceiving();
                controller.saveMusicFile(message);
                return true;
            }
            if(message.getFromBody("command").equals("exit_game")) {
                controller.exitGame(message);
                return true;
            }
        } else if (message.getType().equals(ConnectionMessage.Type.inform)) {
            if (message.getFromBody("information").equals("lobby_termination")) {
                controller.lobbyTerminated(message);
                return true;
            }
            if (message.getFromBody("information").equals("start_game")) {
                controller.gameStarted(message);
                return true;
            }
            if (message.getFromBody("information").equals("online_users")) {
                controller.updateOnlineUsers(message);
                return true;
            }
            if (message.getFromBody("information").equals("store_item_bought")) {
                controller.updateStoreItems(message);
                return true;
            }
            if(message.getFromBody("information").equals("gift_send")) {
                controller.addGift(message);
                return true;
            }
            if (message.getFromBody("information").equals("load_game")) {
                controller.gameLoaded(message);
                return true;
            }

        }
        if(message.getType().equals(ConnectionMessage.Type.update)) {
            if(message.getFromBody("update").equals("update_game")) {
                controller.updateGame(message);
                return true;
            }
        }
        return false;

    }

    @Override
    public void end() {
        exitFlag.set(true);
        super.end();
    }

    public boolean isEnded() {
        return exitFlag.get();
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public ServerConnectionController getController() {
        return controller;
    }
}
