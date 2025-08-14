package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InitialChatPopUp extends PopUpMenu {

    public InitialChatPopUp(Stage stage) {
        super(stage);
        createPopupMenu();
    }

    private ChatScreen openedChat;


    protected void createPopupMenu() {
        popupWindow = new Window("", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);

        popupWindow.getTitleLabel().setText("Chat");
        popupWindow.getTitleLabel().setColor(Color.BLACK);
        popupWindow.setColor(Color.BLUE);
        popupWindow.pad(10);

        Table mainTable = new Table();
        mainTable.top().left();
        mainTable.defaults().pad(5).width(200);

        // "All" button
        TextButton allButton = new TextButton("All", skin);
        allButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ChatScreen chatScreen = new ChatScreen(GameView.getStage(),"all");
                openedChat = chatScreen;
                chatScreen.show();
                App.getInstance().getCurrentGame().getView().isTyping = true;
            }
        });
        mainTable.add(allButton).row();


        Set<String> originalNames = ClientData.getInstance().gameDetails.getPlayers().keySet();
        List<String> playerNames = new ArrayList<>(originalNames);
        playerNames.remove(ClientData.getInstance().selfDetails.username);

        for (String playerName : playerNames) {
            TextButton playerButton = new TextButton(playerName, skin);
            playerButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ChatScreen chatScreen = new ChatScreen(GameView.getStage(), playerName);
                    openedChat = chatScreen;
                    chatScreen.show();
                    App.getInstance().getCurrentGame().getView().isTyping = true;
                }
            });
            mainTable.add(playerButton).row();
        }


        // Close tab
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
    }

    @Override
    protected void updateWindowPosition() {
        float x = 20; // left side margin
        float y = 20; // bottom margin
        popupWindow.setPosition(x, y);
    }

    @Override
    public void show() {
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

    public ChatScreen getOpenedChat() {
        return openedChat;
    }
}
