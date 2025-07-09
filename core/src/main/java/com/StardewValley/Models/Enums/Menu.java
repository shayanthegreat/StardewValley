package com.StardewValley.Models.Enums;

import java.io.Serializable;

public enum Menu implements Serializable {
    RegistrationMenu("registration"),
    MainMenu("main"),
    ProfileMenu("profile"),
    GameMenu("game");

    private final String name;

    Menu(String name) {
        this.name = name;
    }

    public Menu getMenuByName(String name) {
        for(Menu menu : Menu.values()) {
            if(menu.name.equals(name)) {
                return menu;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name + " menu";
    }
}
