package com.seven.clip.nziyodzemethodist;

import java.util.ArrayList;

public class UserData {

    //favlist,reclist,showsplash,color,image,textsize,recordflag,withcaption
    //colorflag,textsizeflag,accflag,faviterator,reciterator,themecolor,themename
    //bookmark,bibleoption
    //firstTimes:biblepickerfirsttime,jsonhandler

    private String userName;
    private String color;
    private String textSize;
    private String bookmark;
    private String theme;
    private String themeName;
    private String image;
    private String bibleOption;

    private boolean showSplash;
    private boolean includeEnglishSplashHymns;

    ArrayList<String> favList;
    ArrayList<String> recList;
    ArrayList<String> splashList;
    ArrayList<UserCaption> userCaptions;
    ArrayList<UserFavoriteStanza> userFavoriteStanzas;

    boolean isShowSplash() {
        return showSplash;
    }

    void setShowSplash(boolean showSplash) {
        this.showSplash = showSplash;
    }

    boolean isIncludeEnglishSplashHymns() {
        return includeEnglishSplashHymns;
    }

    void setIncludeEnglishSplashHymns(boolean includeEnglishSplashHymns) {
        this.includeEnglishSplashHymns = includeEnglishSplashHymns;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    String getThemeName() {
        return themeName;
    }

    public String getBibleOption() {
        return bibleOption;
    }

    public void setBibleOption(String bibleOption) {
        this.bibleOption = bibleOption;
    }


    void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    ArrayList<String> getSplashList() {
        return splashList;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    String getBookmark() {
        return bookmark;
    }

    void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    void setFavList(ArrayList<String> favList) {
        this.favList = favList;
    }

    void setRecList(ArrayList<String> recList) {
        this.recList = recList;
    }

    ArrayList<UserFavoriteStanza> getUserFavoriteStanzas() {
        return userFavoriteStanzas;
    }

    public ArrayList<UserCaption> getUserCaptions() {
        return userCaptions;
    }

    public void setUserCaptions(ArrayList<UserCaption> userCaptions) {
        this.userCaptions = userCaptions;
    }

    public void setUserFavoriteStanzas(ArrayList<UserFavoriteStanza> userFavoriteStanzas) {
        this.userFavoriteStanzas = userFavoriteStanzas;
    }


    static class UserCaption{
        private String hymnNum;
        ArrayList<UserNote> userNotes;
        ArrayList<UserRecording> userRecordings;

        public UserCaption(String hymnNum) {
            this.hymnNum = hymnNum;
        }

        public String getHymnNum() {
            return hymnNum;
        }

        public void setHymnNum(String hymnNum) {
            this.hymnNum = hymnNum;
        }

        public ArrayList<UserNote> getUserNotes() {
            return userNotes;
        }

        public ArrayList<UserRecording> getUserRecordings() {
            return userRecordings;
        }

        static class UserNote{
            private String date;
            private String note;


            public UserNote(String date, String note) {
                this.date = date;
                this.note = note;
            }

            public UserNote() {
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }
        }
        static class UserRecording{
            private String date;
            private String path;
            private long duration=0;

            public UserRecording() {
            }
            public UserRecording(String date, String path) {
                this.date = date;
                this.path = path;
            }
            public UserRecording(String date, String path, long duration) {
                this.date = date;
                this.path = path;
                this.duration = duration;
            }
            public float getDuration() {
                return duration;
            }
            public void setDuration(long duration) {
                this.duration = duration;
            }
            public String getDate() {
                return date;
            }
            public void setDate(String date) {
                this.date = date;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }
        }
    }

    static class UserFavoriteStanza {
        private String hymnNum;
        private boolean isEnglish;
        ArrayList<Integer> stanza;

        public UserFavoriteStanza(String hymnNum, boolean isEnglish, ArrayList<Integer> stanza) {
            this.hymnNum = hymnNum;
            this.isEnglish = isEnglish;
            this.stanza = stanza;
        }

        public boolean isEnglish() {
            return isEnglish;
        }

        public void setEnglish(boolean english) {
            isEnglish = english;
        }


        public String getHymnNum() {
            return hymnNum;
        }

        public void setHymnNum(String hymnNum) {
            this.hymnNum = hymnNum;
        }

        public ArrayList<Integer> getStanza() {
            return stanza;
        }

        public void setStanza(ArrayList<Integer> stanza) {
            this.stanza = stanza;
        }
    }
}
