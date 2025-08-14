package com.StardewValley.Models.Crafting;

import com.StardewValley.Controllers.PlayerController;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Time;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Food extends Item implements Serializable {

    private CookingRecipe recipe;

    public Food(CookingRecipe recipe) {
        super(recipe.getProductName(), 1,true);
        this.recipe = recipe;
        texture = recipe.getTexture();
        sprite = new Sprite(texture);
    }

    private void readObject(ObjectInputStream ois)
        throws IOException, ClassNotFoundException {
        ois.defaultReadObject();

        texture = recipe.getTexture();
        sprite = new Sprite(texture);
    }


    public CookingRecipe getRecipe() {
        return recipe;
    }

    @Override
    public void use() {

    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(Food.this);
    }

    @Override
    public void delete() {

    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public String getName() {
        return recipe.getProductName();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int getPrice() {
        return recipe.getPrice();
    }

    public void eat(){
        App.getInstance().getCurrentGame().getCurrentPlayer().addEnergy(recipe.getEnergy());
        Buff buff = recipe.getBuff();
        PlayerController.getInstance().startEating();
        Time[] lastBuffTime = App.getInstance().getCurrentGame().getCurrentPlayer().getLastBuffTime();
        if(buff != null){
            Time time = new Time(App.getInstance().getCurrentGame().getTime());
            time.addHour(buff.getHours());
            switch (buff.getSkillType()){
                case farming -> lastBuffTime[0] = new Time(time);
                case mining -> lastBuffTime[1] = new Time(time);
                case foraging -> lastBuffTime[2] = new Time(time);
                case fishing -> lastBuffTime[3] = new Time(time);
            }
        }
        App.getInstance().getCurrentGame().getCurrentPlayer().setLastBuffTime(lastBuffTime);
    }
}
