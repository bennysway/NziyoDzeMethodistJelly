package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FavList extends AppCompatActivity {

    ListView ls;
    String list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView nofavText = (TextView) findViewById(R.id.nofavText);
        ls = (ListView) findViewById(R.id.FavListView);
        final Intent toHymn = new Intent(this,hymnDisplay.class);
        final Intent toRemoveFav = new Intent(this,removeFav.class);
        toRemoveFav.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Data favList = new Data(this,"favlist");





        String s = getIntent().getStringExtra("hymnNum");
        String push = getIntent().getStringExtra("push");
        int counter = 0;
        list = favList.get();



        switch (push){
            case "yes":
                list = favList.pushBack(s);
                QuickToast("Added hymn " + s);
                break;
            case "no":
                //QuickToast("got no");
                break;
            default:
                //QuickToast("Empty push");

        }





        for( int i=0; i<list.length(); i++ ) {
            if( list.charAt(i) == ',' ) {
                counter++;
            }
        }
        final String[]favHymns =list.split(",");
        String[] names = new String[counter];
        for(int i=0;i<counter;i++){
            names[i] =favHymns[i]+". "+ getStringResourceByName("hymn"+favHymns[i]+"firstline");
        }

        if(counter==0){
            nofavText.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        }
        else {
            adapter =
                    new ArrayAdapter<String>(
                            this,
                            R.layout.hymn_list,R.id.hymnFirstLinebut,
                            names
                    );
            ls.setAdapter(adapter);
            ls.setVisibility(View.VISIBLE);
            nofavText.setVisibility(View.INVISIBLE);
        }


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toHymn.putExtra("hymnNum",favHymns[i]);
                startActivity(toHymn);
            }
        });

        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                toRemoveFav.putExtra("hymnNum",favHymns[i]);
                startActivity(toRemoveFav);
                return true;
            }
        });





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
