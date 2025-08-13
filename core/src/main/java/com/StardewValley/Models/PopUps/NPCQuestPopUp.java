package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.*;
import com.StardewValley.Models.Communication.NPC;
import com.StardewValley.Models.Communication.NPCQuest;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class NPCQuestPopUp {
    private final Stage stage;
    private Window window;
    private Table questsTable;
    private Label statusLabel;
    private NPCQuest selectedQuest;
    private NPCQuest currentQuest;
    private Window tooltipWindow;
    private NPC npc;
    public NPCQuestPopUp(Stage stage, NPC npc) {
        this.stage = stage;
        this.npc = npc;
        createUI(this.npc);
//        createUI(App.getInstance().getCurrentGame().getCurrentPlayer().getNpcs().get(0));
    }

    public void createUI(NPC npc) {
        if (window != null) window.remove();

        Skin skin = GameAssetManager.getInstance().SKIN;

        window = new Window("NPC Quests", skin);
        window.setModal(true);
        window.setMovable(true);
        window.setResizable(false);
        window.align(Align.topLeft);
        window.pad(10);
        window.setColor(new Color(1, 1, 1, 0.95f));

        // Close button
        TextButton closeBtn = new TextButton("X", skin);
        closeBtn.getLabel().setFontScale(0.8f);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        Table header = new Table();
        header.add(new Label("NPC Quests", skin)).expandX().left();
        header.add(closeBtn).right();
        window.add(header).expandX().fillX().row();

        // Quest table
        questsTable = new Table();
        questsTable.top().left().defaults().pad(4).left();

        ScrollPane scrollPane = new ScrollPane(questsTable);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setForceScroll(false, true);

        window.add(scrollPane).width(600).height(400).padBottom(10).row();

        // Status label
        statusLabel = new Label("Select a quest", new Label.LabelStyle(GameAssetManager.getInstance().MAIN_FONT, Color.WHITE));
        window.add(statusLabel).pad(5).row();

        // Action buttons
        Table buttonTable = new Table();

        TextButton receiveButton = new TextButton("Receive Quest", skin);
        receiveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedQuest != null && !selectedQuest.isDone()) {
                    boolean result = selectedQuest.CanReceive();
                    refresh(npc);
                    UIUtils.showTopMessage(stage, skin, result ? "Quest received!" : "Quest is not active or was received before!");
                }
            }
        });

        TextButton finishButton = new TextButton("Finish Quest", skin);
        finishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedQuest != null && selectedQuest.canFinish(App.getInstance().getCurrentGame().getCurrentPlayer())) {
                    UIUtils.showTopMessage(stage, skin, "Quest finished!");
                    refresh(npc);
                } else if (selectedQuest != null) {
                    UIUtils.showTopMessage(stage, skin, "Quest failed to finish!");
                }
            }
        });

        buttonTable.add(receiveButton).padRight(10);
        buttonTable.add(finishButton);

        window.add(buttonTable).pad(5).row();

        window.pack();
        centerWindow();
        stage.addActor(window);

        refresh(npc);
    }

    private void centerWindow() {
        window.setPosition(
            (stage.getWidth() - window.getWidth()) / 2f,
            (stage.getHeight() - window.getHeight()) / 2f
        );
    }

    public void refresh(NPC npc) {
        questsTable.clear();
        currentQuest = null;
        selectedQuest = null;

        ArrayList<NPCQuest> quests = App.getInstance().getCurrentGame().getCurrentPlayer().getNPCQuests();
        for (NPCQuest quest : quests) {
            if (quest.getNpc().getName().equals(npc.getName())) {
                currentQuest = quest;
                break;
            }
        }

        if (currentQuest == null) {
            questsTable.add(new Label("No quests for this NPC.", GameAssetManager.getInstance().SKIN));
            return;
        }

        for (int i = 0; i < 3; i++) {
            Pair<Item, Integer> req = currentQuest.getRequests().get(i);
            String text = req.getKey().getName() + " x" + req.getValue();

            String status = currentQuest.getDoneQuests().size() > i
                ? "DONE BY " + currentQuest.getDoneQuests().get(i).getUser().getUsername()
                : "NOT DONE";
            if(currentQuest.getReceivedQuests().size() > i){
                status += "  received";
            }
            else{
                status += "  not received";
            }
            Label questLabel = new Label(text + " - " + status, GameAssetManager.getInstance().SKIN);
            questLabel.setWrap(true);
            questLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedQuest = currentQuest;
                    statusLabel.setText("Selected: " + text);
                }
            });

            // Add side by side
            questsTable.add(questLabel).pad(5).expandX().fillX();
            questsTable.row();
        }
    }

    public void show() {
        if (window == null || !window.hasParent()) {
            createUI(this.npc);
        } else {
            refresh(this.npc);
            window.setVisible(true);
        }
    }

    public void hide() {
        if (window != null) {
            window.setVisible(false);
            if (tooltipWindow != null) {
                tooltipWindow.remove();
                tooltipWindow = null;
            }
        }
    }

    public Window getWindow() {
        return window;
    }
}
