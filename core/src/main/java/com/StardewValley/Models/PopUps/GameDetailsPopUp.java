package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Networking.Common.GameDetails;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class GameDetailsPopUp extends PopUpMenu {

    private Table contentTable;
    private ArrayList<GameDetails> games;

    public GameDetailsPopUp(Stage stage, ArrayList<GameDetails> games) {
        super(stage);
        createPopupMenu();
        this.games = games;
    }

    protected void createPopupMenu() {
        popupWindow = new Window("", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);

        popupWindow.getTitleLabel().setText("Game Details");
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        tabs.clear();
        tabs.top().left();
        tabs.defaults().size(70, 70).padRight(4);
        tabs.add(createCloseTabImage(GameAssetManager.getInstance().EXIT_BUTTON));
        popupWindow.add(tabs).row();

        contentTable = new Table();
        contentTable.top().left();
        contentTable.defaults().pad(10);

        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        popupWindow.add(scrollPane).width(400).height(250).row();
        popupWindow.pack();
        popupWindow.setVisible(false);

        updateWindowPosition();
        stage.addActor(popupWindow);
    }

    private void refreshGameDetails() {
        contentTable.clear();

        for (GameDetails gameDetails : games) {

            contentTable.add(new Label("Game ID: " + gameDetails.getGameId(), skin)).left().row();

            contentTable.add(new Label("Players:", skin)).left().row();
            for (String username : gameDetails.getPlayers().keySet()) {
                contentTable.add(new Label("- " + username, skin)).left().row();
            }

            TextButton joinButton = new TextButton("Join", skin);
            joinButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Dialog waitingDialog = new Dialog("", skin);
                    waitingDialog.setModal(true);
                    waitingDialog.setMovable(false);

                    waitingDialog.getTitleLabel().setText("Game Lobby");
                    waitingDialog.getTitleLabel().setColor(Color.BROWN);
                    waitingDialog.setColor(Color.LIGHT_GRAY);
                    waitingDialog.pad(20);

                    ClientController.getInstance().informReadyToLoad(gameDetails.getGameId());

                    waitingDialog.text(new Label("Waiting for other players to join", skin)).row();

                    TextButton leaveButton = new TextButton("Leave", skin);
                    leaveButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            waitingDialog.hide();
                            ClientController.getInstance().informNotReadyToLoad(gameDetails.getGameId());
                        }
                    });

                    waitingDialog.button(leaveButton).padTop(15);

                    waitingDialog.pack();
                    waitingDialog.show(stage);
                }
            });

            contentTable.add(joinButton).padTop(20).row();

        }
    }

    @Override
    public void show() {
        refreshGameDetails();
        super.show();
    }

    public void refresh() {
        refreshGameDetails();
    }
}
