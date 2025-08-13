package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera {
    private static Camera instance;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private float stateTime = 0f;
    private float zoom = 1f;
    private BitmapFont font = new BitmapFont();

    public static final float TILE_SIZE = 32f;

    private Camera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = Main.getInstance().getBatch();
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public void update(float centerX, float centerY) {
        camera.position.set(centerX * TILE_SIZE, centerY * TILE_SIZE, 0);
        camera.viewportWidth = 800 * zoom;
        camera.viewportHeight = 600 * zoom;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
    public Matrix4 getCombined() {
        return camera.combined;
    }

    public void zoomOut() {
        this.zoom = 10f;
    }

    public void zoomIn() {
        this.zoom = 1f;
    }


    public void begin() {
        batch.begin();
    }

    public void end() {
        batch.end();
    }

    public void print(Texture texture, int tileX, int tileY, float width, float height) {
        float pixelX = tileX * TILE_SIZE;
        float pixelY = tileY * TILE_SIZE;

        Rectangle view = new Rectangle(
            camera.position.x - camera.viewportWidth / 2,
            camera.position.y - camera.viewportHeight / 2,
            camera.viewportWidth,
            camera.viewportHeight
        );

        Rectangle tileRect = new Rectangle(pixelX, pixelY, width * TILE_SIZE, height * TILE_SIZE);

        if (view.overlaps(tileRect)) {
            batch.draw(texture, pixelX, pixelY, width * TILE_SIZE, height * TILE_SIZE);
        }
    }

    public void print(Animation<TextureRegion> animation, int tileX, int tileY, int width, int height) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animation.getKeyFrame(stateTime, true);

        float pixelX = tileX * TILE_SIZE;
        float pixelY = tileY * TILE_SIZE;

        Rectangle view = new Rectangle(
            camera.position.x - camera.viewportWidth / 2,
            camera.position.y - camera.viewportHeight / 2,
            camera.viewportWidth,
            camera.viewportHeight
        );

        Rectangle tileRect = new Rectangle(pixelX, pixelY, width * TILE_SIZE, height * TILE_SIZE);

        if (view.overlaps(tileRect)) {
            batch.draw(frame, pixelX, pixelY, width * TILE_SIZE, height * TILE_SIZE);
        }
    }

    public void print(String text, int tileX, int tileY, Stage stage) {
        float pixelX = tileX * TILE_SIZE;
        float pixelY = tileY * TILE_SIZE;

        Rectangle view = new Rectangle(
            camera.position.x - camera.viewportWidth / 2,
            camera.position.y - camera.viewportHeight / 2,
            camera.viewportWidth,
            camera.viewportHeight
        );

        Rectangle tileRect = new Rectangle(pixelX, pixelY, TILE_SIZE, TILE_SIZE);

        if (view.overlaps(tileRect)) {
            Label label = new Label(
                GameAssetManager.getInstance().getEmojiSupport().FilterEmojis(text),
                GameAssetManager.getInstance().getSkin()
            );

            label.setPosition(pixelX, pixelY + TILE_SIZE);

            label.setWrap(true);

            stage.addActor(label);
        }
    }



    public OrthographicCamera getCamera() {
        return camera;
    }
    public float getX(){
        return camera.position.x;
    }

    public float getY(){
        return camera.position.y;
    }

    public float getViewportWidth() {
        return camera.viewportWidth;
    }

    public float getViewportHeight() {
        return camera.viewportHeight;
    }

    public void setZoom(float zoom, float centerX, float centerY) {
        this.zoom = Math.max(0.3f, Math.min(zoom, 3f));
        getCamera().zoom = this.zoom;  // <-- THIS was missing
        update(centerX, centerY);
    }





}
