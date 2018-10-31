package com.seven.clip.nziyodzemethodist;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.seven.clip.nziyodzemethodist.adapters.HomeListAdapter;
import com.seven.clip.nziyodzemethodist.bidirectionalPager.VerticalViewPager;
import com.seven.clip.nziyodzemethodist.bidirectionalPager.ViewPagerAdapter;
import com.seven.clip.nziyodzemethodist.models.HomeListItem;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;

import java.util.ArrayList;

public class SandBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sand_box);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
}
