package com.StardewValley.Views;

import com.StardewValley.Controllers.*;
import com.StardewValley.Controllers.Camera;
import com.StardewValley.Controllers.PlayerController;
import com.StardewValley.Controllers.WordController;
import com.StardewValley.Main;
import com.StardewValley.Models.*;
import com.StardewValley.Models.Map.Map;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Map.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.PopUps.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.StardewValley.Controllers.Camera.TILE_SIZE;

public class GameView implements Screen , InputProcessor {
    private Stage stage;
    private PopUpManager popUpMenu;
    private ToolPopUp toolPopUp;
    private SeedPopUp seedPopUp;
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

        else if(i == Input.Keys.P){
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
        else if(i == Input.Keys.ESCAPE){
            popUpMenu.show();
        }

        else if(i == Input.Keys.T){
            toolPopUp.toggle();
        }
        else if(i == Input.Keys.G){
            App.getInstance().getCurrentGame().getTime().nextDay();
        }
//        else if(i == Input.Keys.X){
//            GameController.getInstance().buildBarn(FarmBuildings.Barn,40,40);
//            GameController.getInstance().buyAnimal(AnimalType.cow,"abbas");
//            GameController.getInstance().buyAnimal(AnimalType.pig,"abbas");
//            GameController.getInstance().buyAnimal(AnimalType.goat,"abbas");
//            GameController.getInstance().buyAnimal(AnimalType.sheep,"abbas");
//        }

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

//        else if(i == Input.Keys.P){
//            PlayerController.getInstance().startPetting();
//        }



        return false;
    }

    @Override
    public boolean keyTyped(char c) {
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
        seedPopUp = new SeedPopUp(stage);
        toolPopUp.show();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            // Step 1: Convert screen coordinates to world coordinates
            Vector3 worldCoordinates = Camera.getInstance().getCamera().unproject(new Vector3(screenX, screenY, 0));

            // Step 2: Convert world coordinates to tile coordinates
            int tileX = (int)(worldCoordinates.x / TILE_SIZE);
            int tileY = (int)(worldCoordinates.y / TILE_SIZE);

            // Optional: if you want to do something with the tile position
            Position clickedPosition = new Position(tileX, tileY);

            Map map = App.getInstance().getCurrentGame().getMap();
            Tile tile = map.getTile(clickedPosition);
            if(tile.isPlowed() && !tile.containsPlant()){
                seedPopUp.setTargetPosition(clickedPosition);
                seedPopUp.setOnSeedSelected((seed, position) -> {
                    GameController.getInstance().plant(seed, position);
                });
                seedPopUp.show();
            }
            else{
                GameController.getInstance().handleTileClick(clickedPosition);
            }
        }
        return false;
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
        GameController.getInstance().update(Gdx.graphics.getDeltaTime());
        AnimalController.getInstance().update();

        LightningEffect lightning = GameController.getInstance().getLightningEffect();
        if (lightning != null) {
            lightning.render(Main.getInstance().getBatch(),
                GameController.getInstance().getLightningX(),
                GameController.getInstance().getLightningY());
        }

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
