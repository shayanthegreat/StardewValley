package com.StardewValley.Models.Animal;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.Enums.SkillType;
import com.StardewValley.Models.Enums.Weather;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.Tools.LevelInfo;
import com.StardewValley.Models.Tools.Tool;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Fish extends Item implements Serializable {

    private FishType type;
    private Quality quality;


    public Fish(FishType type) {
        super(type.getName(), 1,true);
        this.type = type;
    }

    public int getFishingCount(Weather weather, int skillLevel) {
        App app = App.getInstance();
        Game game=app.getCurrentGame();
        Player player=game.getCurrentPlayer();
        if(weather == Weather.rain) {
            double x=(double)player.getSkill(SkillType.fishing).getAmount()+2;
            x*=1.2;
            double random = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
            x*=random;
            return (int)(x % 6);
        }
        else if(weather == Weather.snow){
            double x=(double)player.getSkill(SkillType.fishing).getAmount()+2;
            x*=1;
            double random = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
            x*=random;
            return (int)(x % 6);
        }
        else if(weather == Weather.storm){
            double x=(double)player.getSkill(SkillType.fishing).getAmount()+2;
            x*=0.5;
            double random = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
            x*=random;
            return (int)(x % 6);
        }
        else if(weather == Weather.sunny){
            double x=(double)player.getSkill(SkillType.fishing).getAmount()+2;
            x*=1.5;
            double random = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
            x*=random;
            return (int)(x % 6);
        }
        else return 0;
    }

    @Override
    public void use() {

    }

    @Override
    public void drop(Tile tile) {
        tile.setObject(Fish.this);
    }

    @Override
    public void delete() {

    }
    public String getFishingQuality(Weather weather, int skillLevel, String levelName) {
        App app = App.getInstance();
        Game game=app.getCurrentGame();
        Player player=game.getCurrentPlayer();
        double x=(double)player.getSkill(SkillType.fishing).getAmount()+2;
        double random = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
        x*=random;
//        TODO: check the names with shayan!!
        if(levelName.equals("Training")){
            x*=0.1;
        }
        else if(levelName.equals("Bamboo")){
            x*=0.5;
        }
        else if(levelName.equals("Fiberglass")){
            x*=0.9;
        }
        else if(levelName.equals("Iridium")){
            x*=1.2;
        }
        if(weather == Weather.rain) {
            x/=5.8;
        }
        else if(weather == Weather.snow){
            x/=6;
        }
        else if(weather == Weather.sunny){
            x/=5.5;
        }
        else {
            x/=6.5;
        }
        this.quality = new Quality(x);
        return quality.getQualityName();
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getPrice() {
        return (int) (quality.getPriceRatio() * type.getBasePrice());
    }
}
