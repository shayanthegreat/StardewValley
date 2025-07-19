package com.StardewValley.Controllers;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Map.Direction;
import com.StardewValley.Models.Map.House;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Map.Tile;
import com.StardewValley.Models.Player;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerController {
    private static PlayerController instance ;
    private boolean goingUp = false;
    private boolean goingDown = false;
    private boolean goingLeft = false;
    private boolean goingRight = false;
    private Direction playerDirection = Direction.center;
    private Animation<TextureRegion> animation;


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
            move(0,1);
            playerDirection = Direction.up;
            getAnimation(Direction.up);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingDown && !goingLeft && !goingRight){
            move(0,-1);
            playerDirection = Direction.down;
            getAnimation(Direction.down);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingLeft && !goingUp && !goingDown){
            move(-1,0);
            playerDirection = Direction.left;
            getAnimation(Direction.left);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingRight && !goingDown && !goingUp){
            move(1,0);
            playerDirection = Direction.right;
            getAnimation(Direction.right);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingUp && goingLeft){
            move(-1,1);
            playerDirection = Direction.up;
            getAnimation(Direction.upLeft);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingUp && goingRight){
            move(1,1);
            playerDirection = Direction.up;
            getAnimation(Direction.upRight);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingDown && goingLeft){
            move(-1,-1);
            playerDirection = Direction.down;
            getAnimation(Direction.downLeft);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else if(goingDown && goingRight){
            move(1,-1);
            playerDirection = Direction.right;
            getAnimation(Direction.downRight);
            Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
        }
        else {
//            if(!player.isFainted()){
                animation = App.getInstance().getCurrentGame().getCurrentPlayer().getAvatarType().TiredAnimation(playerDirection);
                Camera.getInstance().print(animation,player.getPosition().x,player.getPosition().y,2,2);
//            }

        }
    }

    private void move(int dx , int dy){
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();

        if(player.isFainted())
            return;

        Tile tile = game.getMap().getTile(new Position(player.getPosition().x + dx,player.getPosition().y + dy ));


        if(tile != null && (tile.getBuilding() == null || (tile.getBuilding() instanceof House)) ){
            player.setPosition(new Position(player.getPosition().x +dx,player.getPosition().y + dy));
            player.decreaseEnergy(0.1f);
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
}
