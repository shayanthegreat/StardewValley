package com.StardewValley.Views;


import com.StardewValley.Controllers.MainMenuController;
import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenu extends MenuView {
    private ImageButton avatarButton;
    private TextButton registrationMenu;
    private TextButton loginMenu;
    private TextButton gameMenu;
    private TextButton exit;
    private Skin skin;
    private Stage stage;
    private Table table;
    private MainMenuController controller;


    public MainMenu(Skin skin) {
        super(skin);
        this.skin = skin;
        controller = MainMenuController.getInstance();
        registrationMenu = new TextButton("SignUp Menu", skin);
        registrationMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new RegistrationMenu(GameAssetManager.getInstance().getSkin()));
            }
        });

        loginMenu = new TextButton("Login Menu", skin);
        loginMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new LoginMenu(GameAssetManager.getInstance().getSkin()));
            }
        });

        gameMenu = new TextButton("Game Menu", skin);
        gameMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new GameMenu(GameAssetManager.getInstance().getSkin()));
            }
        });

        exit = new TextButton("Exit", skin);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });

        avatarButton = new ImageButton(new TextureRegionDrawable(App.getInstance().getCurrentUser().getAvatarTexture()));

        table = new Table();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center().pad(30);

        table.row().padTop(15);
        table.add(avatarButton).width(400).height(50).left();

        table.row().padTop(15);
        table.add(registrationMenu).width(400).height(50).left();

        table.row().padTop(15);
        table.add(loginMenu).width(400).height(50).left();

        table.row().padTop(15);
        table.add(gameMenu).width(400).height(50).left();

        table.row().padTop(15);
        table.add(exit).width(400).height(50).left();

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
