package com.StardewValley.Networking.Server;

import com.StardewValley.Networking.Common.Connection;
import com.StardewValley.Networking.Common.ConnectionMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientConnection extends Connection {
    private String ip;
    private int port;
    private String username;
    private ClientConnectionController controller;

    private AtomicBoolean exitFlag = new AtomicBoolean(false);

    protected ClientConnection(Socket socket, String ip, int port) throws IOException {
        super(socket);
        this.ip = ip;
        this.port = port;
        this.username = null;
        this.controller = new ClientConnectionController(this);
    }

    @Override
    public boolean initialHandshake() {
        if(!refreshStatus()) {
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
        if(message.getType().equals(ConnectionMessage.Type.command)) {
            if(message.getFromBody("command").equals("add_user")) {
                controller.addUser(message);
            }
            if(message.getFromBody("command").equals("get_user")) {
                controller.getUser(message);
            }
            if(message.getFromBody("command").equals("inform_login")) {
                controller.informLogin(message);
            }
        }
//        TODO: handle different messages
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

            if(response == null) {
                return false;
            }

            setOtherSideIP(response.getFromBody("client_ip"));
            setOtherSidePort(response.getIntFromBody("client_port"));
            return true;
        }
        catch (Exception e) {
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
}
