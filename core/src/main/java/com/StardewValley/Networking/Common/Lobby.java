package com.StardewValley.Networking.Common;

import com.StardewValley.Networking.Server.ClientConnection;
import com.StardewValley.Networking.Server.ServerMain;

import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.Random;

public class Lobby {
    private String name;
    private String code;
    private String adminUsername;
    private ArrayList<String> members;
    private Long lastJoin;
    private boolean isPrivate;
    private String password;
    private boolean isVisible;

    public Lobby(String name, String adminUsername, boolean isPrivate, String password, boolean isVisible) {
        this.name = name;
        this.code = generateRandomCode();
        this.adminUsername = adminUsername;
        members = new ArrayList<>();
        members.add(adminUsername);
        lastJoin = System.currentTimeMillis();
        this.isPrivate = isPrivate;
        this.password = password;
        this.isVisible = isVisible;
    }

    public Lobby() {}

    public static String generateRandomCode() {
        String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%*!?";
        int CODE_LENGTH = 8;
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            str.append(CHAR_POOL.charAt(index));
        }
        for(Lobby lobby : ServerMain.getLobbies()) {
            if(lobby.getCode().equals(str.toString())) {
                return generateRandomCode();
            }
        }
        return str.toString();
    }

    public void terminate() {
        for (String member : members) {
            ClientConnection connection = ServerMain.getConnectionByUsername(member);
            try{
                connection.getController().informLobbyTermination();
            }catch (NullPointerException e) {
                System.err.println("no connection found with the username " + member);
            }
        }
        ServerMain.removeLobby(this);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void addMember(String member) {
        members.add(member);
        lastJoin = System.currentTimeMillis();
    }

    public void removeMember(String member) {
        members.remove(member);
        if(members.isEmpty()) {
            ServerMain.removeLobby(this);
        }
        else if(adminUsername.equals(member)) {
            adminUsername = members.get(0);
        }
    }

    public Long getLastJoin() {
        return lastJoin;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Lobby other) {
            return name.equals(other.name) && code.equals(other.code);
        }
        return false;
    }
}
