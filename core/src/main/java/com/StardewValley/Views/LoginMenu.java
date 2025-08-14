package com.StardewValley.Views;

import com.StardewValley.Controllers.RegistrationController;

import com.StardewValley.Main;
import com.StardewValley.Models.Interactions.Commands.RegistrationCommand;
import com.StardewValley.Models.Interactions.Messages.RegistrationMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class LoginMenu extends MenuView {
    private TextButton login;
    private TextButton back;
    private TextField Username;
    private TextField Password;
    private CheckBox stayLoggedIn;
    private Table table;
    private Skin skin;
    private RegistrationController controller;
    private Stage stage;
    private TextButton forgotPassword;

    public LoginMenu(Skin skin) {
        super(skin);
        this.skin = skin;
        login = new TextButton("Login", skin);
        back = new TextButton("Back", skin);
        Username = new TextField("", skin);
        Password = new TextField("", skin);
        stayLoggedIn = new CheckBox("Stay Logged In",skin);
        table = new Table();
        controller = RegistrationController.getInstance();
        forgotPassword = new TextButton("Forgot Password", skin);
        click(skin);
    }

    private void click(Skin skin){
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new RegistrationMenu(skin));
            }
        });

        forgotPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                RegistrationMessage message = controller.forgetPassword(Username.getText());

                if (message.command() != null && message.command().equals(RegistrationCommand.answerQuestion)) {
                    final TextField answerField = new TextField("", skin);
                    Dialog questionDialog = new Dialog("Security Question", skin) {
                        @Override
                        protected void result(Object object) {
                            if (object.equals(true)) {
                                String answer = answerField.getText();
                                controller.showError("This is you password: "+controller.checkAnswer(Username.getText(), answer).message(), stage, skin);
                            }
                        }
                    };

                    questionDialog.setModal(true);
                    questionDialog.setMovable(false);

                    questionDialog.text(message.message());
                    questionDialog.getContentTable().row().padTop(10);
                    questionDialog.getContentTable().add(answerField).width(300).pad(10);

                    questionDialog.button("Submit", true);
                    questionDialog.button("Cancel", false);

                    questionDialog.show(stage);
                }
                else{
                    controller.showError(message.message(),stage,skin);
                }
            }
        });

        login.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showError(controller.login(Username.getText(), Password.getText(), stayLoggedIn.isChecked()).message(), stage, skin);

            }
        });

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

        table.add(new Label("Username", skin)).left();
        table.row().padTop(5);
        table.add(Username).width(400).height(50).left();

        table.row().padTop(15);
        table.add(new Label("Password", skin)).left();
        table.row().padTop(5);
        table.add(Password).width(400).height(50).left();

        table.row().padTop(30);
        table.add(login).width(300).height(60).center();

        table.row().padTop(30);
        table.add(back).width(300).height(60).center();

        table.row().padTop(30);
        table.add(forgotPassword).width(300).height(60).center();

        table.row().padTop(30);
        table.add(stayLoggedIn).width(300).height(60).center();

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
