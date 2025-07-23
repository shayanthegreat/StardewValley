package com.StardewValley.Models.Animal;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Map.TileObject;
import com.StardewValley.Models.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Coop extends TileObject implements Serializable {

    @Override
    public Tile getPlacedTile() {
        return super.getPlacedTile();
    }

    @Override
    public void setPlacedTile(Tile placedTile) {
        super.setPlacedTile(placedTile);
    }

    public ArrayList<Animal> animals;

    public Coop() {
        this.animals = new ArrayList<>();
    }

    public void addAnimal(Animal animal,AnimalType type) {
        animals.add(animal);
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        switch (type) {
            case duck: {
                animal.setPosition(player.getFarm().getCoop().getPlacedTile().getPosition());
                break;
            }
            case rabbit: {
                animal.setPosition(new Position(player.getFarm().getCoop().getPlacedTile().getPosition().x, player.getFarm().getBarn().getPlacedTile().getPosition().y + 1));
                break;
            }
            case dinosaur: {
                animal.setPosition(new Position(player.getFarm().getCoop().getPlacedTile().getPosition().x + 1, player.getFarm().getBarn().getPlacedTile().getPosition().y));
                break;
            }
            case chicken: {
                animal.setPosition(new Position(player.getFarm().getCoop().getPlacedTile().getPosition().x + 1, player.getFarm().getBarn().getPlacedTile().getPosition().y + 1));
                break;
            }
        }

    }

    public Animal getAnimalByName(String name) {
        for (Animal animal : animals) {
            if(animal.getName().equals(name)){
                return animal;
            }
        }
        return null;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

}
