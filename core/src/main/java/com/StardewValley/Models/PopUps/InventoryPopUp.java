package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Crafting.CookingRecipe;
import com.StardewValley.Models.Crafting.Food;
import com.StardewValley.Models.Farming.Seed;
import com.StardewValley.Models.Farming.SeedType;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Tools.BackPack;
import com.StardewValley.Models.Tools.Tool;
import com.StardewValley.Models.Tools.ToolType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.Map;

public class InventoryPopUp extends PopUpMenu {
    private Table blocksTable;
    private Image background;
    private DragAndDrop dragAndDrop;
    private BackPack backPack;

    public InventoryPopUp(Stage stage) {
        super(stage);
        createInventoryContent();
    }

    private void createInventoryContent() {
        blocksTable = new Table();
        blocksTable.top().left();
        blocksTable.defaults().size(80, 80).pad(2);

        dragAndDrop = new DragAndDrop();

        // Populate items
        populateBackpackItems();

        // Scroll pane setup
        ScrollPane scrollPane = new ScrollPane(blocksTable);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setFadeScrollBars(false);

        // Trash can setup
        Texture trashTexture = GameAssetManager.getInstance().TRASH_CAN_COPPER;
        Image trashCanImage = new Image(trashTexture);
        trashCanImage.setSize(80, 100);
        Table trashTable = new Table();
        trashTable.add(trashCanImage).size(80, 100).padLeft(10).top();

        dragAndDrop.addTarget(new DragAndDrop.Target(trashCanImage) {
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Map.Entry<Item, Integer> itemEntry = (Map.Entry<Item, Integer>) payload.getObject();
                backPack.removeItem(itemEntry.getKey(), 1);
                populateBackpackItems(); // Refresh UI
            }
        });

        // Layout
        Table contentAndTrash = new Table();
        contentAndTrash.add(scrollPane).width(420).height(500).fill();
        contentAndTrash.add(trashTable).top().padLeft(10);

        popupWindow.add(contentAndTrash).expand().fill();
        popupWindow.pack();
        updateWindowPosition();
    }

    private void populateBackpackItems() {
        blocksTable.clear();

        int cols = 5;
        int itemCount = 0;

        backPack = App.getInstance().getCurrentGame().getCurrentPlayer().getBackPack();
        for (Map.Entry<Item, Integer> entry : backPack.getItems().entrySet()) {
            Stack stack = new Stack();
            Image slotBg = new Image(GameAssetManager.getInstance().BLOCK);
            Image itemImg = new Image(entry.getKey().getTexture());
            itemImg.setSize(60, 60);

            // Quantity label
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = GameAssetManager.getInstance().MAIN_FONT;
            labelStyle.fontColor = com.badlogic.gdx.graphics.Color.WHITE;
            Label quantityLabel = new Label(String.valueOf(entry.getValue()), labelStyle);
            quantityLabel.setFontScale(2f); // Adjust for visibility

            Table centeredLabel = new Table();
            centeredLabel.add(quantityLabel).center();

            stack.add(slotBg);
            stack.add(itemImg);
            stack.add(centeredLabel); // ⬅️ overlay count

            blocksTable.add(stack).size(80, 80);

            dragAndDrop.addSource(new DragAndDrop.Source(itemImg) {
                public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    Image dragImage = new Image(entry.getKey().getTexture());
                    dragImage.setSize(60, 60);
                    payload.setDragActor(dragImage);
                    payload.setObject(entry);
                    return payload;
                }
            });

            if (++itemCount % cols == 0) blocksTable.row();
        }

        blocksTable.invalidateHierarchy();
    }

    @Override
    public void dispose() {
        if (blocksTable != null) {
            blocksTable.clear();
            blocksTable = null;
        }
        if (background != null) {
            background.remove();
            background = null;
        }
        super.dispose();
    }
    public void refresh() {
        populateBackpackItems();
    }
}
