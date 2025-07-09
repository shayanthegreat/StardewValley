package com.StardewValley.Models.Animal;

import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Map.TileObject;

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

    public void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setPosition(this.getPlacedTile().getPosition());
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
