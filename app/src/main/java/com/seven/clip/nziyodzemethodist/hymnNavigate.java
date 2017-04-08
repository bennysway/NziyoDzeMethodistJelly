package com.seven.clip.nziyodzemethodist;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by bennysway on 20.03.17.
 */

public class hymnNavigate {
    private String favIterator, recIterator,output="0";
    private boolean isFavAffected=false,anyChange=false,isThereNext=true,isTherePrev=true;
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

        if(favList.size()==0)
            isThereNext = false;
        if(recList.size()==0)
            isTherePrev = false;

    }

    public String next() {
        if (recIterator.equals("0") && favIterator.equals("0")) {
            if(favList.size()-1<Integer.valueOf(favIterator)){
                output = "0";
                anyChange = false;
            }
            else{
                output = favList.get(Integer.valueOf(favIterator));
                isFavAffected = true;
                anyChange = true;
            }
        }
        else if (!favIterator.equals("0")){
            if(favList.size()-1<Integer.valueOf(favIterator)){
                output = "0";
                anyChange = false;
            }
            else{
                output = favList.get(Integer.valueOf(favIterator));
                isFavAffected = true;
                anyChange = true;
            }
        }
        else if (!recIterator.equals("0")){
            output = recList.get(Integer.valueOf(recIterator)-1);
            isFavAffected = false;
            anyChange = true;
        }
        return output;
    }

    public String prev(){
        if (recIterator.equals("0") && favIterator.equals("0")) {
            if(recList.size()-1<Integer.valueOf(recIterator)){
                output = "0";
                anyChange = false;
            }
            else{
                output = recList.get(Integer.valueOf(recIterator));
                isFavAffected = false;
                anyChange = true;
            }
        }
        else if (!favIterator.equals("0")){
                output = favList.get(Integer.valueOf(favIterator)-1);
                isFavAffected = true;
                anyChange = true;
        }
        else if (!recIterator.equals("0")){
            if(recList.size()-1<Integer.valueOf(recIterator)){
                output = "0";
                anyChange = false;
            }
            else{
                output = recList.get(Integer.valueOf(recIterator));
                isFavAffected = false;
                anyChange = true;
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
