package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.Animal.Animal;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Player;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

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
        if(player.getFarm().getBarn() == null){
            return;
        }
        for (Animal animal : player.getFarm().getBarn().getAnimals()) {
            Camera.getInstance().print(animal.getType().getTexture(), animal.getPosition().x,animal.getPosition().y,1,1);
        }
    }

    private void moveAnimal() {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        if(player.getFarm().getBarn() == null){
            return;
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



}
