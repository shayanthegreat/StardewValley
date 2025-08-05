package com.StardewValley;

import com.StardewValley.Models.GameAssetManager;
import com.StardewValley.Networking.Client.ClientController;
import com.StardewValley.Networking.Client.ClientData;
import com.StardewValley.Views.*;
import com.StardewValley.Models.Time;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.net.ServerSocket;
import java.util.Scanner;

public class Main extends Game {
    private SpriteBatch batch;
    private static Main main;
    private OrthographicCamera camera;


    private Main() {
    }
    public static Main getInstance() {
        if (main == null) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter Ip: ");
                String selfIp = sc.nextLine();
                System.out.print("Enter Port: ");
                int selfPort = Integer.parseInt(sc.nextLine());
                System.out.print("Enter Server IP: ");
                String serverIp = sc.nextLine();
                System.out.print("Enter Server Port: ");
                int serverPort = Integer.parseInt(sc.nextLine());
                ClientController.getInstance().initConnection(selfIp, selfPort, serverIp, serverPort);
            } catch (Exception e) {
                System.err.println("Could not start the server");
                e.printStackTrace();
            }
            main = new Main();
        }
        return main;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new RegistrationMenu(GameAssetManager.getInstance().getSkin()));//new MainMenu(GameAssetManager.getInstance().getSkin()));//new GameMenu(GameAssetManager.getInstance().getSkin()));//new RegistrationMenu(GameAssetManager.getInstance().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
