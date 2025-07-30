package com.StardewValley.Networking.Client;

import com.StardewValley.Networking.Common.Lobby;

import java.util.ArrayList;

public class ClientData {
    private static ClientData instance;

    private ClientData(){}

    public static ClientData getInstance(){
        if(instance == null){
            instance = new ClientData();
        }
        return instance;
    }

    public ArrayList<Lobby> lobbies;
    public String lobbyCode;
    public ArrayList<String> onlineUsers;

    public Lobby getLobby(String lobbyCode){
        for (Lobby lobby : lobbies) {
            if(lobby.getCode().equals(lobbyCode)){
                return lobby;
            }
        }
        return null;
    }
}
