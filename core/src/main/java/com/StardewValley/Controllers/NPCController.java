package com.StardewValley.Controllers;

import com.StardewValley.Models.*;
import com.StardewValley.Models.Communication.NPC;
import com.StardewValley.Models.Tools.Tool;
import com.StardewValley.Views.GameView;

public class NPCController {
    private static NPCController instance;
    private NPCController(){}
    public static NPCController getInstance(){
        if (instance == null){
            instance = new NPCController();
        }
        return instance;
    }

    public void update(){
        printNPCs();
        getDialogNotif();
    }

    private void printNPCs(){
        Player  player = App.getInstance().getCurrentGame().getCurrentPlayer();
        for (int i = 0; i < player.getNpcs().size(); i++) {
            NPC npc = player.getNpcs().get(i);
            Camera.getInstance().print(GameAssetManager.getInstance().NPC_HOUSES.get(i), npc.getHousePosition().x, npc.getHousePosition().y, 1, 1);
            Camera.getInstance().print(npc.getTexture(),npc.getPosition().x,npc.getPosition().y,1,1);
        }
    }

    private void getDialogNotif(){
        Game game = App.getInstance().getCurrentGame();
        Player player = game.getCurrentPlayer();
        for (NPC npc : player.getNpcs()) {
            String dialogue = npc.getDialogue();
            if(dialogue != null){
                Camera.getInstance().print(GameAssetManager.getInstance().DIALOG,npc.getPosition().x,npc.getPosition().y+1,1,1);
            }
        }
    }

    public void printDialog(NPC npc){
        GameView.showError(npc.getDialogue());
    }

    public void giftNPC(NPC npc,Item item){

        App app = App.getInstance();
        Game game = app.getCurrentGame();
        Player player = game.getCurrentPlayer();

        if(item instanceof Tool){
            GameView.showError("You can not gift tool to NPC");
            return;
        }

        player.getBackPack().removeItem(item,1);

        if(npc.checkFavorite(item)){
            player.getNpcFriendshipByName(npc.getName()).increaseXp(200);

        }
        else{
            player.getNpcFriendshipByName(npc.getName()).increaseXp(50);
        }
    }


}
