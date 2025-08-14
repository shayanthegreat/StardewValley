package com.StardewValley.Views;

import com.StardewValley.Main;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Networking.Common.PlayerDetails;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreBoardView implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;
    private SelectBox<String> sortBox;
    private TextButton backButton;
    private List<PlayerDetails> players;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = GameAssetManager.getInstance().getSkin();
        Gdx.input.setInputProcessor(stage);
        Image bgImage = new Image(new Texture(Gdx.files.internal("Background.jpg")));
        bgImage.setFillParent(true);
        stage.addActor(bgImage);
        players = new ArrayList<>(ClientData.getInstance().gameDetails.getPlayers().values());

        // Create SortBox once
        sortBox = new SelectBox<>(skin);
        sortBox.setItems("Gold", "Quest Count", "Skill Sum");
        sortBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateSort();
            }
        });

        // Create Back Button once
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new GameView());
            }
        });

        // Create table layout
        table = new Table();
        table.setFillParent(true);
        table.top();

        // Static header elements
        table.add(new Label("Scoreboard", skin)).pad(10).row();
        table.add(sortBox).pad(10).row();

        // Fill player list
        updateTable();

        // Add back button
        table.add(backButton).pad(10).row();

        stage.addActor(table);
    }

    private void updateSort() {
        String criteria = sortBox.getSelected();
        switch (criteria) {
            case "Gold":
                players.sort(Comparator.comparingInt(p -> -p.gold));
                break;
            case "Quest Count":
                players.sort(Comparator.comparingInt(p -> -p.questCount));
                break;
            case "Skill Sum":
                players.sort(Comparator.comparingInt(p -> -p.skillSum));
                break;
        }
        updateTable();
    }

    private void updateTable() {
        // Remove all player rows first
        // Preserve header (first 2 rows) and back button
        table.clearChildren();
        table.top();

        // Header
        table.add(new Label("Scoreboard", skin)).pad(10).row();
        table.add(sortBox).pad(10).row();

        // Player list
        for (PlayerDetails p : players) {
            table.add(new Label(
                p.username + " | Gold: " + p.gold +
                    " | Quests: " + p.questCount +
                    " | Skills: " + p.skillSum, skin
            )).left().pad(5).row();
        }

        // Back button
        table.add(backButton).pad(10).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
