package com.StardewValley.Controllers;

import com.StardewValley.Models.Animal.AnimalProduct;
import com.StardewValley.Models.App;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Player;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class HouseController{
    private static HouseController instance;
    private HouseController(){}
    public static HouseController getInstance(){
        if(instance==null){
            instance = new HouseController();
        }
        return instance;
    }




    public void refrigerator(String itemName,boolean put) {
        App app = App.getInstance();
        Player player=app.getCurrentGame().getCurrentPlayer();
        Item item = player.getBackPack().getItemByName(itemName);
        if(!player.isInHouse()) return;
        if(put && (item == null)) {
            return;
        }
        if(!item.isEdible()) {
            GameView.showError("This item is not edible");
        }

        if(!put && !player.getFarm().getHouse().getRefrigerator().checkItemByName(itemName.trim())) {
            return;
        }
        if(put){
            player.decreaseEnergy(3);
            player.getBackPack().removeItem(item,1);
            player.getFarm().getHouse().getRefrigerator().putItem(item,1);
            return ;
        }
        if((player.getBackPack().getCapacity() >= player.getBackPack().getMaxCapacity())){
            return;
        }
        player.decreaseEnergy(3);
        item = player.getFarm().getHouse().getRefrigerator().getItemByName(itemName.trim());
        player.getFarm().getHouse().getRefrigerator().pickItem(item,1);
    }
}
