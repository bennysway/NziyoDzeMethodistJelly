package com.seven.clip.nziyodzemethodist.util;

import android.content.Context;
import android.widget.Toast;

public class Util {

    //UI Element
    public enum Element {
        home,
        church,
        organization,
        hymnList,
        hymnNumber,
        hymn,
        settings,
    }
    public enum Component{
        icon,
        iconBackground,
        text,
        context,
        textBackground,
        contextBackground,
        background

    }

    public static String contextName(Context context){
        return context.getClass().getSimpleName();
    }
    public static void quickToast(Context packageContext, String text){
        Toast.makeText(packageContext, text,
                Toast.LENGTH_SHORT).show();
    }
}
