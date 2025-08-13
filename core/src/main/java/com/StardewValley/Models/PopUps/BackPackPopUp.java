package com.StardewValley.Models.PopUps;

import com.StardewValley.Controllers.FriendShipController;
import com.StardewValley.Controllers.HouseController;
import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;

public class BackPackPopUp extends PopUpMenu {

    private Table itemsTable;
    private String receiver;

    public BackPackPopUp(Stage stage, String receiver) {
        super(stage);
        createPopupMenu();
        this.receiver = receiver;
    }

    protected void createPopupMenu() {
        popupWindow = new Window("", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);

        popupWindow.getTitleLabel().setText("BackPack");
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        tabs.clear();
        tabs.top().left();
        tabs.defaults().size(70, 70).padRight(4);
        tabs.add(createCloseTabImage(GameAssetManager.getInstance().EXIT_BUTTON));
        popupWindow.add(tabs).row();

        itemsTable = new Table();
        itemsTable.top().left();
        itemsTable.defaults().pad(10);

        ScrollPane scrollPane = new ScrollPane(itemsTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        popupWindow.add(scrollPane).width(500).height(300).row();
        popupWindow.pack();
        popupWindow.setVisible(false);

        updateWindowPosition();
        stage.addActor(popupWindow);
    }

    private void refreshBackPackList() {
        itemsTable.clear();
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        HashMap<Item, Integer> backpack = player.getBackPack().getItems();

        if (backpack.isEmpty()) {
            itemsTable.add(new Label("No Items Found", skin)).pad(10).row();
            return;
        }

        for (Item item : backpack.keySet()) {
            Texture texture = item.getTexture();
            Drawable drawable = new TextureRegionDrawable(texture);
            ImageButton button = new ImageButton(drawable);
            Label label = new Label(backpack.get(item).toString(), skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    FriendShipController.getInstance().gifting(item,receiver);
                }
            });

            Table row = new Table();
            row.add(button).size(64, 64).padRight(10);
            row.add(label).left();
            itemsTable.add(row).pad(10).row();
        }
    }




    @Override
    public void show() {
        refreshBackPackList();
        super.show();
    }

    public void refresh() {
        refreshBackPackList();
    }
}

