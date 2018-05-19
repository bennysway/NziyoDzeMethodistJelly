package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.futuremind.recyclerviewfastscroll.RecyclerViewScrollListener;
import com.xenione.libs.swipemaker.AbsCoordinatorLayout;

import java.util.Arrays;

public class HymnList extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyHymnListRVAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    FastScroller mFastScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Data recFlag = new Data(this, "recordflag");
        recFlag.deleteAll();


        final TextView topTitle = findViewById(R.id.hymnListTitle);
        View back = findViewById(R.id.hymnListBackButton);
        //Hymns hymns = new Hymns(this);
        xHymns hymns = new xHymns(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/bh.ttf");
        topTitle.setTypeface(custom_font);

        final String[] sample = hymns.getAllHymns(false);
        Arrays.sort(sample);

        mRecyclerView = findViewById(R.id.hymnList);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mFastScroll = findViewById(R.id.hymnListShonaFastScroller);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyHymnListRVAdapter(sample,this);
        mRecyclerView.setAdapter(mAdapter);
        mFastScroll.setRecyclerView(mRecyclerView);

    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

}
