package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class hymnList2 extends AppCompatActivity {

    ListView listView;
    HymnListScroll scroll;
    TextView pop;
    Handler timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_list2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Intent toFav = new Intent(this,MakeFav.class);
        toFav.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Data recFlag = new Data(this, "recordflag");
        Data favIt = new Data(this,"faviterator");
        Data recIt = new Data(this,"reciterator");
        favIt.update("0");
        recIt.update("0");
        recFlag.deleteAll();



        pop = (TextView) findViewById(R.id.hymnScrollPop);
        TextView topTitle = (TextView) findViewById(R.id.hymnListTitle);
        timer = new Handler();
        scroll = new HymnListScroll();
        View back = findViewById(R.id.hymnListBackButton);
        Hymns hymns = new Hymns(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        topTitle.setTypeface(custom_font);

        final String [] sample = hymns.getAllHymns();
        Arrays.sort(sample);

        MyHymnListAdapter adapter =
                new MyHymnListAdapter(
                        this,
                        sample
                );
        listView = (ListView) findViewById(R.id.hymnList);
        listView.setAdapter(adapter);
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

    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    scroll.up(listView.getFirstVisiblePosition());
                    showPop(scroll.letter());
                    listView.smoothScrollToPositionFromTop(scroll.pos(),0,400);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    scroll.down(listView.getFirstVisiblePosition());
                    showPop(scroll.letter());
                    listView.smoothScrollToPositionFromTop(scroll.pos(),0,100);
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

    public Context getActivity() {
        return this;
    }
    public void QuickToast(String s){
        Toast.makeText(getActivity(), s,
                Toast.LENGTH_SHORT).show();
    }

}
