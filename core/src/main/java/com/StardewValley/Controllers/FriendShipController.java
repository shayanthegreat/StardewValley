package com.StardewValley.Controllers;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Game;
import com.StardewValley.Models.Item;
import com.StardewValley.Models.Player;
import com.StardewValley.Networking.Client.ClientController;

public class FriendShipController {
    private static FriendShipController instance;
    private FriendShipController() {}
    public static FriendShipController getInstance() {
        if (instance == null) {
            instance = new FriendShipController();
        }
        return instance;
    }

    public void gifting(Item item , String receiver){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        player.getBackPack().removeItem(item,1);
        ClientController.getInstance().sendGift(item.getName(),receiver,App.getInstance().getCurrentUser().getUsername());
    }
}
