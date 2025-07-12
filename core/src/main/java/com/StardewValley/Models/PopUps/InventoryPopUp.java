package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Tools.BackPack;
import com.StardewValley.Models.Tools.Tool;
import com.StardewValley.Models.Tools.ToolType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class InventoryPopUp extends PopUpMenu {
    private Table blocksTable;
    private Image background;
    public InventoryPopUp(Stage stage) {
        super(stage);
        createInventoryContent();
    }

    private void createInventoryContent() {
        //BackPack backPack = App.getInstance().getCurrentGame().getCurrentPlayer().getBackPack();
        BackPack backPack = new BackPack();
        backPack.addItem(new Tool(ToolType.scythe), 1);
        blocksTable = new Table();
        blocksTable.top().left();
        blocksTable.defaults().size(80, 80).pad(2); // Adjust size and padding as needed

        int rows = 6;
        int cols = 5;
        int totalBlocks = Math.min(rows * cols, 30); // Limit to 30 blocks (5x6)

        for (int i = 0; i < totalBlocks; i++) {
            Image block = new Image(GameAssetManager.getInstance().BLOCK);

            blocksTable.add(block);

            // Move to next row after each 5 blocks
            if ((i + 1) % cols == 0) {
                blocksTable.row();
            }
        }

        // Add blocks table below the tabs
        popupWindow.add(blocksTable).padTop(20).row();

        // Repack the window to account for new content
        popupWindow.pack();
        updateWindowPosition();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
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
}
