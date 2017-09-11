package com.seven.clip.nziyodzemethodist;


class Verse {
    Verse() {
    }


    void setBook_id() {
    }

    void setBook_name() {
    }

    void setChapter() {
    }

    long getVerse() {
        return verse;
    }

    void setVerse(long verse) {
        this.verse = verse;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private long verse;
    private String text;
}
