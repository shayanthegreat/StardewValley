package com.StardewValley.Networking.Client;

import com.StardewValley.Controllers.GameController;
import com.StardewValley.Models.User;
import com.StardewValley.Networking.Common.*;
import com.StardewValley.Views.InLobbyView;
import com.badlogic.gdx.graphics.Texture;

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
        System.out.println(1);
        GameDetails game = ConnectionMessage.gameDetailsFromJson(message.getFromBody("game_details"));
        ArrayList<String> usernames = message.getFromBody("usernames");
        ArrayList<String> avatarPaths = message.getFromBody("avatar_paths");
        System.out.println(2);
        int mapId = message.getIntFromBody("map_id");
        Chat chat = new Chat(usernames);
        game.setChat(chat);
        System.out.println(3);
        data.gameDetails = game;
        data.isInGame = true;
        User[] users = new User[usernames.size()];
        for (int i = 0; i < usernames.size(); i++) {
            users[i] = new User(usernames.get(i),"","","","");
//            users[i].setAvatarTexture(new Texture());
        }
        System.out.println(4);
        GameController.getInstance().createGameWithUsersAndMaps(users, mapId);

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

    public void updateStoreItems(ConnectionMessage message) {
        String store = message.getFromBody("store");
        String item = message.getFromBody("item");
        int count = message.getIntFromBody("count");

//        TODO: reduce the quantity of the item from the store
    }
}
