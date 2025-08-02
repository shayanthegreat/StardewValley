package com.StardewValley.Controllers;

import com.StardewValley.Main;
import com.StardewValley.Models.Animal.AnimalProduct;
import com.StardewValley.Models.Animal.Barn;
import com.StardewValley.Models.Animal.Coop;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Enums.Weather;
import com.StardewValley.Models.Farming.Plant;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Map.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.concurrent.ThreadLocalRandom;

public class WordController {
    private boolean wideView = false;
    private Camera camera;
    private static WordController instance;
    private Texture texture;
    private ParticleEffect rainEffect;
    private Weather lastWeather = null;
    private boolean isWide = false;


    private WordController() {
        camera = Camera.getInstance();
    }
    public static WordController getInstance() {
        if(instance == null) {
            instance = new WordController();
        }
        return instance;
    }

    public void update(){
        updateWeather();
        printMap();

        if (rainEffect != null) {
            rainEffect.update(Gdx.graphics.getDeltaTime());
            rainEffect.draw(Main.getInstance().getBatch());
        }

    }

    private void updateWeather() {
        Weather current = App.getInstance().getCurrentGame().getTodayWeather();

        if (current != lastWeather) {
            lastWeather = current;

            if (rainEffect != null) {
                rainEffect.dispose();
                rainEffect = null;
            }

            if (current == Weather.rain) {
                rainEffect = new ParticleEffect();
                rainEffect.load(
                    Gdx.files.internal("RainEffect/Particle Park Rain.p"),
                    Gdx.files.internal("RainEffect/")
                );
                rainEffect.start();
            }
            else if(current == Weather.snow){
                rainEffect = new ParticleEffect();
                rainEffect.load(
                    Gdx.files.internal("SnowEffect/Particle Park Snow Flakes.p"),
                    Gdx.files.internal("SnowEffect/")
                );
            }
        }

        if (rainEffect != null) {
            float x = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition().x;
            float y = App.getInstance().getCurrentGame().getCurrentPlayer().getPosition().y;
            rainEffect.setPosition(x*32f-400f, y*32f+500f);
        }
    }

    public void zoom(){
        wideView = !wideView;
        if(wideView){
            camera.zoomOut();
        }
        else {
            camera.zoomIn();
        }
    }


