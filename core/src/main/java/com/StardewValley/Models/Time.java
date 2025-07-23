package com.StardewValley.Models;

import com.StardewValley.Main;
import com.StardewValley.Models.Enums.Season;
import com.StardewValley.Models.Interactions.Messages.GameMessage;
import com.StardewValley.Models.Map.Position;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;

public class Time implements Serializable {

    private int clockWidth = 375;
    private int clockHeight = 275;
//    private Position position = new Position(Gdx.graphics.getWidth()-clockWidth, Gdx.graphics.getHeight()-clockHeight);
    private Sprite clockMain = new Sprite(GameAssetManager.getInstance().CLOCK_MAIN);
    private Sprite clockArrow = new Sprite(GameAssetManager.getInstance().CLOCK_ARROW);
    private Sprite seasonSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[1]);
    private Sprite weatherSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[6]);
    private float clockScale = 1f;
    private int hour;
    private int day;
    private Season season;

    public Time() {
        hour = 9;
        day = 1;
        season = Season.spring;
    }
    public Time (Time time) {
        hour = time.hour;
        day = time.day;
        season = time.season;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public Season getSeason() {
        return season;
    }

    public String calculateWeekDay(){
        switch (getDay() % 7){
            case 0: {
                return "saturday";
            }
            case 1: {
                return "sunday";
            }
            case 2: {
                return "monday";
            }
            case 3: {
                return "tuesday";
            }
            case 4: {
                return "wednesday";
            }
            case 5: {
                return "thursday";
            }
            case 6: {
                return "friday";
            }
            default: {
                return "error";
            }
        }
    }

    public void nextSeason(){
        season = season.getNext();
    }

    public void nextDay() {
        if(day == 28) {
            day = 1;
        }
        else {
            day++;
        }
    }

    public void nextHour() {
        if(hour == 22) {
            hour = 9;
        }
        else {
            hour++;
        }
    }
    public static int getDayDifference(Time time1, Time time2){
        int seasonDiff = time2.getSeason().ordinal() - time1.getSeason().ordinal();
        int dayDiff = time2.getDay() - time1.getDay();
        return seasonDiff * 28 + dayDiff;
    }
    public int calDaysDifference(Time time){
        Time time1 = Time.minimum(this, time);
        Time time2 = Time.maximum(this, time);

        int seasonDiff = time2.getSeason().ordinal() - time1.getSeason().ordinal();
        int dayDiff = time2.getDay() - time1.getDay();

        return seasonDiff * 28 + dayDiff;
    }

    public int calHoursDifference(Time time){
        int dayDiff = calDaysDifference(time);
        int hourDiff = Time.maximum(this, time).getHour() - Time.minimum(this, time).getHour();

        return dayDiff * 24 + hourDiff;
    }

    @Override
    public String toString() {
        return season.name() + " / " + day + "th day / " + hour;
    }

    public static Time minimum(Time time1, Time time2){
        if(time1.getSeason().ordinal() != time2.getSeason().ordinal()){
            if(time1.getSeason().ordinal() < time2.getSeason().ordinal()){
                return time1;
            }
            return time2;
        }
        else if(time1.getDay() != time2.getDay()){
            if(time1.getDay() < time2.getDay()){
                return time1;
            }
            return time2;
        }
        else{
            if(time1.getHour() < time2.getHour()){
                return time1;
            }
            return time2;
        }
    }

    public static Time maximum(Time time1, Time time2){
        Time temp = minimum(time1, time2);
        if(time1.equals(temp)){
            return time2;
        }
        return time1;
    }

    public static Time addHour(Time time, int hour){
        Time newTime = new Time();
        newTime.hour = time.getHour()+hour;
        newTime.day = time.getDay();
        if(newTime.hour > 22){
            newTime.hour -= 22;
            newTime.hour += 9;
            newTime.day ++;
        }
        newTime.season = time.getSeason();
        return newTime;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Time){
            Time time = (Time)obj;
            boolean f1 = getHour() == time.getHour();
            boolean f2 = getDay() == time.getDay();
            boolean f3 = getSeason() == time.getSeason();
            return f1 && f2 && f3;
        }
        return false;
    }


    public float getArrowRotation() {
        final int MIN_GAME_HOUR = 9;
        final int MAX_GAME_HOUR = 22;
        final int TOTAL_DISPLAY_HOURS = MAX_GAME_HOUR - MIN_GAME_HOUR; // 22 - 9 = 13 intervals between hours

        // Define the rotation range
        final float MIN_ROTATION_DEGREES = 0f;
        final float MAX_ROTATION_DEGREES = 180f;
        final float TOTAL_ROTATION_RANGE = MAX_ROTATION_DEGREES - MIN_ROTATION_DEGREES; // 180 degrees
        float relativeHour = (float)hour - MIN_GAME_HOUR;
        float percentage = relativeHour / TOTAL_DISPLAY_HOURS; // Divide by intervals, not total steps
        float rotation = MIN_ROTATION_DEGREES + (percentage * TOTAL_ROTATION_RANGE);

        float libgdxRotation = 180 - rotation; // Adjust for LibGDX's default 0-degree being "right"

        return libgdxRotation;
    }
    public void updateBatch(Batch batch, Position ignoredPosition) {
        updateWeather();
        updateSeason();

        float centerX = Gdx.graphics.getWidth() / 2f - clockWidth / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f - clockHeight / 2f;

        clockMain.setSize(clockWidth, clockHeight);
        clockMain.setPosition(centerX, centerY);
        clockMain.draw(batch);

        clockArrow.setSize(clockWidth * 0.1f, clockHeight * 0.28f);
        clockArrow.setRotation(getArrowRotation());
        clockArrow.setPosition(centerX + 100, centerY + 180);
        clockArrow.setOrigin(clockWidth * 0.1f * clockScale / 2f - 1, 0f);
        clockArrow.draw(batch);

        weatherSprite.setSize(clockWidth * 0.180f, clockHeight * 0.200f);
        weatherSprite.setPosition(centerX + 0.405f * clockWidth, centerY + 0.55f * clockHeight);
        weatherSprite.draw(batch);

        seasonSprite.setSize(weatherSprite.getWidth(), weatherSprite.getHeight());
        seasonSprite.setPosition(centerX + 0.405f * clockWidth + 0.33f * clockWidth, centerY + 0.55f * clockHeight);
        seasonSprite.draw(batch);

        GameAssetManager.getInstance().MAIN_FONT.setColor(Color.WHITE);
        GameAssetManager.getInstance().MAIN_FONT.getData().setScale(2f);

        String timeText = String.format("%d:00 %s",
            hour <= 12 ? hour : hour - 12,
            hour < 12 ? "AM" : "PM");
        String dateText = String.format("%s %d, %s",
            season.name().substring(0, 1).toUpperCase() +
                season.name().substring(1),
            day,
            calculateWeekDay());

        GlyphLayout timeLayout = new GlyphLayout(GameAssetManager.getInstance().MAIN_FONT, timeText);
        GameAssetManager.getInstance().MAIN_FONT.draw(batch, dateText,
            centerX + clockWidth / 2 - timeLayout.width / 2,
            centerY + clockHeight * 0.9f);

        GlyphLayout dateLayout = new GlyphLayout(GameAssetManager.getInstance().MAIN_FONT, dateText);
        GameAssetManager.getInstance().MAIN_FONT.draw(batch, timeText,
            centerX + clockWidth / 2,
            centerY + clockHeight * 0.5f);
    }


    public void updateWeather(){
//        switch (App.getInstance().getCurrentGame().getTodayWeather()) {
//            case rain -> weatherSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[6]);
//            case snow -> weatherSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[9]);
//            case storm -> weatherSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[11]);
//            case sunny -> weatherSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[7]);
//        }
    }

    public void updateSeason(){
        switch (season) {
            case spring -> seasonSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[0]);
            case summer -> seasonSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[1]);
            case fall -> seasonSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[2]);
            case winter -> seasonSprite = new Sprite(GameAssetManager.getInstance().ClOCK_MANNERS[4]);
        }
    }

}
