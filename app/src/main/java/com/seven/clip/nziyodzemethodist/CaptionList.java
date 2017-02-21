package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
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
    MyCaptionListAdapter adapter;
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
        TextView capTitle = (TextView) findViewById(R.id.captionsTitle);
        ls = (ListView) findViewById(R.id.captionsListView);
        View back = findViewById(R.id.captionsBackButton);
        withCaption = new Data(this,"withcaption");

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        capTitle.setTypeface(custom_font);

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
                    new MyCaptionListAdapter(
                            this,
                            names
                    );
            ls.setAdapter(adapter);
            ls.setVisibility(View.VISIBLE);
            noCapsText.setVisibility(View.INVISIBLE);
        }



    }
    @Override
    public void onResume(){
        super.onResume();
        if(ls.getAdapter()!=null){
            ls.invalidateViews();
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
