package com.seven.clip.nziyodzemethodist;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClearData extends AppCompatActivity {

    UserDataIO userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_data);

        TextView prompt = findViewById(R.id.clearDataPrompt);
        Button yes = findViewById(R.id.clearDataYesBut);
        Button no = findViewById(R.id.clearDataNoBut);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //favlist,reclist,showsplash,color,image,textsize,recordflag,withcaption
        //colorflag,textsizeflag,accflag,faviterator,reciterator,themecolor,themename
        //bookmark,bibleoption
//firstTimes:   biblepickerfirsttime,

        getWindow().setLayout((int)(width*.8),(int)(height*.3));

        userData = new UserDataIO(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        prompt.setTypeface(custom_font);


        prompt.setText("Clear All saved Favourites and Recent history?" );
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userData.clearFavoriteList();
                userData.clearRecentList();
                userData.clearSplashList();
                userData.setShowSplash(true);
                userData.setUserImage("");
                userData.clearColor();
                userData.clearTextSize();
                userData.setTheme("");
                userData.setThemeName("");
                userData.deleteBookmark();
                userData.setBible("");
                userData.setUserName("");
                QuickToast("Everything will be fully cleared when you restart the hymn book.");
                userData.save();
                finish();

            }
        });


    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

}
