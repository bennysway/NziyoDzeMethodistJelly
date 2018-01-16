package com.seven.clip.nziyodzemethodist;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class xHymns {
    private Context context;
    private HymnDB hymnDB;
    private List<String> engIndex;

    xHymns(Context cxt) {
        this.context=cxt;
        hymnDB = new Gson().fromJson(readJSONFromAsset(),HymnDB.class);
        engIndex = Arrays.asList(getAllHymnNumbers(true));
    }

    public String getTitle(String num, boolean isEnglish,boolean withNumber){
        if(withNumber){
            if(!isEnglish)
                return num + ". " +hymnDB.shonaHymnsList.get(Integer.valueOf(num)-1).getHymnName();
            else
                return num + ". " +hymnDB.englishHymnsList.get(engIndex.indexOf(num)).getHymnName();
        } else {
            if(!isEnglish)
                return hymnDB.shonaHymnsList.get(Integer.valueOf(num)-1).getHymnName();
            else
                return hymnDB.englishHymnsList.get(engIndex.indexOf(num)).getHymnName();
        }

    }


    String[] getCaption(String num){
        return hymnDB.shonaHymnsList.get(Integer.valueOf(num)-1).getCaptions();
    }


    String[] getHymn(String num, boolean isEnglish){
        if(!isEnglish)
            return hymnDB.shonaHymnsList.get(Integer.valueOf(num)-1).getStanzas();
        else
            return hymnDB.englishHymnsList.get(engIndex.indexOf(num)).getStanzas();
    }
    String[] getAllHymns(boolean isEnglish){
        if(!isEnglish) {
            String [] array = new String[hymnDB.shonaHymnsList.size()];
            for (int i=0;i<hymnDB.shonaHymnsList.size();i++)
                array[i]=hymnDB.shonaHymnsList.get(i).getHymnName();
            return array;
        }
        else {
            String [] array = new String[hymnDB.englishHymnsList.size()];
            for (int i=0;i<hymnDB.englishHymnsList.size();i++)
                array[i]=hymnDB.englishHymnsList.get(i).getHymnName();
            return array;
        }
    }
    String[] getAllHymns(){
        String [] array = new String[hymnDB.shonaHymnsList.size() + hymnDB.englishHymnsList.size()];
        for (int i=0;i<hymnDB.shonaHymnsList.size();i++)
            array[i]=hymnDB.shonaHymnsList.get(i).getHymnName();
        for (int i=0;i<hymnDB.englishHymnsList.size();i++)
            array[i+hymnDB.shonaHymnsList.size()]=hymnDB.englishHymnsList.get(i).getHymnName();
        return array;
    }

    String[] getAllHymnNumbers(boolean isEnglish){
        if(!isEnglish) {
            String [] array = new String[hymnDB.shonaHymnsList.size()];
            for (int i=0;i<hymnDB.shonaHymnsList.size();i++)
                array[i]=hymnDB.shonaHymnsList.get(i).getHymnNum();
            return array;
        }
        else {
            String [] array = new String[hymnDB.englishHymnsList.size()];
            for (int i=0;i<hymnDB.englishHymnsList.size();i++)
                array[i]=hymnDB.englishHymnsList.get(i).getHymnNum();
            return array;
        }
    }

    boolean hasChorus(String num, boolean isEnglish) {
        if (!isEnglish)
            return hymnDB.shonaHymnsList.get(Integer.valueOf(num) - 1).isHasChorus();
        else
            return hymnDB.englishHymnsList.get(engIndex.indexOf(num)).isHasChorus();
    }


    String getChorus(String num,boolean isEnglish){
        if(!isEnglish)
            return hymnDB.shonaHymnsList.get(Integer.valueOf(num) - 1).getChorus();
        else
            return hymnDB.englishHymnsList.get(engIndex.indexOf(num)).getChorus();
    }

    String searchNum(String name){
        String [] shonaArray = getAllHymns(false);
        String result="";
        for(int i=0;i<shonaArray.length;i++){
            if(shonaArray[i].equals(name)){
                result = String.valueOf(i+1);
                return result;
            }
        }
        String [] englishArray = getAllHymns(true);
        for(int i=0;i<englishArray.length;i++){
            if(englishArray[i].equals(name)){
                result = engIndex.get(i);
            }
        }
        return result;
    }

    boolean searchIsEnglish(String name){
        String [] shonaArray = getAllHymns(false);
        boolean result=false;
        for (String aShonaArray : shonaArray) {
            if (aShonaArray.equals(name)) return false;
        }
        String [] englishArray = getAllHymns(true);
        for (String anEnglishArray : englishArray) {
            if (anEnglishArray.equals(name)) result = true;
        }
        return result;
    }


    public String readJSONFromAsset() {
        String json;
        try {
            InputStream is = context.getAssets().open("hymnsDB.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
