package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class TextSizePrompt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_size_prompt);

        TextView prompt = (TextView) findViewById(R.id.changeTextPrompt);
        Button yes = (Button) findViewById(R.id.changeTextYesBut);
        Button no = (Button) findViewById(R.id.changeTextNoBut);

        final String value = String.valueOf(getIntent().getFloatExtra("size",40f));

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        prompt.setTypeface(custom_font);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        final Data size = new Data(this,"textsize");

        getWindow().setLayout((int)(width),(int)(height*.4));

        prompt.setText("Do you want to keep the Text size you have just changed?" );
        prompt.setTextSize(40f);


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size.update(value);
                finish();
            }
        });
    }
}
