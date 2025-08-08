package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Reaction extends PopUpMenu {

    private TextField inputField;
    private TextButton[] buttons;

    public Reaction(Stage stage) {
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
        for (int i = 0; i < 5; i++) {
            final int index = i;
            buttons[i] = new TextButton("Button " + (i + 1), skin);
            buttons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // TODO: Action for Button (index + 1)
                }
            });
            mainTable.add(buttons[i]).width(200).row();
        }

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
}
