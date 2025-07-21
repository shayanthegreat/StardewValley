package com.StardewValley.Networking.Server;

import com.StardewValley.Networking.Common.Connection;
import com.StardewValley.Networking.Common.ConnectionMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ClientConnection extends Connection {
    private String ip;
    private int port;
    private String username;
    private ClientConnectionController controller;

    private boolean exitFlag = false;

    protected ClientConnection(Socket socket, String ip, int port, String clientIp, int clienPort) throws IOException {
        super(socket, clientIp, clienPort);
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
        super.end();
        ServerMain.removeConnection(this);
    }

    @Override
    protected boolean handleMessage(ConnectionMessage message) {
//        TODO: handle different messages
        return false;
    }

    public void terminate() {
        exitFlag = true;
        super.end();
    }

    public boolean isEnded() {
        return exitFlag;
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
}
