package com.StardewValley.Views;

import com.StardewValley.Controllers.Camera;
import com.StardewValley.Controllers.PlayerController;
import com.StardewValley.Controllers.WordController;
import com.StardewValley.Main;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.PopUps.InventoryPopUp;
import com.StardewValley.Models.PopUps.PopUpManager;
import com.StardewValley.Models.PopUps.PopUpMenu;
import com.StardewValley.Models.PopUps.ToolPopUp;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.StardewValley.Controllers.Camera.TILE_SIZE;

public class GameView implements Screen , InputProcessor {
    private Stage stage;
    private PopUpManager popUpMenu;
    private ToolPopUp toolPopUp;
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

        else if(i == Input.Keys.ESCAPE){
            popUpMenu.show();
        }

        else if(i == Input.Keys.T){
            toolPopUp.toggle();
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

        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

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
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);  // your GameView InputProcessor first
        multiplexer.addProcessor(stage); // stage input second for UI drag/drop
        Gdx.input.setInputProcessor(multiplexer);
        popUpMenu = PopUpManager.getInstance(stage);
        toolPopUp = new ToolPopUp(stage);
        toolPopUp.show();
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();
        Camera.getInstance().update(player.getPosition().x, player.getPosition().y);
        Main.getInstance().getBatch().begin();
//        All To print
        WordController.getInstance().update();
        PlayerController.getInstance().update();
//        All To print
        if (player.getCurrentTool() != null) {
            Texture toolTexture = player.getCurrentTool().getTexture();
            float toolDrawX = player.getPosition().x + 1.5f; // offset right by 1 tile
            float toolDrawY = player.getPosition().y;       // same vertical position (adjust if needed)

            // Adjust scaling/size as needed; here we draw 1x1 tile size
            Main.getInstance().getBatch().draw(toolTexture, toolDrawX * TILE_SIZE, toolDrawY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        game.getTime().updateBatch(Main.getInstance().getBatch(), player.getPosition());
        Main.getInstance().getBatch().end();
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
