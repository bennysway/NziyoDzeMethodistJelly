package com.seven.clip.nziyodzemethodist;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

public class EnResource {
    private Context context;
    private List store;

    public EnResource(Context current){
        this.context = current;
        store = Arrays.asList(getList().split(","));
    }

    public String getList(){
        return context.getResources().getString(R.string.en_hymn_list);
    }

    public boolean isEn(String num){
        return store.contains(num);
    }
}
