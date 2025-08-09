package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Common.Reaction;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReactionPopUp extends PopUpMenu {

    private TextField inputField;
    private TextButton[] buttons;

    public ReactionPopUp(Stage stage) {
        super(stage);
        createPopupMenu();
    }


    protected void createPopupMenu() {
        popupWindow = new Window("", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);

        popupWindow.getTitleLabel().setText("Actions");
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        Table mainTable = new Table();
        mainTable.top().left();
        mainTable.defaults().pad(5);

        // TextBox
        inputField = new TextField("", skin);
        mainTable.add(inputField).width(200).row();

        // 5 Buttons with TODOs
        buttons = new TextButton[5];
        for (int i = 0; i < 4; i++) {
            final int index = i;
            buttons[i] = new TextButton(com.StardewValley.Networking.Common.Reaction.getDefaults().get(i), skin);
            buttons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ClientController.getInstance().setReaction(com.StardewValley.Networking.Common.Reaction.getDefaults().get(index));
                }
            });
            mainTable.add(buttons[i]).width(200).row();
        }

        buttons[4] = new TextButton("Send", skin);
        buttons[4].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(checkInput(inputField.getText()))
                    ClientController.getInstance().setReaction(inputField.getText());
            }
        });
        mainTable.add(buttons[4]).width(200).row();

        // Close Tab
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
        float x = Gdx.graphics.getWidth() - popupWindow.getWidth() - 20;
        float y = 20; // bottom-right corner with margin
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
                GameView.isTyping = false;
            }
        });
        return closeTab;
    }

    private boolean checkInput(String text) {
        Pattern pattern = Pattern.compile("/set default (?<reaction>.+?)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            String reaction = matcher.group("reaction");
            Reaction.addDefault(reaction);
            return false;
        }
        return true;

    }
}
