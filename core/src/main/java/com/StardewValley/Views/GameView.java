package com.StardewValley.Views;

import com.StardewValley.Controllers.*;
import com.StardewValley.Main;
import com.StardewValley.Models.*;
import com.StardewValley.Models.Animal.AnimalType;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Map.*;
import com.StardewValley.Models.PopUps.*;
import com.StardewValley.Models.Store.Store;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;

import static com.StardewValley.Controllers.Camera.TILE_SIZE;

public class GameView implements Screen , InputProcessor {
    private static Stage stage;
    private PopUpManager popUpMenu;
    private ToolPopUp toolPopUp;
    private SeedPopUp seedPopUp;
    private AnimalPopUp animalPopUp;
//    private BackPackPopUp backPackPopUp;
//    private RefrigeratorPopUp refrigeratorPopUp;
    private GiftingNPCPopUp giftingNPCPopUp;
    private StorePopUp storePopUp;
    private ReactionPopUp reactionPopUp;
    private CookingPopUp cookingPopUp;
    private FridgePopUp fridgePopUp;
    private CraftingPopUp craftingPopUp;
    private ArtPopUp artPopUp;
    // Tool rotation animation state
    private boolean isToolAnimating = false;
    private float toolAnimationTime = 0f;
    private float toolAnimationDuration = 0.3f; // seconds
    private float toolMaxRotation = 45f;
    private Label activeBuffsLabel;
    private TextButton reaction;
    public boolean isTyping = false;
    private TextButton scoreBoard;
    private TextButton chat;
    private InitialChatPopUp initialChatPopUp;
    private FriendshipPopUp friendshipPopUp;

    @Override
    public boolean keyDown(int i) {
        if(isTyping){
            return false;
        }
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

        else if(i == Input.Keys.X){
            GameController.getInstance().cheatSeason();
        }

        else if(i == Input.Keys.F){
            friendshipPopUp.show();
        }

        else if(i == Input.Keys.R) {
            GameController.getInstance().cheatThor();
        } else if (i == Input.Keys.M) {
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
            App.getInstance().getCurrentGame().nextDay();
        }
        else if(i == Input.Keys.C){
            cookingPopUp.show();
        }
        else if(i == Input.Keys.Q){
//            fridgePopUp.show();
        }
        else if(i == Input.Keys.B){
            craftingPopUp.show();
        }
        else if(i == Input.Keys.X){
            GameController.getInstance().buyAnimal(AnimalType.cow,"abbas");
            GameController.getInstance().buyAnimal(AnimalType.pig,"ahmad");
            GameController.getInstance().buyAnimal(AnimalType.goat,"asd");
            GameController.getInstance().buyAnimal(AnimalType.sheep,"asss");
        }
        else if (i == Input.Keys.Z) App.getInstance().getCurrentGame().getCurrentPlayer().setCurrentTool(null);
        else if(i == Input.Keys.V){
            artPopUp.show();
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
        App.getInstance().getCurrentGame().setView(this);
        stage = new Stage();
        reaction = new TextButton("React", GameAssetManager.getInstance().getSkin());
        reaction.setSize(120, 50);
        reaction.setPosition(Gdx.graphics.getWidth() - reaction.getWidth() - 10, 30);
        reaction.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                reactionPopUp.show();
                isTyping = true;
            }
        });
        stage.addActor(reaction);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);  // your GameView InputProcessor first
        multiplexer.addProcessor(stage); // stage input second for UI drag/drop
        Gdx.input.setInputProcessor(multiplexer);
//        popUpMenu = PopUpManager.getInstance(stage);
        scoreBoard = new TextButton("Score Board", GameAssetManager.getInstance().getSkin());
        scoreBoard.setSize(120, 50);
        scoreBoard.setPosition(Gdx.graphics.getWidth() - reaction.getWidth() - 10, 100);
        scoreBoard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Main.getInstance().setScreen(new ScoreBoardView());
            }
        });
        stage.addActor(scoreBoard);

        chat = new TextButton("Chat", GameAssetManager.getInstance().getSkin());
        chat.setSize(120, 50);
        chat.setPosition(30, 170);
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                initialChatPopUp.show();
            }
        });
        stage.addActor(chat);
        popUpMenu = PopUpManager.set(stage);
        toolPopUp = new ToolPopUp(stage);
        seedPopUp = new SeedPopUp(stage);
        animalPopUp = new AnimalPopUp(stage);
