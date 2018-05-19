package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Captions extends AppCompatActivity {

    long starttime = 0;
    boolean barAvail=false,playing;
    UserDataIO userData;
    TextView notesTextView, recordingsTextView;
    String capStoreKey,hymnName,hymnNumber;
    View bar;
    int hasOption;
    ViewPager viewPager;
    CaptionsTabAdapter mAdapter;
    float travelDist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Data withCaption = new Data(this,"withcaption");
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        notesTextView = findViewById(R.id.notesTitle);
        recordingsTextView = findViewById(R.id.recordingsTitle);
        viewPager = findViewById(R.id.captionListViewPager);

        notesTextView.setTypeface(custom_font);
        recordingsTextView.setTypeface(custom_font);

        final String hymnNumWord = getIntent().getStringExtra("hymnNumWord");
        hymnNumber = getIntent().getStringExtra("hymnNum");
        hasOption = getIntent().getIntExtra("isEn",0);
        hymnName = getIntent().getStringExtra("hymnName");
        capStoreKey = hymnNumWord;
        //Data storedKey = new Data(this,capStoreKey);

        userData = new UserDataIO(this);

        viewPager = findViewById(R.id.captionListViewPager);
        mAdapter = new CaptionsTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    //Recent Focus
                    notesTextView.animate().xBy(travelDist);
                    recordingsTextView.animate().xBy(travelDist);
                    notesTextView.animate().alpha(1f);
                    recordingsTextView.animate().alpha(.3f);
                    notesTextView.animate().scaleX(1f);
                    notesTextView.animate().scaleY(1f);
                    recordingsTextView.animate().scaleX(.8f);
                    recordingsTextView.animate().scaleY(.8f);
                }
                if(position==1){
                    //Splash Focus
                    notesTextView.animate().xBy(-travelDist);
                    recordingsTextView.animate().xBy(-travelDist);
                    notesTextView.animate().alpha(.3f);
                    recordingsTextView.animate().alpha(1f);
                    recordingsTextView.animate().scaleX(1f);
                    recordingsTextView.animate().scaleY(1f);
                    notesTextView.animate().scaleX(.8f);
                    notesTextView.animate().scaleY(.8f);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        recordingsTextView.animate().alpha(.3f);
        final View addCaption =findViewById(R.id.addCaptionBut);


        addCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent askType = new Intent(Captions.this,addCaption.class);
                askType.putExtra("hymnNum",hymnNumber);
                askType.putExtra("hymnNumWord",hymnNumWord);
                askType.putExtra("isEn",hasOption);
                startActivity(askType);
            }
        });

    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    public ArrayList<UserData.UserCaption.UserNote> getNotes(){
        return userData.getCaptionNotes(hymnNumber);
    }
    public ArrayList<UserData.UserCaption.UserRecording> getRecordings(){
        return userData.getCaptionRecordings(hymnNumber);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        travelDist = (notesTextView.getWidth() + recordingsTextView.getWidth())/2f;
    }

}
