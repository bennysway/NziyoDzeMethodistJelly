package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Random;

public class Splash extends AppCompatActivity {
    Runnable beginActivity;
    Runnable show1,show2,show3,showTitle,hideshows;
    Handler start,timer;
    Intent toStart;
    ImageView spl;
    int i;
    boolean isBookmarkAvail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");

        final Button bookmark = (Button) findViewById(R.id.bookmarkSplashBut);
        final Data booking = new Data(this,"bookmark");
        isBookmarkAvail = false;

        Random r=new Random();
        spl = (ImageView) findViewById(R.id.splashImage);
        final TextView line1 = (TextView) findViewById(R.id.line1);
        final TextView line2 = (TextView) findViewById(R.id.line2);
        final TextView line3 = (TextView) findViewById(R.id.hymnline);

        line1.setTypeface(custom_font);
        line2.setTypeface(custom_font);
        line3.setTypeface(custom_font);

        final View skip =findViewById(R.id.skipSplash);
        toStart = new Intent(this,MainDrawer.class);
        timer = new Handler();
        i=r.nextInt(321)+1;
        String h = "hymn" + IntToStr(i);
        String [] hymn;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean longSplash = preferences.getBoolean("long_splash",true);
        Data recordFlag = new Data(this,"recordflag");
        recordFlag.deleteAll();
        start = new Handler();

        int s = r.nextInt(6)+1;
        spl.setScaleX(1.3f);
        spl.setScaleY(1.3f);
        spl.animate().scaleX(1f).scaleY(1f).setDuration(13000);
        int splId = getResourceId("spl"+s,"drawable",getPackageName());
        spl.setImageResource(splId);

        int resourceId = getResourceId(h,"array",getPackageName());
        hymn = getResources().getStringArray(resourceId);

        try {
            final String [] lines = hymn[0].split("\n");
            lines[2]= "Hymn " + IntToStr(i);
            line1.setText(lines[0]);
            line2.setText(lines[1]);
            line3.setText(lines[2]);
            line1.setAlpha(0);
            line2.setAlpha(0);
            line3.setAlpha(0);
        }
        catch(ArrayIndexOutOfBoundsException exception) {
            final String [] lines = hymn[0].split("\n");
            lines[1]= "Hymn " + IntToStr(i);
            line1.setText(lines[0]);
            line2.setText(lines[1]);
            line3.setText("");
            line1.setAlpha(0);
            line2.setAlpha(0);
            line3.setAlpha(0);
        }

        if(!booking.get().isEmpty()){
            isBookmarkAvail = true;
            bookmark.setVisibility(View.VISIBLE);
            bookmark.setText(booking.get());
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopAnimation();
                    Intent toHymn = new Intent(Splash.this,hymnDisplay.class);
                    toHymn.putExtra("hymnNum", booking.get());
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
                Intent toHymn = new Intent(Splash.this,hymnDisplay.class);
                toHymn.putExtra("hymnNum", String.valueOf(i));
                startActivity(toHymn);
            }
        });

        skip.setAlpha(0f);
        skip.animate().alpha(.5f).setDuration(2000);




    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName)
    {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public String IntToStr(int i){
        return Integer.toString(i);
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

}
