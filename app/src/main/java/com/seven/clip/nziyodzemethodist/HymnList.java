package com.seven.clip.nziyodzemethodist;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.Arrays;

public class HymnList extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyHymnListRVAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    //FastScroller mFastScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_list);
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
        //mFastScroll = findViewById(R.id.hymnListShonaFastScroller);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyHymnListRVAdapter(sample,this);
        mRecyclerView.setAdapter(mAdapter);
        //mFastScroll.setRecyclerView(mRecyclerView);

    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

}
