package com.StardewValley.Views;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.PopUps.InventoryPopUp;
import com.StardewValley.Models.PopUps.PopUpMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameView implements Screen {
    private Stage stage;
    private InventoryPopUp farmMenu;

    public GameView() {
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        // Create the popup menu
        farmMenu = new InventoryPopUp(stage);

        // Create a button to show the menu (for testing)
        TextButton showMenuButton = new TextButton("Show Farm Menu", GameAssetManager.getInstance().SKIN);
        showMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                farmMenu.show();
                showMenuButton.remove();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(showMenuButton);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
