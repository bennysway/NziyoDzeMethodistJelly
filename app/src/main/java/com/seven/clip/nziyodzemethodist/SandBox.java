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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sand_box);

    }
}