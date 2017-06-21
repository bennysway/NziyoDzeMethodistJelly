package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.graphics.Color.parseColor;

public class Search extends AppCompatActivity {

    ListView ls;
    TextSwitcher searchTitle;
    int it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        TextView noResults = (TextView) findViewById(R.id.noResultText);
        searchTitle = (TextSwitcher) findViewById(R.id.searchTitle);
        View backBut = findViewById(R.id.searchBackBut);
        ls = (ListView) findViewById(R.id.searchListView);
        final Intent toHymn = new Intent(this,hymnDisplay.class);
        Data recordFlag = new Data(this,"recordflag");
        recordFlag.deleteAll();

        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchTitle.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                TextView text = new TextView(Search.this);
                text.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                text.setBackground(getResources().getDrawable(R.drawable.top_rounded_white));
                text.setTextColor(parseColor("#cc5f00"));
                text.setTextSize(22);
                return text;
            }
        });

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.fade_out);

        // set the animation type of textSwitcher
        searchTitle.setInAnimation(in);
        searchTitle.setOutAnimation(out);

        String search = getIntent().getStringExtra("search");
        final ArrayList<SearchResults> results = new ArrayList<>();

        String title = "You searched for: \"" + search + "\"";
        final String textToShow[]={"Search Results",title,"hits"};

        for(int i=1;i<=317; i++){
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
        int counter = results.size();
        if(counter==1)
            textToShow[2] = "Found " + String.valueOf(counter) + " hit.";
        else
            textToShow[2] = "Found " + String.valueOf(counter) + " hits.";

        if (results.size() > 0) {
            Collections.sort(results, new Comparator<SearchResults>() {
                @Override
                public int compare(final SearchResults object1, final SearchResults object2) {
                    return object1.getTitle().compareTo(object2.getTitle());
                }
            });
        }

        Runnable animateTitle = new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler();
                it=0;
                for(int i=0;i<3;i++){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            searchTitle.setText(textToShow[it]);
                            it++;
                        }
                    },4000 * i);
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchTitle.setText(textToShow[0]);
                    }
                },12000);
            }
        };

        if(counter==0){
            animateTitle.run();
            noResults.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        }
        else {
            ls.setAdapter(new MySearchListApdapter(this, results));
            ls.setVisibility(View.VISIBLE);
            noResults.setVisibility(View.INVISIBLE);
            animateTitle.run();
        }


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toHymn.putExtra("hymnNum",results.get(i).getHymnNum());
                startActivity(toHymn);
            }
        });



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
}
