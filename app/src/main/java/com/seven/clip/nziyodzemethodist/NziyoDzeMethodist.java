package com.seven.clip.nziyodzemethodist;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Theme;

import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * Created by bennysway on 12.02.17.
 */

public class NziyoDzeMethodist extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static HymnDatabaseFile databaseFile;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public void onCreate() {
        super.onCreate();
        NziyoDzeMethodist.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return NziyoDzeMethodist.context;
    }
    public static Theme currentTheme;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        currentTheme = ColorThemes.getTheme(base);
    }
}
