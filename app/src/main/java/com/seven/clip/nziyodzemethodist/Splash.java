package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rodolfonavalon.shaperipplelibrary.ShapeRipple;
import com.rodolfonavalon.shaperipplelibrary.model.Circle;

import java.util.Random;

import static android.graphics.Color.parseColor;

public class Splash extends AppCompatActivity {
    Runnable beginActivity;
    Runnable show1,show2,show3,showTitle,hideshows;
    Handler start,timer;
    Intent toStart;
    ShapeRipple ripple;
    int i;
    boolean isBookmarkAvail;
    UserDataIO userData;

    @Override
    protected void onPause() {
        userData.save();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        userData = new UserDataIO(this);

        final Button bookmark = findViewById(R.id.bookmarkSplashBut);
        isBookmarkAvail = false;


        Random r=new Random();
        ripple = findViewById(R.id.splashImage);
        final TextView line1 = findViewById(R.id.line1);
        final TextView line2 = findViewById(R.id.line2);
        final TextView line3 = findViewById(R.id.hymnline);

        line1.setTypeface(custom_font);
        line2.setTypeface(custom_font);
        line3.setTypeface(custom_font);

        final ImageView skip = findViewById(R.id.skipSplash);
        toStart = new Intent(this,MainDrawer.class);
        timer = new Handler();
        //Pick random hymn

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean longSplash = preferences.getBoolean("long_splash",true);
        boolean includeEnglish = preferences.getBoolean("include_english",true);
        Data recordFlag = new Data(this,"recordflag");
        recordFlag.deleteAll();
        start = new Handler();

        boolean isEnglish;

        if(includeEnglish)
            isEnglish = getRandomBoolean();
        else
            isEnglish = false;

        xHymns hymns = new xHymns(this);
        i=r.nextInt(hymns.getAllHymns(isEnglish).length);
        final String [] hymn = hymns.getAllHymnNumbers(isEnglish);


        ripple.setBackgroundColor(parseColor("#e0e0e0"));
        ripple.setRippleCount(40);
        ripple.setRippleShape(new Circle());
        ripple.setEnableColorTransition(true);
        ripple.setEnableRandomPosition(true);
        ripple.setRippleDuration(10000);
        ripple.setRippleMaximumRadius(200);
        ripple.setRippleColor(parseColor("#ffffff"));

        try {
            final String [] lines = hymns.getHymn(hymn[i],isEnglish)[0].split("\n");
            lines[2]= "Hymn " + hymn[i];
            line1.setText(lines[0]);
            line2.setText(lines[1]);
            line3.setText(lines[2]);
            line1.setAlpha(0);
            line2.setAlpha(0);
            line3.setAlpha(0);
        }
        catch(ArrayIndexOutOfBoundsException exception) {
            final String [] lines = hymns.getHymn(hymn[i],isEnglish)[0].split("\n");
            lines[1]= "Hymn " + hymn[i];
            line1.setText(lines[0]);
            line2.setText(lines[1]);
            line3.setText("");
            line1.setAlpha(0);
            line2.setAlpha(0);
            line3.setAlpha(0);
        }
        userData.getSplashList().add(0,hymn[i]);

        if(!userData.getBookmark().equals("")){
            isBookmarkAvail = true;
            bookmark.setVisibility(View.VISIBLE);
            bookmark.setText(userData.getBookmark());
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopAnimation();
                    Intent toHymn = new Intent(Splash.this,HymnDisplay.class);
                    toHymn.putExtra("hymnNum", userData.getBookmark());
                    bookmark.animate().y(-200f);
                    startActivity(toHymn);
                }
            });
            bookmark.setY(-200f);
            bookmark.animate().y(0f);
        }

        //////Runnables
        beginActivity = (new Runnable() {
            @Override
            public void run() {
                startActivity(toStart);
            }
        });
        show1 = new Runnable() {
            @Override
            public void run() {
                line1.animate().alpha(1);

            }
        };
        show2 = new Runnable() {
            @Override
            public void run() {
                line2.animate().alpha(1);

            }
        };
        show3 = new Runnable() {
            @Override
            public void run() {
                line3.animate().alpha(1);

            }
        };
        hideshows = new Runnable() {
            @Override
            public void run() {
                line1.animate().alpha(0);
                line2.animate().alpha(0);
                line3.animate().alpha(0);
            }
        };
        showTitle = new Runnable() {
            @Override
            public void run() {
                if(isBookmarkAvail)
                    bookmark.animate().y(-200);
                line1.setText("Nziyo DzeMethodist");
                start.postDelayed(beginActivity,4000);
                ripple.animate().alpha(0f).setDuration(3000);
                line1.animate().alpha(1).setDuration(100);
                line1.animate().scaleY(1.1f).scaleX(1.1f).setDuration(3000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        line1.animate().alpha(.5f).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                line1.animate().alpha(1f).setDuration(100);
                            }
                        });
                    }
                });
            }
        };


        if(longSplash){
            timer.postDelayed(show1,1000);
            timer.postDelayed(show2,3000);
            timer.postDelayed(show3,5000);
            timer.postDelayed(hideshows,7000);
            timer.postDelayed(showTitle,7300);
        }
        else
            showTitle.run();

        skip.setColorFilter(Color.WHITE);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnimation();
            }
        });
        line3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnimation();
                Intent toHymn = new Intent(Splash.this,HymnDisplay.class);
                toHymn.putExtra("hymnNum", hymn[i]);
                startActivity(toHymn);
            }
        });


    }

    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    public void stopAnimation(){
        timer.removeCallbacks(show1);
        timer.removeCallbacks(show2);
        timer.removeCallbacks(show3);
        timer.removeCallbacks(showTitle);
        start.removeCallbacks(beginActivity);
        startActivity(toStart);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            timer.removeCallbacks(show1);
            timer.removeCallbacks(show2);
            timer.removeCallbacks(show3);
            timer.removeCallbacks(showTitle);
            start.removeCallbacks(beginActivity);
            QuickToast("HymnBook Closed");
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public boolean getRandomBoolean() {
        return Math.random() < 0.5;
        //I tried another approaches here, still the same result
    }

}
