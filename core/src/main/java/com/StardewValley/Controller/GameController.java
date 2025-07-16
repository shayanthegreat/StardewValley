package com.StardewValley.Controller;

import com.StardewValley.Main;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Communication.FriendShip;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Interactions.Messages.GameMessage;
import com.StardewValley.Models.Map.Farm;
import com.StardewValley.Models.Map.FarmMap;
import com.StardewValley.Models.Map.Map;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.User;
import com.StardewValley.View.MenuView;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

public class GameController implements Controller {
    private static GameController instance;

    private GameController() {
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    @Override
    public void showError(String error, Stage stage, Skin skin) {
        final Dialog dialog = new Dialog("!!!", skin) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(error);
        dialog.button("OK");
        dialog.show(stage);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dialog.hide();
            }
        }, 5);
    }

    public GameMessage createGameWithUsersAndMaps(User[] users, int mapIDs) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Farm> farms = new ArrayList<>();

        for (int i = 0; i < users.length; i++) {
            Farm farm = new Farm(FarmMap.getFarmMap(mapIDs), i);
            Player player = new Player(users[i], farm);
            farms.add(farm);
            players.add(player);
        }

        for (int i = 0; i < players.size(); i++) {
            for (Player player : players) {
                if (!players.get(i).equals(player)) {
                    players.get(i).addFriendShip(new FriendShip(player));
                }
            }
        }

        Map map = new Map(farms);
        Game game = new Game(players, map);
        App app = App.getInstance();
        app.addGame(game);
        app.setCurrentGame(game);
        app.setCurrentGameStarter(app.getCurrentUser());

        for (User user : users) {
            user.setCurrentGame(game);
        }
        return new GameMessage(null,"You successfully created the game");
    }

    public void changeMenu(MenuView menu){
        Main.getInstance().getScreen().dispose();
        Main.getInstance().setScreen(menu);
    }

}
