package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PopUpMenu {
    protected Stage stage;
    protected Window popupWindow;
    protected Skin skin;
    protected ImageButton trashCan;
    protected Texture slot;
    protected Table tabs;
    protected ImageButton closeButton;  // Close button

    public enum TabType {
        INVENTORY, SKILL, SOCIAL, MAP, CRAFTING, COOKING
    }

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

        popupWindow.getTitleLabel().setText("Wiki Farm");
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        // Title bar close button
        // Tab row
        tabs.clear();
        tabs.top().left();
        tabs.defaults().size(70, 70).padRight(4);

        tabs.add(createTabImage(GameAssetManager.getInstance().INVENTORY_TAB, TabType.INVENTORY));
        tabs.add(createTabImage(GameAssetManager.getInstance().SKILL_TAB, TabType.SKILL));
        tabs.add(createTabImage(GameAssetManager.getInstance().SOCIAL_TAB, TabType.SOCIAL));
        tabs.add(createTabImage(GameAssetManager.getInstance().MAP_TAB, TabType.MAP));
        tabs.add(createTabImage(GameAssetManager.getInstance().CRAFTING_TAB, TabType.CRAFTING));
        tabs.add(createTabImage(GameAssetManager.getInstance().COOKING_TAB, TabType.COOKING));

        // Add Close icon to the end of tabs
        tabs.add(createCloseTabImage(GameAssetManager.getInstance().EXIT_BUTTON));

        popupWindow.add(tabs).row();
        popupWindow.pack();
        popupWindow.setVisible(false);

        updateWindowPosition();
        stage.addActor(popupWindow);
    }

    private Image createTabImage(Texture texture, TabType type) {
        Image tab = new Image(texture);
        tab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onTabClicked(type);
            }
        });
        return tab;
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

    protected void onTabClicked(TabType type) {
        switch (type) {
            case INVENTORY:
                PopUpManager.getInstance(stage).showInventoryTab();
                break;
            case SKILL:
                PopUpManager.getInstance(stage).showSkillTab();
                break;
            case SOCIAL:
                PopUpManager.getInstance(stage).showSocialTab();
                break;
            default:
                System.out.println("Tab clicked: " + type);
                break;
        }
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
