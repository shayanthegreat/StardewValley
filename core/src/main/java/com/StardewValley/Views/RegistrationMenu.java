package com.StardewValley.Views;

import com.StardewValley.Controllers.RegistrationController;
import com.StardewValley.Models.Enums.Texts;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Interactions.Commands.RegistrationCommand;
import com.StardewValley.Models.Interactions.Messages.RegistrationMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class RegistrationMenu extends MenuView {
    private Stage stage;
    private TextButton registrationButton;
    private TextField username;
    private TextField password;
    private TextField nickName;
    private TextField email;
    private SelectBox<String> gender;
    private Table table;
    private RegistrationController controller;
    private TextButton loginMenu;
    private TextButton randomPassword;



    public RegistrationMenu(Skin skin) {
        super(skin);
        controller = RegistrationController.getInstance();
        stage = new Stage();
        registrationButton = new TextButton(Texts.SignUp.getText(), skin);
        username = new TextField("", skin);
        password = new TextField("", skin);
        nickName = new TextField("", skin);
        email = new TextField("", skin);
        gender = new SelectBox<>(skin);
        gender.setItems("male", "female");
        loginMenu = new TextButton(Texts.Login.getText(), skin);
        randomPassword = new TextButton(Texts.Random.getText(), skin);
        table = new Table(skin);
        click(skin);
    }

    private void click(Skin skin) {
        registrationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RegistrationMessage message = controller.Register(username.getText(), password.getText(), password.getText(), nickName.getText(), email.getText(), gender.getSelected());
                String result = message.message();
                if(message.command() != null &&  message.command().equals(RegistrationCommand.askQuestion)) {
                   controller.changeMenu(new PickQuestion(skin));
                }
                else {
                    Dialog dialog = new Dialog("", GameAssetManager.getInstance().getSkin());
                    dialog.text(result);
                    dialog.button("OK");
                    dialog.show(stage);
                }
            }
        });

        randomPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RegistrationMessage message = controller.Register(username.getText(), "random", "random", nickName.getText(), email.getText(), gender.getSelected());
                String result = message.message();

                Dialog dialog = new Dialog("", skin) {
                    @Override
                    protected void result(Object object) {
                        boolean accepted = (Boolean) object;
                        if(message.command() != null && message.command().equals(RegistrationCommand.askForPassword))
                            controller.acceptRandomPassword(accepted);
                    }
                };

                dialog.text(result);
                dialog.button("Accept", true);
                dialog.button("Cancel", false);
                dialog.show(stage);
            }

        });

        loginMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new LoginMenu(skin));
            }
        });
    }




    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        gender.getStyle().font.getData().setScale(1.2f);

        table.setFillParent(true);
        table.center().pad(30);

        table.add(new Label("Username", GameAssetManager.getInstance().getSkin())).left();
        table.row().padTop(5);
        table.add(username).width(400).height(50).left();

        table.row().padTop(15);
        table.add(new Label("Password", GameAssetManager.getInstance().getSkin())).left();
        table.row().padTop(5);
        table.add(password).width(400).height(50).left();

        table.row().padTop(15);
        table.add(new Label("Nickname", GameAssetManager.getInstance().getSkin())).left();
        table.row().padTop(5);
        table.add(nickName).width(400).height(50).left();

        table.row().padTop(15);
        table.add(new Label("Email", GameAssetManager.getInstance().getSkin())).left();
        table.row().padTop(5);
        table.add(email).width(400).height(50).left();

        table.row().padTop(15);
        table.add(new Label("Gender", GameAssetManager.getInstance().getSkin())).left();
        table.row().padTop(5);
        table.add(gender).width(400).height(50).left();

        table.row().padTop(30);
        table.add(loginMenu).width(300).height(60).center();

        table.row().padTop(30);
        table.add(registrationButton).width(300).height(60).center();

        table.row().padTop(30);
        table.add(randomPassword).width(300).height(60).center();

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
