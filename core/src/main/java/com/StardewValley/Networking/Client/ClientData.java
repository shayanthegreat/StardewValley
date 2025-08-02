package com.StardewValley.Networking.Client;

import com.StardewValley.Networking.Common.ConnectionMessage;
import com.StardewValley.Networking.Common.GameDetails;
import com.StardewValley.Networking.Common.Lobby;
import com.StardewValley.Networking.Common.PlayerDetails;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientData {
    private static ClientData instance;

    private ClientData() {
    }

    public static ClientData getInstance() {
        if (instance == null) {
            instance = new ClientData();
        }
        return instance;
    }

    public ServerConnection connection;
    public ArrayList<Lobby> lobbies;
    public String lobbyCode;
    public ArrayList<String> onlineUsers;
    public boolean isInGame = false;
    public GameDetails gameDetails;
    public PlayerDetails selfDetails;
//    send selfDetails to server, receive gameDetails from server

    public Lobby getLobby(String lobbyCode) {
        for (Lobby lobby : lobbies) {
            if (lobby.getCode().equals(lobbyCode)) {
                return lobby;
            }
        }
        return null;
    }

    public void updateAndSendSelf() {
        selfDetails.updateInfo();
        String json = ConnectionMessage.playerDetailsToJson(selfDetails);
        ConnectionMessage update = new ConnectionMessage(new HashMap<>() {{
            put("update", "update_self");
            put("json", json);
            put("game-code", gameDetails.getGameId());
        }}, ConnectionMessage.Type.update);
        connection.sendMessage(update);
    }
}
