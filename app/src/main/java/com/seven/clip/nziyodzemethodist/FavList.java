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

public class FavList extends AppCompatActivity {

    ListView ls;
    String list;
    MyFavListAdapter adapter;
    Data favList;
    String[] names;
    int counter = 0;
    TextView nofavText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nofavText = (TextView) findViewById(R.id.nofavText);
        TextView favTitle = (TextView) findViewById(R.id.favTitle);
        ls = (ListView) findViewById(R.id.FavListView);
        View back = findViewById(R.id.favBackBut);
        final Intent toHymn = new Intent(this,hymnDisplay.class);
        final Intent toRemoveFav = new Intent(this,removeFav.class);
        toRemoveFav.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        favList = new Data(this,"favlist");

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        favTitle.setTypeface(custom_font);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list = favList.get();
        for( int i=0; i<list.length(); i++ ) {
            if( list.charAt(i) == ',' ) {
                counter++;
            }
        }
        final String[]favHymns =list.split(",");
        names = new String[counter];
        for(int i=0;i<counter;i++){
            names[i] =favHymns[i]+". "+ getStringResourceByName("hymn"+favHymns[i]+"firstline");
        }
        if(counter==0){
            nofavText.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        }
        else {
            adapter =
                    new MyFavListAdapter(
                            this,
                            names
                    );
            ls.setAdapter(adapter);
            ls.setVisibility(View.VISIBLE);
            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent toRemoveFav = new Intent(FavList.this,removeFav.class);
                    toRemoveFav.putExtra("hymnNum",favHymns[position]);
                    startActivityForResult(toRemoveFav,2);
                }
            });
            nofavText.setVisibility(View.INVISIBLE);
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
