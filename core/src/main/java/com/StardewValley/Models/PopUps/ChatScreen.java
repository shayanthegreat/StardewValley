package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Networking.Common.ChatMessage;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class ChatScreen extends PopUpMenu {

    private final String otherUsername;
    private TextField inputField;
    private Table messagesTable;

    public ChatScreen(Stage stage, String otherUsername) {
        super(stage);
        this.otherUsername = otherUsername;
        createPopupMenu();
    }


    protected void createPopupMenu() {
        popupWindow = new Window("", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);

        popupWindow.getTitleLabel().setText("Chat with " + otherUsername);
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        Table mainTable = new Table();
        mainTable.top().left();
        mainTable.defaults().pad(5);

        messagesTable = new Table();
        ScrollPane scrollPane = new ScrollPane(messagesTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        mainTable.add(scrollPane).width(300).height(300).row();

        inputField = new TextField("", skin);
        mainTable.add(inputField).width(220).left();

        // Send button
        TextButton sendButton = new TextButton("Send", skin);
        sendButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String text = inputField.getText();
                ClientController.getInstance().sendChatMessage(text, otherUsername);
            }
        });
        mainTable.add(sendButton).width(70).right().row();

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

        refreshMessages();
    }

    private void refreshMessages() {
        messagesTable.clear();
        ArrayList<ChatMessage> messages = ClientData.getInstance()
            .gameDetails.getChat()
            .getChats(ClientData.getInstance().selfDetails.username, otherUsername);

        if (messages != null) {
            for (ChatMessage msg : messages) {
                Label label = new Label(msg.sender + ": " + msg.text, skin);
//                Tagging
                if(msg.text.contains(ClientData.getInstance().selfDetails.username)) {
                    label.setColor(Color.RED);
                }
                label.setWrap(true);
                label.setColor(Color.BLACK);
                messagesTable.add(label).width(280).left().row();
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
        refreshMessages();
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
                GameView.isTyping = false;
            }
        });
        return closeTab;
    }
}
