package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.Animal.Animal;
import com.StardewValley.Models.Animal.AnimalProduct;
import com.StardewValley.Models.Animal.AnimalType;
import com.StardewValley.Models.Animal.Barn;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.Weather;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.Tools.Tool;
import com.StardewValley.Models.Tools.ToolType;
import com.StardewValley.Models.Tools.WateringCan;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Random;

public class AnimalController implements Controller{
    private static AnimalController animalController;
    private AnimalController() {}
    public static AnimalController getInstance() {
        if (animalController == null) {
            animalController = new AnimalController();
        }
        return animalController;
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

    public void update(){
        printAnimal();
        moveAnimal();
    }

    private void printAnimal() {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        if(player.getFarm().getCoop() == null){
            return;
        }
        for (Animal animal : player.getFarm().getCoop().getAnimals()) {
            if(animal.isWalking())  return;
            Camera.getInstance().print(animal.getType().getTexture(), animal.getPosition().x,animal.getPosition().y,1,1);
        }
    }

    public void shepherdAnimals(Animal animal) {
        App app = App.getInstance();
        Game game = app.getCurrentGame();
        Player player = app.getCurrentGame().getCurrentPlayer();
        int x =  (int)(Math.random() * 8) + 4 + animal.getPosition().x;
        int y =  (int)(Math.random() * 8) + 4 + animal.getPosition().y;

        if (!animal.isOutside()) {
//            if (game.getTodayWeather() != Weather.sunny) {
//                GameView.showError( "The animal can not go outside in this weather");
//                return;
//            }
            animal.setOutside(true);
            animal.setTargetPosition(new Position(x, y));
            animal.increaseFriendship(8);
            animal.setIsWalking(true);
            player.setPosition(new Position(x+1, y));

        } else {
            if (animal.getType().isInCage()) {
                animal.setTargetPosition(player.getFarm().getCoop().getPlacedTile().getPosition());
                animal.setIsWalking(false);
                animal.setOutside(true);
            } else {
                animal.setTargetPosition(player.getFarm().getBarn().getPlacedTile().getPosition());
                animal.setOutside(false);
                animal.setIsWalking(true);
                animal.setLastFeedingTime(game.getTime());
                animal.increaseFriendship(8);
            }

        }
    }

    private void moveAnimal() {
        Barn barn = App.getInstance().getCurrentGame().getCurrentPlayer().getFarm().getBarn();
        if (barn == null) return;

        ArrayList<Animal> allAnimals = barn.getAnimals();
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

        for (Animal animal : allAnimals) {
            Position current = animal.getPosition();
            Position target = animal.getTargetPosition();

            if (animal.isWalking() && target != null) {
                float dx = target.x - current.x;
                float dy = target.y - current.y;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);


                if (distance < 0.05f) {
                    animal.setPosition(target);
                    animal.setIsWalking(false);
                } else {
                    float speed = 2f;
                    float normX = dx / distance;
                    float normY = dy / distance;
                    current.x += normX * speed;
                    current.y += normY * speed;
                    animal.setPosition(current);
                }


            }

            Camera.getInstance().print(animal.getType().getAnimation(), current.x, current.y, 1, 1);
        }
    }



    public void petAnimal(String animalName){
        App app = App.getInstance();
        Player player=app.getCurrentGame().getCurrentPlayer();

        if (player.getFarm().getBarn() == null && player.getFarm().getCoop() == null) {
            return;
        }

        Animal animal = null;

        if (player.getFarm().getCoop() != null) {
            animal = player.getFarm().getCoop().getAnimalByName(animalName);
        }

        if (animal == null && player.getFarm().getBarn() != null) {
            animal = player.getFarm().getBarn().getAnimalByName(animalName);
        }

        if (animal == null) {
            return;
        }

        PlayerController.getInstance().startPetting(animal);

        if( Math.abs(player.getPosition().x-animal.getPosition().x)<=1 && Math.abs(player.getPosition().y-animal.getPosition().y)<=1){
            animal.increaseFriendship(15);
            animal.setLastPettingTime(app.getCurrentGame().getTime());
        }

    }

    public void feedingHay(String animalName) {
        App app = App.getInstance();
        Game game = app.getCurrentGame();
        Player player = app.getCurrentGame().getCurrentPlayer();
        Animal animal = player.getFarm().getBarn().getAnimalByName(animalName);
        if (animal == null) {
            animal = player.getFarm().getCoop().getAnimalByName(animalName);
        }
        if (animal == null) {
            return;
        }
        if (animal.isOutside()) {
            return;
        }

        PlayerController.getInstance().startFeeding(animal);

        animal.setLastFeedingTime(game.getTime());
        player.decreaseHay(2);
    }

