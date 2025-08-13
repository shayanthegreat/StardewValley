package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Artisan.ArtisanType;
import com.StardewValley.Models.Crafting.CookingRecipe;
import com.StardewValley.Models.Crafting.CraftingItem;
import com.StardewValley.Models.Crafting.CraftingRecipe;
import com.StardewValley.Models.Crafting.Food;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Tools.BackPack;
import com.StardewValley.Models.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Map;

public class ArtPopUp {
    private final Stage stage;
    private final Window window;
    private final Table recipesTable;

    public ArtPopUp(Stage stage) {
        this.stage = stage;

        window = new Window("Artisan", GameAssetManager.getInstance().getSkin());
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
        header.add(new Label("Artisan", GameAssetManager.getInstance().getSkin())).expandX().left();
        header.add(closeBtn).right();
        window.add(header).expandX().fillX().row();

        recipesTable = new Table();
        recipesTable.top().left();
        recipesTable.defaults().size(100, 100).pad(4);

        ScrollPane scrollPane = new ScrollPane(recipesTable);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(false, true);
        scrollPane.setFadeScrollBars(false);

        window.add(scrollPane).width(600).height(500).padBottom(10);
        window.row();
        window.pack();
        centerWindow();
        populateRecipes();
    }

    private void centerWindow() {
        window.setPosition(
            (stage.getWidth() - window.getWidth()) / 2f,
            (stage.getHeight() - window.getHeight()) / 2f
        );
    }

    private void populateRecipes() {
        recipesTable.clear();


        int cols = 5;
        int index = 0;

        for (ArtisanType recipe : ArtisanType.values()) {
            Stack stack = new Stack();

            Image bg = new Image(GameAssetManager.getInstance().BLOCK);
            Texture texture = recipe.getTexture();
            Image recipeImage = new Image(texture);
            recipeImage.setSize(64, 64);


            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = GameAssetManager.getInstance().MAIN_FONT;
            labelStyle.fontColor = Color.WHITE;
            Label nameLabel = new Label(recipe.getName(), labelStyle);
            nameLabel.setFontScale(0.9f);

            Table labelTable = new Table();
            labelTable.bottom();
            labelTable.add(nameLabel).pad(2);

            stack.add(bg);
            stack.add(recipeImage);
            stack.add(labelTable);

            recipesTable.add(stack);
            if (++index % cols == 0) recipesTable.row();
        }

        recipesTable.invalidateHierarchy();
    }


    public void refresh() {
        populateRecipes();
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
}
