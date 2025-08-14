package com.StardewValley.Views;

import com.StardewValley.Main;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Interactions.Commands.RegistrationCommand;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Networking.Common.Lobby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class LobbyView implements Screen {
    private Stage stage;
    private Skin skin;

    private Table visibleLobbyTable;
    private Table searchedLobbyTable;
    private Table onlineUsersTable;
    private TextButton back;

    private TextField searchField;
    private TextButton searchButton;
    private TextButton createLobbyButton;
    private Label searchResultLabel;
    private TextButton refreshButton;

    private float updateTimer = 0;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = GameAssetManager.getInstance().getSkin();
        back = new TextButton("Back", skin);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new GameMenu(GameAssetManager.getInstance().getSkin()));
            }
        });
        visibleLobbyTable = new Table(skin);
        searchedLobbyTable = new Table(skin);
        onlineUsersTable = new Table(skin);

        searchField = new TextField("", skin);
        searchButton = new TextButton("Search Lobby", skin);
        createLobbyButton = new TextButton("Create Lobby", skin);
        searchResultLabel = new Label("", skin);

        searchButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String code = searchField.getText().trim();
                Lobby found = ClientData.getInstance().getLobbyByName(code);
                if (found != null) {
                    showSearchedLobby(found);
                } else {
                    searchResultLabel.setText("Lobby not found");
                    searchedLobbyTable.clear();
                }
            }
        });

        refreshButton = new TextButton("Refresh", skin);
        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                ClientController.getInstance().refreshLobbies();
            }
        });

        createLobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Dialog dialog = new Dialog("Create Lobby", skin);
                dialog.pad(20);
                final TextField nameField = new TextField("", skin);
                final TextField passwordField = new TextField("", skin);
                final CheckBox visibleCheckBox = new CheckBox("Visible", skin);
                final CheckBox privateCheckBox = new CheckBox("Private", skin);

                nameField.setMessageText("Lobby name...");
                passwordField.setMessageText("Password...");

                Table dialogTable = new Table(skin);
                dialogTable.pad(10).defaults().pad(5);

                dialogTable.add(new Label("Name:", skin)).left();
                dialogTable.add(nameField).width(200).row();

                dialogTable.add(new Label("Password:", skin)).left();
                dialogTable.add(passwordField).width(200).row();

                dialogTable.add(visibleCheckBox).colspan(2).left().row();
                dialogTable.add(privateCheckBox).colspan(2).left().row();

                dialog.getContentTable().add(dialogTable).row();

                TextButton createButton = new TextButton("Create", skin);
                TextButton cancelButton = new TextButton("Cancel", skin);

                dialog.button(createButton);
                dialog.button(cancelButton);
                dialog.show(stage);

                createButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String name = nameField.getText().trim();
                        String password = passwordField.getText().trim();
                        boolean visible = visibleCheckBox.isChecked();
                        boolean isPrivate = privateCheckBox.isChecked();

                        ClientController.getInstance().createLobby(name, isPrivate, password, visible);
                        Main.getInstance().getScreen().dispose();
                        Main.getInstance().setScreen(new InLobbyView());
                        dialog.hide();
                    }
                });

                cancelButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dialog.hide();
                    }
                });
            }
        });

        Table root = new Table();
        root.setFillParent(true);
        root.pad(20);

        Table leftPanel = new Table(skin);
        leftPanel.pad(10);
        leftPanel.add(new Label("Visible Lobbies", skin, "title")).left().row();

        ScrollPane visibleScroll = new ScrollPane(visibleLobbyTable, skin);
        visibleScroll.setFadeScrollBars(false);
        leftPanel.add(visibleScroll).expand().fill().row();
        leftPanel.add(createLobbyButton).padTop(10);

        Table rightPanel = new Table(skin);
        rightPanel.pad(10);
        rightPanel.add(new Label("Search Lobby by Code", skin, "title")).left().row();
        rightPanel.add(searchField).fillX().padTop(5).row();
        rightPanel.add(searchButton).padTop(5).row();
        rightPanel.add(searchResultLabel).padTop(5).row();

        ScrollPane searchScroll = new ScrollPane(searchedLobbyTable, skin);
        searchScroll.setFadeScrollBars(false);
        rightPanel.add(searchScroll).expand().fill().padTop(10);

        Table bottomPanel = new Table(skin);
        bottomPanel.pad(10);
        bottomPanel.add(new Label("Online Users", skin, "title")).left().row();
        ScrollPane userScroll = new ScrollPane(onlineUsersTable, skin);
        userScroll.setFadeScrollBars(false);
        bottomPanel.add(userScroll).expandX().fillX();

        root.add(leftPanel).expand().fill().padRight(20);
        root.add(rightPanel).width(300).top();
        root.row();
        root.add(bottomPanel).colspan(2).expandX().fillX().padTop(20);
        root.add(refreshButton).expandX().fillX().padTop(20);
        root.row();
        root.add(back).colspan(2).expandX().fillX();

        stage.addActor(root);
    }

    private void buildVisibleLobbyTable() {
        visibleLobbyTable.clear();
        for (Lobby lobby : ClientData.getInstance().lobbies) {
            if (lobby.isVisible()) {
                Table row = new Table(skin);
                TextButton joinButton = new TextButton("Join", skin);
                row.add(joinButton);
                joinButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        final TextField codeField = new TextField("", skin);
                        Dialog dialog = new Dialog("", skin) {

                            @Override
                            protected void result(Object object) {
                                boolean accepted = (Boolean) object;
                                if(accepted){
                                    ClientController.getInstance().joinLobby(lobby.getCode());
                                    Main.getInstance().getScreen().dispose();
                                    Main.getInstance().setScreen(new InLobbyView());
                                }
                            }
                        };
                        dialog.add(codeField);
                        dialog.button("Join", true);
                        dialog.button("Cancel", false);
                        dialog.show(stage);

                    }
                });
                row.add(new Label(lobby.getName() + " (" + lobby.getCode() + ")", skin)).left().padRight(10);


                visibleLobbyTable.add(row).left().pad(5).row();
            }
        }
    }

    private void showSearchedLobby(Lobby lobby) {
        searchedLobbyTable.clear();
        searchResultLabel.setText("Lobby Found!");
        searchedLobbyTable.add(new Label("Name: " + lobby.getName(), skin)).left().pad(5).row();
        searchedLobbyTable.add(new Label("Code: " + lobby.getCode(), skin)).left().pad(5).row();
        searchedLobbyTable.add(new Label("Private: " + (lobby.isPrivate() ? "Yes" : "No"), skin));
        TextButton button = new TextButton("Join", skin);
        searchedLobbyTable.add(button).left().pad(5).row();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final TextField codeField = new TextField("", skin);
                Dialog dialog = new Dialog("", skin) {

                    @Override
                    protected void result(Object object) {
                        boolean accepted = (Boolean) object;
                        if(accepted){
                            ClientController.getInstance().joinLobby(lobby.getCode());
                            Main.getInstance().getScreen().dispose();
                            Main.getInstance().setScreen(new InLobbyView());
                        }

                    }
                };
                dialog.add(codeField);
                dialog.button("Join", true);
                dialog.button("Cancel", false);
                dialog.show(stage);
            }
        });
    }

    private void buildOnlineUsersTable() {
        onlineUsersTable.clear();
        onlineUsersTable.add(new Label("Username", skin)).pad(5);
        onlineUsersTable.add(new Label("In Lobby?", skin)).pad(5);
        onlineUsersTable.add(new Label("Lobby Code", skin)).pad(5);
        onlineUsersTable.row();

        for (String username : ClientData.getInstance().onlineUsers) {
            String lobbyCode = "-";
            String inLobby = "No";

            for (Lobby lobby : ClientData.getInstance().lobbies) {
                if (lobby.getMembers().contains(username)) {
                    inLobby = "Yes";
                    lobbyCode = lobby.getCode();
                    break;
                }
            }

            onlineUsersTable.add(new Label(username, skin)).pad(5);
            onlineUsersTable.add(new Label(inLobby, skin)).pad(5);
            onlineUsersTable.add(new Label(lobbyCode, skin)).pad(5);
            onlineUsersTable.row();
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();


        updateTimer += delta;
        if (updateTimer >= 1f) {
            buildVisibleLobbyTable();
            buildOnlineUsersTable();
            updateTimer = 0;
        }

    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
