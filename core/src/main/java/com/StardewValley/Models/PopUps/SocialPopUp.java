package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.App;
import com.StardewValley.Models.Communication.NPCFriendship;
import com.StardewValley.Models.Communication.NPCQuest;
import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Models.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SocialPopUp extends PopUpMenu {

    public SocialPopUp(Stage stage) {
        super(stage);
        createSocialContent(); // Initial content
    }

    // Called to rebuild the popup when reopened
    public void refresh() {
        popupWindow.clear();                // Clear existing UI
        popupWindow.add(tabs).row();        // Re-add tabs
        createSocialContent();              // Rebuild content
    }

    private void createSocialContent() {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

        Table socialTable = new Table();
        socialTable.defaults().pad(10).left();

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = GameAssetManager.getInstance().MAIN_FONT;
        style.fontColor = Color.WHITE;
        // Map NPC name to their active quest (if any)
        HashMap<String, NPCQuest> questMap = new HashMap<>();
        for (NPCQuest quest : player.getNPCQuests()) {
            if (!quest.isDone()) {
                questMap.put(quest.getNpc().getName(), quest);
            }
        }

        // Friendship rows
        for (NPCFriendship friendship : player.getNPCFriendships()) {
            String npcName = friendship.getNpc().getName();
            int level = friendship.getLevel();
            int xp = friendship.getXp();

            Table row = new Table();
            row.defaults().left().padRight(10);

            Label nameLabel = new Label(npcName, style);
            nameLabel.setFontScale(1.5f);

            Label levelLabel = new Label("Level: " + level + " (" + xp + "/800 XP)", style);
            levelLabel.setFontScale(1.3f);

            row.add(nameLabel);
            row.add(levelLabel);

            socialTable.add(row).left().row();
            socialTable.add().height(10).row(); // spacer
        }

        // Quest section
        ArrayList<NPCQuest> npcQuests = player.getNPCQuests();
        for (NPCQuest quest : npcQuests) {
            Table row = new Table();
            int step = quest.getActiveQuest();
            String task = quest.getActiveQuestString();

            Label npcNameLabel = new Label(quest.getNpc().getName(), style);
            npcNameLabel.setFontScale(1.7f);

            Label questStepLabel = new Label("Quest Step: " + (step + 1) + "/3", style);
            questStepLabel.setFontScale(1.2f);

            row.add(npcNameLabel);
            row.add(questStepLabel).row();

            Label taskLabel = new Label("→ Task: " + task, style);
            taskLabel.setFontScale(1.1f);

            row.add(taskLabel).colspan(3).padLeft(40).row();
            socialTable.add(row).left().row();
            socialTable.add().height(10).row();
        }

        // Scrollable content
        ScrollPane scrollPane = new ScrollPane(socialTable);
        scrollPane.setFadeScrollBars(false);

        popupWindow.add(scrollPane).width(500).height(500).expand().fill();
        popupWindow.pack();
        updateWindowPosition();
    }
}
