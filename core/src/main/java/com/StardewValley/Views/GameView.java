package com.StardewValley.Views;

import com.StardewValley.Controllers.*;
import com.StardewValley.Main;
import com.StardewValley.Models.*;
import com.StardewValley.Models.Animal.AnimalType;
import com.StardewValley.Models.Enums.FarmBuildings;
import com.StardewValley.Models.Map.Lake;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameView implements Screen , InputProcessor {
    private Stage stage;


    @Override
    public boolean keyDown(int i) {
        if(i == Input.Keys.W){
            PlayerController.getInstance().setGoingUp(true);
        }

        else if(i == Input.Keys.A){
            PlayerController.getInstance().setGoingLeft(true);
        }

        else if(i == Input.Keys.D){
            PlayerController.getInstance().setGoingRight(true);
        }

        else if(i == Input.Keys.S){
            PlayerController.getInstance().setGoingDown(true);
        }

        else if(i == Input.Keys.T){
            GameController.getInstance().cheatTime();
        }

        else if(i == Input.Keys.F){
            GameController.getInstance().cheatSeason();
        }

        else if(i == Input.Keys.R){
            GameController.getInstance().cheatThor();
        }

        else if(i == Input.Keys.M){
            WordController.getInstance().zoom();
        }

        else if(i == Input.Keys.Y && PlayerController.getInstance().whatIsClose() instanceof Lake){
            Main.getInstance().getScreen().dispose();
            Main.getInstance().setScreen(new FishingMiniGameView(GameAssetManager.getInstance().getSkin()));
        }

        else if(i == Input.Keys.X){
            GameController.getInstance().buildBarn(FarmBuildings.Barn,40,40);
            GameController.getInstance().buyAnimal(AnimalType.cow,"abbas");
            GameController.getInstance().buyAnimal(AnimalType.pig,"abbas");
            GameController.getInstance().buyAnimal(AnimalType.goat,"abbas");
            GameController.getInstance().buyAnimal(AnimalType.sheep,"abbas");
        }

        return false;
    }

    @Override
    public boolean keyUp(int i) {
        if(i == Input.Keys.W){
            PlayerController.getInstance().setGoingUp(false);
        }

        else if(i == Input.Keys.A){
            PlayerController.getInstance().setGoingLeft(false);
        }

        else if(i == Input.Keys.D){
            PlayerController.getInstance().setGoingRight(false);
        }

        else if(i == Input.Keys.S){
            PlayerController.getInstance().setGoingDown(false);
        }

        else if(i == Input.Keys.P){
            PlayerController.getInstance().startPetting();
        }



        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1f);
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();
        Camera.getInstance().update(player.getPosition().x, player.getPosition().y);
        Main.getInstance().getBatch().setProjectionMatrix(Camera.getInstance().getCombined());
        Main.getInstance().getBatch().begin();
//        All To print
        WordController.getInstance().update();
        PlayerController.getInstance().update();
        GameController.getInstance().update(Gdx.graphics.getDeltaTime());
        AnimalController.getInstance().update();

        LightningEffect lightning = GameController.getInstance().getLightningEffect();
        if (lightning != null) {
            lightning.render(Main.getInstance().getBatch(),
                GameController.getInstance().getLightningX(),
                GameController.getInstance().getLightningY());
        }

//        All To print
        Main.getInstance().getBatch().end();
        WordController.getInstance().drawDarknessOverlay();

        stage.act(Math.min( Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
