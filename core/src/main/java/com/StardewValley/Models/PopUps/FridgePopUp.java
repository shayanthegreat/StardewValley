package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Crafting.Refrigerator;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Tools.BackPack;
import com.StardewValley.Models.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import java.util.HashMap;
import java.util.Map;

public class FridgePopUp {
    private Refrigerator refrigerator;
    private Stage stage;
    private Window window;
    private Table fridgeTable;
    private Item selectedItem;
    private DragAndDrop dragAndDrop;
    private BackPack backPack;

    public FridgePopUp(Stage stage) {
        this.stage = stage;
        this.refrigerator = App.getInstance().getCurrentGame().getCurrentPlayer().getFarm().getHouse().getRefrigerator();
        this.backPack = App.getInstance().getCurrentGame().getCurrentPlayer().getBackPack();

        window = new Window("Fridge", GameAssetManager.getInstance().getSkin());
        window.setModal(true);
        window.setMovable(false);
        window.setResizable(false);
        window.align(Align.top);
        window.pad(10);

        // Close button
        TextButton closeBtn = new TextButton("X", GameAssetManager.getInstance().getSkin());
        closeBtn.getLabel().setFontScale(0.8f);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        Table header = new Table();
        header.add(new Label("Fridge", GameAssetManager.getInstance().getSkin())).expandX().left();
        header.add(closeBtn).right();
        window.add(header).expandX().fillX().row();

        fridgeTable = new Table();
        fridgeTable.top().left();
        fridgeTable.defaults().size(100, 100).pad(4);

        ScrollPane scrollPane = new ScrollPane(fridgeTable);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setFadeScrollBars(false);

        window.add(scrollPane).width(600).height(500).padBottom(10);
        window.row();

        // Drag and Drop Setup
        dragAndDrop = new DragAndDrop();

        // Backpack image (drop target)
        Image backpackImage = new Image(GameAssetManager.getInstance().BACKPACK);
        backpackImage.setSize(500, 500);
        window.add(backpackImage).pad(5).center();
        window.row();

        dragAndDrop.addTarget(new DragAndDrop.Target(backpackImage) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Map.Entry<Item, Integer> entry = (Map.Entry<Item, Integer>) payload.getObject();
                Item item = entry.getKey();
                if (refrigerator.removeItem(item, 1)) {
                    populate();
                    UIUtils.showTopMessage(stage, GameAssetManager.getInstance().SKIN, "Send to Backpack");
                }
                else{
                    UIUtils.showTopMessage(stage, GameAssetManager.getInstance().SKIN, "Failed to remove item");
                }
            }
        });

        window.pack();
        centerWindow();
        populate();
    }

    private void centerWindow() {
        window.setPosition(
            (stage.getWidth() - window.getWidth()) / 2f,
            (stage.getHeight() - window.getHeight()) / 2f
        );
    }

    private void populate() {
        fridgeTable.clear();
        this.refrigerator = App.getInstance().getCurrentGame().getCurrentPlayer().getFarm().getHouse().getRefrigerator();
        HashMap<Item, Integer> fridgeItems = refrigerator.getItems();
        int cols = 5;
        int index = 0;

        for (Map.Entry<Item, Integer> entry : fridgeItems.entrySet()) {
            Stack stack = new Stack();
            Item item = entry.getKey();

            Image bg = new Image(GameAssetManager.getInstance().BLOCK);
            Image itemImage = new Image(item.getTexture());
            itemImage.setSize(64, 64);

            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = GameAssetManager.getInstance().MAIN_FONT;
            labelStyle.fontColor = Color.WHITE;
            Label quantityLabel = new Label(String.valueOf(entry.getValue()), labelStyle);
            quantityLabel.setFontScale(1.2f);

            Table labelTable = new Table();
            labelTable.bottom();
            labelTable.add(quantityLabel).pad(2);

            stack.add(bg);
            stack.add(itemImage);
            stack.add(labelTable);

            fridgeTable.add(stack);
            if (++index % cols == 0) fridgeTable.row();

            // Drag source for each fridge item
            dragAndDrop.addSource(new DragAndDrop.Source(itemImage) {
                @Override
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    Image dragImg = new Image(item.getTexture());
                    dragImg.setSize(64, 64);
                    payload.setDragActor(dragImg);
                    payload.setObject(entry);
                    return payload;
                }
            });
        }

        fridgeTable.invalidateHierarchy();
    }

    public void refresh() {
        populate();
    }

    public void show() {
        refresh();
        if (!window.hasParent()) {
            centerWindow();
            stage.addActor(window);
        }
    }

    public void hide() {
        window.remove();
    }

    public Window getWindow() {
        return window;
    }

    public boolean isClicked(int x, int y) {
        Position position = App.getInstance().getCurrentGame().getCurrentPlayer().getFarm().getHouse().getOrigin();
        if(x == position.x && y == position.y) {
            return true;
        }
        return false;
    }

}
