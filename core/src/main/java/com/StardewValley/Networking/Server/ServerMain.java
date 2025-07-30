package com.StardewValley.Networking.Server;

import com.StardewValley.Networking.Common.ConnectionMessage;
import com.StardewValley.Networking.Common.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import static com.StardewValley.Networking.Common.Connection.TIMEOUT;

public class ServerMain {
    private static String ip;
    private static int port;
    private static ServerSocket serverSocket;
    private static boolean exitFlag = false;
    private static ArrayList<ClientConnection> connections = new ArrayList<>();

    private static ArrayList<Lobby> lobbies = new ArrayList<>();

    public static void main(String[] args) {
        UserDAO.initializeDatabase();
        try {
            Scanner sc = new Scanner(System.in);
            ip = sc.nextLine();
            port = Integer.parseInt(sc.nextLine());
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println("Could not start the server");
            e.printStackTrace();
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        Runnable refreshStatus = () -> {
            ArrayList<String> onlineUsers = new ArrayList<>();
            ArrayList<ClientConnection> onlineConnections = new ArrayList<>();
            Iterator<ClientConnection> it = connections.iterator();
            while (it.hasNext()) {
                ClientConnection connection = it.next();
                if(!connection.refreshStatus()) {
                    connection.end();
                }
                if(!connection.getUsername().isEmpty() && !connection.isInGame()) {
                    String info = connection.getUsername();
                    if(!connection.getLobbyCode().isEmpty()) {
                        Lobby lobby = getLobbyByCode(connection.getLobbyCode());
                        if(lobby == null) {
                            continue;
                        }
                        info += " : " + lobby.getName() + "(" + lobby.getCode() + ")";
                    }
                    onlineUsers.add(info);
                    onlineConnections.add(connection);
                }
            }
            ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
                put("information", "online_users");
                put("online_users", onlineUsers);
            }}, ConnectionMessage.Type.response);

            for (ClientConnection connection : onlineConnections) {
                connection.sendMessage(message);
            }
        };
        Runnable checkLobbies = () -> {
            Long time = System.currentTimeMillis();
            Iterator<Lobby> it = lobbies.iterator();
            while (it.hasNext()) {
                Lobby lobby = it.next();
                if(time - lobby.getLastJoin() > (3 * 60 * 1000)) {
                    lobby.terminate();
                }
            }
        };
        scheduler.scheduleAtFixedRate(refreshStatus, 10, 3, TimeUnit.SECONDS);


        try {
            serverSocket.setSoTimeout(TIMEOUT);
            while (!exitFlag) {
                try {
                    Socket socket = serverSocket.accept();
                    handleConnection(socket);
                } catch (SocketTimeoutException e) {
                    continue;
                } catch (Exception e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serverSocket.close();
        } catch (Exception ignored) {
        }
    }

    public static void handleConnection(Socket socket) {
        if (socket == null) {
            return;
        }
        try{
            new ClientConnection(socket, ip, port).start();
        }
        catch (Exception e) {
            e.printStackTrace();
            try{
                socket.close();
            }
            catch (Exception ignored) {}
        }

    }

    public static void terminateAll() {
        exitFlag = true;
        for (ClientConnection connection : connections) {
            connection.end();
        }
        connections.clear();

    }

    public static ClientConnection getConnectionByIpPort(String ip, int port) {
        for (ClientConnection connection : connections) {
            if (connection.getOtherSideIP().equals(ip) && connection.getOtherSidePort() == port) {
                return connection;
            }
        }
        return null;
    }

    public static ClientConnection getConnectionByUsername(String username) {
        for (ClientConnection connection : connections) {
            if (connection.getUsername().equals(username)) {
                return connection;
            }
        }
        return null;
    }

    public static boolean isEnded() {
        return exitFlag;
    }

    public static List<ClientConnection> getConnections() {
        return List.copyOf(connections);
    }

    public static void addConnection(ClientConnection connection) {
        if (connection != null && !connections.contains(connection)) {
            connections.add(connection);
        }
    }

    public static void removeConnection(ClientConnection connection) {
        if (connection != null) {
            connections.remove(connection);
            connection.end();
        }
    }

    public static void addLobby(Lobby lobby) {
        lobbies.add(lobby);
    }

    public static void removeLobby(Lobby lobby) {
        lobbies.remove(lobby);
    }

    public static ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public static Lobby getLobbyByCode(String code) {
        for(Lobby lobby : lobbies) {
            if(lobby.getCode().equals(code)) {
                return lobby;
            }
        }
        return null;
    }
}