    private void printMap() {
        Game game = App.getInstance().getCurrentGame();
        Map map = game.getMap();
        camera.update(game.getCurrentPlayer().getPosition().x, game.getCurrentPlayer().getPosition().y);
        switch (App.getInstance().getCurrentGame().getTime().getSeason()){
            case spring -> {
                texture = GameAssetManager.getInstance().SPRING_FLOORING;
                break;
            }
            case winter -> {
                texture = GameAssetManager.getInstance().WINTER_FLOORING;
                break;
            }
            case summer -> {
                texture = GameAssetManager.getInstance().SUMMER_FLOORING;
                break;
            }
            default -> texture = GameAssetManager.getInstance().FALL_FLOORING;

        }

        int [] topX = new int[4];
        int [] topY = new int[4];
        int iC = 0;
        int jC = 0;
        for (Farm farm : map.getFarms()) {
            topX[iC++] = farm.getTopLeft().x;
            topY[jC++] = farm.getTopLeft().y;
        }

        for(int i=-100 ; i<350; i++){
            for(int j=-100 ; j<350; j++){
                Tile tile = map.getTile(new Position(i , j));

                if(tile == null ){
                    camera.print(GameAssetManager.getInstance().STONE_FLOORING, i , j , 1 , 1);
                    continue;
                }

                Building building = tile.getBuilding();
                TileObject tileObject = tile.getObject();
                camera.print(texture, i,j,1,1);

                if(building == null){
                    if(tileObject == null){
                        if(tile.isPlowed()){
                            camera.print(GameAssetManager.getInstance().PLOWED_FLOOR, i , j , 1 , 1);
                        }
                    }
                    else if(tileObject instanceof Item){
                        camera.print(((Item) tileObject).getTexture(), i,j,1,1);
                    }
                    else if(tileObject instanceof Plant){
                        camera.print(((Plant) tileObject).getType().getCrop().getTexture(), i,j,1,1);
                    }
                }

                if( building instanceof House house){
                    if(i==house.getOrigin().x + 3 && j==house.getOrigin().y + 3){
                        if(App.getInstance().getCurrentGame().getCurrentPlayer().isInHouse()){
                            camera.print(GameAssetManager.getInstance().IN_HOUSE, house.getOrigin().x,house.getOrigin().y,4,4);
                            camera.print(GameAssetManager.getInstance().REFRIGERATOR,i-3,j-3,1,1);
                        }
                        else{
                            camera.print(GameAssetManager.getInstance().HOUSE, house.getOrigin().x,house.getOrigin().y,4,4);
                        }
                    }
                }

                else if(building instanceof Lake lake){
                    ThreadLocalRandom rand = ThreadLocalRandom.current();
                    int random = rand.nextInt(2);
                    int random2 = rand.nextInt(3);
                    camera.print(GameAssetManager.getInstance().LAKE, i,j,1,1);
                    switch(random2){
                        case 0:{
                            camera.print(GameAssetManager.getInstance().FISH, i+random,j+random,0.5f,0.5f);
                            break;
                        }
                        case 1:{
                            camera.print(GameAssetManager.getInstance().FISH2, i+random,j+random,0.5f,0.5f);
                            break;
                        }
                        case 2:{
                            camera.print(GameAssetManager.getInstance().FISH3, i+random,j+random,0.5f,0.5f);
                            break;
                        }
                    }
                }

                else if(building instanceof GreenHouse greenHouse){
                    if(!greenHouse.isBuilt() && i==greenHouse.getTopRight().x && j== greenHouse.getTopRight().y){
                        camera.print(GameAssetManager.getInstance().GREEN_HOUSE, greenHouse.getOrigin().x,greenHouse.getOrigin().y,5,6);
                    }
                    else if(i==greenHouse.getTopRight().x && j== greenHouse.getTopRight().y){
                        camera.print(GameAssetManager.getInstance().BUILT_GREENHOUSE, greenHouse.getOrigin().x,greenHouse.getOrigin().y,5,6);
                    }
                }

                else if(tileObject instanceof Barn barn){
                    camera.print(GameAssetManager.getInstance().BARN, barn.getPlacedTile().getPosition().x,barn.getPlacedTile().getPosition().y,2,2);
                }

                else if(tileObject instanceof Coop coop){
                    camera.print(GameAssetManager.getInstance().COOP,coop.getPlacedTile().getPosition().x,coop.getPlacedTile().getPosition().y,2,2);
                }


                if((i>topX[0]-1 && i<topX[0]+99 && (j==topY[0] || j==topY[0]+99)) || (j<topY[0]+99 && j>topY[0]-1 && (i==topX[0] || i==topX[0]+99) )){
                    camera.print(GameAssetManager.getInstance().FENCE, i,j,1,1);
                }
                if(i==topX[0]+99 && j==topY[0]+99){
                    camera.print(GameAssetManager.getInstance().GATE, i,j,1,1);
                }

                if((i>topX[1]-1 && i<topX[1]+99 && (j==topY[1] || j==topY[1]+99)) || (j<topY[1]+99 && j>topY[1]-1 && (i==topX[1] || i==topX[1]+99) )){
                    camera.print(GameAssetManager.getInstance().FENCE, i,j,1,1);
                }
                if(i==topX[1]+99 && j==topY[1]+99){
                    camera.print(GameAssetManager.getInstance().GATE, i,j,1,1);
                }

                if((i>topX[2]-1 && i<topX[2]+99 && (j==topY[2] || j==topY[2]+99)) || (j<topY[2]+99 && j>topY[2]-1 && (i==topX[2] || i==topX[2]+99) )){
                    camera.print(GameAssetManager.getInstance().FENCE, i,j,1,1);
                }
                if(i==topX[2]+99 && j==topY[2]+99){
                    camera.print(GameAssetManager.getInstance().GATE, i,j,1,1);
                }

                if((i>topX[3]-1 && i<topX[3]+99 && (j==topY[3] || j==topY[3]+99)) || (j<topY[3]+99 && j>topY[3]-1 && (i==topX[3] || i==topX[3]+99) )){
                    camera.print(GameAssetManager.getInstance().FENCE, i,j,1,1);
                }
                if(i==topX[3]+99 && j==topY[3]+99){
                    camera.print(GameAssetManager.getInstance().GATE, i,j,1,1);
                }

            }
        }

    }

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public void drawDarknessOverlay() {
        int hour = App.getInstance().getCurrentGame().getTime().getHour();
        float alpha;

        if (hour < 9 || hour > 22) {
            alpha = 1f;
        } else {
            float progress = (hour - 9) / 13f;
            alpha = progress * 0.7f;
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(Camera.getInstance().getCombined());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, alpha);

        float x = Camera.getInstance().getX() - Camera.getInstance().getViewportWidth() / 2f;
        float y = Camera.getInstance().getY() - Camera.getInstance().getViewportHeight() / 2f;
        float w = Camera.getInstance().getViewportWidth();
        float h = Camera.getInstance().getViewportHeight();
        shapeRenderer.rect(x, y, w, h);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

}