//        backPackPopUp = new BackPackPopUp(stage);
//        refrigeratorPopUp = new RefrigeratorPopUp(stage);
        giftingNPCPopUp = new GiftingNPCPopUp(stage);
        toolPopUp.show();
        storePopUp = new StorePopUp(stage);
        storePopUp.hide();
        cookingPopUp = new CookingPopUp(stage);
        cookingPopUp.hide();
        fridgePopUp = new FridgePopUp(stage);
        fridgePopUp.hide();
        craftingPopUp = new CraftingPopUp(stage);
        craftingPopUp.hide();
        toolPopUp.show();
        artPopUp = new ArtPopUp(stage);
        artPopUp.hide();
        reactionPopUp = new ReactionPopUp(stage);
        initialChatPopUp = new InitialChatPopUp(stage);
        initialChatPopUp.hide();
        friendshipPopUp = new FriendshipPopUp(stage);
        friendshipPopUp.hide();

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

            if (App.getInstance().getCurrentGame().getCurrentPlayer().getCurrentTool() != null) {
                isToolAnimating = true;
                toolAnimationTime = 0f;
            }

            Map map = App.getInstance().getCurrentGame().getMap();
            Tile tile = map.getTile(clickedPosition);
            if(tile == null){
                return false;
            }
            Building building = tile.getBuilding();
            if(tile.isPlowed() && !tile.containsPlant()){
                seedPopUp.setTargetPosition(clickedPosition);
                seedPopUp.setOnSeedSelected((seed, position) -> {
                    GameController.getInstance().plant(seed, position);
                });
                seedPopUp.show();
            }
            else if(building instanceof Store){
                NPCVillage npcVillages = App.getInstance().getCurrentGame().getMap().getNpcVillage();
                for (int i = 0; i < npcVillages.getStorePositions().size(); i++) {
                    if (npcVillages.getStorePositions().get(i).equals(clickedPosition)) {
                        storePopUp.refresh(npcVillages.getStores().get(i));
                        storePopUp.show();
                    }
                }
            } else {
                GameController.getInstance().handleTileClick(clickedPosition, stage);
            }
        }

        if (button == Input.Buttons.RIGHT) {
            GameController.getInstance().handleScape();
            Vector3 worldCoordinates = Camera.getInstance().getCamera().unproject(new Vector3(screenX, screenY, 0));
            int tileX = (int)(worldCoordinates.x / TILE_SIZE);
            int tileY = (int)(worldCoordinates.y / TILE_SIZE);
            Position clickedPosition = new Position(tileX, tileY);

            if (fridgePopUp.isClicked(tileX, tileY)) {
                fridgePopUp.show();
            }
            else if(App.getInstance().getCurrentGame().getCurrentPlayer().isInHouse()){
                popUpMenu.show();
            }
            else if(giftingNPCPopUp.isClicked(clickedPosition)){
                giftingNPCPopUp.show();
            }
            else {
                animalPopUp.show();
            }
        }

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
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();
        Camera.getInstance().update(player.getPosition().x, player.getPosition().y);

        Main.getInstance().getBatch().begin();

        WordController.getInstance().update();
        PlayerController.getInstance().update();
        GameController.getInstance().update(Gdx.graphics.getDeltaTime());
        AnimalController.getInstance().update();
        NPCController.getInstance().update();
        if(initialChatPopUp.getOpenedChat() != null){
            initialChatPopUp.getOpenedChat().refreshMessages();
            initialChatPopUp.getOpenedChat().updateWindowPosition();
        }

        LightningEffect lightning = GameController.getInstance().getLightningEffect();
        if (lightning != null) {
            lightning.render(Main.getInstance().getBatch(),
                GameController.getInstance().getLightningX(),
                GameController.getInstance().getLightningY());
        }

        if (player.getCurrentTool() != null) {
            Texture toolTexture = player.getCurrentTool().getTexture();
            float toolDrawX = player.getPosition().x + 1.5f;
            float toolDrawY = player.getPosition().y;
            float rotation = 0f;

            if (isToolAnimating) {
                toolAnimationTime += delta;
                float progress = toolAnimationTime / toolAnimationDuration;
                if (progress < 0.5f) rotation = toolMaxRotation * (progress / 0.5f);
                else if (progress < 1f) rotation = toolMaxRotation * (1f - (progress - 0.5f) / 0.5f);
                else {
                    isToolAnimating = false;
                    toolAnimationTime = 0f;
                }
            }

            Main.getInstance().getBatch().draw(
                toolTexture,
                toolDrawX * TILE_SIZE, toolDrawY * TILE_SIZE,
                TILE_SIZE / 2f, TILE_SIZE / 2f,
                TILE_SIZE, TILE_SIZE,
                1f, 1f,
                rotation,
                0, 0,
                toolTexture.getWidth(), toolTexture.getHeight(),
                false, false
            );
        }

        game.getTime().updateBatch(Main.getInstance().getBatch(), player.getPosition());
        Main.getInstance().getBatch().end();

        showOrUpdateActiveBuffs();
        WordController.getInstance().drawDarknessOverlay();
        stage.act(Math.min(delta, 1 / 30f));
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

    public static void showError(String error) {
        final Dialog dialog = new Dialog("!!!", GameAssetManager.getInstance().getSkin()) {
            @Override
            protected void result(Object object) {
            }
        };

        dialog.text(error);
        dialog.button("OK");

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                dialog.hide();
            }
        }, 5);
    }


    private void showOrUpdateActiveBuffs() {
        StringBuilder sb = new StringBuilder();
        Time[] lastBuffTime = App.getInstance().getCurrentGame().getCurrentPlayer().getLastBuffTime();

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: sb.append("Farming Buff: "); break;
                case 1: sb.append("Mining Buff: "); break;
                case 2: sb.append("Foraging Buff: "); break;
                case 3: sb.append("Fishing Buff: "); break;
            }

            if (Time.compareTime(App.getInstance().getCurrentGame().getTime(), lastBuffTime[i])) {
                sb.append("Active!\n");
            } else {
                sb.append("Not Active!\n");
            }
        }

        if (activeBuffsLabel == null) {
            activeBuffsLabel = new Label(sb.toString(), GameAssetManager.getInstance().getSkin());
            activeBuffsLabel.setFontScale(1.2f);
            activeBuffsLabel.setAlignment(Align.left);
            activeBuffsLabel.setPosition(50, stage.getHeight() - 100); // Top-left
            stage.addActor(activeBuffsLabel);
        } else {
            activeBuffsLabel.setText(sb.toString());
        }
    }
    public static Stage getStage() {
        return stage;
    }

    public InitialChatPopUp getChatPopUp() {
        return initialChatPopUp;
    }
}
