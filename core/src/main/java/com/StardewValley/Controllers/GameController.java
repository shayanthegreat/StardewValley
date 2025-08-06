package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.*;
import com.StardewValley.Models.Animal.*;
import com.StardewValley.Models.Communication.FriendShip;
import com.StardewValley.Models.Communication.NPC;
import com.StardewValley.Models.Crafting.Material;
import com.StardewValley.Models.Crafting.MaterialType;
import com.StardewValley.Models.Enums.FarmBuildings;
import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.Farming.Plant;
import com.StardewValley.Models.Farming.PlantType;
import com.StardewValley.Models.Farming.Seed;
import com.StardewValley.Models.Interactions.Messages.GameMessage;
import com.StardewValley.Models.Map.*;
import com.StardewValley.Models.PopUps.PopUpManager;
import com.StardewValley.Views.MenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameController implements Controller {
    private static GameController instance;
    private static long time;

    private GameController() {
        time = System.currentTimeMillis();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void update(float delta) {
        if (lightningEffect != null) {
            lightningEffect.update(delta);
            if (lightningEffect.isFinished()) {
                lightningEffect = null;
            }
        }
        time(System.currentTimeMillis());
    }


    private LightningEffect lightningEffect;
    private float lightningX, lightningY;


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

    public void cheatTime(){
        Game game = App.getInstance().getCurrentGame();
        game.nextHour();
    }

    public void cheatSeason(){
        Game game = App.getInstance().getCurrentGame();
        game.nextSeason();
    }

    public void cheatThor() {
        int x = ThreadLocalRandom.current().nextInt(1, 100);
        int y = ThreadLocalRandom.current().nextInt(1, 100);

        App.getInstance().getCurrentGame().thor(new Position(x, y));
        lightningEffect = new LightningEffect();
        lightningX = x;
        lightningY = y;
    }

    public LightningEffect getLightningEffect() {
        return lightningEffect;
    }

    public float getLightningX() {
        return lightningX;
    }

    public float getLightningY() {
        return lightningY;
    }


    private void time(long l) {
        long elapsed = l - time;

        int hoursToAdvance = (int) (elapsed / 30000);
        if (hoursToAdvance > 0) {
            for (int i = 0; i < hoursToAdvance; i++) {
                App.getInstance().getCurrentGame().nextHour();
            }
            time += hoursToAdvance * 30000;
        }
    }


    public void buildGreenHouse() {
        App app = App.getInstance();
        Game game = app.getCurrentGame();
        Player player = app.getCurrentGame().getCurrentPlayer();
//        if(!player.getBackPack().checkItem(new Material(MaterialType.wood),15)){
//            return;
//        }
//        player.getBackPack().removeItem(new Material(MaterialType.wood),15);
//        if(player.getMoney() < 40){
//            return;
//        }
        player.decreaseMoney(40);
        Farm farm = App.getInstance().getCurrentGame().getCurrentPlayer().getFarm();
        farm.getGreenHouse().build();
    }

    public void fishing(boolean isPerfect){
        App app = App.getInstance();
        Game game = app.getCurrentGame();
        Player player = app.getCurrentGame().getCurrentPlayer();
        player.decreaseEnergy(8);
        boolean isLegendary = isPerfect;
        FishType type = generateRandomFish(game.getTime().getSeason(), isLegendary);
        if(type == null){
            Gdx.app.exit();
        }
        Fish fish = new Fish(type);
        int count = fish.getFishingCount(game.getTodayWeather(), player.getSkill(SkillType.fishing).getLevel());
        String quality = fish.getFishingQuality(game.getTodayWeather(),player.getSkill(SkillType.fishing).getLevel(),"Training");
        player.getBackPack().addItem(fish,count);
        if(isPerfect){
            player.getSkill(SkillType.fishing).addAmount(player.getSkill(SkillType.fishing).getLevel() * 2);
        }
        else {
            player.getSkill(SkillType.fishing).addAmount(5);
        }
    }

    private FishType generateRandomFish(Season season,boolean legendary) {
        Random rand = new Random();
        if(!legendary){
            int fishCount = rand.nextInt(4) + 1;
            if(season==Season.spring){
                switch (fishCount) {
                    case 1:{
                        return FishType.flounder;
                    }
                    case 2:{
                        return FishType.lionFish;
                    }
                    case 3:{
                        return FishType.herring;
                    }
                    case 4:{
                        return FishType.ghostFish;
                    }
                }
            }
            else if(season==Season.winter){
                switch (fishCount) {
                    case 1:{
                        return FishType.midnightCarp;
                    }
                    case 2:{
                        return FishType.squid;
                    }
                    case 3:{
                        return FishType.tuna;
                    }
                    case 4:{
                        return FishType.perch;
                    }
                }
            }
            else if(season==Season.fall){
                switch (fishCount) {
                    case 1:{
                        return FishType.salmon;
                    }
                    case 2:{
                        return FishType.sardine;
                    }
                    case 3:{
                        return FishType.shad;
                    }
                    case 4:{
                        return FishType.blueDiscus;
                    }
                }
            }
            else if(season==Season.summer){
                switch (fishCount) {
                    case 1:{
                        return FishType.tilapia;
                    }
                    case 2:{
                        return FishType.dorado;
                    }
                    case 3:{
                        return FishType.sunFish;
                    }
                    case 4:{
                        return FishType.rainbowTrout;
                    }
                }
            }
        }
        else{
            int fishCount = rand.nextInt(5) + 1;
            if(season==Season.spring){
                switch (fishCount) {
                    case 1:{
                        return FishType.flounder;
                    }
                    case 2:{
                        return FishType.lionFish;
                    }
                    case 3:{
                        return FishType.herring;
                    }
                    case 4:{
                        return FishType.ghostFish;
                    }
                    case 5:{
                        return FishType.legend;
                    }
                }
            }
            else if(season==Season.winter){
                switch (fishCount) {
                    case 1:{
                        return FishType.midnightCarp;
                    }
                    case 2:{
                        return FishType.squid;
                    }
                    case 3:{
                        return FishType.tuna;
                    }
                    case 4:{
                        return FishType.perch;
                    }
                    case 5:{
                        return FishType.glacierFish;
                    }
                }
            }
            else if(season==Season.fall){
                switch (fishCount) {
                    case 1:{
                        return FishType.salmon;
                    }
                    case 2:{
                        return FishType.sardine;
                    }
                    case 3:{
                        return FishType.shad;
                    }
                    case 4:{
                        return FishType.blueDiscus;
                    }
                    case 5:{
                        return FishType.angler;
                    }
                }
            }
            else if(season== Season.summer){
                switch (fishCount) {
                    case 1:{
                        return FishType.tilapia;
                    }
                    case 2:{
                        return FishType.dorado;
                    }
                    case 3:{
                        return FishType.sunFish;
                    }
                    case 4:{
                        return FishType.rainbowTrout;
                    }
                    case 5:{
                        return FishType.crimsonFish;
                    }
                }
            }
        }
        return null;
    }

    public void buildBarn( int x, int y) {
        App app = App.getInstance();
        Player player = app.getCurrentGame().getCurrentPlayer();
//        if(player.getCurrentStore()==null || !player.getCurrentStore().getOwnerName().equals("Robin")){
//            return;
//        }
        Position position = new Position(x, y);
        Position position1 = new Position(x+1,y);
        Position position2 = new Position(x, y+1);
        Position position3 = new Position(x+1, y+1);
        if(player.getFarm().getBarn() != null){
            return;
        }
        if (player.getFarm().getTile(position) == null) {
            return;
        }
        if (player.getFarm().getTile(position1) == null) {
            return;
        }
        if (player.getFarm().getTile(position2) == null) {
            return;
        }
        if (player.getFarm().getTile(position3) == null) {
            return;
        }
        Tile tile = player.getFarm().getTile(position);
        if (!tile.isTotallyEmpty()) {
            return;
        }
        Tile tile1 = player.getFarm().getTile(position1);
        Tile tile2 = player.getFarm().getTile(position2);
        Tile tile3 = player.getFarm().getTile(position3);
        if(!tile1.isTotallyEmpty()){
            return;
        }
        if(!tile2.isTotallyEmpty()){
            return;
        }
        if(!tile3.isTotallyEmpty()){
            return;
        }
//        if(!player.getBackPack().checkItem(new Material(MaterialType.wood),350) || !player.getBackPack().checkItem(new Material(MaterialType.stone),150)){
//            return;
//        }
        Barn barn = new Barn();
        player.getFarm().setBarn(barn);
        tile.setObject(barn);
        tile1.setObject(barn);
        tile2.setObject(barn);
        tile3.setObject(barn);
        barn.setPlacedTile(tile);

    }

    public void buildCoop(int x , int y){
        App app = App.getInstance();
        Player player = app.getCurrentGame().getCurrentPlayer();
//        if(player.getCurrentStore()==null || !player.getCurrentStore().getOwnerName().equals("Robin")){
//            return;
//        }
        Position position = new Position(x, y);
        Position position1 = new Position(x+1,y);
        Position position2 = new Position(x, y+1);
        Position position3 = new Position(x+1, y+1);
        if(player.getFarm().getCoop() != null){
            return;
        }
        if (player.getFarm().getTile(position) == null) {
            return;
        }
        if (player.getFarm().getTile(position1) == null) {
            return;
        }
        if (player.getFarm().getTile(position2) == null) {
            return;
        }
        if (player.getFarm().getTile(position3) == null) {
            return;
        }
        Tile tile = player.getFarm().getTile(position);
        if (!tile.isTotallyEmpty()) {
            return;
        }
        Tile tile1 = player.getFarm().getTile(position1);
        Tile tile2 = player.getFarm().getTile(position2);
        Tile tile3 = player.getFarm().getTile(position3);
        if(!tile1.isTotallyEmpty()){
            return;
        }
        if(!tile2.isTotallyEmpty()){
            return;
        }
        if(!tile3.isTotallyEmpty()){
            return;
        }
//        if(!player.getBackPack().checkItem(new Material(MaterialType.wood),350) || !player.getBackPack().checkItem(new Material(MaterialType.stone),150)){
//            return;
//        }
        Coop coop = new Coop();
        player.getFarm().setCoop(coop);
        tile.setObject(coop);
        tile1.setObject(coop);
        tile2.setObject(coop);
        tile3.setObject(coop);
        coop.setPlacedTile(tile);
    }

    public void buyAnimal(AnimalType animalType1,String animalName){
        App app = App.getInstance();
        Player player=app.getCurrentGame().getCurrentPlayer();
        if(animalType1==null){
            return;
        }
        Animal animal = new Animal(animalType1,player,animalName);
        if(animalType1.isInCage()){
            if(player.getFarm().getCoop()==null){
                return;
            }

//                TODO:check if there is the needed cage for this animal
            player.getFarm().getCoop().addAnimal(animal,animalType1);
        }
        else {
            if(player.getFarm().getBarn()==null){
                return;
            }
//            TODO:check if there is the needed barn

            player.getFarm().getBarn().addAnimal(animal,animalType1);
        }
//        if(player.getMoney() < animal.getType().getCost()){
//            return;
//        }
//        player.decreaseMoney(animal.getType().getCost());
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

    public void handleTileClick(Position position, Stage stage) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        Position playerPos = player.getPosition();

        if(player.isChoosingBarn()){
            player.setChoosingBarn(false);
            buildBarn(position.x, position.y);
        }
        else if(player.isChoosingCoop()){
            player.setChoosingCoop(false);
            buildCoop(position.x, position.y);
        }

        for (NPC npc : player.getNpcs()) {
            if(position.equals(npc.getPosition())){
                NPCController.getInstance().printDialog(npc);
            }
        }

        if(isNear(position)){
            if(player.getCurrentTool() != null && !PopUpManager.instance.isVisible()){
                GameMessage gameMessage = player.getCurrentTool().use(position);
                System.out.println(gameMessage.message());
//                GameView currentView = (GameView) Main.getInstance().getScreen();
//                currentView.triggerToolUseAnimation();
            }
        }

        Item item = PopUpManager.getInstance(stage).getInventoryPopUp().getSelectedItem();
        if(player.getFarm().getTile(position) != null && item != null){
            item.drop(player.getFarm().getTile(position));
            player.getBackPack().removeItem(item, 1);
            PopUpManager.getInstance(stage).getInventoryPopUp().refresh();
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

    public boolean handleScape(){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        if(player.isChoosingBarn()){
            player.setChoosingBarn(false);
            return false;
        }
        else if(player.isChoosingCoop()){
            player.setChoosingCoop(false);
            return false;
        }
        return true;
    }

}
