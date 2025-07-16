package com.StardewValley.View;

import com.StardewValley.Controller.GameController;
import com.StardewValley.Main;
import com.StardewValley.Models.Interactions.Messages.GameMessage;
import com.badlogic.gdx.Gdx;
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

    public GameMenu(Skin skin) {
        super(skin);
        this.skin = skin;
        startGame = new TextButton("New Game", skin);
        back = new TextButton("Back", skin);
        controller = GameController.getInstance();
        startGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new PregameView(skin));
            }
        });
        stage = new Stage();
        table = new Table();
    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        table.setFillParent(true);
        table.center().pad(30);

        table.row().padTop(5);
        table.add(startGame).width(400).height(50).left();

        table.row().padTop(15);
        table.row().padTop(5);
        table.add(back).width(400).height(50).left();
        stage.addActor(table);

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
        Main.getInstance().getBatch().begin();
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
