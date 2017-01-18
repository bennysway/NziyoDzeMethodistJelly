package com.seven.clip.nziyodzemethodist;

/**
 * Created by bennysway on 28.12.16.
 */

public class CaptionStorage {
    private String title = "";
    private String type = "";
    private String path = "";
    private String hymnNum = "";

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getHymnNum() {
        return hymnNum;
    }

    public void setHymnNum(String hymnNum) {
        this.hymnNum = hymnNum;
    }
}
