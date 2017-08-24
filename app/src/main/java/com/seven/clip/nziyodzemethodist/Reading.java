package com.seven.clip.nziyodzemethodist;

import android.os.Parcel;
import android.os.Parcelable;

class Reading implements Parcelable {
    private String date;
    private String datename;
    private String english_theme;
    private String shona_theme;
    private String title;
    private String ot,nt,gospel;
    private long psalm;
    private long reading_id;

    protected Reading(Parcel in) {
        date = in.readString();
        datename = in.readString();
        english_theme = in.readString();
        shona_theme = in.readString();
        title = in.readString();
        ot = in.readString();
        nt = in.readString();
        gospel = in.readString();
        psalm = in.readLong();
        reading_id = in.readLong();
    }

    Reading(){}

    public static final Creator<Reading> CREATOR = new Creator<Reading>() {
        @Override
        public Reading createFromParcel(Parcel in) {
            return new Reading(in);
        }

        @Override
        public Reading[] newArray(int size) {
            return new Reading[size];
        }
    };

    public long getReading_id() {
        return reading_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDatename() {
        return datename;
    }

    public void setDatename(String datename) {
        this.datename = datename;
    }

    public String getEnglish_theme() {
        return english_theme;
    }

    public void setEnglish_theme(String english_theme) {
        this.english_theme = english_theme;
    }

    public String getShona_theme() {
        return shona_theme;
    }

    public void setShona_theme(String shona_theme) {
        this.shona_theme = shona_theme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getNt() {
        return nt;
    }

    public void setNt(String nt) {
        this.nt = nt;
    }

    public String getGospel() {
        return gospel;
    }

    public void setGospel(String gospel) {
        this.gospel = gospel;
    }

    public long getPsalm() {
        return psalm;
    }

    public void setReading_id(long reading_id) {
        this.reading_id = reading_id;
    }

    public void setPsalm(long psalm) {
        this.psalm = psalm;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(datename);
        dest.writeString(english_theme);
        dest.writeString(shona_theme);
        dest.writeString(title);
        dest.writeString(ot);
        dest.writeString(nt);
        dest.writeString(gospel);
        dest.writeLong(psalm);
        dest.writeLong(reading_id);
    }
}