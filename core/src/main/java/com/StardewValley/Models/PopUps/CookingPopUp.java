package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Crafting.CookingRecipe;
import com.StardewValley.Models.Crafting.Food;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Tools.BackPack;
import com.StardewValley.Models.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Map;

public class CookingPopUp {
    private final Stage stage;
    private final Window window;
    private final Table recipesTable;
    private final Label energyLabel;
    private CookingRecipe selectedRecipe;
    private Window tooltipWindow;

    public CookingPopUp(Stage stage) {
        this.stage = stage;

        window = new Window("Cooking", GameAssetManager.getInstance().getSkin());
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
        header.add(new Label("Cooking", GameAssetManager.getInstance().getSkin())).expandX().left();
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

        energyLabel = new Label("Energy: -", new Label.LabelStyle(GameAssetManager.getInstance().MAIN_FONT, Color.WHITE));
        window.add(energyLabel).pad(5);
        window.row();

        TextButton cookButton = new TextButton("Cook", GameAssetManager.getInstance().getSkin());
        cookButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedRecipe != null) {
                    BackPack backPack = App.getInstance().getCurrentGame().getCurrentPlayer().getBackPack();
                    if (selectedRecipe.canCook(backPack)) {
                        selectedRecipe.consume(backPack);
                        backPack.addItem(new Food(selectedRecipe), 1);
                        refresh();
                        UIUtils.showTopMessage(stage, GameAssetManager.getInstance().SKIN, "you cooked some " + selectedRecipe.getProductName());
                    }
                    else{
                        UIUtils.showTopMessage(stage, GameAssetManager.getInstance().SKIN, "you don't have enough ingredients");
                    }
                }
            }
        });

        window.add(cookButton).pad(5);
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

        ArrayList<CookingRecipe> knownRecipes = App.getInstance()
            .getCurrentGame()
            .getCurrentPlayer()
            .getKnownCookingRecipes();

        int cols = 5;
        int index = 0;

        for (CookingRecipe recipe : CookingRecipe.values()) {
            Stack stack = new Stack();

            Image bg = new Image(GameAssetManager.getInstance().BLOCK);
            Texture texture = recipe.getTexture();
            Image recipeImage = new Image(texture);
            recipeImage.setSize(64, 64);

            boolean isKnown = knownRecipes.contains(recipe);
            if (!isKnown) {
                recipeImage.setColor(0.3f, 0.3f, 0.3f, 1);
            }

            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = GameAssetManager.getInstance().MAIN_FONT;
            labelStyle.fontColor = Color.WHITE;
            Label nameLabel = new Label(recipe.getProductName(), labelStyle);
            nameLabel.setFontScale(0.9f);

            Table labelTable = new Table();
            labelTable.bottom();
            labelTable.add(nameLabel).pad(2);

            stack.add(bg);
            stack.add(recipeImage);
            stack.add(labelTable);

            // Always add listeners regardless of isKnown
            stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (isKnown) {  // Only allow selecting known recipes
                        selectedRecipe = recipe;
                        energyLabel.setText("Energy: " + recipe.getEnergy());
                    }
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    showIngredientsTooltip(recipe, stack);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    hideIngredientsTooltip();
                }
            });

            recipesTable.add(stack);
            if (++index % cols == 0) recipesTable.row();
        }

        recipesTable.invalidateHierarchy();
    }

    private void showIngredientsTooltip(CookingRecipe recipe, Actor target) {
        if (tooltipWindow != null) tooltipWindow.remove();

        tooltipWindow = new Window("Ingredients", GameAssetManager.getInstance().getSkin());
        tooltipWindow.setMovable(false);

        for (Map.Entry<Item, Integer> entry : recipe.getIngredients().entrySet()) {
            Item item = entry.getKey();
            Texture texture = item.getTexture();
            Image image = new Image(texture);
            Label amountLabel = new Label("x" + entry.getValue(), GameAssetManager.getInstance().getSkin());

            HorizontalGroup group = new HorizontalGroup();
            group.space(5);
            group.addActor(image);
            group.addActor(amountLabel);
            tooltipWindow.add(group).row();
        }

        tooltipWindow.pack();
        stage.addActor(tooltipWindow);

        // Position tooltip over the hovered recipe
        Vector2 stageCoords = target.localToStageCoordinates(new Vector2(0, 0));
        float tooltipX = stageCoords.x + (target.getWidth() - tooltipWindow.getWidth()) / 2f;
        float tooltipY = stageCoords.y + target.getHeight() - tooltipWindow.getHeight() - 5; // Slightly above

        // Keep tooltip inside stage bounds horizontally
        tooltipX = Math.max(0, Math.min(tooltipX, stage.getWidth() - tooltipWindow.getWidth()));
        // And vertically inside stage bounds
        tooltipY = Math.max(0, Math.min(tooltipY, stage.getHeight() - tooltipWindow.getHeight()));

        tooltipWindow.setPosition(tooltipX, tooltipY);
    }


    private void hideIngredientsTooltip() {
        if (tooltipWindow != null) {
            tooltipWindow.remove();
            tooltipWindow = null;
        }
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
        if (tooltipWindow != null) {
            tooltipWindow.remove();
            tooltipWindow = null;
        }
    }

    public Window getWindow() {
        return window;
    }
}
