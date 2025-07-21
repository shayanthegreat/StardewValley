package com.StardewValley.Networking.Client;

import com.StardewValley.Networking.Common.Connection;
import com.StardewValley.Networking.Common.ConnectionMessage;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Connection {
    private String ip;
    private int port;
    private ServerConnectionController controller = ServerConnectionController.getInstance();

    private boolean exitFlag = false;

    protected ServerConnection(Socket socket, String ip, int port, String serverIp, int serverPort) throws IOException {
        super(socket, serverIp, serverPort);
        this.port = port;
        this.ip = ip;
        controller.setConnection(this);
    }

    @Override
    public boolean initialHandshake() {
        try {
            socket.setSoTimeout(TIMEOUT);

            dataInputStream.readUTF();
            sendMessage(controller.status());

            socket.setSoTimeout(0);
            return true;
        } catch (Exception e) {
            return false;
        }
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

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
