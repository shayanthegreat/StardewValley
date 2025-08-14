package com.StardewValley.Models.Animal;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Map.TileObject;
import com.StardewValley.Models.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Barn extends TileObject implements Serializable {
    @Override
    public Tile getPlacedTile() {
        return super.getPlacedTile();
    }

    @Override
    public void setPlacedTile(Tile placedTile) {
        super.setPlacedTile(placedTile);
    }

    private ArrayList<Animal> animals;

    public Barn() {
        this.animals = new ArrayList<>();
    }

    public void addAnimal(Animal animal,AnimalType type) {
        animals.add(animal);
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        switch (type){
            case cow:{
                animal.setPosition(new Position(player.getFarm().getBarn().getPlacedTile().getPosition().x,player.getFarm().getBarn().getPlacedTile().getPosition().y));
                break;
            }
            case pig:{
                animal.setPosition(new Position(player.getFarm().getBarn().getPlacedTile().getPosition().x,player.getFarm().getBarn().getPlacedTile().getPosition().y+1));
                break;
            }
            case goat:{
                animal.setPosition(new Position(player.getFarm().getBarn().getPlacedTile().getPosition().x+1,player.getFarm().getBarn().getPlacedTile().getPosition().y));
                break;
            }
            case sheep:{
                animal.setPosition(new Position(player.getFarm().getBarn().getPlacedTile().getPosition().x+1,player.getFarm().getBarn().getPlacedTile().getPosition().y+1));
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
