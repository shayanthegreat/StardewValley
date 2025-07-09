package com.StardewValley.Models;

import com.StardewValley.Models.Enums.Menu;

import java.io.*;
import java.util.ArrayList;

public class App implements Serializable {

    private static App instance;

    private App() {
    }

    public static App getInstance() {
        if (instance == null) {
            instance = App.loadApp();
        }
        return instance;
    }

    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Game> games = new ArrayList<>();
    private Menu currentMenu = Menu.RegistrationMenu;
    private User currentUser;
    private Game currentGame;
    private User currentGameStarter;
    private boolean stayLoggedIn;

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public User getCurrentGameStarter() {
        return currentGameStarter;
    }

    public void setCurrentGameStarter(User currentGameStarter) {
        this.currentGameStarter = currentGameStarter;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void removeLastUser() {
        users.removeLast();
    }

    public User getLastUser() {
        return users.getLast();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public static App loadApp() {
        File file = new File("app.ser");

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (App) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return new App();
            }
        } else {
            return new App();
        }
    }

    public void saveApp() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("app.ser")
        )) {
            oos.writeObject(instance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        instance = null;
    }

}
