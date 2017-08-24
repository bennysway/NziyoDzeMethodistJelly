package com.seven.clip.nziyodzemethodist;

/**
 * nothing special here
 * Created by bennysway on 28.12.16.
 */

class SearchResults {
    private String title = "";
    private String caption = "";
    private String hymnNum = "";
    private boolean isInEnglish = false;

    boolean getIsInEnglish() {
        return isInEnglish;
    }

    void setInEnglish(boolean inEnglish) {
        isInEnglish = inEnglish;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    void setCaption(String caption) {
        this.caption = caption;
    }

    String getCaption() {
        return caption;
    }
    public void setHymnNum(String hymnNum) {
        this.hymnNum = hymnNum;
    }

    public String getHymnNum() {
        return hymnNum;
    }
}
