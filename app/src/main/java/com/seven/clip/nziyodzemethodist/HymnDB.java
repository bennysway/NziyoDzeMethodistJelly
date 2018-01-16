package com.seven.clip.nziyodzemethodist;

import java.util.List;

/**
 * Created by bennysway on 13.11.17.
 */

public class HymnDB {

    List<shonaHymns> shonaHymnsList;
    List<englishHymns> englishHymnsList;

    public HymnDB(List<shonaHymns> shonaHymnsList, List<englishHymns> englishHymnsList) {
        this.shonaHymnsList = shonaHymnsList;
        this.englishHymnsList = englishHymnsList;
    }


    static class shonaHymns{
        String hymnName;
        String hymnNum;
        boolean hasEnglishVersion;
        String[] captions;
        boolean hasChorus;
        String chorus;
        String[] stanzas;

        public shonaHymns(String hymnName,String hymnNum,boolean hasEnglishVersion,String[] captions,boolean hasChorus, String chorus, String[] stanzas){
            this.hymnName = hymnName;
            this.hymnNum = hymnNum;
            this.hasEnglishVersion = hasEnglishVersion;
            this.captions = captions;
            this.hasChorus = hasChorus;
            this.chorus = chorus;
            this.stanzas = stanzas;

        }

        public String getHymnName() {
            return hymnName;
        }

        public void setHymnName(String hymnName) {
            this.hymnName = hymnName;
        }

        public String getHymnNum() {
            return hymnNum;
        }

        public void setHymnNum(String hymnNum) {
            this.hymnNum = hymnNum;
        }

        public boolean isHasEnglishVersion() {
            return hasEnglishVersion;
        }

        public void setHasEnglishVersion(boolean hasEnglishVersion) {
            this.hasEnglishVersion = hasEnglishVersion;
        }

        public String[] getCaptions() {
            return captions;
        }

        public void setCaptions(String[] captions) {
            this.captions = captions;
        }

        public boolean isHasChorus() {
            return hasChorus;
        }

        public void setHasChorus(boolean hasChorus) {
            this.hasChorus = hasChorus;
        }

        public String getChorus() {
            return chorus;
        }

        public void setChorus(String chorus) {
            this.chorus = chorus;
        }

        public String[] getStanzas() {
            return stanzas;
        }

        public void setStanzas(String[] stanzas) {
            this.stanzas = stanzas;
        }

        public shonaHymns() {

        }
    }

    static class englishHymns{
        public String getHymnName() {
            return hymnName;
        }

        public void setHymnName(String hymnName) {
            this.hymnName = hymnName;
        }

        public String getHymnNum() {
            return hymnNum;
        }

        public void setHymnNum(String hymnNum) {
            this.hymnNum = hymnNum;
        }

        public boolean isHasChorus() {
            return hasChorus;
        }

        public void setHasChorus(boolean hasChorus) {
            this.hasChorus = hasChorus;
        }

        public String getChorus() {
            return chorus;
        }

        public void setChorus(String chorus) {
            this.chorus = chorus;
        }

        public String[] getStanzas() {
            return stanzas;
        }

        public void setStanzas(String[] stanzas) {
            this.stanzas = stanzas;
        }

        String hymnName;
        String hymnNum;
        boolean hasChorus;
        String chorus;
        String[] stanzas;

        public englishHymns(String hymnName, String hymnNum, boolean hasChorus, String chorus, String[] stanzas){
            this.hymnName = hymnName;
            this.hymnNum = hymnNum;
            this.hasChorus = hasChorus;
            this.chorus = chorus;
            this.stanzas = stanzas;

        }

        public englishHymns() {
        }
    }


}
