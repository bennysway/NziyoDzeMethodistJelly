package com.seven.clip.nziyodzemethodist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AdjustTextSize extends AppCompatActivity {

    int r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_text_size);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.5));

        SeekBar track = (SeekBar)findViewById(R.id.textSizeSeekBar);
        final TextView sample = (TextView)findViewById(R.id.textSizeSample);
        Button acceptSize = (Button) findViewById(R.id.textSizeApplyBut);
        Button defaultSize = (Button) findViewById(R.id.textSizeDefaultBut);
        final Data size = new Data(this,"textsize");

        track.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                r=progress+25;
                sample.setText(IntToStr(progress+25));
                sample.setTextSize((float)(progress+25));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        acceptSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size.update(IntToStr(r));
                QuickToast("Text size set to "+IntToStr(r)+" sp");
                finish();

            }
        });
        defaultSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size.update("40");
                QuickToast("Text size set to 40 sp");
                finish();

            }
        });



    }

    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    public String IntToStr(int i){
        return Integer.toString(i);
    }


}
