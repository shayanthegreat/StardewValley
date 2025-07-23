package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.Tools.BackPack;
import com.StardewValley.Models.Tools.Tool;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Map;

public class ToolPopUp {

    private final Window window;
    private final Stage stage;

    public ToolPopUp(Stage stage) {
        this.stage = stage;

        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = GameAssetManager.getInstance().MAIN_FONT;

        this.window = new Window("Tools", windowStyle);
        window.setMovable(false);
        window.setResizable(false);
        window.pad(10);
        window.setVisible(false); // initially hidden

        createContent();
        stage.addActor(window);
        positionWindow();
    }

    private void createContent() {
        window.clear(); // ✅ Clear previous content before re-adding

        Table toolTable = new Table();
        toolTable.defaults().pad(5).size(80);
        toolTable.align(Align.left);

        // Set background color: semi-transparent black
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.5f);
        pixmap.fill();
        Texture bgTexture = new Texture(pixmap);
        pixmap.dispose(); // cleanup
        toolTable.setBackground(new Image(bgTexture).getDrawable());

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        BackPack backPack = player.getBackPack();

        for (Map.Entry<Item, Integer> entry : backPack.getItems().entrySet()) {
            if (entry.getKey() instanceof Tool tool) {
                Image toolImage = new Image(tool.getTexture());
                toolImage.setSize(64, 64);

                toolImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        player.setCurrentTool(tool);
                    }
                });

                toolTable.add(toolImage);
            }
        }

        ScrollPane scrollPane = new ScrollPane(toolTable);
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(true, false);

        window.add(scrollPane).width(500).height(100);
        window.pack();
    }

    private void positionWindow() {
        float stageWidth = stage.getViewport().getWorldWidth();
        float stageHeight = stage.getViewport().getWorldHeight();
        window.pack(); // Ensure correct dimensions
        float x = (stageWidth - window.getWidth()) / 2f;
        float y = 10f; // distance from bottom

        window.setPosition(x, y);
    }

    public void show() {
        positionWindow();
        window.setVisible(true);
        if (!stage.getActors().contains(window, true)) {
            stage.addActor(window);
        }
    }

    public void hide() {
        window.setVisible(false);
    }

    public void remove() {
        window.remove();
    }

    public boolean isVisible() {
        return window.isVisible();
    }

    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }

    /** Toggle visibility: if visible, hide; if hidden, show. */
    public void toggle() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    /** Rebuilds the tool popup with latest data */
    public void refresh() {
        createContent(); // Safely refresh content
    }
}
