package com.StardewValley.Controllers;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Map.*;

public class WordController {
    private boolean wideView = false;
    private Camera camera;
    private static WordController instance;
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
        printMap();
    }

    public boolean isWideView() {
        return wideView;
    }

    public void setWideView(boolean wideView) {
        this.wideView = wideView;
    }

    private void printMap() {
        Game game = App.getInstance().getCurrentGame();
        Map map = game.getMap();
        camera.update(game.getCurrentPlayer().getPosition().x, game.getCurrentPlayer().getPosition().y);

        for(int i=-100 ; i<350; i++){
            for(int j=-100 ; j<350; j++){
                Tile tile = map.getTile(new Position(i , j));
                if(tile == null){
                    camera.print(GameAssetManager.getInstance().STONE_FLOORING, i , j , 1 , 1);
                    continue;
                }
                Building building = tile.getBuilding();
                TileObject tileObject = tile.getObject();
                if(building == null ){
                    camera.print(GameAssetManager.getInstance().GRASS_FLOORING, i,j,1,1);
                }
            }
        }
    }

}
