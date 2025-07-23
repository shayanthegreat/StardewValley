package com.StardewValley.Views;

import com.StardewValley.Controllers.GameController;
import com.StardewValley.Main;
import com.StardewValley.Models.App;
import com.StardewValley.Models.FishBehavior;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Fish {
    float y;
    float velocity;
    FishBehavior behavior;
    Texture texture;

    public Fish() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 6);
        behavior = new FishBehavior(randomNum);
        y =  FishingMiniGameView.GAME_HEIGHT / 2;
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int random = rand.nextInt(3);
        Texture fishTexture = GameAssetManager.getInstance().FISH;
        switch (random) {
            case 0:{
                fishTexture = GameAssetManager.getInstance().FISH;
                break;
            }
            case 1:{
                fishTexture = GameAssetManager.getInstance().FISH2;
                break;
            }
            case 2:{
                fishTexture = GameAssetManager.getInstance().FISH3;
                break;
            }
        }
        this.texture = fishTexture;
    }

    public void update(float delta) {
        y += velocity * delta;
        velocity = behavior.behavior();
        y = MathUtils.clamp(y, 0, FishingMiniGameView.GAME_HEIGHT);
    }
}

class CatchMeter {
    boolean isPerfect = true;
    float fill = 0;
    float fillRate = 20f;
    float drainRate = 10f;

    public void update(float delta, boolean isInBar) {
        if (isInBar) {
            fill += fillRate * delta;
        } else {
            isPerfect = false;
            fill -= drainRate * delta;
        }
        fill = MathUtils.clamp(fill, 0, 100);

    }

    public boolean isFull() {
        return fill >= 100;
    }
}

class FishingBar {
    float y;
    float height = 40;
    float velocity = 0;
    float gravity = -300;
    float riseSpeed = 150;

    public void update(float delta, boolean rising) {
        if (rising) {
            velocity = riseSpeed;
        } else {
            velocity += gravity * delta;
        }
        y += velocity * delta;
        y = MathUtils.clamp(y, 0, FishingMiniGameView.GAME_HEIGHT - height);
    }

    public boolean overlaps(float fishY) {
        return fishY >= y && fishY <= y + height;
    }
}

public class FishingMiniGameView implements Screen {
    public static final float GAME_WIDTH = 300f;
    public static final float GAME_HEIGHT = 300f;
    private Stage stage;
    private TextButton exit;
    private Table table;
    private SpriteBatch batch;

    public FishingMiniGameView(Skin skin) {
        exit = new TextButton("Exit", skin);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().getScreen().dispose();
                Main.getInstance().setScreen(new GameView());
            }
        });
        batch = Main.getInstance().getBatch();
    }

    private final FishingBar fishingBar = new FishingBar();
    private final CatchMeter catchMeter = new CatchMeter();
    private final Fish fish = new Fish();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private float centerX;
    private float centerY;

    private void drawFish(float y) {
        Texture fishTexture = fish.texture;
        float x = centerX - fishTexture.getWidth() / 2f;
        float drawY =centerY - GAME_HEIGHT / 2f + y - 18f;
        batch.draw(fishTexture, x, drawY);
    }


    private void drawFishingBar(float y, float height) {
        float x = centerX - 30;
        float drawY = centerY - GAME_HEIGHT / 2f + y;
        shapeRenderer.setColor(0, 1, 0, 0.6f);
        shapeRenderer.rect(x, drawY, 60, height);
    }


    private void drawCatchMeter(float fill) {
        float x = centerX + 50;
        float y = centerY - 50;

        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
        shapeRenderer.rect(x, y, 20, 100);

        shapeRenderer.setColor(1, 0.5f, 0, 1);
        shapeRenderer.rect(x, y, 20, fill);
    }


    @Override
    public void show() {
        centerX = Gdx.graphics.getWidth() / 2f;
        centerY = Gdx.graphics.getHeight() / 2f;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table = new Table();

        table.setFillParent(true);
        table.center().pad(100);
        table.row().pad(100);
        table.add(exit);
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);
        boolean isPressingR = Gdx.input.isKeyPressed(Input.Keys.R);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        fishingBar.update(delta, isPressingR);
        fish.update(delta);
        catchMeter.update(delta, fishingBar.overlaps(fish.y));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawFishingBar(fishingBar.y, fishingBar.height);
        drawCatchMeter(catchMeter.fill);
        shapeRenderer.end();

        batch.setProjectionMatrix(stage.getCamera().combined);

        batch.begin();
        drawFish(fish.y);
        batch.end();



        if (catchMeter.isFull()) {
            GameController.getInstance().fishing(catchMeter.isPerfect);
            Main.getInstance().getScreen().dispose();
            Main.getInstance().setScreen(new GameView());
        }
    }


    @Override
    public void resize(int width, int height) {
        centerX = width / 2f;
        centerY = height / 2f;
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

}
