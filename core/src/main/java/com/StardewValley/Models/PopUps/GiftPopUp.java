package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.*;
import com.StardewValley.Models.Communication.FriendShip;
import com.StardewValley.Models.Communication.Gift;
import com.StardewValley.Models.Communication.NPC;
import com.StardewValley.Models.Communication.NPCQuest;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class GiftPopUp {
    private final Stage stage;
    private Window window;
    private Table questsTable;
    private Label statusLabel;
    private Window tooltipWindow;

    public GiftPopUp(Stage stage) {
        this.stage = stage;
        createUI();
    }

    public void createUI() {
        if (window != null) window.remove();

        Skin skin = GameAssetManager.getInstance().SKIN;

        window = new Window("Gift Log", skin);
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
        header.add(new Label("Gift Log", skin)).expandX().left();
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

        // Action buttons (if needed)
        Table buttonTable = new Table();
        window.add(buttonTable).pad(5).row();

        window.pack();
        centerWindow();
        stage.addActor(window);

        refresh();
    }

    private void centerWindow() {
        window.setPosition(
            (stage.getWidth() - window.getWidth()) / 2f,
            (stage.getHeight() - window.getHeight()) / 2f
        );
    }

    public void refresh() {
        questsTable.clear();
        Skin skin = GameAssetManager.getInstance().SKIN;

        ArrayList<FriendShip> friendShips = App.getInstance()
            .getCurrentGame()
            .getCurrentPlayer()
            .getFriendShips();

        if (friendShips.isEmpty()) {
            questsTable.add(new Label("No friendship or gift data found.", skin))
                .left().pad(10);
            return;
        }

        for (FriendShip friendShip : friendShips) {
            Player player = friendShip.getPlayer();
            String playerName = (player.getUser() != null && player.getUser().getUsername() != null)
                ? player.getUser().getUsername()
                : "Unknown Player";

            // Friend header row
            Label friendLabel = new Label(playerName, skin, "title");
            friendLabel.setColor(Color.GOLD);
            questsTable.add(friendLabel).left().padTop(10).row();

            ArrayList<Gift> gifts = friendShip.getGiftLog();
            if (gifts.isEmpty()) {
                Label noGiftsLabel = new Label("No gifts given/received.", skin);
                noGiftsLabel.setColor(Color.LIGHT_GRAY);
                questsTable.add(noGiftsLabel).padLeft(20).left().row();
                continue;
            }

            for (Gift gift : gifts) {
                String giftText = gift.getItem().getName() + " x" + gift.getAmount();
                Label giftLabel = new Label("• " + giftText, skin);
                questsTable.add(giftLabel).padLeft(20).left().row();
            }
        }
    }

    public void show() {
        if (window == null || !window.hasParent()) {
            createUI();
        } else {
            refresh();
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
