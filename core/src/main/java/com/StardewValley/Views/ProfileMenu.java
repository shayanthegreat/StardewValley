package com.StardewValley.Views;

import com.StardewValley.Controllers.ProfileMenuController;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Interactions.Messages.ProfileMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ProfileMenu extends MenuView{
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton back;
    private TextButton information;
    private TextButton changeUsername;
    private TextButton changePassword;
    private TextButton changeEmail;
    private TextButton changeNickname;
    private ProfileMenuController controller;

    public ProfileMenu(Skin skin){
        super(skin);
        this.skin = skin;
        controller = ProfileMenuController.getInstance();
        back = new TextButton("Back", skin);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeMenu(new MainMenu(GameAssetManager.getInstance().getSkin()));
            }
        });

        information = new TextButton("Information", skin);
        information.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ProfileMessage message = controller.showUserInfo();
                Dialog dialog = new Dialog("Information", skin);
                dialog.setFillParent(true);
                dialog.text(message.message());
                dialog.button("OK", skin);
                dialog.show(stage);
            }
        });

        changeUsername = new TextButton("Change Username", skin);
        changeUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final TextField usernameField = new TextField("", skin);
                usernameField.setMessageText("Enter new username");

                Dialog dialog = new Dialog("Change Username", skin) {
                    @Override
                    protected void result(Object obj) {
                        if (Boolean.TRUE.equals(obj)) {
                            String newUsername = usernameField.getText();
                            ProfileMessage message = controller.changeUsername(newUsername);

                            Dialog confirm = new Dialog("Result", skin);
                            confirm.text(message.message());
                            confirm.button("OK");
                            confirm.show(stage);
                        }
                    }
                };

                dialog.setModal(true);
                dialog.setMovable(false);
                dialog.text("New Username:");

                dialog.getContentTable().row().padTop(10);
                dialog.getContentTable().add(usernameField).width(300);

                dialog.button("Submit", true);
                dialog.button("Cancel", false);

                dialog.show(stage);
            }
        });

        changeNickname = new TextButton("Change Nickname", skin);
        changeNickname.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final TextField usernameField = new TextField("", skin);
                usernameField.setMessageText("Enter new username");

                Dialog dialog = new Dialog("Change Username", skin) {
                    @Override
                    protected void result(Object obj) {
                        if (Boolean.TRUE.equals(obj)) {
                            String newUsername = usernameField.getText();
                            ProfileMessage message = controller.changeNickname(newUsername);

                            Dialog confirm = new Dialog("Result", skin);
                            confirm.text(message.message());
                            confirm.button("OK");
                            confirm.show(stage);
                        }
                    }
                };

                dialog.setModal(true);
                dialog.setMovable(false);
                dialog.text("New Username:");

                dialog.getContentTable().row().padTop(10);
                dialog.getContentTable().add(usernameField).width(300);

                dialog.button("Submit", true);
                dialog.button("Cancel", false);

                dialog.show(stage);
            }
        });

        changeEmail = new TextButton("Change Email", skin);
        changeEmail.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final TextField usernameField = new TextField("", skin);
                usernameField.setMessageText("Enter new username");

                Dialog dialog = new Dialog("Change Username", skin) {
                    @Override
                    protected void result(Object obj) {
                        if (Boolean.TRUE.equals(obj)) {
                            String newUsername = usernameField.getText();
                            ProfileMessage message = controller.changeEmail(newUsername);

                            Dialog confirm = new Dialog("Result", skin);
                            confirm.text(message.message());
                            confirm.button("OK");
                            confirm.show(stage);
                        }
                    }
                };

                dialog.setModal(true);
                dialog.setMovable(false);
                dialog.text("New Username:");

                dialog.getContentTable().row().padTop(10);
                dialog.getContentTable().add(usernameField).width(300);

                dialog.button("Submit", true);
                dialog.button("Cancel", false);

                dialog.show(stage);
            }
        });

        changePassword = new TextButton("Change Password", skin);
        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final TextField usernameField = new TextField("", skin);
                usernameField.setMessageText("Enter old password username");

                final TextField newPasswordField = new TextField("", skin);
                newPasswordField.setMessageText("Enter new password");

                Dialog dialog = new Dialog("Change Username", skin) {
                    @Override
                    protected void result(Object obj) {
                        if (Boolean.TRUE.equals(obj)) {
                            String newUsername = usernameField.getText();
                            ProfileMessage message = controller.changePassword(newPasswordField.getMessageText(),newUsername);

                            Dialog confirm = new Dialog("Result", skin);
                            confirm.text(message.message());
                            confirm.button("OK");
                            confirm.show(stage);
                        }
                    }
                };

                dialog.setModal(true);
                dialog.setMovable(false);
                dialog.text("New Username:");

                dialog.getContentTable().row().padTop(10);
                dialog.getContentTable().add(usernameField).width(300);
                dialog.getContentTable().row().padTop(10);
                dialog.getContentTable().add(newPasswordField).width(300);

                dialog.button("Submit", true);
                dialog.button("Cancel", false);

                dialog.show(stage);
            }
        });

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

        table.row().padTop(15);
        table.add(information).width(400).height(50).left();

        table.row().padTop(15);
        table.add(changeUsername).width(400).height(50).left();

        table.row().padTop(15);
        table.add(changePassword).width(400).height(50).left();

        table.row().padTop(15);
        table.add(changeNickname).width(400).height(50).left();

        table.row().padTop(15);
        table.add(changeEmail).width(400).height(50).left();

        table.row().padTop(15);
        table.add(back).width(400).height(50).left();

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
