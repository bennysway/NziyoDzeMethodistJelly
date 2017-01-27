package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

//keys include: favlist,reclist,showsplash,color,image,textsize,recordflag,withcaptions
//              colorflag,textsizeflag,


public class Data {

    private String list;
    private SharedPreferences.Editor editor;

    Data(Context context, String key) {
        editor = context.getSharedPreferences(key, MODE_PRIVATE).edit();
        SharedPreferences prefs = context.getSharedPreferences(key, MODE_PRIVATE);
        String restoredText = prefs.getString("list", null);
        list = "";
        if (restoredText != null) {
            list = prefs.getString("list", "");
        }
    }

    String pushBack(String data) {
        list = list + data + ",";
        editor.putString("list", list);
        editor.apply();
        return list;
    }

    String pushFront(String data) {
        list = data + "," + list;
        editor.putString("list", list);
        editor.apply();
        return list;
    }

    void delete(String data) {
        list = list.replace(data + ",", "");
        editor.putString("list", list);
        editor.apply();
    }

    void deleteRecord(String data) {
        list = list.replace(data, "");
        editor.putString("list", list);
        editor.apply();
    }

    void deleteCaption(String data) {
        list = list.replace(data, "");
        editor.putString("list", list);
        editor.apply();
    }

    void deleteAll() {
        list = "";
        editor.putString("list", list);
        editor.apply();
    }

    void update(String data) {
        list = data;
        editor.putString("list", list);
        editor.apply();
    }

    boolean find(String data) {
        String search = "." + list;
        data += ",";
        return search.indexOf(data) > 0;
    }

    String get(){
        return list;
    }
}