    public void sellAnimal(String animalName) {
        App app = App.getInstance();
        Game game = app.getCurrentGame();
        Player player = app.getCurrentGame().getCurrentPlayer();
        Animal animal = player.getFarm().getBarn().getAnimalByName(animalName);
        if (animal != null) {
            int price = animal.calSellingPrice();
            player.addMoney(price);
            player.getFarm().getBarn().getAnimals().remove(animal);
        }
        else{
            animal = player.getFarm().getCoop().getAnimalByName(animalName);
            if (animal != null) {
                int price = animal.calSellingPrice();
                player.addMoney(price);
                player.getFarm().getCoop().getAnimals().remove(animal);
            }
        }
    }

    public void showProducts(){
        App app = App.getInstance();
        Player player = app.getCurrentGame().getCurrentPlayer();
        if(player.getFarm().getBarn() != null){
            for (Animal animal : player.getFarm().getBarn().getAnimals()) {
                if(animal.doesProduce()){
                    AnimalProduct animalProduct = animal.whichProduct();
                    animal.setCurrentProduct(animalProduct);
                    Tile tile = player.getFarm().getTile(new Position(animal.getPosition().x + 2,animal.getPosition().y));
                    tile.setObject(animalProduct);
                }
            }
        }
        if(player.getFarm().getCoop() != null){
            for (Animal animal : player.getFarm().getCoop().getAnimals()) {
                if(animal.doesProduce()){
                    AnimalProduct animalProduct = animal.whichProduct();
                    animal.setCurrentProduct(animalProduct);
                    Tile tile = player.getFarm().getTile(new Position(animal.getPosition().x + 2,animal.getPosition().y));
                    tile.setObject(animalProduct);
                }
            }
        }
    }

    public void collectProducts(String animalName){
        App app = App.getInstance();
        Game game = app.getCurrentGame();
        Player player = app.getCurrentGame().getCurrentPlayer();
        Animal animal = null;
        if(player.getFarm().getBarn() != null){
            animal= player.getFarm().getBarn().getAnimalByName(animalName);
        }
        if (animal == null && player.getFarm().getCoop() != null) {
            animal = player.getFarm().getCoop().getAnimalByName(animalName);
        }
        if (animal == null) {
            return;
        }

        if(animal.getCurrentProduct() != null){
            if(animal.getType().equals(AnimalType.cow) || animal.getType().equals(AnimalType.goat)){
                if(!player.getBackPack().checkItem(new WateringCan(ToolType.wateringCan),1)){
                    return;
                }
                player.decreaseEnergy(4);
                if(player.getBackPack().getCapacity()>=player.getBackPack().getMaxCapacity()){
                    return;
                }

                player.getBackPack().addItem(animal.getCurrentProduct(),1);
                animal.setCurrentProduct(null);
                animal.increaseFriendship(5);
                Tile tile = player.getFarm().getTile(new Position(animal.getPosition().x+2,animal.getPosition().y));
                tile.setObject(null);
                animal.setCollected(true);

            }
            else if(animal.getType().equals(AnimalType.sheep)){
                if(!player.getBackPack().checkItem(new Tool(ToolType.shear),1)){
                    return;
                }
                player.decreaseEnergy(4);
                if(player.getBackPack().getCapacity()>=player.getBackPack().getMaxCapacity()){
                    return;
                }
                player.getBackPack().addItem(animal.getCurrentProduct(),1);
                animal.setCurrentProduct(null);
                animal.increaseFriendship(5);
                Tile tile = player.getFarm().getTile(new Position(animal.getPosition().x+2,animal.getPosition().y));
                tile.setObject(null);
                animal.setCollected(true);

            }
            else if(animal.getType().equals(AnimalType.pig)){
                if(animal.isOutside()){
                    if(player.getBackPack().getCapacity()>=player.getBackPack().getMaxCapacity()){
                        return;
                    }
                    player.getBackPack().addItem(animal.getCurrentProduct(),1);
                    animal.setCurrentProduct(null);
                    Tile tile = player.getFarm().getTile(new Position(animal.getPosition().x+2,animal.getPosition().y));
                    tile.setObject(null);
                    animal.setCollected(true);
                }
            }
        }

    }





}
