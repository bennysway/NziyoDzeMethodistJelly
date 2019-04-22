package com.seven.clip.nziyodzemethodist.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reading implements Parcelable {
    private String date;
    private String monthId;
    private String englishTheme;
    private String shonaTheme;
    private String title;
    private String ot,nt,gospel;
    private long psalm;
    private long time;
    private String locked;
    private Reading(Parcel in) {
        date = in.readString();
        monthId = in.readString();
        englishTheme = in.readString();
        shonaTheme = in.readString();
        title = in.readString();
        ot = in.readString();
        nt = in.readString();
        gospel = in.readString();
        psalm = in.readLong();
        time = in.readLong();
        locked = in.readString();
    }

    public Reading(){}

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

    public String getMonthId() {
        return monthId;
    }

    public void setMonthId(String monthId) {
        this.monthId = monthId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnglishTheme() {
        return englishTheme;
    }

    public void setEnglishTheme(String englishTheme) {
        this.englishTheme = englishTheme;
    }

    public String getShonaTheme() {
        return shonaTheme;
    }

    public void setShonaTheme(String shonaTheme) {
        this.shonaTheme = shonaTheme;
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

    public void setPsalm(long psalm) {
        this.psalm = psalm;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(monthId);
        dest.writeString(englishTheme);
        dest.writeString(shonaTheme);
        dest.writeString(title);
        dest.writeString(ot);
        dest.writeString(nt);
        dest.writeString(gospel);
        dest.writeLong(psalm);
        dest.writeLong(time);
        dest.writeString(locked);
    }
}