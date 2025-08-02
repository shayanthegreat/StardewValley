package com.StardewValley.Networking.Client;

import com.StardewValley.Networking.Common.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerConnectionController {
    private static ServerConnectionController instance;
    private ServerConnection connection;
    private ClientData data = ClientData.getInstance();

    private ServerConnectionController() {
    }

    public static ServerConnectionController getInstance() {
        if (instance == null) {
            instance = new ServerConnectionController();
        }
        return instance;
    }

    public ConnectionMessage status() {
        ConnectionMessage message = new ConnectionMessage(new HashMap<>() {{
            put("command", "status");
            put("response", "ok");
            put("client_ip", connection.getIp());
            put("client_port", connection.getPort());
        }}, ConnectionMessage.Type.response);

        return message;
    }

    public void lobbyTerminated(ConnectionMessage message) {
        data.lobbyCode = "";

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ClientController.getInstance().refreshLobbies();
    }

    public void gameStarted(ConnectionMessage message) {
        GameDetails game = ConnectionMessage.gameDetailsFromJson(message.getFromBody("game_details"));
        ArrayList<String> usernames = message.getFromBody("usernames");
        Chat chat = new Chat(usernames);
        game.setChat(chat);
        data.gameDetails = game;
        data.isInGame = true;


        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (data.isInGame) {
                data.updateAndSendSelf();
            } else {
                scheduler.shutdown();
            }
        }, 5, 1, TimeUnit.SECONDS);
//        TODO: do needed stuff
    }

    public void updateOnlineUsers(ConnectionMessage message) {
        data.onlineUsers = message.getFromBody("online_users");

    }

    public void setConnection(ServerConnection connection) {
        this.connection = connection;
    }

    public void updateGame(ConnectionMessage message) {
        GameDetails oldGame = data.gameDetails;
        GameDetails newGame = ConnectionMessage.gameDetailsFromJson(message.getFromBody("json"));
        for(String member : oldGame.getPlayers().keySet()) {
            Reaction oldReaction = oldGame.getPlayerByUsername(member).reaction;
            Reaction newReaction = newGame.getPlayerByUsername(member).reaction;
            if(!oldReaction.text.equals(newReaction.text) && !newReaction.text.isEmpty()) {
                newReaction.time = System.currentTimeMillis();
            }
        }
        data.gameDetails = newGame;

        ArrayList<ChatMessage> newMessages = ConnectionMessage.newMessagesFromJson(message.getFromBody("new_messages"));
        data.gameDetails.setChat(oldGame.getChat());
        data.gameDetails.getChat().updateNewMessages(newMessages);
    }
}
