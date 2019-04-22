package com.seven.clip.nziyodzemethodist.models;
import java.util.ArrayList;

public class Hymn {
    public class Header{
        public String hymnName;
        String id;
    }
    class Attributes{
        int hymnNumber;
        String language;
        ArrayList<String> relationIds;
        ArrayList<String> relationLanguages;
        boolean hasChorus;

        Attributes(){
            relationIds = new ArrayList<>();
            relationLanguages = new ArrayList<>();
        }
    }
    class Captions{
        String tune;
        String meter;
        String key;
        String tonicSolfa = "";
        String subtitle;
        String ndebele;
    }

    class Content{
        String chorus;
        String[] stanzas;
    }

    public Hymn(){
        header = new Header();
        attributes = new Attributes();
        captions = new Captions();
        content = new Content();
    }

    Header header;
    Attributes attributes;
    Captions captions;
    Content content;
}
