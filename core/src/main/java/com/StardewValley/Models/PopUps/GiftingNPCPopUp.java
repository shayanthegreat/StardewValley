package com.StardewValley.Models.PopUps;

import com.StardewValley.Controllers.HouseController;
import com.StardewValley.Controllers.NPCController;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Communication.NPC;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.Tools.Tool;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;

public class GiftingNPCPopUp extends PopUpMenu {

    private Table itemsTable;
    private NPC npc;

    public GiftingNPCPopUp(Stage stage) {
        super(stage);
        createPopupMenu();
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
        HashMap<Item, Integer> backpack = getItems();
        Table informationTable = new Table();
        Texture NPCTexture = npc.getTexture();
        Drawable drawable1 = new TextureRegionDrawable(NPCTexture);
        Image image = new Image(drawable1);
        informationTable.add(image);
        Label backpackLabel = new Label("Level: "+player.getNpcFriendshipByName(npc.getName()).getLevel()+"\n" + "XP: "+player.getNpcFriendshipByName(npc.getName()).getXp(), skin);
        informationTable.add(backpackLabel);
        itemsTable.add(informationTable);

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
                    NPCController.getInstance().giftNPC(npc,item);
                    hide();
                    PopUpManager.getInstance(stage).hide();
                }
            });

            Table row = new Table();
            row.add(button).size(64, 64).padRight(10);
            row.add(label).left();
            itemsTable.add(row).pad(10).row();
        }
    }

    private HashMap<Item, Integer> getItems() {
        HashMap<Item, Integer> items = new HashMap<>();
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        for (Item item : player.getBackPack().getItems().keySet()) {
            if(!(item instanceof Tool)){
                items.put(item, player.getBackPack().getItems().get(item));
            }
        }
        return items;
    }




    @Override
    public void show() {
        refreshBackPackList();
        super.show();
    }

    public void refresh() {
        refreshBackPackList();
    }

    public boolean isClicked(Position position) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        for (NPC playerNpc : player.getNpcs()) {
            if(position.equals(playerNpc.getPosition())) {
                this.npc = playerNpc;
                return true;
            }
        }
        return false;
    }
}

