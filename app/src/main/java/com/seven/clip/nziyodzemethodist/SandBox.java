package com.seven.clip.nziyodzemethodist;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SandBox extends AppCompatActivity {

    SandBoxView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new SandBoxView(this);
        setContentView(view);
    }
}
