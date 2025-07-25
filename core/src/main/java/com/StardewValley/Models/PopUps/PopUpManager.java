package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PopUpManager {
    public static PopUpManager instance;
    public static PopUpManager getInstance(Stage stage) {
        if(instance == null){
            instance = new PopUpManager(stage);
        }
        return instance;
    }


    private Stage stage;
    private Window popupWindow;
    private Skin skin;
    private Table tabs;
    private InventoryPopUp inventoryPopUp;
    private SkillPopUp skillPopUp;
    private SocialPopUp socialPopUp;
    public PopUpManager(Stage stage) {
        this.stage = stage;
        this.skin = GameAssetManager.getInstance().SKIN;

        createPopupMenu();

        // Create your sub popups with the same stage
        inventoryPopUp = new InventoryPopUp(stage);
        skillPopUp = new SkillPopUp(stage);
        socialPopUp = new SocialPopUp(stage);
        // Initially hide sub popups windows
//        inventoryPopUp.hide();
        skillPopUp.hide();
        socialPopUp.hide();
    }

    private void createPopupMenu() {
        popupWindow = new Window("Main Menu", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);
        popupWindow.pad(10);

        tabs = new Table();
        tabs.defaults().size(70, 70).padRight(4);

        Image inventoryTab = new Image(GameAssetManager.getInstance().INVENTORY_TAB);
        Image skillTab = new Image(GameAssetManager.getInstance().SKILL_TAB);
        //Image socialTab = new Image(GameAssetManager.getInstance().SOCIAL_TAB);
        inventoryTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showInventoryTab();
            }
        });

        skillTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showSkillTab();
            }
        });





        tabs.add(inventoryTab);
        tabs.add(skillTab);

        popupWindow.add(tabs).row();

        popupWindow.pack();
        popupWindow.setVisible(false);
        stage.addActor(popupWindow);

        updateWindowPosition();
    }

    private void updateWindowPosition() {
        popupWindow.setPosition(
            (stage.getWidth() - popupWindow.getWidth()) / 2,
            (stage.getHeight() - popupWindow.getHeight()) / 2 + 200
        );
    }

    public void show() {
        popupWindow.setVisible(true);
        showInventoryTab();
    }

    public void hide() {
        popupWindow.setVisible(false);
        inventoryPopUp.hide();
        skillPopUp.hide();
        socialPopUp.hide();
    }

    public void showInventoryTab() {
        skillPopUp.hide();
        inventoryPopUp.show();
        socialPopUp.hide();
        // Optionally, hide your main popupWindow content (or keep visible as tabs container)
        popupWindow.setVisible(true);
        inventoryPopUp.refresh();
    }

    public void showSkillTab() {
        skillPopUp.refresh();
        inventoryPopUp.hide();
        skillPopUp.show();
        socialPopUp.hide();
        popupWindow.setVisible(true);
        inventoryPopUp.refresh();
    }

    public void showSocialTab() {
        socialPopUp.refresh();
        socialPopUp.show();
        inventoryPopUp.hide();
        skillPopUp.hide();
        popupWindow.setVisible(true);
        inventoryPopUp.refresh();

    }

    public boolean isVisible() {
        return popupWindow.isVisible();
    }

    public InventoryPopUp getInventoryPopUp() {
        return inventoryPopUp;
    }
}

