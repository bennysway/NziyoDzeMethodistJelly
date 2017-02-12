package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class pickNum2 extends AppCompatActivity {

    ListView listView;
    MyNumListAdapter adapter;
    NumListScroll scroll;
    TextView pop, theTextView;
    Handler timer;


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
        pop = (TextView) findViewById(R.id.numScrollPop);
        timer = new Handler();
        scroll = new NumListScroll();
        recordFlag.deleteAll();


        final String [] sample = new String[317];
        for(int i=0;i<317;i++)
            sample[i] = String.valueOf(i+1);


        adapter =
                new MyNumListAdapter(
                        this,
                        sample
                );
        listView = (ListView) findViewById(R.id.numberList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toHymn.putExtra("hymnNum",String.valueOf(i+1));
                startActivityForResult(toHymn,1);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                toFav.putExtra("hymnNum",String.valueOf(i+1));
                startActivityForResult(toFav,1);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_in);
                return true;
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            if(null!=data)
                listView.invalidateViews();
        }

    }
    public Context getActivity() {
        return this;
    }
    public void vis(View v){
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        v.animate().alpha(1f);
    }
    public void invis(final View v) {
        v.animate().alpha(0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void QuickToast(String s) {
        Toast.makeText(getActivity(), s,
                Toast.LENGTH_SHORT).show();
    }
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    scroll.up(listView.getFirstVisiblePosition());
                    showPop(scroll.letter());
                    listView.smoothScrollToPositionFromTop(scroll.pos(),0,1);

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    scroll.down(listView.getLastVisiblePosition());
                    showPop(scroll.letter());
                    listView.smoothScrollToPositionFromTop(scroll.pos(),0,1);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
    public void showPop(String a) {
        Runnable rr = new Runnable() {
            @Override
            public void run() {invis(pop);}
        };
        pop.setText(a);
        timer.removeCallbacks(rr);
        vis(pop);
        timer.postDelayed(rr,3000);
    }







}
