package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavList extends AppCompatActivity {

    ViewPager viewPager;
    FavoritesTabAdapter mAdapter;
    UserDataIO userData;
    float travelDist;
    TextView favTitle,underlineTitle;
    RelativeLayout titleHolder;

    @Override
    protected void onPause() {
        userData.save();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);

        viewPager = findViewById(R.id.favoriteListViewPager);
        mAdapter = new FavoritesTabAdapter(getSupportFragmentManager());
        favTitle = findViewById(R.id.favTitle);
        underlineTitle = findViewById(R.id.favoriteUnderlineTitle);

        View back = findViewById(R.id.favBackBut);
        final Intent toRemoveFav = new Intent(this,removeFav.class);
        toRemoveFav.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        userData = new UserDataIO(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        favTitle.setTypeface(custom_font);
        underlineTitle.setTypeface(custom_font);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    //Recent Focus
                    favTitle.animate().xBy(travelDist);
                    underlineTitle.animate().xBy(travelDist);
                    favTitle.animate().alpha(1f);
                    underlineTitle.animate().alpha(.3f);
                    favTitle.animate().scaleX(1f);
                    favTitle.animate().scaleY(1f);
                    underlineTitle.animate().scaleX(.8f);
                    underlineTitle.animate().scaleY(.8f);
                }
                if(position==1){
                    //Splash Focus
                    favTitle.animate().xBy(-travelDist);
                    underlineTitle.animate().xBy(-travelDist);
                    favTitle.animate().alpha(.3f);
                    underlineTitle.animate().alpha(1f);
                    underlineTitle.animate().scaleX(1f);
                    underlineTitle.animate().scaleY(1f);
                    favTitle.animate().scaleX(.8f);
                    favTitle.animate().scaleY(.8f);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        underlineTitle.animate().alpha(.3f);
        underlineTitle.animate().scaleX(.8f);
        underlineTitle.animate().scaleY(.8f);

        favTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0,true);
            }
        });
        underlineTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,true);
            }
        });


    }
    @Override
    public void onResume(){
        super.onResume();
        //Todo
    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    public ArrayList<String> getFavorites(){
        return userData.getFavoriteList();
    }
    public ArrayList<UserData.UserFavoriteStanza> getFavoriteStanza(){
        return userData.getFavoriteStanza();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        travelDist = (favTitle.getWidth() + underlineTitle.getWidth())/2f;
    }

}
