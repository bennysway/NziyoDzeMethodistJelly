package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Search extends AppCompatActivity {

    ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        TextView noResults = (TextView) findViewById(R.id.noResultText);
        ls = (ListView) findViewById(R.id.searchListView);
        final Intent toHymn = new Intent(this,hymnDisplay.class);
        Data recordFlag = new Data(this,"recordflag");
        recordFlag.deleteAll();



        String search = getIntent().getStringExtra("search");
        final ArrayList<SearchResults> results = new ArrayList<>();

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
        if (results.size() > 0) {
            Collections.sort(results, new Comparator<SearchResults>() {
                @Override
                public int compare(final SearchResults object1, final SearchResults object2) {
                    return object1.getTitle().compareTo(object2.getTitle());
                }
            });
        }
        if(counter==0){
            noResults.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        }
        else {
            ls.setAdapter(new MySearchListApdapter(this, results));
            ls.setVisibility(View.VISIBLE);
            noResults.setVisibility(View.INVISIBLE);
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
