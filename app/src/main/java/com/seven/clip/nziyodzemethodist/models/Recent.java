package com.seven.clip.nziyodzemethodist.models;

import java.util.ArrayList;
import java.util.Date;

public class Recent {
    public Hymn.Header header;
    String hymnId;
    public ArrayList<Date> datesAccessed;

    Recent(){
        datesAccessed = new ArrayList<>();
    }
}
