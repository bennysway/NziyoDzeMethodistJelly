package com.seven.clip.nziyodzemethodist.models;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.List;

public class HymnDatabaseFile {
    public class Hymn {
        public class Header{
            public String hymnName;
            public String id;
        }
        public class Attributes{
            public int hymnNumber;
            public String language;
            public String[] relationIds;
            public String[] relationLanguages;
            public boolean hasChorus;
        }
        public class Captions{
            public String tune;
            public String meter;
            public String key;
            public String tonicSolfa;
            public String subtitle;
            public String ndebele;
        }

        public class Content{
            public String chorus;
            public String[] stanzas;
        }
        public Header header;
        public Attributes attributes;
        public Captions captions;
        public Content content;
    }
    public class Manifests{
        public String databaseName;
        public String databaseId;
        public String language;
        public int count;
        public long lastModified;
    }
    public List<Hymn> database;
    public SparseArray<String> index = new SparseArray<>();
    public Manifests manifest;

    public Hymn get(String id){
        for(Hymn hymn: database){
            if(hymn.header.id.equals(id))
                return hymn;
        }
        return null;
    }
}
