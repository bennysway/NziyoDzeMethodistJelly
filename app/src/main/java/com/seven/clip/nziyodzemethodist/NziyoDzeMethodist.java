package com.seven.clip.nziyodzemethodist;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by bennysway on 12.02.17.
 */

public class NziyoDzeMethodist extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
