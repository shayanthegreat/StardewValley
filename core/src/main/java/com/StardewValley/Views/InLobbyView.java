package com.StardewValley.Views;

import com.StardewValley.Main;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Networking.Common.Lobby;
import com.StardewValley.Networking.Server.ClientConnection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InLobbyView implements Screen {

    private TextButton leaveButton;
    private TextButton startGameButton;
    private SelectBox<Integer> mapSelectBox;
    private Table memberTable;
    private Table rootTable;
    private Stage stage;
    private Skin skin;
    private TextButton refreshLobbiesButton;
    private static boolean isGameStarted = false;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Lobby currentLobby = ClientData.getInstance().getLobby(ClientData.getInstance().lobbyCode);

        skin = GameAssetManager.getInstance().getSkin();

        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.pad(20);
        stage.addActor(rootTable);

        // Buttons
        startGameButton = new TextButton("Start Game", skin);
        leaveButton = new TextButton("Leave Lobby", skin);
        refreshLobbiesButton = new TextButton("Refresh", skin);

        // Map ID selector
        mapSelectBox = new SelectBox<>(skin);
        mapSelectBox.setItems(1, 2, 3, 4);
        mapSelectBox.setSelected(1); // Default map ID

        // Add click listeners
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int selectedMapId = mapSelectBox.getSelected();
                ClientController.getInstance().startGame(selectedMapId);
            }
        });

        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ClientController.getInstance().leaveLobby();
            }
        });

        refreshLobbiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ClientController.getInstance().refreshLobbies();
            }
        });

        // Buttons + Map Select table
        Table buttonTable = new Table();
        buttonTable.add(new Label("Select Map:", skin)).padBottom(5).row();
        buttonTable.add(mapSelectBox).padBottom(15).row();
        buttonTable.add(startGameButton).pad(10).row();
        buttonTable.add(leaveButton).pad(10);
        buttonTable.add(refreshLobbiesButton).pad(10);

        // Member list
        memberTable = new Table(skin);
        memberTable.defaults().pad(5);
        updateMemberList();

        // Layout
        rootTable.add(new Label(currentLobby.getName() + "(" + currentLobby.getCode() + ")", skin)).padBottom(5).row();
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

    public static void setInGame(boolean inGame) {
        isGameStarted = inGame;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
        updateMemberList();
        if(ClientData.getInstance().lobbyCode.isEmpty()){
            Main.getInstance().getScreen().dispose();
            Main.getInstance().setScreen(new LobbyView());
        }
        if(isGameStarted){
            Main.getInstance().setScreen(new GameView());
        }
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
        stage.dispose();
    }
}
