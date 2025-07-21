package com.StardewValley.Networking.Common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Connection extends Thread {
    public static final int TIMEOUT = 2000;

    protected final DataInputStream dataInputStream;
    protected final DataOutputStream dataOutputStream;
    protected final BlockingQueue<ConnectionMessage> receivedMessagesQueue;
    protected String otherSideIP;
    protected int otherSidePort;
    protected Socket socket;
    protected AtomicBoolean end;
    protected boolean initialized = false;

    protected Connection(Socket socket, String otherSideIP, int otherSidePort) throws IOException {
        this.otherSidePort = otherSidePort;
        this.otherSideIP = otherSideIP;
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.receivedMessagesQueue = new LinkedBlockingQueue<>();
        this.end = new AtomicBoolean(false);
    }

    public synchronized void sendMessage(ConnectionMessage message) {
        String JSONString = message.toJson();

        try {
            dataOutputStream.writeUTF(JSONString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionMessage sendAndWaitForResponse(ConnectionMessage message, int timeoutMilli) {
        sendMessage(message);
        try {
            if (initialized) return receivedMessagesQueue.poll(timeoutMilli, TimeUnit.MILLISECONDS);
            socket.setSoTimeout(timeoutMilli);
            var result = ConnectionMessage.fromJson(dataInputStream.readUTF());
            socket.setSoTimeout(0);
            return result;
        } catch (Exception e) {
            System.err.println("Request Timed out.");
            return null;
        }
    }

    abstract public boolean initialHandshake();

    abstract protected boolean handleMessage(ConnectionMessage message);
    @Override
    public void run() {
        initialized = false;
        if (!initialHandshake()) {
            System.err.println("Initial HandShake failed with remote device.");
            end();
            return;
        }

        initialized = true;
        while (!end.get()) {
            try {
                String receivedStr = dataInputStream.readUTF();
                ConnectionMessage message = ConnectionMessage.fromJson(receivedStr);
                boolean handled = handleMessage(message);
                if (!handled) try {
                    receivedMessagesQueue.put(message);
                } catch (InterruptedException e) {}
            } catch (Exception e) {
                break;
            }
        }

        end();
    }

    public void end() {
        end.set(true);
        try {
            socket.close();
        } catch (IOException e) {}
    }

    public String getOtherSideIP() {
        return otherSideIP;
    }

    public void setOtherSideIP(String otherSideIP) {
        this.otherSideIP = otherSideIP;
    }

    public int getOtherSidePort() {
        return otherSidePort;
    }

    public void setOtherSidePort(int otherSidePort) {
        this.otherSidePort = otherSidePort;
    }
}
