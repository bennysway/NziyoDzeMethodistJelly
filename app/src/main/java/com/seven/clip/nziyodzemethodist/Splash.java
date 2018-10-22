package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LayerDrawable ld = ((LayerDrawable) getWindow().getDecorView().getBackground());
        InsetDrawable insert = (InsetDrawable) ld.getDrawable(7);
        InsetDrawable insert2 = (InsetDrawable) ld.getDrawable(3);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ((Animatable) insert.getDrawable()).start();
            ((Animatable) insert2.getDrawable()).start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this,MainDrawer.class));
                }
            },4000);
        }
        else{
            startActivity(new Intent(Splash.this,MainDrawer.class));
        }


    }
}
