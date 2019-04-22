package com.seven.clip.nziyodzemethodist;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Search extends AppCompatActivity {

    LinearLayout searchTitle;
    ArrayList<SearchResults> results;
    ArrayList<SearchResults> enResults;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        viewPager = findViewById(R.id.searchViewPager);
        searchTitle = findViewById(R.id.searchTitle);
        final ImageView shBut = findViewById(R.id.searchShBut);
        final ImageView enBut = findViewById(R.id.searchEnBut);
        final Button shonaBut = findViewById(R.id.shonaSearchListButton);
        final Button englishBut = findViewById(R.id.englishSearchListButton);
        final CircularTextView shonaCount = findViewById(R.id.shonaSearchCount);
        final CircularTextView englishCount = findViewById(R.id.englishSearchCount);
        View backBut = findViewById(R.id.searchBackBut);
        Data recordFlag = new Data(this,"recordflag");
        recordFlag.deleteAll();
        int enCounter=0;

        SearchTabAdapter mAdapter = new SearchTabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        shonaBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0,true);
            }
        });

        englishBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,true);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0){
                    shBut.setAlpha(1-positionOffset + .2f);
                    enBut.setAlpha(positionOffset + .2f);
                    englishCount.setScaleX(positionOffset);
                    englishCount.setScaleY(positionOffset);
                    shonaCount.setScaleX(1-positionOffset);
                    shonaCount.setScaleY(1-positionOffset);

                }
                if(position==1){
                    enBut.setAlpha(1-positionOffset + .2f);
                    shBut.setAlpha(positionOffset + .2f);
                    shonaCount.setScaleX(positionOffset);
                    shonaCount.setScaleY(positionOffset);
                    englishCount.setScaleX(1-positionOffset);
                    englishCount.setScaleY(1-positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String search = getIntent().getStringExtra("search");
        results = new ArrayList<>();
        enResults = new ArrayList<>();

        String list = getStringResourceByName("en_hymn_list");

        for( int i=0; i<list.length(); i++ ) {
            if( list.charAt(i) == ',' ) {
                enCounter++;
            }
        } enCounter--;
        final String[]engNumList =list.split(",");

        for(int i=1;i<=321; i++){
            String h = "hymn" + String.valueOf(i) ;
            int resourceId = getResourceId(h,"array",getPackageName());
            String [] hymn = getResources().getStringArray(resourceId);
            final int length =getResources().getStringArray(getResourceId(h,"array",getPackageName())).length;
            for(int j=0;j<length;j++){
                String x = hymn[j];
                if((x.toLowerCase()).contains(search.toLowerCase())){
                    SearchResults p = new SearchResults();
                    p.setHymnNum(IntToStr(i));
                    p.setTitle(getStringResourceByName("hymn"+IntToStr(i)+"firstline"));
                    p.setInEnglish(false);
                    int front = x.indexOf(search);
                    int end= x.length();
                    if(front<0)
                        front=0;
                    int back = front + search.length()+30;
                    if(back>end)
                        back=end;
                    String cap = x.substring(front,back);
                    cap = cap.replace("\n"," ");
                    cap +="...";
                    p.setCaption(cap);
                    results.add(p);
                }

            }
        }

        for(int i=0;i<=enCounter; i++){
            String h = "enhymn" + engNumList[i] ;
            int resourceId = getResourceId(h,"array",getPackageName());
            String [] hymn = getResources().getStringArray(resourceId);
            final int length =getResources().getStringArray(getResourceId(h,"array",getPackageName())).length;
            for(int j=0;j<length;j++){
                String x = hymn[j];
                if((x.toLowerCase()).contains(search.toLowerCase())){
                    SearchResults p = new SearchResults();
                    p.setHymnNum(engNumList[i]);
                    p.setTitle(getStringResourceByName("enhymn"+engNumList[i]+"firstline"));
                    p.setInEnglish(true);
                    int front = x.indexOf(search);
                    int end= x.length();
                    if(front<0)
                        front=0;
                    int back = front + search.length()+30;
                    if(back>end)
                        back=end;
                    String cap = x.substring(front,back);
                    cap = cap.replace("\n"," ");
                    cap +="...";
                    p.setCaption(cap);
                    enResults.add(p);
                }

            }
        }

        shonaCount.setText(String.valueOf(results.size()));
        englishCount.setText(String.valueOf(enResults.size()));

        if (results.size() > 0) {
            Collections.sort(results, new Comparator<SearchResults>() {
                @Override
                public int compare(final SearchResults object1, final SearchResults object2) {
                    return object1.getTitle().compareTo(object2.getTitle());
                }
            });
            shBut.setColorFilter(getResources().getColor(R.color.burn));
            shonaCount.setSolidColor("#cc5f00");

        } else{
            shBut.setColorFilter(getResources().getColor(R.color.fadedBlack));
            shonaCount.setSolidColor("#70000000");
        }

        if (enResults.size() > 0) {
            Collections.sort(results, new Comparator<SearchResults>() {
                @Override
                public int compare(final SearchResults object1, final SearchResults object2) {
                    return object1.getTitle().compareTo(object2.getTitle());
                }
            });
            enBut.setColorFilter(getResources().getColor(R.color.burn));
            englishCount.setSolidColor("#cc5f00");
        } else{
            enBut.setColorFilter(getResources().getColor(R.color.fadedBlack));
            englishCount.setSolidColor("#70000000");
        }

        if(enResults.size() > 0 && !(results.size()>0)){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(1,true);
                }
            },600);
        }
    }

    public String IntToStr(int i){
        return Integer.toString(i);
    }

    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
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
    @NonNull
    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }

    public ArrayList<SearchResults> getShonaResults() {
        return results;
    }
    public ArrayList<SearchResults> getEnglishResults() {
        return enResults;
    }


}
