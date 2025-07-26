package com.StardewValley.Controllers;

import com.StardewValley.Models.Animal.Animal;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Map.*;
import com.StardewValley.Models.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

public class PlayerController {
    private static PlayerController instance ;
    private boolean goingUp = false;
    private boolean goingDown = false;
    private boolean goingLeft = false;
    private boolean goingRight = false;
    private Direction playerDirection = Direction.center;
    private Animation<TextureRegion> animation;
    private long pettingStartTime = 0;
    private boolean petting = false;
    private boolean feeding = false;


    public void update(){
        movePlayer();
        petting();
        feeding();
    }


    private PlayerController(){}
    public static PlayerController getInstance(){
        if(instance == null){
            instance = new PlayerController();
        }
        return instance;
    }

    public void movePlayer(){
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();
        if(petting)  return;
        if(goingUp && !goingLeft && !goingRight){
            playerDirection = Direction.up;
            getAnimation(Direction.up);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
            move(0,1);
        }
        else if(goingDown && !goingLeft && !goingRight){
            playerDirection = Direction.down;
            getAnimation(Direction.down);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
            move(0,-1);
        }
        else if(goingLeft && !goingUp && !goingDown){
            playerDirection = Direction.left;
            getAnimation(Direction.left);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
            move(-1,0);
        }
        else if(goingRight && !goingDown && !goingUp){
            playerDirection = Direction.right;
            getAnimation(Direction.right);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
            move(1,0);
        }
        else if(goingUp && goingLeft){
            playerDirection = Direction.up;
            getAnimation(Direction.upLeft);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
            move(-1,1);
        }
        else if(goingUp && goingRight){
            playerDirection = Direction.up;
            getAnimation(Direction.upRight);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
            move(1,1);
        }
        else if(goingDown && goingLeft){
            playerDirection = Direction.down;
            getAnimation(Direction.downLeft);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
            move(-1,-1);
        }
        else if(goingDown && goingRight){
            playerDirection = Direction.right;
            getAnimation(Direction.downRight);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
            move(1,-1);
        }
        else {
            animation = App.getInstance().getCurrentGame().getCurrentPlayer().getAvatarType().TiredAnimation(playerDirection);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,1,1);
        }
    }

    private void petting(){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        if(petting){
            if(TimeUtils.millis() - pettingStartTime > 3000){
                petting = false;
                return;
            }
            Camera.getInstance().print(player.getAvatarType().pettingAnimation(),player.getPosition().x,player.getPosition().y,1,1);
        }
    }

    private void feeding(){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        if(feeding){
            if(TimeUtils.millis() - pettingStartTime > 3000){
                feeding = false;
                return;
            }
            Camera.getInstance().print(player.getAvatarType().feedingAnimation(),player.getPosition().x,player.getPosition().y,1,1);
        }
    }

    private void move(int dx , int dy){
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();

        if(player.isFainted())
            return;

        Tile tile = game.getMap().getTile(new Position(player.getPosition().x + dx,player.getPosition().y + dy ));


        if(tile != null && (tile.getBuilding() == null || (tile.getBuilding() instanceof House)) && tile.getObject() == null ){
            player.setPosition(new Position(player.getPosition().x +dx,player.getPosition().y + dy));
            player.decreaseEnergy(0.3f);
            if(player.getEnergy().amount == 0){
                player.setFainted(true);
            }
        }
    }

    private void getAnimation(Direction direction){
        if(App.getInstance().getCurrentGame().getCurrentPlayer().isFainted()){
           animation = App.getInstance().getCurrentGame().getCurrentPlayer().getAvatarType().faintAnimation(direction);
        }
        else{
            animation = App.getInstance().getCurrentGame().getCurrentPlayer().getAvatarType().walkingAnimation(direction);
        }
    }


    public void setGoingUp(boolean goingUp){
        this.goingUp = goingUp;
    }
    public void setGoingDown(boolean goingDown){
        this.goingDown = goingDown;
    }
    public void setGoingLeft(boolean goingLeft){
        this.goingLeft = goingLeft;
    }
    public void setGoingRight(boolean goingRight){
        this.goingRight = goingRight;
    }

    public Building whatIsClose(){
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                if(i==0 && j==0)continue;
                Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
                Farm farm = player.getFarm();
                Building building = farm.getTile(new Position(player.getPosition().x + i,player.getPosition().y + j)).getBuilding();
                if(building != null){
                    return building;
                }
            }
        }
        return null;
    }

    public void startPetting(Animal animal){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        player.setPosition(new Position(animal.getPosition().x-1,animal.getPosition().y));
        pettingStartTime = TimeUtils.millis();
        petting = true;
    }

    public void startFeeding(Animal animal){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        player.setPosition(new Position(animal.getPosition().x+1,animal.getPosition().y));
        feeding = true;
        pettingStartTime = TimeUtils.millis();
    }
}
