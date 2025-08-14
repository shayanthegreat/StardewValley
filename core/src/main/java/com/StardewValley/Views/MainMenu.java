package com.StardewValley.Views;


import com.StardewValley.Controllers.MainMenuController;
import com.StardewValley.Main;
import com.StardewValley.Models.App;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    private TextButton profileMenu;
    private Stage stage;
    private Table table;
    private MainMenuController controller;
    private TextButton loginMenuButton;


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



        loginMenuButton = new TextButton("Login Menu", skin);
        loginMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.logout();
            }
        });

        profileMenu = new TextButton("Profile Menu", skin);
        profileMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new ProfileMenu(GameAssetManager.getInstance().getSkin()));
            }
        });

        exit = new TextButton("Exit", skin);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });

        avatarButton = new ImageButton(new TextureRegionDrawable(new Texture(App.getInstance().getCurrentUser().getAvatarPath())));

        table = new Table();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Image bgImage = new Image(new Texture(Gdx.files.internal("Background.jpg")));
        bgImage.setFillParent(true);
        stage.addActor(bgImage);
        table.setFillParent(true);
        table.center();
        table.defaults().width(300).height(60).padTop(15);

        Table profileRow = new Table();
        profileRow.add(new Label(App.getInstance().getCurrentUser().getNickname(), skin))
            .padRight(15).left();
        profileRow.add(avatarButton).size(60);
        table.add(profileRow).colspan(2).center();
        table.row();

        table.add(registrationMenu).fillX();
        table.row();
        table.add(profileMenu).fillX();
        table.row();
        table.add(gameMenu).fillX();
        table.row();
        table.add(loginMenu).fillX();
        table.row();
        table.add(exit).fillX();

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
