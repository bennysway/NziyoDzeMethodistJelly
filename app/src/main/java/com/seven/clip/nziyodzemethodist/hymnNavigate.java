package com.seven.clip.nziyodzemethodist;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by bennysway on 20.03.17.
 */

public class hymnNavigate {
    private String favIterator, recIterator,output="0";
    private boolean isFavAffected=false,anyChange=false,isThereNext=false,isTherePrev=false;
    private LinkedList<String> favList;
    private LinkedList<String> recList;

    hymnNavigate(String fav, String rec, String favI, String recI){
        favIterator = favI;
        recIterator = recI;
        if(favIterator.equals(""))
            favIterator = "0";
        if(recIterator.equals(""))
            recIterator = "0";

        favList = new LinkedList<>(Arrays.asList(fav.split(",")));
        recList = new LinkedList<>(Arrays.asList(rec.split(",")));

        if(!fav.equals(""))
            isThereNext = true;
        if(!rec.equals(""))
            isTherePrev = true;

    }

    public String next() {
        if (recIterator.equals("0") && favIterator.equals("0")) {
            if(favList.size()-1<Integer.valueOf(favIterator)){
                output = "0";
                anyChange = false;
                isThereNext = false;
                isTherePrev = false;
            }
            else if(favList.size()==1) {
                output = "0";
                isThereNext = false;
            }
            else{
                output = favList.get(Integer.valueOf(favIterator));
                isFavAffected = true;
                anyChange = true;
                isTherePrev = true;
            }
        }
        else if (!favIterator.equals("0")){
            if(favList.size()-1<Integer.valueOf(favIterator)){
                output = "0";
                anyChange = false;
                isThereNext = false;
                isTherePrev = true;
            }
            else{
                output = favList.get(Integer.valueOf(favIterator));
                isFavAffected = true;
                anyChange = true;
                isTherePrev = true;
            }
        }
        else if (!recIterator.equals("0")){
            output = recList.get(Integer.valueOf(recIterator)-1);
            isFavAffected = false;
            anyChange = true;
            isTherePrev = true;
        }
        return output;
    }

    public String prev(){
        if (recIterator.equals("0") && favIterator.equals("0")) {
            if(recList.size()-1<Integer.valueOf(recIterator)){
                output = "0";
                anyChange = false;
                isTherePrev = false;
                isThereNext = false;
            }
            else if(recList.size()==1){
                output = "0";
                isTherePrev = false;
            }
            else{
                output = recList.get(Integer.valueOf(recIterator));
                isFavAffected = false;
                anyChange = true;
                isThereNext = true;
            }
        }
        else if (!favIterator.equals("0")){
                output = favList.get(Integer.valueOf(favIterator)-1);
                isFavAffected = true;
                anyChange = true;
                isThereNext = true;
        }
        else if (!recIterator.equals("0")){
            if(recList.size()-1<Integer.valueOf(recIterator)){
                output = "0";
                anyChange = false;
                isTherePrev = false;
                isThereNext = true;
            }
            else{
                output = recList.get(Integer.valueOf(recIterator));
                isFavAffected = false;
                anyChange = true;
                isThereNext = true;
            }
        }
        return output;
    }

    public String nextText(){
        if (recIterator.equals("0") && favIterator.equals("0")) {
            if(favList.size()-1<Integer.valueOf(favIterator)){
                output = "Next Favourite not available";
                isThereNext = false;
            }
            else if(favList.size()==1){
                output = "Next Favourite not available";
                isThereNext = false;
            }
            else{
                output = "Next Favourite (hymn " + favList.get(Integer.valueOf(favIterator)) +")";
                isThereNext = true;
            }
        }
        else if (!favIterator.equals("0")){
            if(favList.size()-1<Integer.valueOf(favIterator)){
                output = "Next Favourite not available";
                isThereNext = false;
            }
            else{
                output = "Next Favourite (hymn " + favList.get(Integer.valueOf(favIterator)) +")";
                isThereNext = true;
            }
        }
        else if (!recIterator.equals("0")){
            output = "Forward (hymn " + recList.get(Integer.valueOf(recIterator)-1) +")";
            isThereNext = true;
        }
        return output;
    }

    public String prevText(){
        if (recIterator.equals("0") && favIterator.equals("0")) {
            if(recList.size()-1<Integer.valueOf(recIterator)){
                output = "No Recent Hymns";
                isTherePrev = false;
            }
            else if(recList.size()==1){
                output = "No Recent Hymns";
                isTherePrev = false;
            }
            else{
                output = "Previous (hymn " + recList.get(Integer.valueOf(recIterator)) +")";
                isTherePrev = true;
            }
        }
        else if (!favIterator.equals("0")){
            output = "Previous Favourite (hymn " + favList.get(Integer.valueOf(favIterator)-1) + ")";
            isTherePrev = true;
        }
        else if (!recIterator.equals("0")){
            if(recList.size()-1<Integer.valueOf(recIterator)){
                output = "No more Recent Hymns";
                isTherePrev = false;
            }
            else{
                output = "Previous (hymn " + recList.get(Integer.valueOf(recIterator)) + ")";
                isTherePrev = true;
            }
        }
        return output;
    }

    void update(String fav, String rec){
        favIterator = fav;
        recIterator = rec;
    }

    boolean nextAccess(){
        return isThereNext;
    }

    boolean prevAccess(){
        return isTherePrev;
    }

    boolean changes(){
        return anyChange;
    }

    boolean isFavChanged(){
        return isFavAffected;
    }
}
