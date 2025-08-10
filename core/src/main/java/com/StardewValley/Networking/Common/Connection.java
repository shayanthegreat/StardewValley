package com.StardewValley.Networking.Common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Connection extends Thread {
    public static final int TIMEOUT = 5000;
    private static final int MAX_MESSAGE_SIZE = 10_000_000;

    protected final DataInputStream dataInputStream;
    protected final DataOutputStream dataOutputStream;
    protected final BlockingQueue<ConnectionMessage> receivedMessagesQueue;
    protected String otherSideIP;
    protected int otherSidePort;
    protected Socket socket;
    protected AtomicBoolean end;
    protected boolean initialized = false;

    protected Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.receivedMessagesQueue = new LinkedBlockingQueue<>();
        this.end = new AtomicBoolean(false);
    }

    public synchronized void sendMessage(ConnectionMessage message) {
        String JSONString = message.toJson();
        try {
            byte[] jsonBytes = JSONString.getBytes(StandardCharsets.UTF_8);
            dataOutputStream.writeInt(jsonBytes.length);
            dataOutputStream.write(jsonBytes);
            dataOutputStream.flush();
        } catch (IOException e) {
            System.err.println("Failed to send message: " + e.getMessage());
            end();
        }
    }

    public ConnectionMessage sendAndWaitForResponse(ConnectionMessage message, int timeoutMilli) {
        sendMessage(message);
        try {
            if (initialized) {
                return receivedMessagesQueue.poll(timeoutMilli, TimeUnit.MILLISECONDS);
            }
            socket.setSoTimeout(timeoutMilli);
            String json = readMessage();
            socket.setSoTimeout(0);
            return ConnectionMessage.fromJson(json);
        } catch (EOFException e) {
            System.out.println("Connection closed while waiting for response.");
            return null;
        } catch (Exception e) {
            System.err.println("Request timed out or failed: " + e.getMessage());
            return null;
        }
    }

    protected String readMessage() throws IOException {
        int length = dataInputStream.readInt();
        if (length <= 0 || length > MAX_MESSAGE_SIZE) {
            throw new IOException("Invalid message length: " + length);
        }
        byte[] jsonBytes = new byte[length];
        dataInputStream.readFully(jsonBytes);
        return new String(jsonBytes, StandardCharsets.UTF_8);
    }

    public abstract boolean initialHandshake();

    protected abstract boolean handleMessage(ConnectionMessage message);

    @Override
    public void run() {
        initialized = false;
        if (!initialHandshake()) {
            System.err.println("Initial handshake failed with remote device.");
            end();
            return;
        }

        initialized = true;
        while (!end.get()) {
            try {
                String receivedStr = readMessage();
                ConnectionMessage message = ConnectionMessage.fromJson(receivedStr);
                boolean handled = handleMessage(message);
                if (!handled) {
                    receivedMessagesQueue.put(message);
                }
            } catch (EOFException e) {
                System.out.println("Remote closed the connection.");
                break;
            } catch (Exception e) {
                System.err.println("Connection error: " + e.getMessage());
                break;
            }
        }

        end();
    }

    public void end() {
        end.set(true);
        try {
            socket.close();
        } catch (IOException ignored) {}
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
