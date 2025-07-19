package com.StardewValley.Models.Enums;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Map.Direction;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum AvatarType {
    ABIGAIL("Abigail", GameAssetManager.getInstance().ABIGAIL,"female"),
    SAM("Sam",GameAssetManager.getInstance().SAM,"male"),
    Harvey("Harvey",GameAssetManager.getInstance().Harvey,"male");

    private final String name;
    private final Texture texture;
    private final TextureRegion[][] textureRegion;
    private final String gender;

    AvatarType(String name, Texture texture, String gender) {
        this.name = name;
        this.texture = texture;
        this.gender = gender;
        this.textureRegion = TextureRegion.split(texture, 16, 32);
    }

    public Animation walkingAnimation(Direction direction) {
        int x = 0;
        switch (direction) {
            case up ->{
                x = 2;
                break;
            }
            case down ->{
                x = 0;
                break;
            }
            case left, upLeft, downLeft->{
                x = 3;
                break;
            }
            case right, upRight,downRight->{
                x = 1;
                break;
            }
        }
        Animation<TextureRegion> animation = new Animation(0.1f, textureRegion[x][1] , textureRegion[x][2] , textureRegion[x][3] , textureRegion[x][0] );
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    public Animation TiredAnimation(Direction direction) {
        int x = 0;
        switch (direction) {
            case up ->{
                x = 2;
                break;
            }
            case down ->{
                x = 0;
                break;
            }
            case left, upLeft, downLeft->{
                x = 3;
                break;
            }
            case right, upRight,downRight->{
                x = 1;
                break;
            }
        }
        Animation<TextureRegion> animation = new Animation(0.1f, textureRegion[x][0] );
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        return animation;
    }

    public Animation faintAnimation(Direction direction) {
        Animation<TextureRegion> animation = new Animation(0.1f, textureRegion[0][0] , textureRegion[0][3] , textureRegion[1][3] , textureRegion[1][0] );
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        return animation;
    }

}
