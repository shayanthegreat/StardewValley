package com.StardewValley.Models.PopUps;

import com.StardewValley.Controllers.AnimalController;
import com.StardewValley.Controllers.GameController;
import com.StardewValley.Controllers.PlayerController;
import com.StardewValley.Models.Animal.Animal;
import com.StardewValley.Models.Animal.Barn;
import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Map.Position;
import com.StardewValley.Models.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class AnimalPopUp extends PopUpMenu {

    private Table animalTable;

    public AnimalPopUp(Stage stage) {
        super(stage);
        createPopupMenu();
    }

    protected void createPopupMenu() {
        popupWindow = new Window("", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);

        popupWindow.getTitleLabel().setText("Animal Info");
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        tabs.clear();
        tabs.top().left();
        tabs.defaults().size(70, 70).padRight(4);
        tabs.add(createCloseTabImage(GameAssetManager.getInstance().EXIT_BUTTON));
        popupWindow.add(tabs).row();

        animalTable = new Table();
        animalTable.top().left();
        animalTable.defaults().pad(10);

        ScrollPane scrollPane = new ScrollPane(animalTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        popupWindow.add(scrollPane).width(500).height(300).row();
        popupWindow.pack();
        popupWindow.setVisible(false);

        updateWindowPosition();
        stage.addActor(popupWindow);
    }

    private void refreshAnimalList() {
        animalTable.clear();
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        ArrayList<Animal> animals = showAnimals();

        Table building = new Table();
        if (player.getFarm().getBarn() == null) {
            TextButton buildBarn = new TextButton("Build Barn", skin);
            buildBarn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.setChoosingBarn(true);
                    hide();
                    PopUpManager.getInstance(stage).hide();
                }
            });
            building.add(buildBarn).size(64, 64).padRight(30);
        }

        if(!player.getFarm().getGreenHouse().isBuilt()){
            TextButton buildGreenHouse = new TextButton("Build Green House", skin);
            buildGreenHouse.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameController.getInstance().buildGreenHouse();
                    hide();
                    PopUpManager.getInstance(stage).hide();
                }
            });
            building.add(buildGreenHouse).size(64, 64).padRight(30);
        }

        if (player.getFarm().getCoop() == null) {
            TextButton buildCoop = new TextButton("Build Coop", skin);
            buildCoop.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.setChoosingCoop(true);
                    hide();
                    PopUpManager.getInstance(stage).hide();
                }
            });
            building.add(buildCoop).size(64, 64).padRight(30);
        }

        if (building.getChildren().size > 0) {
            animalTable.add(building).left().padBottom(20).row(); // 🆗 always added
        }

        if (animals.isEmpty()) {
            animalTable.add(new Label("No Animals Found", skin)).pad(10).row();
            return;
        }

        for (Animal animal : animals) {
            Texture texture = animal.getType().getTexture();
            String name = animal.getName();

            if (texture == null) {
                System.out.println("⚠️ Texture is null for animal: " + name);
                continue;
            }


            Drawable drawable = new TextureRegionDrawable(texture);
            ImageButton image = new ImageButton(drawable);
            Label nameLabel = new Label(name, skin);

            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    animalTable.clear();

                    Table whatToDo = new Table();
                    whatToDo.top().left().pad(20);
                    whatToDo.defaults().pad(10);

                    animalTable.add(new Label("Name: " + name, skin)).left().row();
                    animalTable.add(new Image(texture)).size(64, 64).padBottom(20).row();

                    TextButton pet = new TextButton("Pet", skin);
                    TextButton feed = new TextButton("Feed", skin);
                    TextButton sell = new TextButton("Sell", skin);
                    TextButton back = new TextButton("Back", skin);
                    TextButton showProducts = new TextButton("Show Products", skin);
                    TextButton collectProducts = new TextButton("Collect Products", skin);
                    TextButton goOut = new TextButton("Shepherd", skin);

                    pet.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            AnimalController.getInstance().petAnimal(animal.getName());
                            hide();
                            PopUpManager.getInstance(stage).hide();
                        }
                    });

                    feed.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            AnimalController.getInstance().feedingHay(animal.getName());
                            hide();
                            PopUpManager.getInstance(stage).hide();
                        }
                    });

                    sell.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            AnimalController.getInstance().sellAnimal(animal.getName());
                            hide();
                            PopUpManager.getInstance(stage).hide();
                        }
                    });

                    back.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            refreshAnimalList();
                        }
                    });

                    showProducts.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            AnimalController.getInstance().showProducts();
                            hide();
                            PopUpManager.getInstance(stage).hide();
                        }
                    });

                    collectProducts.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            AnimalController.getInstance().collectProducts(animal.getName());
                            hide();
                            PopUpManager.getInstance(stage).hide();
                        }
                    });
                    goOut.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            AnimalController.getInstance().shepherdAnimals(animal);
                            hide();
                            PopUpManager.getInstance(stage).hide();
                        }
                    });



                    whatToDo.add(pet);
                    whatToDo.add(feed);
                    whatToDo.add(sell).row();
                    whatToDo.add(showProducts);
                    whatToDo.add(collectProducts);
                    whatToDo.add(back).row();
                    whatToDo.add(goOut).row();

                    animalTable.add(whatToDo).left().row();
                }
            });

            Table row = new Table();
            row.add(image).size(64, 64).padRight(10);
            row.add(nameLabel).left();



            animalTable.add(row).left().row();
        }

    }



    private ArrayList<Animal> showAnimals() {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        ArrayList<Animal> animals = new ArrayList<>();

        if (player.getFarm().getBarn() != null) {
            animals.addAll(player.getFarm().getBarn().getAnimals());
        }

        if (player.getFarm().getCoop() != null) {
            animals.addAll(player.getFarm().getCoop().getAnimals());
        }

        return animals;
    }

    @Override
    public void show() {
        refreshAnimalList();
        super.show();
    }

    public void refresh() {
        refreshAnimalList();
    }
}
