package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecList extends AppCompatActivity {

    ListView ls;
    String list = "";
    Data recordFlag,recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recordFlag = new Data(this,"recordflag");
        recList = new Data(this,"reclist");
        recordFlag.deleteAll();


        TextView norecText = (TextView) findViewById(R.id.norecText);
        TextView recTitle = (TextView) findViewById(R.id.recentsTitle);
        View back = findViewById(R.id.recentBackButton);
        ls = (ListView) findViewById(R.id.RecListView);
        final Intent toHome = new Intent(this,MainDrawer.class);
        FloatingActionButton del = (FloatingActionButton) findViewById(R.id.deleteHistory);
        int counter = 0;

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        recTitle.setTypeface(custom_font);

        list =recList.get();
        for( int i=0; i<list.length(); i++ ) {
            if( list.charAt(i) == ',' ) {
                counter++;
            }
        }
        final String[]recHymns =list.split(",");
        String[] names = new String[counter];
        for(int i=0;i<counter;i++){
            names[i] =recHymns[i]+". "+ getStringResourceByName("hymn"+recHymns[i]+"firstline");
        }

        if(counter==0){
            norecText.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        }
        else {
            MyRecListAdapter adapter =
                    new MyRecListAdapter(
                            this,
                            names
                    );
            ls.setAdapter(adapter);
            ls.setVisibility(View.VISIBLE);
            norecText.setVisibility(View.INVISIBLE);
        }
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recList.deleteAll();
                QuickToast("History has been cleared.");
                startActivity(toHome);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }


}
