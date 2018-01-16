package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by bennysway on 15.11.17.
 */

public class UserDataIO {

    //favlist,reclist,showsplash,color,image,textsize,recordflag,withcaption
    //colorflag,textsizeflag,accflag,faviterator,reciterator,themecolor,themename
    //bookmark,bibleoption
    //firstTimes:biblepickerfirsttime,jsonhandler

    private Context context;
    private Data favListData;
    private Data recListData;
    private Data colorData;
    private Data image;
    private Data textSizeData;
    private Data captionList;
    private Data accFlag;
    private Data theme;
    private Data themeName;
    private Data bookmarkData;
    private Data usernameData;
    private Data userDataFile;
    SharedPreferences preferences;
    String name;
    SharedPreferences.Editor editor;
    UserData userData;
    //
    public UserDataIO(Context context) {
        //Set Context
        this.context = context;
        //Get username;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        name = preferences.getString("example_text","");
        editor = preferences.edit();
        this.usernameData = new Data(context,"username");
        usernameData.update(name);
        //Set Other Data
        this.colorData = new Data(context,"color");
        this.textSizeData = new Data(context, "textsize");
        this.bookmarkData = new Data(context,"bookmark");
        this.favListData = new Data(context, "favlist");
        this.recListData = new Data(context, "reclist");
        this.captionList = new Data(context, "withcaption");
        this.userDataFile = new Data(context,"userdatafile");
        this.theme = new Data(context,"themecolor");
        this.themeName = new Data(context,"themename");
        this.image = new Data(context,"image");
        //

        if(userDataFile.get().equals("")) {
            userData = new UserData();
            //
            userData.favList = new ArrayList<>();
            userData.recList = new ArrayList<>();
            userData.splashList = new ArrayList<>();
            userData.userCaptions = new ArrayList<>();
            userData.userFavoriteStanzas = new ArrayList<>();
            //
            loadFromShared();
        } else
            userData = loadFromJson();

    }
    public UserData loadFromShared(){
        userData.setUserName(usernameData.get());
        userData.setColor(colorData.get());
        userData.setTextSize(textSizeData.get());
        userData.setBookmark(bookmarkData.get());
        userData.setTheme(theme.get());
        userData.setThemeName(themeName.get());
        userData.setImage(image.get());
        userData.setShowSplash(preferences.getBoolean("long_splash",true));
        userData.setIncludeEnglishSplashHymns(preferences.getBoolean("include_english",true));

        String [] captions = captionList.get().split(",");
        String [] favorites = favListData.get().split(",");
        String [] recent = recListData.get().split(",");

        if(captions.length>0 && !captionList.get().equals("")){
            for(int i=0;i<captions.length;i++){
                String safe = NumToWord.convert(Integer.parseInt(captions[i])) + "key";
                Data storedKey = new Data(context,safe);
                String raw = storedKey.get();
                UserData.UserCaption userCaption = new UserData.UserCaption(captions[i]);
                userCaption.setHymnNum(captions[i]);
                userCaption.userNotes = new ArrayList<>();
                userCaption.userRecordings = new ArrayList<>();

                String[] rawArray = raw.split(",");

                int size =rawArray.length;
                for(int j=0;j<size;j+=3){
                    if(rawArray[j+1].equals("note")){
                        UserData.UserCaption.UserNote note = new UserData.UserCaption.UserNote();
                        note.setDate(rawArray[j]);
                        note.setNote(new Data(context,safe + rawArray[j+2]).get());
                        userCaption.userNotes.add(note);
                    } else {
                        UserData.UserCaption.UserRecording recording = new UserData.UserCaption.UserRecording();
                        recording.setDate(rawArray[j]);
                        recording.setPath(rawArray[j+2]);
                        recording.setDuration(0);
                        userCaption.userRecordings.add(recording);
                    }
                }
                userData.userCaptions.add(userCaption);
            }
        }

        if(favorites.length>0 && !favListData.get().equals("")){
            userData.setFavList((ArrayList<String>) Arrays.asList(favorites));
        }
        if(recent.length>0 && !recListData.get().equals("")){
            userData.setRecList((ArrayList<String>) Arrays.asList(recent));
        }
        userDataFile.update(new Gson().toJson(userData));
        return userData;
    }

