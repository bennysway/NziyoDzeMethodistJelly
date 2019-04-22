package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecList extends AppCompatActivity{

    UserDataIO userData;

    ViewPager viewPager;
    RecentTabAdapter mAdapter;
    float travelDist;
    TextView recTitle,splashTitle;
    RelativeLayout titleHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_list);

        userData = new UserDataIO(this);
        mAdapter = new RecentTabAdapter(getSupportFragmentManager());

        recTitle = findViewById(R.id.recentsTitle);
        splashTitle = findViewById(R.id.recentSplashTitle);
        titleHolder = findViewById(R.id.recentTitleHolder);

        View back = findViewById(R.id.recentBackButton);
        viewPager = findViewById(R.id.recentListViewPager);

        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    //Recent Focus
                    recTitle.animate().xBy(travelDist);
                    splashTitle.animate().xBy(travelDist);
                    recTitle.animate().alpha(1f);
                    splashTitle.animate().alpha(.3f);
                    recTitle.animate().scaleX(1f);
                    recTitle.animate().scaleY(1f);
                    splashTitle.animate().scaleX(.8f);
                    splashTitle.animate().scaleY(.8f);
                }
                if(position==1){
                    //Splash Focus
                    recTitle.animate().xBy(-travelDist);
                    splashTitle.animate().xBy(-travelDist);
                    recTitle.animate().alpha(.3f);
                    splashTitle.animate().alpha(1f);
                    splashTitle.animate().scaleX(1f);
                    splashTitle.animate().scaleY(1f);
                    recTitle.animate().scaleX(.8f);
                    recTitle.animate().scaleY(.8f);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        splashTitle.animate().alpha(.3f);
        splashTitle.animate().scaleX(.8f);
        splashTitle.animate().scaleY(.8f);

        recTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0,true);
            }
        });
        splashTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,true);
            }
        });

        final Intent toHome = new Intent(this, MainDrawer.class);
        FloatingActionButton del = findViewById(R.id.deleteHistory);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/bh.ttf");
        recTitle.setTypeface(custom_font);
        splashTitle.setTypeface(custom_font);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void QuickToast(String s) {
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    public ArrayList<String> getData(int position){
        if(position==0)
            return userData.getRecentList();
        else
            return userData.getSplashList();
    }


    @Override
    protected void onPause() {
        userData.save();
        super.onPause();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        travelDist = (recTitle.getWidth() + splashTitle.getWidth())/2f;
    }
}
