package com.StardewValley.Models.PopUps;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import javax.swing.*;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MusicListPopUp extends PopUpMenu {

    private Table musicTable;

    public MusicListPopUp(Stage stage) {
        super(stage);

        createPopupMenu();
    }

    protected void createPopupMenu() {
        popupWindow = new Window("Music Library", skin);
        popupWindow.setModal(true);
        popupWindow.setMovable(false);
        popupWindow.getTitleLabel().setColor(Color.BROWN);
        popupWindow.setColor(Color.LIGHT_GRAY);
        popupWindow.pad(10);

        Table mainTable = new Table();
        mainTable.top().left();
        mainTable.defaults().pad(5);

        // Upload button
        TextButton uploadButton = new TextButton("Upload Music", skin);
        uploadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openFileChooserAndUpload();
            }
        });
        mainTable.add(uploadButton).width(200).padBottom(10).row();

        // Scrollable music list
        musicTable = new Table();
        ScrollPane scrollPane = new ScrollPane(musicTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        mainTable.add(scrollPane).width(350).height(300).row();

        // Tabs (close button)
        tabs.clear();
        tabs.top().left();
        tabs.defaults().size(70, 70).padRight(4);
        tabs.add(createCloseTabImage(GameAssetManager.getInstance().EXIT_BUTTON));

        popupWindow.add(tabs).row();
        popupWindow.add(mainTable).pad(10);
        popupWindow.pack();
        popupWindow.setVisible(false);

        updateWindowPosition();
        stage.addActor(popupWindow);

        refreshMusicListUI();
    }

    private void refreshMusicListUI() {
        musicTable.clear();

        HashMap<String, ArrayList<String>> musicList = ClientData.getInstance().musicList;

        for (Map.Entry<String, ArrayList<String>> entry : musicList.entrySet()) {
            String uploader = entry.getKey();
            ArrayList<String> files = entry.getValue();

            // Section label for uploader
            Label uploaderLabel = new Label("Uploader: " + uploader, skin);
            uploaderLabel.setColor(Color.GOLD);
            musicTable.add(uploaderLabel).left().padTop(10).row();

            for (String filename : files) {
                TextButton fileButton = new TextButton(filename, skin);
                fileButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        ClientController.getInstance().playMusic(uploader, filename);
                    }
                });
                musicTable.add(fileButton).fillX().row();
            }
        }
    }

    @Override
    public void updateWindowPosition() {
        float x = Gdx.graphics.getWidth() / 2f - popupWindow.getWidth() / 2f;
        float y = Gdx.graphics.getHeight() / 2f - popupWindow.getHeight() / 2f;
        popupWindow.setPosition(x, y);
    }

    @Override
    public void show() {
        refreshMusicListUI();
        ClientController.getInstance().refreshMusicList();
        updateWindowPosition();
        super.show();
    }

    Image createCloseTabImage(Texture texture) {
        Image closeTab = new Image(texture);
        closeTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                PopUpManager.getInstance(stage).hide();
            }
        });
        return closeTab;
    }


    private void openFileChooserAndUpload() {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Music File to Upload");

            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Audio Files", "mp3", "wav", "ogg"));

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ClientController.getInstance().uploadMusic(selectedFile);
            }
        });
    }

}
