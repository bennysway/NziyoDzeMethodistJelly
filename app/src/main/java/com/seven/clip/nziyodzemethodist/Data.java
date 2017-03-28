package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

//keys include: favlist,reclist,showsplash,color,image,textsize,recordflag,withcaption
//              colorflag,textsizeflag,accflag,faviterator,reciterator,


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
        else if(key.equals("favlist")){
            Collections.sort(storage, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
                }
            });
            commitData();
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
        editor.putString("list", check(list));
        editor.apply();
    }

    boolean find(String data) {
        return storage.contains(data);
    }

    private String check(String test){
        if(test.length()>0){
            if(test.charAt(0)==',')
                test=test.replaceFirst(",","");
            int listLength = test.length();
            //if(test.charAt(listLength-2)==','&&listLength>2)
                //test=test.substring(0,listLength-1);
            if(test.contains(",,"))
                test=test.replace(",,",",");
        }
        return test;

    }

    private void commitData(){
        list = "";
        int size = storage.size();
        for(int i=0; i<size; i++)
            list = list + storage.get(i) + ",";
        editor.putString("list", check(list));
        editor.apply();
    }

    String get(){
        return check(list);
    }
    String next(String a) {
        int next = storage.lastIndexOf(a)+1;
        if(next<0)
            return "false";
        return storage.get(next);
    }
    ///special functions
    String nextHistory (int a){
        return storage.get(a+1);
    }
    String prevHistory (int a){
        return storage.get(a-1);
    }

}


