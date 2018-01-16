package com.seven.clip.nziyodzemethodist;

import android.content.Context;


class Hymns {
    private Context context;

    Hymns(Context cxt) {
        this.context=cxt;
    }

    public String getTitle(String num, boolean isEnglish) {
        String t = "hymn" + num + "firstline";
        if(isEnglish) t = "en" + t;
        return context.getString(getResourceId(t,"string",context.getPackageName()));
    }

    String[] getCaption(String num) {
        String c = "hymn" + num + "caption";
        int captionResourceId = getResourceId(c,"array",context.getPackageName());
        return context.getResources().getStringArray(captionResourceId);
    }


    String[] getHymn(String num, boolean isEnglish) {
        String h = "hymn" + num ;
        if(isEnglish) h = "en" + h;
        int resourceId = getResourceId(h,"array",context.getPackageName());
        return context.getResources().getStringArray(resourceId);
    }
    String[] getAllHymns(){
        String c = "hymnfirstlines";
        int captionResourceId = getResourceId(c,"array",context.getPackageName());
        return context.getResources().getStringArray(captionResourceId);
    }

    boolean hasChorus(String num, boolean isEnglish){
        return getHymn(num,isEnglish)[0].contains("chorus");
    }

    String getChorus(String num,boolean isEnglish){
        if (hasChorus(num,isEnglish))
            return getHymn(num,isEnglish)[0].substring(getHymn(num,isEnglish)[0].indexOf("chorus"));
        else
            return "No chorus Available";
    }



    private int getResourceId(String pVariableName, String pResourcename, String pPackageName){
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
