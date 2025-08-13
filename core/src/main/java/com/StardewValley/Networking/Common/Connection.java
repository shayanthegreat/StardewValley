package com.StardewValley.Networking.Common;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Connection extends Thread {
    public static final int TIMEOUT = 5000;
    private static final int MAX_JSON_MESSAGE_SIZE = 10_000_000;
    private static final int MAX_FILE_CHUNK_SIZE = 100_000_000;

    protected final DataInputStream dataInputStream;
    protected final DataOutputStream dataOutputStream;
    protected final BlockingQueue<ConnectionMessage> receivedMessagesQueue;
    protected String otherSideIP;
    protected int otherSidePort;
    protected Socket socket;
    protected AtomicBoolean end;
    protected boolean initialized = false;
    private FileOutputStream currentFileOut;
    private long expectedFileSize;
    private long bytesReceived;
    private String currentFileName;


    protected Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.receivedMessagesQueue = new LinkedBlockingQueue<>();
        this.end = new AtomicBoolean(false);
        currentFileOut = null;
        expectedFileSize = 0;
        bytesReceived = 0;
        currentFileName = null;

    }

    public synchronized void sendFrame(int frameType, byte[] data, int offset, int length) throws IOException {
        dataOutputStream.writeInt(frameType);
        dataOutputStream.writeInt(length);
        dataOutputStream.write(data, offset, length);
        dataOutputStream.flush();
    }


    public synchronized void sendMessage(ConnectionMessage message) {
        String JSONString = message.toJson();
        try {
            byte[] jsonBytes = JSONString.getBytes(StandardCharsets.UTF_8);
            sendFrame(1, jsonBytes, 0, jsonBytes.length);
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
            Frame frame = readFrame();
            String json = new String(frame.data, StandardCharsets.UTF_8);
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

    protected Frame readFrame() throws IOException {
        int frameType = dataInputStream.readInt();
        int length = dataInputStream.readInt();

        if (length <= 0) {
            throw new IOException("Invalid payload length: " + length);
        }
        if (frameType == 1) {
            if (length > MAX_JSON_MESSAGE_SIZE) {
                throw new IOException("JSON message too large: " + length);
            }
        } else if (frameType == 2) {
            if (length > MAX_FILE_CHUNK_SIZE) {
                throw new IOException("File chunk too large: " + length);
            }
        } else {
            if (length > MAX_JSON_MESSAGE_SIZE) {
                throw new IOException("Unknown frame type with too large payload: " + length);
            }
        }

        byte[] payload = new byte[length];
        dataInputStream.readFully(payload);
        return new Frame(frameType, payload);
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
                Frame frame = readFrame();
                if (frame.type == 1) {
                    String json = new String(frame.data, StandardCharsets.UTF_8);
                    ConnectionMessage message = ConnectionMessage.fromJson(json);
                    if (!handleMessage(message)) {
                        receivedMessagesQueue.put(message);
                    }
                } else if (frame.type == 2) {
                    handleFileChunk(frame.data);
                } else {
                    System.err.println("Unknown frame type: " + frame.type);
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
        if (currentFileOut != null) {
            System.err.println("Connection ended mid-file transfer. Closing file...");
            endFileReceiving();
        }
        end.set(true);
        try {
            socket.close();
        } catch (IOException ignored) {
        }
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

    public void startFileReceiving(ConnectionMessage message) {
        currentFileName = message.getFromBody("filename");
        expectedFileSize = message.getLongFromBody("filesize");
        bytesReceived = 0;
        File saveDir = new File("temp_receives");
        if (!saveDir.exists()) saveDir.mkdirs();

        try {
            if (currentFileOut != null) {
                currentFileOut.close();
            }
            currentFileOut = new FileOutputStream(new File(saveDir, currentFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Receiving file: " + currentFileName + " (" + expectedFileSize + " bytes)");
    }

    public void endFileReceiving() {
        try {
            if (currentFileOut != null) {
                currentFileOut.close();
                currentFileOut = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bytesReceived != expectedFileSize) {
            System.err.println("Warning: File size mismatch for " + currentFileName +
                " (expected " + expectedFileSize + ", got " + bytesReceived + ")");
        }

        System.out.println("File received: " + currentFileName);
        currentFileName = null;
        expectedFileSize = 0;
        bytesReceived = 0;
    }

    protected void handleFileChunk(byte[] data) {
        if (currentFileOut == null) {
            System.err.println("Received file chunk but no file is open.");
            return;
        }
        try {
            currentFileOut.write(data);
            bytesReceived += data.length;
            if (bytesReceived > expectedFileSize) {
                System.err.println("Warning: received more bytes than expected for file " + currentFileName);
            }
        } catch (IOException e) {
            endFileReceiving();
            e.printStackTrace();
        }
    }

    public synchronized void sendFile(File file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File does not exist: " + file.getAbsolutePath());
        }

        var metaBody = new HashMap<String, Object>();
        metaBody.put("command", "file_meta");
        metaBody.put("filename", file.getName());
        metaBody.put("filesize", file.length());
        sendMessage(new ConnectionMessage(metaBody, ConnectionMessage.Type.command));
        System.out.println(1);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                sendFrame(2, buffer, 0, read);
            }
            System.out.println(2);
        }

        var completeBody = new HashMap<String, Object>();
        completeBody.put("command", "file_complete");
        completeBody.put("filename", file.getName());
        sendMessage(new ConnectionMessage(completeBody, ConnectionMessage.Type.command));
        System.out.println(3);
    }

    public static class Frame {
        public final int type;
        public final byte[] data;

        public Frame(int type, byte[] data) {
            this.type = type;
            this.data = data;
        }
    }
}

