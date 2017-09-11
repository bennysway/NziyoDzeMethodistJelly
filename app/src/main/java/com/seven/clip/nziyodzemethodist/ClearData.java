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
        final Data favList = new Data(this,"favlist");
        final Data recList = new Data(this,"reclist");
        final Data showSplash = new Data(this,"showsplash");
        final Data image = new Data(this,"image");
        final Data color = new Data(this,"color");
        final Data size = new Data(this,"textsize");
        final Data recordFlag = new Data(this,"recordflag");
        final Data withCaption = new Data(this,"withcaption");
        final Data textSize = new Data(this,"textsize");
        final Data themeColor = new Data(this,"themecolor");
        final Data themeName = new Data(this,"themename");
        final Data booking = new Data(this,"bookmark");
        final Data bibleOption = new Data(this, "bibleoption");
        final Data biblePickerFirstTime = new Data(this, "biblepickerfirsttime");
        final Zvinokosha access = new Zvinokosha(this);

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
                favList.deleteAll();
                recList.deleteAll();
                showSplash.deleteAll();
                image.deleteAll();
                color.deleteAll();
                size.deleteAll();
                recordFlag.deleteAll();
                access.clear();
                withCaption.deleteAll();
                themeColor.deleteAll();
                themeName.deleteAll();
                textSize.deleteAll();
                booking.deleteAll();
                bibleOption.deleteAll();
                biblePickerFirstTime.deleteAll();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ClearData.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("example_text", "Set name");
                editor.apply();
                QuickToast("Everything will be fully cleared when you restart the hymn book.");
                finish();
            }
        });


    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

}
