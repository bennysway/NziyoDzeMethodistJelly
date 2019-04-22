package com.seven.clip.nziyodzemethodist.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.seven.clip.nziyodzemethodist.R;

import java.util.EnumSet;

import static android.graphics.Color.parseColor;

public class ColorThemes {
    private static String cool = "#40c4ff";

    public static Theme getTheme(Context context){
        Theme result;
        String themeJsonString;
        themeJsonString = context.getSharedPreferences("theme",0).getString("data","");
        if(!themeJsonString.equals("")){
            result = (new Gson()).fromJson(themeJsonString, Theme.class);
        } else {
            int color = context.getResources().getColor(R.color.cool);
            String strColor = String.format("#%06X", 0xFFFFFF & color);
            result = new Theme(strColor);
            updateThemePreference(context,result);
        }
        return result;
    }

    private static void updateThemePreference(final Context context, final Theme theme){
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                String json = new Gson().toJson(theme);
                SharedPreferences.Editor editor= context.getSharedPreferences("theme",0).edit();
                editor.putString("data", json);
                editor.apply();
                editor.commit();
                return null;
            }
        }.execute(context);
    }


    public static void setMode(Context context, Theme theme){
        theme.setDayMode(!theme.isInDayMode());
        updateThemePreference(context,theme);
    }

    private static boolean isColorDark(String test) {
        int color = parseColor(test);
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return darkness >= 0.333;
    }

    public static void addBackgroundFilter(View view, String color){
        view.getBackground().mutate().setColorFilter(parseColor(color), PorterDuff.Mode.SRC_ATOP);
    }
    public static void addDrawableFilter(Drawable drawable, String color){
        drawable.setColorFilter(parseColor(color),PorterDuff.Mode.SRC_ATOP);
    }
    public static int getLighterColor(int color){
        float factor = .2f;
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }
    public static int getDarkerColor(int color){
        float factor = .15f;
        int red = (int) ((Color.red(color) * (1 - factor) / 255 - factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 - factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 - factor) * 255);
        red = Math.max(red,0);
        blue = Math.max(blue,0);
        green = Math.max(green,0);
        return Color.argb(Color.alpha(color), red, green, blue);
    }
    public static int getLowerHue(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] += (hsv[0] * 0.2f);
        color = Color.HSVToColor(hsv);
        return color;
    }
    public static int getHigherHue(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = 1.0f - 0.95f * (1.0f - hsv[0]);
        color = Color.HSVToColor(hsv);
        return color;
    }
    public static String getLighterColor(String color){
        return String.format("#%06X", (0xFFFFFF & getLighterColor(parseColor(color))));
    }
    public static String getDarkerColor(String color){
        return String.format("#%06X", (0xFFFFFF & getDarkerColor(parseColor(color))));

    }
    public static String getLowerHue(String color) {
        return String.format("#%06X", (0xFFFFFF & getLowerHue(parseColor(color))));

    }
    public static String getHigherHue(String color) {
        return String.format("#%06X", (0xFFFFFF & getHigherHue(parseColor(color))));

    }


}
