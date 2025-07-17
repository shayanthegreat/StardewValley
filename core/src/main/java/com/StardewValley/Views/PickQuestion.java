package com.StardewValley.Views;

import com.StardewValley.Controllers.RegistrationController;
import com.StardewValley.Main;
import com.StardewValley.Models.Enums.Question;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class PickQuestion extends MenuView {
    private SelectBox<String> question;
    private TextField answer;
    private RegistrationController controller;
    private Stage stage;
    private Table table;
    private TextButton submitButton;

    public PickQuestion(Skin skin) {
        super(skin);
        question = new SelectBox<>(skin);
        question.setItems(Question.getQuestion(1).toString(),
            Question.getQuestion(2).toString(),
            Question.getQuestion(3).toString(),
            Question.getQuestion(4).toString(),
            Question.getQuestion(5).toString());
        controller = RegistrationController.getInstance();
        answer = new TextField("", skin);
        submitButton = new TextButton("Submit", skin);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.pickQuestion(question.getSelectedIndex()+1, answer.getText());
                controller.changeMenu(new LoginMenu(skin));
            }
        });
        stage = new Stage();
        table = new Table(skin);
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.add(question).width(500).height(60).center();
        table.row().pad(15);
        table.add(answer).width(500).height(60).center();
        table.row().pad(15);
        table.add(submitButton).width(500).height(60).center();
        stage.addActor(table);

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
        Main.getInstance().getBatch().begin();
        Main.getInstance().getBatch().end();
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
