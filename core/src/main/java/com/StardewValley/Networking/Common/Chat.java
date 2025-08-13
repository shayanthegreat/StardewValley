package com.StardewValley.Networking.Common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Chat {

    public static String SEPARATOR = "~";
    private transient HashMap<String, ArrayList<ChatMessage>> chats;
    private Queue<ChatMessage> newMessages = new ConcurrentLinkedQueue<>();
    private AtomicInteger lastRegisteredId = new AtomicInteger(0);

    public Chat(ArrayList<String> members) {
        chats = new HashMap<>();
        for(int i = 0; i < members.size(); i++) {
            for(int j = i + 1; j < members.size(); j++) {
                chats.put(members.get(i) + SEPARATOR + members.get(j), new ArrayList<>());
            }
        }
        chats.put("all", new ArrayList<>());
    }

    public Chat() {}

    public void registerNewMessage(String text, String sender, String receiver) {
        String chatName;
        if(receiver.equals("all")) {
            chatName = "all";
        }else {
            chatName = sender + SEPARATOR + receiver;
            if(!chats.containsKey(chatName)) {
                chatName = receiver + SEPARATOR + sender;
                if(!chats.containsKey(chatName)) {
                    return;
                }
            }
        }
        int id = lastRegisteredId.getAndIncrement();
        ChatMessage message = new ChatMessage(text, chatName, sender, id);
        synchronized (newMessages) {
            newMessages.add(message);
            if (newMessages.size() > 10) {
                newMessages.poll();
            }
        }
        chats.get(chatName).add(message);
    }

    public void updateNewMessages(ArrayList<ChatMessage> messages) {
        messages.sort(Comparator.comparingInt(message -> message.id));
        for(ChatMessage message : messages) {
            if(message.id <= lastRegisteredId.get()) {
                continue;
            }
            chats.get(message.chatName).add(message);
            lastRegisteredId.set(message.id);
        }

    }

    public Queue<ChatMessage> getNewMessages() {
        return newMessages;
    }

    public ArrayList<ChatMessage> getChats(String first, String second) {
        if(first.equals("all") || second.equals("all")) {
            return chats.get("all");
        }
        if(chats.containsKey(first + SEPARATOR + second)) {
            return chats.get(first + SEPARATOR + second);
        }
        if(chats.containsKey(second + SEPARATOR + first)) {
            return chats.get(second + SEPARATOR + first);
        }

        return null;
    }
}
