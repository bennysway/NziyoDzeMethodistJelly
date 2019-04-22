package com.seven.clip.nziyodzemethodist;

import android.app.Application;
import android.content.Context;

import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Theme;

import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * Created by bennysway on 12.02.17.
 */

public class NziyoDzeMethodist extends Application {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public static Theme currentTheme;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        currentTheme = ColorThemes.getTheme(base);
    }
}
