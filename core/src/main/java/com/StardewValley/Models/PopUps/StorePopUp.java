package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Crafting.CookingRecipe;
import com.StardewValley.Models.Crafting.CraftingRecipe;
import com.StardewValley.Models.Crafting.Recipe;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Store.Store;
import com.StardewValley.Models.Store.StoreItem;
import com.StardewValley.Models.Store.StoreRecipes;
import com.StardewValley.Models.UIUtils;
import com.StardewValley.Networking.Client.ClientController;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;

public class StorePopUp {
    private final Stage stage;
    private Window window;
    private Table itemTable;
    private ScrollPane scrollPane;

    private final HashMap<StoreItem, Integer> itemQuantities = new HashMap<>();
    private final HashMap<StoreRecipes, Integer> recipeQuantities = new HashMap<>();

    private Label totalPriceLabel;
    private int totalPrice = 0;
    private boolean showOnlyAvailable = false;

    public StorePopUp(Stage stage) {
        this.stage = stage;
        createUI(App.getInstance().getCurrentGame().getMap().getNpcVillage().getStores().get(2));
    }

    public void createUI(Store store) {
        Skin skin = GameAssetManager.getInstance().SKIN;

        window = new Window(store.getName(), skin);
        window.setColor(new Color(1, 1, 1, 0.95f));
        window.setMovable(true);
        window.setModal(true);

        itemTable = new Table();
        itemTable.top().left().defaults().pad(4);

        scrollPane = new ScrollPane(itemTable, skin);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(false, true);

        TextButton refreshButton = new TextButton("Refresh", skin);
        TextButton toggleFilterButton = new TextButton("Show All", skin);
        TextButton closeButton = new TextButton("X", skin);

        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                refresh(store);
            }
        });

        toggleFilterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showOnlyAvailable = !showOnlyAvailable;
                toggleFilterButton.setText(showOnlyAvailable ? "Available Only" : "Show All");
                refresh(store);
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });

        // Trash Button
        ImageButton trashButton = new ImageButton(new Image(GameAssetManager.getInstance().TRASH_CAN_COPPER).getDrawable());
        trashButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                PopUpManager.getInstance(stage).showInventoryTab();
            }
        });

        Table topBar = new Table();
        topBar.add(refreshButton).left().pad(5);
        topBar.add(toggleFilterButton).pad(5);
        topBar.add().expandX();
        topBar.add(trashButton).size(30, 30).right().pad(5);
        topBar.add(closeButton).right().pad(5);

        totalPriceLabel = new Label("Total: $0", skin);
        TextButton purchaseButton = new TextButton("Purchase", skin);

        purchaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.getInstance().getCurrentGame().getCurrentPlayer().getMoney() >= totalPrice) {
                    for (StoreItem storeItem : itemQuantities.keySet()) {
                        int quantity = itemQuantities.get(storeItem);
                        if (quantity > 0) {
                            App.getInstance().getCurrentGame().getCurrentPlayer().getBackPack().addItem(storeItem.getItem(), quantity);
                            storeItem.removeDailyLimit(quantity);
                            ClientController.getInstance().storeItemBought(store.getName(), storeItem.getItem().toString(), quantity);
                        }
                    }
                    for (StoreRecipes storeRecipes : recipeQuantities.keySet()) {
                        int quantity = recipeQuantities.get(storeRecipes);
                        if (quantity > 0) {
                            Recipe recipe = storeRecipes.getRecipe();
                            if (recipe instanceof CookingRecipe) {
                                App.getInstance().getCurrentGame().getCurrentPlayer().getKnownCookingRecipes().add((CookingRecipe) recipe);
                            } else {
                                App.getInstance().getCurrentGame().getCurrentPlayer().getKnownCraftingRecipes().add((CraftingRecipe) recipe);
                            }
                            storeRecipes.removeDailyLimit(quantity);
                            ClientController.getInstance().storeItemBought(store.getName(), storeRecipes.getRecipe().getProductName(), quantity);
                        }
                    }
                    App.getInstance().getCurrentGame().getCurrentPlayer().decreaseMoney(totalPrice);
                    UIUtils.showTopMessage(stage, GameAssetManager.getInstance().SKIN, "Purchase successful!");
                    close();
                } else {
                    UIUtils.showTopMessage(stage, GameAssetManager.getInstance().SKIN, "Not enough money!");
                }
            }
        });

        Table bottomBar = new Table();
        bottomBar.add(totalPriceLabel).left().pad(5).expandX();
        bottomBar.add(purchaseButton).right().pad(5);

        window.clear();
        window.add(topBar).expandX().fillX().row();
        window.add(scrollPane).width(550).height(500).fill().pad(10).row();
        window.add(bottomBar).expandX().fillX().pad(5).row();
        window.pack();

        window.setPosition(
            (stage.getWidth() - window.getWidth()) / 2f,
            (stage.getHeight() - window.getHeight()) / 2f
        );

        stage.addActor(window);
        refresh(store);
    }

    public void refresh(Store store) {
        itemTable.clear();
        itemQuantities.clear();
        recipeQuantities.clear();
        totalPriceLabel.setText("Total: $0");

        Skin skin = GameAssetManager.getInstance().SKIN;

        for (StoreItem storeItem : store.getItems()) {
            int dailyLimit = storeItem.getDailyLimit();
            if (showOnlyAvailable && dailyLimit <= 0) continue;

            Item item = storeItem.getItem();
            int price = storeItem.getPrice();
            itemQuantities.put(storeItem, 0);
            Table itemRow = new Table().defaults().pad(4).center().getTable();

            Image itemImage = new Image(item.getTexture());
            itemImage.setSize(60, 60);
            if(dailyLimit == 0){
                itemImage.setColor(0.3f, 0.3f, 0.3f, 1);
            }
            itemRow.add(itemImage).size(70);

            Label nameLabel = new Label(item.getName() + " ($" + price + ")", skin);
            itemRow.add(nameLabel).width(140).left();

            TextButton minusBtn = new TextButton("-", skin);
            Label quantityLabel = new Label("0", skin);
            TextButton plusBtn = new TextButton("+", skin);

            minusBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int current = itemQuantities.get(storeItem);
                    if (current > 0) {
                        itemQuantities.put(storeItem, current - 1);
                        quantityLabel.setText(String.valueOf(current - 1));
                        updateTotalPrice();
                    }
                }
            });

            plusBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int current = itemQuantities.get(storeItem);
                    if (current < storeItem.getDailyLimit()) {
                        itemQuantities.put(storeItem, current + 1);
                        quantityLabel.setText(String.valueOf(current + 1));
                        updateTotalPrice();
                    }
                }
            });

            itemRow.add(minusBtn).size(30, 30);
            itemRow.add(quantityLabel).width(30);
            itemRow.add(plusBtn).size(30, 30);

            if (dailyLimit <= 0) {
                itemRow.setColor(Color.GRAY);
            }

            itemTable.add(itemRow).expandX().fillX().row();
        }

        for (StoreRecipes recipeEntry : store.getRecipes()) {
            int dailyLimit = recipeEntry.getDailyLimit();
            if (showOnlyAvailable && dailyLimit <= 0) continue;

            int price = recipeEntry.getPrice();
            String recipeName = recipeEntry.getRecipe().getProductName();
            recipeQuantities.put(recipeEntry, 0);

            Table recipeRow = new Table().defaults().pad(4).center().getTable();

            Image recipeImage = new Image(recipeEntry.getRecipe().getTexture());
            if(dailyLimit == 0){
                recipeImage.setColor(0.3f, 0.3f, 0.3f, 1);
            }
            recipeImage.setSize(60, 60);
            recipeRow.add(recipeImage).size(70);

            Label nameLabel = new Label("Recipe: " + recipeName + " ($" + price + ")", skin);
            recipeRow.add(nameLabel).width(140).left();

            TextButton minusBtn = new TextButton("-", skin);
            Label quantityLabel = new Label("0", skin);
            TextButton plusBtn = new TextButton("+", skin);

            minusBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int current = recipeQuantities.get(recipeEntry);
                    if (current > 0) {
                        recipeQuantities.put(recipeEntry, current - 1);
                        quantityLabel.setText(String.valueOf(current - 1));
                        updateTotalPrice();
                    }
                }
            });

            plusBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    int current = recipeQuantities.get(recipeEntry);
                    if (current < recipeEntry.getDailyLimit()) {
                        recipeQuantities.put(recipeEntry, current + 1);
                        quantityLabel.setText(String.valueOf(current + 1));
                        updateTotalPrice();
                    }
                }
            });

            recipeRow.add(minusBtn).size(30, 30);
            recipeRow.add(quantityLabel).width(30);
            recipeRow.add(plusBtn).size(30, 30);

            if (dailyLimit <= 0) {
                recipeRow.setColor(Color.GRAY);
            }

            itemTable.add(recipeRow).expandX().fillX().row();
        }

        itemTable.invalidateHierarchy();
    }

    private void updateTotalPrice() {
        int total = 0;
        for (StoreItem storeItem : itemQuantities.keySet()) {
            int quantity = itemQuantities.get(storeItem);
            total += storeItem.getPrice() * quantity;
        }
        for (StoreRecipes recipe : recipeQuantities.keySet()) {
            int quantity = recipeQuantities.get(recipe);
            total += recipe.getPrice() * quantity;
        }
        totalPriceLabel.setText("Total: $" + total);
        totalPrice = total;
    }

    public void close() {
        window.remove();
    }

    public void dispose() {
        window.remove();
        itemTable.clear();
    }

    public void show() {
        if (window != null && !window.hasParent()) {
            stage.addActor(window);
        }
        window.setVisible(true);
    }

    public void hide() {
        if (window != null) {
            window.setVisible(false);
        }
    }

    public HashMap<StoreItem, Integer> getItemQuantities() {
        return itemQuantities;
    }

    public HashMap<StoreRecipes, Integer> getRecipeQuantities() {
        return recipeQuantities;
    }

    public boolean isVisible() {
        return window.isVisible();
    }
}
