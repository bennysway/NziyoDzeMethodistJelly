package com.seven.clip.nziyodzemethodist.models;

import java.util.ArrayList;

public class HymnDatabaseFile {
    public class Database{
        public class Hymn {
            public class Header{
                public String hymnName;
                String id;
            }
            class Attributes{
                int hymnNumber;
                String language;
                String[] relationIds;
                String[] relationLanguages;
                boolean hasChorus;
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
            Header header;
            Attributes attributes;
            Captions captions;
            Content content;
        }

        Hymn[] hymns;
    }
    public class Manifest{
        String databaseName;
        long lastModified;
        int count;
    }
    Database database;
    Manifest manifest;
}
