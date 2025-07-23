package com.StardewValley.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LightningEffect {
    private Animation<TextureRegion> lightningAnimation;
    private float stateTime = 0f;
    private boolean finished = false;

    public LightningEffect() {
        Texture sheet = new Texture(Gdx.files.internal("Lightning/ChatGPT Image Jul 19, 2025, 05_04_44 PM.png"));
        int FRAME_COLS = 4;
        int FRAME_ROWS = 4;

        TextureRegion[][] tmp = TextureRegion.split(sheet,
            sheet.getWidth() / FRAME_COLS,
            sheet.getHeight() / FRAME_ROWS);

        TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        lightningAnimation = new Animation<>(0.05f, frames);
        lightningAnimation.setPlayMode(Animation.PlayMode.NORMAL);
    }

    public void update(float delta) {
        if (!finished) {
            stateTime += delta;
            if (lightningAnimation.isAnimationFinished(stateTime)) {
                finished = true;
            }
        }
    }

    public void render(SpriteBatch batch, float x, float y) {
        if (!finished) {
            TextureRegion currentFrame = lightningAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, x, y);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
