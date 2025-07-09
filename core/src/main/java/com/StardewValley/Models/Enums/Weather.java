package com.StardewValley.Models.Enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum Weather implements Serializable {
    sunny(new ArrayList<>(Arrays.asList(Season.values()))),
    rain(new ArrayList<>(Arrays.asList(Season.spring, Season.summer, Season.fall))),
    storm(new ArrayList<>(Arrays.asList(Season.spring, Season.summer, Season.fall))),
    snow(new ArrayList<>(Arrays.asList(Season.winter)));
    ;

    private final ArrayList<Season> seasons;

    Weather(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public static Weather getRandomWeather(Season season) {
        List<Weather> possible = getPossibleWeathers(season);
        return possible.get(ThreadLocalRandom.current().nextInt(possible.size()));
    }

    public static List<Weather> getPossibleWeathers(Season season) {
        List<Weather> possible = new ArrayList<>();
        for (Weather weather : values()) {
            if (weather.containsSeason(season)) {
                possible.add(weather);
            }
        }
        return possible;
    }
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public boolean containsSeason(Season season) {
        return seasons.contains(season);
    }

    public static Weather getWeatherType(String weather) {
        return switch (weather) {
            case "sunny" -> Weather.sunny;
            case "rain" -> Weather.rain;
            case "storm" -> Weather.storm;
            case "snow" -> Weather.snow;
            default -> null;
        };
    }
}
