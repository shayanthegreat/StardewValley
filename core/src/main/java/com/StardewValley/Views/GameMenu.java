package com.StardewValley.Views;

import com.StardewValley.Controllers.GameController;
import com.StardewValley.Main;
import com.StardewValley.Models.PopUps.GameDetailsPopUp;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Common.GameDetails;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class GameMenu extends MenuView {
    private Stage stage;
    private TextButton startGame;
    private TextButton back;
    private GameController controller;
    private Table table;
    private Skin skin;
    private TextButton loadGame;
    private GameDetailsPopUp gameDetailsPopUp;

    public GameMenu(Skin skin) {
        super(skin);
        this.skin = skin;
        startGame = new TextButton("New Game", skin);
        back = new TextButton("Back", skin);
        controller = GameController.getInstance();
        startGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new LobbyView());
            }
        });
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new MainMenu(skin));
            }
        });
        loadGame = new TextButton("Load Game", skin);
        loadGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ArrayList<GameDetails> games = ClientController.getInstance().getGames();
                gameDetailsPopUp = new GameDetailsPopUp(stage, games);
                gameDetailsPopUp.show();
            }
        });
        stage = new Stage();
        table = new Table();
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image bgImage = new Image(new Texture(Gdx.files.internal("Background.jpg")));
        bgImage.setFillParent(true);
        stage.addActor(bgImage);
        table.setFillParent(true);
        table.center().pad(30);

        table.row().padTop(5);
        table.add(startGame).width(400).height(50).left();

        table.row().padTop(5);
        table.add(loadGame).width(400).height(50).left();


        table.row().padTop(5);
        table.add(back).width(400).height(50).left();
        stage.addActor(table);


    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
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
