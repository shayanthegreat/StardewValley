package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.*;
import com.StardewValley.Models.Communication.FriendShip;
import com.StardewValley.Models.Farming.Plant;
import com.StardewValley.Models.Farming.PlantType;
import com.StardewValley.Models.Farming.Seed;
import com.StardewValley.Models.Interactions.Messages.GameMessage;
import com.StardewValley.Models.Map.*;
import com.StardewValley.Views.MenuView;
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

    public boolean isNear(Position position) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Position playerPos = player.getPosition();

        int dx = Math.abs(playerPos.x - position.x);
        int dy = Math.abs(playerPos.y - position.y);

        if(dx * dx + dy * dy <= 9){
            return true;
        }
        return false;
    }

    public void handleTileClick(Position position) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Position playerPos = player.getPosition();

        if(isNear(position)){
            if(player.getCurrentTool() != null){
                GameMessage gameMessage = player.getCurrentTool().use(position);
                System.out.println(gameMessage.message());
            }
        }
    }

    public void plant(Seed seed, Position position) {
        Map map = App.getInstance().getCurrentGame().getMap();
        Tile tile = map.getTile(position);
        PlantType plantType = PlantType.getPlantTypeBySeed(seed.getType());
        Plant plant = new Plant(plantType, new Time(App.getInstance().getCurrentGame().getTime()));
        tile.setObject(plant);
        tile.setContainsPlant(true);
        plant.setPlacedTile(tile);
        App.getInstance().getCurrentGame().getCurrentPlayer().getBackPack().removeItem(seed, 1);
    }

}
