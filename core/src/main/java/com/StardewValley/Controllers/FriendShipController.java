package com.StardewValley.Controllers;

import com.StardewValley.Models.*;
import com.StardewValley.Models.Communication.FriendShip;
import com.StardewValley.Models.Communication.Gift;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Views.GameView;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
        Player player1 = App.getInstance().getCurrentGame().getPlayerByUsername(receiver);
//        if(player.getFriendShipByPlayer(player1).getLevel() == 0){
//            GameView.showError("your friendship level is low'");
//            return;
//        }
        player.getFriendShipByPlayer(player1).increaseXp(20);
        player.getBackPack().removeItem(item,1);
        ClientController.getInstance().sendGift(item.getName(),receiver,App.getInstance().getCurrentUser().getUsername());
    }

    public void showNumberChooser(String title, Stage stage , Player receiver , Player sender, Item item) {
        final Skin skin = GameAssetManager.getInstance().getSkin();

        final SelectBox<Integer> selectBox = new SelectBox<>(skin);
        selectBox.setItems(1, 2, 3, 4, 5);

        final Dialog dialog = new Dialog(title, skin) {
            @Override
            protected void result(Object object) {
                if (Boolean.TRUE.equals(object)) {
//                    Gift gift = new Gift(item,1);
//                    receiver.getFriendShipByPlayer(receiver).addGift(gift);
//                    gift.setRate(selectBox.getSelected());
                }
            }
        };

        dialog.getContentTable().add(selectBox).pad(10);
        dialog.button("OK", true);   // Pass a simple flag for OK
        dialog.button("Cancel", false);

        dialog.show(stage);
    }


}
