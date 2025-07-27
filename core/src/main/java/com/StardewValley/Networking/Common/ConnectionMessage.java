package com.StardewValley.Networking.Common;

import com.StardewValley.Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;


public class ConnectionMessage {
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static final Gson gson;

    static {
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public synchronized static ConnectionMessage fromJson(String json) {
        return gson.fromJson(json, ConnectionMessage.class);
    }

    public static synchronized String userToJson(User user) {
        return gson.toJson(user);
    }

    public static synchronized User userFromJson(String json) {
        return gson.fromJson(json, User.class);
    }

    public static synchronized String lobbyToJson(Lobby lobby) {
        return gson.toJson(lobby);
    }

    public static synchronized Lobby lobbyFromJson(String json) {
        return gson.fromJson(json, Lobby.class);
    }


    private Type type;
    private HashMap<String, Object> body;

    public ConnectionMessage() {}

    public ConnectionMessage(HashMap<String, Object> body, Type type) {
        this.body = body;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public <T> T getFromBody(String fieldName) {
        return (T) body.get(fieldName);
    }

    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName)));
    }

    public enum Type {
        command,
        response,
        inform,
    }
}
