package com.StardewValley.Views;

import com.StardewValley.Main;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Networking.Common.PlayerDetails;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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

        players = new ArrayList<>(ClientData.getInstance().gameDetails.getPlayers().values());

        sortBox = new SelectBox<>(skin);
        sortBox.setItems("Gold", "Quest Count", "Skill Sum");
        sortBox.addListener(event -> {
            updateSort();
            return false;
        });
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event , float x, float y) {
                Main.getInstance().setScreen(new GameView());
            }
        });

        table = new Table();
        table.setFillParent(true);

        table.top().add(new Label("Scoreboard", skin)).pad(10).row();
        table.add(sortBox).pad(10).row();
        table.add(backButton).pad(10).row();

        updateTable();

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
        table.clearChildren();
        table.top().add(new Label("Scoreboard", skin)).pad(10).row();
        table.add(sortBox).pad(10).row();

        for (PlayerDetails p : players) {
            table.add(new Label(p.username + " | Gold: " + p.gold +
                " | Quests: " + p.questCount +
                " | Skills: " + p.skillSum, skin)).left().pad(5).row();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
