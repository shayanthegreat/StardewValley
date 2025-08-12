package com.StardewValley.Networking.Client;

import com.StardewValley.Controllers.GameController;
import com.StardewValley.Models.App;
import com.StardewValley.Models.User;
import com.StardewValley.Networking.Common.*;
import com.StardewValley.Views.InLobbyView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
    }

    public void gameStarted(ConnectionMessage message) {
        System.out.println(1);
        GameDetails game = ConnectionMessage.gameDetailsFromJson(message.getFromBody("game_details"));
        System.out.println((String) message.getFromBody("game_details"));
        ArrayList<String> usernames = message.getFromBody("usernames");
        ArrayList<String> avatarPaths = message.getFromBody("avatar_paths");
        System.out.println(2);
        int mapId = message.getIntFromBody("map_id");
        Chat chat = new Chat(usernames);
        game.setChat(chat);
        data.selfDetails = new PlayerDetails(App.getInstance().getCurrentUser().getUsername());
        data.gameDetails = game;
        data.isInGame = true;
        User[] users = new User[usernames.size()];
        for (int i = 0; i < usernames.size(); i++) {
            users[i] = new User(usernames.get(i),"","","","");
//            TODO: set avatars
//            users[i].setAvatarTexture(new Texture());
        }
        GameController.getInstance().createGameWithUsersAndMaps(users, mapId);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (data.isInGame) {
                data.updateAndSendSelf();
            } else {
                scheduler.shutdown();
            }
        }, 3, 1, TimeUnit.SECONDS);

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
            if(oldReaction == null) {
                oldReaction = new Reaction("");
                System.out.println("brrrr");
            }
            Reaction newReaction = newGame.getPlayerByUsername(member).reaction;
            if(newReaction == null) {
                newReaction = new Reaction("");
                System.out.println("brrrr");
            }
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

    private String sourceOfMusic = "";

    public void setSourceOfMusic(String sourceOfMusic) {
        this.sourceOfMusic = sourceOfMusic;
    }

    public void saveMusicFile(ConnectionMessage message) {
        String name = message.getFromBody("filename");
        String sourcePath = "temp_receives/" + name;
        File source = new File(sourcePath);
        String targetDirPath = "received_musics/" + App.getInstance().getCurrentUser().getUsername();
        File targetDir = new File(targetDirPath);
        if(!targetDir.exists()) targetDir.mkdirs();
        if(!source.exists()){
            System.err.println("Error: File (" + name + ") does not exist");
            return;
        }

        try {
            Path sourceFile = source.toPath();
            Path targetFile = targetDir.toPath().resolve(sourceOfMusic + "~" + source.getName());

            Files.move(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            File file = targetFile.toFile();

            if (data.currentMusic != null && data.currentMusic.isPlaying()) {
                data.currentMusic.pause();
            }

            try {
                FileHandle handle = Gdx.files.absolute(file.getAbsolutePath());
                data.currentMusic = Gdx.audio.newMusic(handle);
                data.currentMusic.play();
            } catch (Exception e) {
                System.err.println("Error playing music: " + e.getMessage());
            }} catch (Exception e) {
            e.printStackTrace();
        }
    }
}
