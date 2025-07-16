package com.StardewValley.Controller;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.AvatarType;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Map.Direction;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Player;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerController {
    private static PlayerController instance ;
    private boolean goingUp = false;
    private boolean goingDown = false;
    private boolean goingLeft = false;
    private boolean goingRight = false;


    public void update(){
        movePlayer();
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
        if(goingUp && !goingLeft && !goingRight){
            player.setPosition(new Position(player.getPosition().x,player.getPosition().y+1));
            Animation animation = player.getAvatarType().walkingAnimation(Direction.up);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingDown && !goingLeft && !goingRight){
            player.setPosition(new Position(player.getPosition().x,player.getPosition().y-1));
            Animation animation = player.getAvatarType().walkingAnimation(Direction.down);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingLeft && !goingUp && !goingDown){
            player.setPosition(new Position(player.getPosition().x-1,player.getPosition().y));
            Animation animation = player.getAvatarType().walkingAnimation(Direction.left);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingRight && !goingDown && !goingUp){
            player.setPosition(new Position(player.getPosition().x+1,player.getPosition().y));
            Animation animation = player.getAvatarType().walkingAnimation(Direction.right);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingUp && goingLeft){
            player.setPosition(new Position(player.getPosition().x-1,player.getPosition().y+1));
            Animation animation = player.getAvatarType().walkingAnimation(Direction.upLeft);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingUp && goingRight){
            player.setPosition(new Position(player.getPosition().x+1,player.getPosition().y+1));
            Animation animation = player.getAvatarType().walkingAnimation(Direction.upRight);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingDown && goingLeft){
            player.setPosition(new Position(player.getPosition().x-1,player.getPosition().y-1));
            Animation animation = player.getAvatarType().walkingAnimation(Direction.downLeft);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingDown && goingRight){
            player.setPosition(new Position(player.getPosition().x+1,player.getPosition().y-1));
            Animation animation = player.getAvatarType().walkingAnimation(Direction.downRight);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else {
            Animation animation = player.getAvatarType().walkingAnimation(Direction.center);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
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
}
