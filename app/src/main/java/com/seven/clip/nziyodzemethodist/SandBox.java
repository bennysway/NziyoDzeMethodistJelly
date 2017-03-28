package com.seven.clip.nziyodzemethodist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SandBox extends Activity {

    String newHymn;
    TextView testString;
    hymnNavigate navigate;
    int backCountNum;
    Data favIterator;
    Data recIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sand_box);

        testString = (TextView) findViewById(R.id.sandyText);
        final Button next = (Button) findViewById(R.id.sandboxNext);
        final Button prev = (Button) findViewById(R.id.sandboxPrev);
        favIterator = new Data(this,"faviterator");
        recIterator = new Data(this,"reciterator");
        final Data favList = new Data(this, "favlist");
        final Data recList = new Data(this, "reclist");
        navigate = new hymnNavigate(favList.get(),recList.get(),favIterator.get(),recIterator.get());

        final String num = getIntent().getStringExtra("hymnNum");
        final String hymn = "hymn " + num + " opened";
        testString.setText(hymn);

        next.setText(navigate.nextText());
        prev.setText(navigate.prevText());
        if(favIterator.get().equals(""))
            favIterator.update("0");
        if(recIterator.get().equals(""))
            recIterator.update("0");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run(navigate.next());
                if(navigate.changes()){
                    if(navigate.isFavChanged())
                        favIterator.update(String.valueOf(Integer.valueOf(favIterator.get())+1));
                    else
                        recIterator.update(String.valueOf(Integer.valueOf(recIterator.get())-1));
                    navigate.update(favIterator.get(),recIterator.get());
                    next.setText(navigate.nextText());
                    prev.setText(navigate.prevText());

                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run(navigate.prev());
                if(navigate.changes()){
                    if(navigate.isFavChanged())
                        favIterator.update(String.valueOf(Integer.valueOf(favIterator.get())-1));
                    else
                        recIterator.update(String.valueOf(Integer.valueOf(recIterator.get())+1));
                    navigate.update(favIterator.get(),recIterator.get());
                    next.setText(navigate.nextText());
                    prev.setText(navigate.prevText());
                }
            }
        });




    }
    public void run(String a) {
        if(a.length()<4)
            testString.setText("hymn " + a + " opened");
        else
            testString.setText("no navigation");
    }
    @Override
    public void onPause() {
        super.onPause();
        favIterator.update("0");
        recIterator.update("0");
    }



}