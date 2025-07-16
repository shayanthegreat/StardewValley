package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Color;

public class SkillPopUp extends PopUpMenu {

    public SkillPopUp(Stage stage) {
        super(stage);
        createSkillContent();
    }

    private void createSkillContent() {
        Table skillTable = new Table();
        skillTable.defaults().pad(10).left();

        Texture farmingIcon = GameAssetManager.getInstance().FARMING_SKILL_ICON;
        Texture fishingIcon = GameAssetManager.getInstance().FISHING_SKILL_ICON;
        Texture foragingIcon = GameAssetManager.getInstance().FORAGING_SKILL_ICON;
        Texture miningIcon = GameAssetManager.getInstance().MINING_SKILL_ICON;

        // TODO get levels from CurrentPlayer!;
        addSkillRow(skillTable, "Farming", farmingIcon, 3, "Increases crop yield and farming efficiency.");
        addSkillRow(skillTable, "Fishing", fishingIcon, 2, "Improves chance and speed of catching fish.");
        addSkillRow(skillTable, "Foraging", foragingIcon, 4, "Boosts foraging items and resource drops.");
        addSkillRow(skillTable, "Mining", miningIcon, 1, "Speeds up mining and boosts ore collection.");

        ScrollPane scrollPane = new ScrollPane(skillTable);
        scrollPane.setFadeScrollBars(false);

        popupWindow.add(scrollPane).width(400).height(500).expand().fill();
        popupWindow.pack();
        updateWindowPosition();
    }

    private void addSkillRow(Table table, String name, Texture icon, int level, String hoverText) {
        Image iconImage = new Image(icon);
        iconImage.setSize(80, 80);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = GameAssetManager.getInstance().MAIN_FONT;
        style.fontColor = Color.WHITE;

        Label nameLabel = new Label(name, style);
        Label levelLabel = new Label("Level " + level, style);
        nameLabel.setFontScale(2f);
        levelLabel.setFontScale(2f);

        Table row = new Table();
        row.add(iconImage).size(80, 80).padRight(10);
        row.add(nameLabel).padRight(10);
        row.add(levelLabel);

        // Tooltip-like label
        final Label detailLabel = new Label(hoverText, style);
        detailLabel.setWrap(true);
        detailLabel.setVisible(false);
        detailLabel.setColor(Color.LIGHT_GRAY);
        detailLabel.setFontScale(1.5f);

        // Background highlight on hover
        row.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                row.setColor(Color.DARK_GRAY);
                row.setBackground(GameAssetManager.getInstance().SKIN.newDrawable("white", new Color(0.2f, 0.2f, 0.2f, 0.5f)));
                detailLabel.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                row.setColor(Color.WHITE);
                detailLabel.setVisible(false);
            }
        });

        table.add(row).row();
        table.add(detailLabel).padLeft(100).width(280).row(); // align with icon
    }
}