    public UserData loadFromJson(){
        Gson gson = new Gson();
        return gson.fromJson(userDataFile.get(),UserData.class);
    }
    //Favourites
    public ArrayList<String> getFavoriteList(){
        return userData.favList;
    }
    public void addToFavorites(String hymnNumber){
        userData.favList.add(hymnNumber);
        Collections.sort(userData.favList);
    }
    public boolean isFavorite(String hymnNumber){
        return userData.favList.contains(hymnNumber);
    }
    public void removeFromFavorites(String hymnNumber){
        userData.favList.remove(hymnNumber);
    }
    public void clearFavoriteList() {
        userData.favList.clear();
    }
    //Recent
    public ArrayList<String> getRecentList(){
        return userData.recList;
    }
    public void addToRecentList(String hymnNumber){
        userData.recList.add(0,hymnNumber);
    }
    public void removeFromRecentList(String hymnNumber){
        userData.recList.remove(hymnNumber);
    }
    public Map<String,Integer> getFrequencyTable(){
        Map data = new TreeMap();
        for(String hymnNumber : userData.recList){
            if(!data.containsKey(hymnNumber)){
                data.put(hymnNumber,Collections.frequency(userData.recList,hymnNumber));
            }
        }
        return data;
    }
    public void clearRecentList() {
        userData.recList.clear();
    }
    //Captions
    public UserData.UserCaption getHymnCaptionData(String hymnNumber){
        UserData.UserCaption caption = new UserData.UserCaption(hymnNumber);
        for(UserData.UserCaption mCaption : userData.userCaptions)
            if(mCaption.getHymnNum().equals(hymnNumber))
                caption = mCaption;
        return caption;
    }
    public void addNote(String hymnNumber,String date, String note){
        for(UserData.UserCaption caption : userData.userCaptions){
            if(caption.getHymnNum().equals(hymnNumber)){
                caption.userNotes.add(new UserData.UserCaption.UserNote(date,note));
            } else {
                UserData.UserCaption userCaption = new UserData.UserCaption(hymnNumber);
                userCaption.setHymnNum(hymnNumber);
                userCaption.userNotes.add(new UserData.UserCaption.UserNote(date,note));
                userData.userCaptions.add(userCaption);
            }
        }
    }
    public void addRecording(String hymnNumber,String date,String path){
        for(UserData.UserCaption caption : userData.userCaptions){
            if(caption.getHymnNum().equals(hymnNumber)){
                caption.userRecordings.add(new UserData.UserCaption.UserRecording(date,path));
            } else {
                UserData.UserCaption userCaption = new UserData.UserCaption(hymnNumber);
                userCaption.setHymnNum(hymnNumber);
                userCaption.userRecordings.add(new UserData.UserCaption.UserRecording(date,path));
                userData.userCaptions.add(userCaption);
            }
        }
    }
    public void addRecording(String hymnNumber,String date,String path,long duration){
        for(UserData.UserCaption caption : userData.userCaptions){
            if(caption.getHymnNum().equals(hymnNumber)){
                caption.userRecordings.add(new UserData.UserCaption.UserRecording(date,path,duration));
            } else {
                UserData.UserCaption userCaption = new UserData.UserCaption(hymnNumber);
                userCaption.setHymnNum(hymnNumber);
                userCaption.userRecordings.add(new UserData.UserCaption.UserRecording(date,path,duration));
                userData.userCaptions.add(userCaption);
            }
        }
    }

    public void removeNote(String number, String date, String note){
        for(UserData.UserCaption caption : userData.userCaptions){
            if(caption.getHymnNum().equals(number)){
                for(UserData.UserCaption.UserNote note1 : caption.userNotes){
                    if(note1.getDate().equals(date)&&note1.getNote().equals(note)){
                        caption.userNotes.remove(note1);
                    }
                }
            }
        }
    }
    public void removeRecording(String number, String date, String path){
        for(UserData.UserCaption caption : userData.userCaptions){
            if(caption.getHymnNum().equals(number)){
                for(UserData.UserCaption.UserRecording recording : caption.userRecordings){
                    if(recording.getDate().equals(date)&&recording.getPath().equals(path)){
                        caption.userNotes.remove(recording);
                    }
                }
            }
        }
    }
    public ArrayList<String> getCaptionList(){
        ArrayList<String> list = new ArrayList<>();
        for(UserData.UserCaption caption : userData.userCaptions){
            if(!list.contains(caption.getHymnNum())){
                list.add(caption.getHymnNum());
            }
        }
        return list;
    }
    public ArrayList<UserData.UserCaption.UserNote> getCaptionNotes(String hymnNumber){
        ArrayList<UserData.UserCaption.UserNote> notes = new ArrayList<>();
        for(UserData.UserCaption caption : userData.userCaptions){
            if(caption.getHymnNum().equals(hymnNumber))
                notes = caption.getUserNotes();
        }
        return notes;
    }
    public ArrayList<UserData.UserCaption.UserRecording> getCaptionRecordings(String hymnNumber){
        ArrayList<UserData.UserCaption.UserRecording> recordings = new ArrayList<>();
        for(UserData.UserCaption caption : userData.userCaptions){
            if(caption.getHymnNum().equals(hymnNumber)){
                recordings = caption.getUserRecordings();
            }
        }
        return recordings;
    }
    //Bookmarks
    public void setBookmark(String hymnNumber){
        userData.setBookmark(hymnNumber);
    }
    public void deleteBookmark(){
        userData.setBookmark("");
    }
    public String getBookmark(){
        return userData.getBookmark();
    }
    //Color
    public void setUserColor(String color){
        userData.setColor(color);
    }
    public void clearColor(){
        userData.setColor("");
    }
    public String getUserColor(){ return userData.getColor(); }
    public void setThemeName(String string){ userData.setThemeName(string);}
    public void setTheme(String string){ userData.setTheme(string);}
    public String getThemeName(){ return userData.getThemeName();}
    public String getTheme(){ return userData.getTheme();}
    //TextSize
    public void setUserTextSize(String textSize){
        userData.setTextSize(textSize);
    }
    public void clearTextSize(){
        userData.setTextSize("40");
    }
    public String getTextSize(){
        return userData.getTextSize();
    }
    //Underlines
    public ArrayList<UserData.UserFavoriteStanza> getFavoriteStanza(){
        return userData.getUserFavoriteStanzas();
    }
    //Global
    public void save(){
        userDataFile.update(new Gson().toJson(userData));
    }
    public String getUserName(){ return userData.getUserName(); }
    //Splash
    public ArrayList<String> getSplashList(){ return userData.getSplashList(); }
    public void addToSplashList(String number){
        userData.splashList.add(number);
    }
    public void removeFromSplashList(String number){ userData.splashList.remove(number); }
    public void clearSplashList(){ userData.splashList.clear(); }
    public void setShowSplash(boolean bool){ userData.setShowSplash(bool);}
    public void setShowEnglish(boolean bool){ userData.setIncludeEnglishSplashHymns(bool);}
    public boolean getShowSplash(){ return userData.isShowSplash(); }
    public boolean getShowEnglishSplashHymns(){ return userData.isIncludeEnglishSplashHymns(); }

}
