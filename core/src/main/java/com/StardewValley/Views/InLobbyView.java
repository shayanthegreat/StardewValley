package com.StardewValley.Views;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Networking.Common.Lobby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InLobbyView implements Screen {

    private TextButton leaveButton;
    private TextButton startGameButton;
    private Table memberTable;
    private Table rootTable;
    private Stage stage;
    private Skin skin;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = GameAssetManager.getInstance().getSkin();

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.pad(20);
        stage.addActor(rootTable);

        // Buttons
        startGameButton = new TextButton("Start Game", skin);
        leaveButton = new TextButton("Leave Lobby", skin);

        // Add click listeners
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO: Implement game start logic
                System.out.println("Start Game Clicked");
                // Example: ClientController.getInstance().startGame(mapId);
            }
        });

        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO: Implement leave lobby logic
                System.out.println("Leave Lobby Clicked");
                // Example: ClientController.getInstance().leaveLobby();
            }
        });

        // Buttons table
        Table buttonTable = new Table();
        buttonTable.add(startGameButton).pad(10).row();
        buttonTable.add(leaveButton).pad(10);

        // Member list
        memberTable = new Table(skin);
        memberTable.defaults().pad(5);
        updateMemberList();

        // Layout
        rootTable.add(new Label("Lobby Members:", skin)).left().top().padBottom(10).row();
        rootTable.add(memberTable).left().top().expandY().row();
        rootTable.add(buttonTable).bottom().right();
    }

    private void updateMemberList() {
        memberTable.clear();
        Lobby currentLobby = ClientData.getInstance().getLobby(ClientData.getInstance().lobbyCode);
        if (currentLobby != null) {
            for (String member : currentLobby.getMembers()) {
                Label label = new Label(member, skin);
                label.setAlignment(Align.left);
                memberTable.add(label).left().row();
            }
        } else {
            memberTable.add(new Label("No lobby data found.", skin)).left().row();
        }
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
