package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Color;

public class PopUpMenu {
    protected Stage stage;
    protected Window popupWindow;
    protected Skin skin;
    protected ImageButton trashCan;
    protected Texture slot;
    protected Table tabs;

    public PopUpMenu(Stage stage) {
        this.stage = stage;
        this.skin = GameAssetManager.getInstance().SKIN;
        this.slot = GameAssetManager.getInstance().BLOCK;
        this.tabs = new Table();
        createPopupMenu();
    }

    private void createPopupMenu() {
        popupWindow = new Window("", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);

        // Customize the appearance
        popupWindow.getTitleLabel().setText("Wiki Farm");
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        // Initialize tabs if not already done
        if (tabs == null) {
            tabs = new Table();
        }
        tabs.clear();
        tabs.top().left();
        tabs.defaults().size(70, 70).padRight(4);

        // Create tab images with listeners
        Image[] tabsArray = {
            createTabImage(GameAssetManager.getInstance().INVENTORY_TAB),
            createTabImage(GameAssetManager.getInstance().SKILL_TAB),
            createTabImage(GameAssetManager.getInstance().SOCIAL_TAB),
            createTabImage(GameAssetManager.getInstance().MAP_TAB),
            createTabImage(GameAssetManager.getInstance().CRAFTING_TAB),
            createTabImage(GameAssetManager.getInstance().COOKING_TAB)
        };

        // Add all tabs to the table
        for (Image tab : tabsArray) {
            tabs.add(tab);
        }

        popupWindow.add(tabs).row();
        // Size and hide initially
        popupWindow.pack();
        popupWindow.setVisible(false);

        // Center the window
        updateWindowPosition();
        stage.addActor(popupWindow);
    }

    private Image createTabImage(Texture texture) {
        Image tab = new Image(texture);
        tab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Handle tab click here
                // You can add specific behavior for each tab
            }
        });
        return tab;
    }

    protected void updateWindowPosition() {
        if (stage != null && popupWindow != null) {
            popupWindow.setPosition(
                (stage.getWidth() - popupWindow.getWidth()) / 2,
                (stage.getHeight() - popupWindow.getHeight()) / 2 + 200
            );
        }
    }

    public void show() {
        if (popupWindow != null) {
            updateWindowPosition();
            popupWindow.setVisible(true);
        }
    }

    public void hide() {
        if (popupWindow != null) {
            popupWindow.setVisible(false);
        }
    }

    public boolean isVisible() {
        return popupWindow != null && popupWindow.isVisible();
    }

    // Cleanup resources when no longer needed
    public void dispose() {
        if (popupWindow != null) {
            popupWindow.remove();
            popupWindow = null;
        }
        skin = null;
        stage = null;
        slot = null;
        if (tabs != null) {
            tabs.clear();
            tabs = null;
        }
    }
}
