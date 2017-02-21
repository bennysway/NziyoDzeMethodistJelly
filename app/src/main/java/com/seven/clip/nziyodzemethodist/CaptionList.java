package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CaptionList extends AppCompatActivity {
    ListView ls;
    String list;
    MyFavListAdapter adapter;
    Data withCaption;
    String[] names;
    int counter = 0;
    TextView noCapsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        noCapsText = (TextView) findViewById(R.id.noCaptionsText);
        ls = (ListView) findViewById(R.id.captionListView);
        View back = findViewById(R.id.captionsBackButton);
        withCaption = new Data(this,"withcaption");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = withCaption.get();
        for( int i=0; i<list.length(); i++ ) {
            if( list.charAt(i) == ',' ) {
                counter++;
            }
        }
        final String[]captionHymns =list.split(",");
        names = new String[counter];
        for(int i=0;i<counter;i++){
            names[i] =captionHymns[i]+". "+ getStringResourceByName("hymn"+captionHymns[i]+"firstline");
        }
        if(counter==0){
            noCapsText.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        }
        else {
            adapter =
                    new MyFavListAdapter(
                            this,
                            names
                    );
            ls.setAdapter(adapter);
            ls.setVisibility(View.VISIBLE);
            noCapsText.setVisibility(View.INVISIBLE);
        }



    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
}
