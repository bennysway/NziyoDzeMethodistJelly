package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import static android.content.Context.MODE_PRIVATE;

//keys include: favlist,reclist,showsplash,color,image,textsize,recordflag,withcaption
//              colorflag,textsizeflag,accflag


public class Data {

    private String list;
    private SharedPreferences.Editor editor;
    private LinkedList<String> storage;

    public Data(Context context, String key) {
        editor = context.getSharedPreferences(key, MODE_PRIVATE).edit();
        SharedPreferences prefs = context.getSharedPreferences(key, MODE_PRIVATE);
        String restoredText = prefs.getString("list", null);
        list = "";
        if (restoredText != null) {
            list = prefs.getString("list", "");
        }
        storage = new LinkedList<>(Arrays.asList(list.split(",")));
        if(key.equals("withcaption")){
            clearKeys();
        }
    }

    void pushBack(String data) {
        storage.add(data);
        commitData();
    }

    void pushFront(String data) {
        storage.addFirst(data);
        commitData();
    }

    String size(){
        return String.valueOf(storage.size());
    }

    private void clearKeys(){
        for(String s:storage){
            if(s.contains("key")){
                storage.clear();
                deleteAll();
                break;
            }
        }

    }

    void delete(String data) {
        if(storage.size()==1){
            deleteAll();
        } else {
            storage.remove(data);
            commitData();
        }

    }


    void deleteAll() {
        list="";
        editor.putString("list", list);
        editor.apply();
    }

    void update(String data) {
        list = data;
        editor.putString("list", list);
        editor.apply();
    }

    boolean find(String data) {
        return storage.contains(data);
    }

    private void commitData(){
        list = "";
        int size = storage.size();
        for(int i=0; i<size; i++)
            list = list + storage.get(i) + ",";
        if(list.charAt(0)==',')
            list=list.replaceFirst(",","");
        editor.putString("list", list);
        editor.apply();
    }

    String get(){
        return list;
    }

}

