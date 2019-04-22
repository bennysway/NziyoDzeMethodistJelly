package com.seven.clip.nziyodzemethodist.models;

public class CustomMenuItem {
    public String subtitle;
    public String title;
    public int itemIconRes;

    public CustomMenuItem(String title, int itemIconRes){
        this.title = title;
        this.itemIconRes = itemIconRes;
    }
    public CustomMenuItem(String title, String subtitle , int itemIconRes){
        this.title = title;
        this.subtitle = subtitle;
        this.itemIconRes = itemIconRes;
    }
}
