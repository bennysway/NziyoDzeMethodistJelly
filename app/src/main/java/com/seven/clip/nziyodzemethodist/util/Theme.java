package com.seven.clip.nziyodzemethodist.util;

import static com.seven.clip.nziyodzemethodist.util.ColorThemes.getDarkerColor;
import static com.seven.clip.nziyodzemethodist.util.ColorThemes.getLighterColor;

public class Theme {
    private String themeName = "Default";
    private boolean isDayMode;
    private ColorMode dayMode;
    private ColorMode nightMode;
    private class ColorMode{
        private String iconColor, iconBackgroundColor, textColor, textBackgroundColor, contextColor, contextBackgroundColor;
    }
    //Getters
    public String getIconColor() {
        return isDayMode? dayMode.iconColor : nightMode.iconColor;
    }
    public String getIconBackgroundColor() {
        return isDayMode? dayMode.iconBackgroundColor : nightMode.iconBackgroundColor;
    }
    public String getTextColor() {
        return isDayMode? dayMode.textColor : nightMode.textColor;
    }
    public String getTextBackgroundColor() {
        return isDayMode? dayMode.textBackgroundColor : nightMode.textBackgroundColor;
    }
    public String getContextColor() {
        return isDayMode? dayMode.contextColor : nightMode.contextColor;
    }
    public String getContextBackgroundColor() {
        return isDayMode? dayMode.contextBackgroundColor : nightMode.contextBackgroundColor;
    }
    public String getThemeName(){
        return themeName;
    }
    //Setters
    public void setDayMode(boolean dayMode) {
        isDayMode = dayMode;
    }
    public boolean isInDayMode() {
        return isDayMode;
    }
    public void setThemeName(String name){
        this.themeName = name;
    }

    public Theme(String color) {
        String white = "#ffffff";
        String black = "#000000";
        dayMode = new ColorMode();
        nightMode = new ColorMode();
        dayMode.iconBackgroundColor = color;
        //Constants
        dayMode.iconColor = white;
        dayMode.textColor = black;
        dayMode.textBackgroundColor = white;
        dayMode.contextColor = getDarkerColor(color);
        dayMode.contextBackgroundColor = getLighterColor(color);

        nightMode.iconBackgroundColor = "#00000000";
        nightMode.iconColor = color;
        nightMode.textColor = white;
        nightMode.textBackgroundColor = black;
        nightMode.contextColor = getLighterColor(color);
        nightMode.contextBackgroundColor = getDarkerColor(color);
        isDayMode = true;
    }



    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Theme)) return false;
        Theme theme = (Theme) object;
        return this.getIconBackgroundColor().equals(theme.getIconBackgroundColor()) && this.isDayMode == theme.isDayMode;
    }
}