package com.seven.clip.nziyodzemethodist.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import static android.graphics.Color.parseColor;

public class ColorThemes {

    private static String white = "#ffffff";
    private static String black = "#000000";
    private static String cool = "#40c4ff";
    private static String dimWhite ="#fafafa";
    private static String dimBlack = "#101010";

    public static String getColor(Context context, Util.Element element, Util.Component component) {
        String mode = context.getSharedPreferences("color",0).getString("mode","day");
        boolean isDay = mode.equals("day");
        String defaultColor = "#40c4ff";
        switch (component){
            case icon:
                defaultColor = isDay ? white : cool;
                break;
            case iconBackground:
                defaultColor = isDay ? cool : black;
                break;
            case text:
                defaultColor = isDay ? black : white;
                break;
            case context:
                defaultColor = isDay ? black : white;
                break;
            case contextBackground:
                defaultColor = isDay ? white : black;
                break;
            case textBackground:
                defaultColor = isDay ? dimWhite : dimBlack;
                break;
            case background:
                defaultColor = isDay ? white : black;
        }
        return context.getSharedPreferences("color",0).getString(element.name() +":"+ component.name() + ":" + mode, defaultColor);
    }
    public static void setMode(Context context, boolean isDayMode){
        SharedPreferences.Editor editor= context.getSharedPreferences("color",0).edit();
        editor.putString("mode", isDayMode ? "day" : "night");
        editor.apply();
        editor.commit();
    }

    public static void setColor(Context context, Util.Element element, String color) {
        boolean isColorDark = isColorDark(color);
        SharedPreferences.Editor editor= context.getSharedPreferences("color",0).edit();
        //Set Day colors
        editor.putString(element.name() + ":icon:day",isColorDark ? white : black);
        editor.putString(element.name() + ":iconBackground:day",color);
        editor.putString(element.name() + ":text:day",black);
        editor.putString(element.name() + ":textBackground:day",white);
        editor.putString(element.name() + ":context:day",isColorDark ? white : black);
        editor.putString(element.name() + ":contextBackground:day",color);
        editor.putString(element.name() + ":background:day",white);
        //Set Night colors
        editor.putString(element.name() + ":icon:night",isColorDark ? black : color);
        editor.putString(element.name() + ":iconBackground:night",isColorDark ? color : black);
        editor.putString(element.name() + ":text:night",white);
        editor.putString(element.name() + ":textBackground:night",black);
        editor.putString(element.name() + ":context:night",isColorDark ? white : color);
        editor.putString(element.name() + ":contextBackground:night",black);
        editor.putString(element.name() + ":background:night",black);
        //Save
        editor.apply();
        editor.commit();
    }

    public static void setDefault(Context context, Util.Element element){
        SharedPreferences.Editor editor= context.getSharedPreferences("color",0).edit();
        editor.remove(element.name() + ":icon:day");
        editor.remove(element.name() + ":iconBackground:day");
        editor.remove(element.name() + ":text:day");
        editor.remove(element.name() + ":textBackground:day");
        editor.remove(element.name() + ":context:day");
        editor.remove(element.name() + ":contextBackground:day");
        editor.remove(element.name() + ":background:day");
        editor.remove(element.name() + ":icon:night");
        editor.remove(element.name() + ":iconBackground:night");
        editor.remove(element.name() + ":text:night");
        editor.remove(element.name() + ":textBackground:night");
        editor.remove(element.name() + ":context:night");
        editor.remove(element.name() + ":contextBackground:night");
        editor.remove(element.name() + ":background:night");
        editor.apply();
        editor.commit();
    }

    public static void setDefaultAll(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("color",0).edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    private static boolean isColorDark(String test) {
        int color = parseColor(test);
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return darkness >= 0.333;
    }

}
