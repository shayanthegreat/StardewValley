package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Farming.Seed;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Player;
import com.StardewValley.Models.Tools.BackPack;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Map;
import java.util.function.BiConsumer;

public class SeedPopUp {

    private final Window window;
    private final Stage stage;
    private Position targetPosition;
    private BiConsumer<Seed, Position> onSeedSelected;

    public SeedPopUp(Stage stage) {
        this.stage = stage;

        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = GameAssetManager.getInstance().MAIN_FONT;

        this.window = new Window("Seeds", windowStyle);
        window.setMovable(false);
        window.setResizable(false);
        window.pad(10);
        window.setVisible(false);
        stage.addActor(window);
    }

    private void createContent() {
        Table seedTable = new Table();
        seedTable.defaults().pad(5).size(80);
        seedTable.align(Align.left);

        // Background for the seedTable
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.5f);
        pixmap.fill();
        Texture bgTexture = new Texture(pixmap);
        pixmap.dispose();
        seedTable.setBackground(new Image(bgTexture).getDrawable());

        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        BackPack backPack = player.getBackPack();

        for (Map.Entry<com.StardewValley.Models.Item, Integer> entry : backPack.getItems().entrySet()) {
            if (entry.getKey() instanceof Seed seed) {
                Image seedImage = new Image(seed.getTexture());
                seedImage.setSize(64, 64);

                seedImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (onSeedSelected != null && targetPosition != null) {
                            onSeedSelected.accept(seed, targetPosition);
                            hide();
                        }
                    }
                });

                seedTable.add(seedImage);
            }
        }

        ScrollPane scrollPane = new ScrollPane(seedTable);
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(true, false);

        window.clear(); // clear previous content
        window.add(scrollPane).width(500).height(100).pad(20).top().left();
        window.row();
        window.pack();
    }

    private void positionWindow() {
        float stageWidth = stage.getViewport().getWorldWidth();
        float stageHeight = stage.getViewport().getWorldHeight();
        window.pack();
        float x = (stageWidth - window.getWidth()) / 2f;
        float y = 10f;
        window.setPosition(x, y);
    }

    public void show() {
        createContent();      // dynamically regenerate
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

    public void toggle() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    public void setTargetPosition(Position position) {
        this.targetPosition = position;
    }

    public void setOnSeedSelected(BiConsumer<Seed, Position> onSeedSelected) {
        this.onSeedSelected = onSeedSelected;
    }
}
