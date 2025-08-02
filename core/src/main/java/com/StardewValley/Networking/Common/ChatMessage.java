package com.StardewValley.Networking.Common;

public class ChatMessage {
    public String text;
    public String chatName;
    public String sender;
    public int id;

    public ChatMessage(String text, String chatName, String sender, int id) {
        this.text = text;
        this.chatName = chatName;
        this.sender = sender;
        this.id = id;
    }

    public ChatMessage() {}
}
