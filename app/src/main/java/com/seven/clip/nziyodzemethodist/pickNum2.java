package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class pickNum2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_num2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Intent toHymn = new Intent(this,hymnDisplay.class);
        final Intent toFav = new Intent(this,MakeFav.class);
        toFav.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Data recordFlag = new Data(this,"recordflag");
        recordFlag.deleteAll();





        final String [] sample = new String[317];
        for(int i=0;i<317;i++)
            sample[i] = String.valueOf(i+1);


        MyNumAdapter adapter =
                new MyNumAdapter(
                        this,
                        sample
                );
        ListView listView = (ListView) findViewById(R.id.numberList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toHymn.putExtra("hymnNum",String.valueOf(i+1));
                startActivity(toHymn);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                toFav.putExtra("hymnNum",String.valueOf(i+1));
                startActivity(toFav);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_in);
                return true;
            }
        });



    }

    public Context getActivity() {
        return this;
    }
    public void QuickToast(String s) {
        Toast.makeText(getActivity(), s,
                Toast.LENGTH_SHORT).show();
    }





}
