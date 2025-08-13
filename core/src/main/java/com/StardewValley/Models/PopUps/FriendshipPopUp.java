package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Communication.FriendShip;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

public class FriendshipPopUp extends PopUpMenu {

    private Table friendshipsTable;

    public FriendshipPopUp(Stage stage) {
        super(stage);
        createPopupMenu();
    }


    protected void createPopupMenu() {
        popupWindow = new Window("", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);

        popupWindow.getTitleLabel().setText("Friendships");
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        Table mainTable = new Table();
        mainTable.top().left();
        mainTable.defaults().pad(5);

        friendshipsTable = new Table();
        ScrollPane scrollPane = new ScrollPane(friendshipsTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        mainTable.add(scrollPane).width(400).height(300).row();

        tabs.clear();
        tabs.top().left();
        tabs.defaults().size(70, 70).padRight(4);
        tabs.add(createCloseTabImage(GameAssetManager.getInstance().EXIT_BUTTON));

        popupWindow.add(tabs).row();
        popupWindow.add(mainTable).pad(10);
        popupWindow.pack();
        popupWindow.setVisible(false);

        updateWindowPosition();
        stage.addActor(popupWindow);

        refreshFriendships();
    }

    private void refreshFriendships() {
        friendshipsTable.clear();

        List<FriendShip> friendships = App.getInstance().getCurrentGame().getCurrentPlayer().getFriendShips();

        if (friendships != null) {
            for (FriendShip f : friendships) {
                String username = f.getPlayer().getUser().getUsername();
                int xp = f.getXp();

                // Username & XP label
                Label infoLabel = new Label(username + " - XP: " + xp, skin);
                infoLabel.setColor(Color.BLACK);
                friendshipsTable.add(infoLabel).width(250).left();

                // Gift button
                TextButton giftButton = new TextButton("Gift", skin);
                giftButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        // TODO: Implement gifting logic for player "username"
                        System.out.println("Gift button clicked for " + username);
                    }
                });
                friendshipsTable.add(giftButton).width(100).right();

                friendshipsTable.row();
            }
        }
    }

    @Override
    protected void updateWindowPosition() {
        float x = Gdx.graphics.getWidth() / 2f - popupWindow.getWidth() / 2f;
        float y = Gdx.graphics.getHeight() / 2f - popupWindow.getHeight() / 2f;
        popupWindow.setPosition(x, y);
    }

    @Override
    public void show() {
        refreshFriendships();
        updateWindowPosition();
        super.show();
    }

    Image createCloseTabImage(Texture texture) {
        Image closeTab = new Image(texture);
        closeTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                PopUpManager.getInstance(stage).hide();
            }
        });
        return closeTab;
    }
}
