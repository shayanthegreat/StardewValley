package com.StardewValley.Networking.Server;

import com.StardewValley.Networking.Common.Connection;
import com.StardewValley.Networking.Common.ConnectionMessage;
import com.StardewValley.Networking.Common.GameDetails;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientConnection extends Connection {
    private String ip;
    private int port;
    private ClientConnectionController controller;
    private AtomicBoolean exitFlag = new AtomicBoolean(false);
    private long lastRefresh;

    private String username;
    private String lobbyCode;
    private boolean isInGame;
    private GameDetails game;

    protected ClientConnection(Socket socket, String ip, int port) throws IOException {
        super(socket);
        this.ip = ip;
        this.port = port;
        this.lastRefresh = System.currentTimeMillis();
        this.username = "";
        this.lobbyCode = "";
        this.isInGame = false;
        this.controller = new ClientConnectionController(this);
    }

    @Override
    public boolean initialHandshake() {
        if (!refreshStatus()) {
            return false;
        }
        ServerMain.addConnection(this);
        return false;
    }

    @Override
    public void end() {
        exitFlag.set(true);
        super.end();
        ServerMain.removeConnection(this);
    }

    @Override
    protected boolean handleMessage(ConnectionMessage message) {
        if (message.getType().equals(ConnectionMessage.Type.command)) {
            if (message.getFromBody("command").equals("add_user")) {
                controller.addUser(message);
                return true;
            }
            if (message.getFromBody("command").equals("get_user")) {
                controller.getUser(message);
                return true;
            }
            if (message.getFromBody("command").equals("send_lobbies")) {
                controller.sendLobbies(message);
                return true;
            }
            if (message.getFromBody("command").equals("create_lobby")) {
                controller.createLobby(message);
                return true;
            }
            if (message.getFromBody("command").equals("join_lobby")) {
                controller.joinLobby(message);
                return true;
            }
            if (message.getFromBody("command").equals("leave_lobby")) {
                controller.leaveLobby(message);
                return true;
            }
            if (message.getFromBody("command").equals("start_game")) {
                controller.startGame(message);
                return true;
            }
            if(message.getFromBody("command").equals("send_chat_message")) {
                controller.sendChatMessage(message);
                return true;
            }
            if(message.getFromBody("command").equals("store_item_bought")) {
                controller.storeItemBought(message);
                return true;
            }

        }
        if (message.getType().equals(ConnectionMessage.Type.inform)) {
            if (message.getFromBody("information").equals("inform_login")) {
                controller.informLogin(message);
                return true;
            }
            if (message.getFromBody("information").equals("inform_logout")) {
                controller.informLogout(message);
                return true;
            }
        }
        if(message.getType().equals(ConnectionMessage.Type.update)) {
            if(message.getFromBody("update").equals("update_self")) {
                controller.updateSelf(message);
            }
        }
        return false;
    }

    public boolean isEnded() {
        return exitFlag.get();
    }

    public boolean refreshStatus() {
        ConnectionMessage request = new ConnectionMessage(new HashMap<>() {{
            put("command", "status");
        }}, ConnectionMessage.Type.command);
        try {
            ConnectionMessage response = sendAndWaitForResponse(request, TIMEOUT);

            if (response == null) {
                return false;
            }

            setOtherSideIP(response.getFromBody("client_ip"));
            setOtherSidePort(response.getIntFromBody("client_port"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ClientConnectionController getController() {
        return controller;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public GameDetails getGame() {
        return game;
    }

    public void setGame(GameDetails game) {
        this.game = game;
    }

    public long getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(long lastRefresh) {
        this.lastRefresh = lastRefresh;
    }
}
