package com.StardewValley.Networking.Common;

import java.util.ArrayList;

public class Reaction {
    public String text;
    public transient long time = 0;

    public Reaction(String text, long time) {
        this.text = text;
        this.time = time;
    }

    public Reaction(String text) {
        this.text = text;
    }

    public Reaction() {}

    private static ArrayList<String> defaults = new ArrayList<>() {{
        add("😂😂");
        add("Boom💥");
        add("😡🤬");
        add("GG🔥");
    }};

    public static boolean isValid(String s) {
        return s.codePointCount(0, s.length()) <= 10;
    }

    public static void addDefault(String s) {
        defaults.add(0, s);
        defaults.remove(defaults.size() - 1);
    }

    public static ArrayList<String> getDefaults() {
        return defaults;
    }

}
