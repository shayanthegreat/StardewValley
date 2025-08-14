package com.StardewValley.Views;

import com.StardewValley.Controllers.GameController;
import com.StardewValley.Main;
import com.StardewValley.Models.App;
import com.StardewValley.Models.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class PregameView extends MenuView{
    private Stage stage;
    private Skin skin;
    private TextButton addPlayerButton;
    private GameController controller;
    private String[] usernames;
    private ImageButton map1Button ;
    private int selectedMapId;
    private Table table;


    public PregameView(Skin skin) {
        super(skin);
        this.skin = skin;
        stage = new Stage();
        addPlayerButton = new TextButton("Add Player", skin);
        addPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addPlayer(skin);
            }
        });
        controller = GameController.getInstance();
        map1Button = new ImageButton(new TextureRegionDrawable(new Texture("download.png")));
        map1Button.addListener(new ClickListener() {
            User[] users = new User[] { App.getInstance().getCurrentUser() };
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        table = new Table();
    }

    private void addPlayer(Skin skin) {
        final TextField player1 = new TextField("", skin);
        final TextField player2 = new TextField("", skin);
        final TextField player3 = new TextField("", skin);

        Dialog dialog = new Dialog("Add Players", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    String name1 = player1.getText().trim();
                    String name2 = player2.getText().trim();
                    String name3 = player3.getText().trim();

                    ArrayList<String> usernamesList = new ArrayList<>();
                    if (!name1.isEmpty()) usernamesList.add(name1);
                    if (!name2.isEmpty()) usernamesList.add(name2);
                    if (!name3.isEmpty()) usernamesList.add(name3);

                    usernames = usernamesList.toArray(new String[0]);
                }
            }
        };

        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);

        dialog.getContentTable().pad(10);

        dialog.getContentTable().add(new Label("Player 1 Username", skin)).left();
        dialog.getContentTable().row().padTop(5);
        dialog.getContentTable().add(player1).width(300);

        dialog.getContentTable().row().padTop(15);
        dialog.getContentTable().add(new Label("Player 2 Username", skin)).left();
        dialog.getContentTable().row().padTop(5);
        dialog.getContentTable().add(player2).width(300);

        dialog.getContentTable().row().padTop(15);
        dialog.getContentTable().add(new Label("Player 3 Username", skin)).left();
        dialog.getContentTable().row().padTop(5);
        dialog.getContentTable().add(player3).width(300);

        dialog.button("Submit", true);
        dialog.button("Cancel", false);

        dialog.show(stage);
    }




    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Image bgImage = new Image(new Texture(Gdx.files.internal("Background.jpg")));
        bgImage.setFillParent(true);
        stage.addActor(bgImage);

        table.setFillParent(true);
        table.center().pad(30);

        table.row().padTop(5);
        table.add(addPlayerButton).width(400).height(50).left();

        table.row().padTop(15);
        table.row().padTop(5);
        table.add(map1Button).width(400).height(50).left();
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
        stage.act(Math.min( Gdx.graphics.getDeltaTime(), 1 / 30f));
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
