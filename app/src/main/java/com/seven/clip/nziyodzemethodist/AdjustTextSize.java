package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
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

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        final Intent intent = new Intent();

        getWindow().setLayout((int)(width*.9),(int)(height*.5));

        SeekBar track = findViewById(R.id.textSizeSeekBar);
        final TextView sample = findViewById(R.id.textSizeSample);
        final Button acceptSize = findViewById(R.id.textSizeApplyBut);
        final Data size = new Data(this,"textsize");

        track.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                r=progress+25;
                sample.setText(IntToStr(progress+25));
                sample.setTextSize((float)(progress+25));
                acceptSize.setText("Apply");
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
                if(r==0)
                    r=40;
                size.update(IntToStr(r));
                intent.putExtra("size",r);
                if(r==40)
                QuickToast("Set to default text size");
                else
                QuickToast("Text size changed");
                setResult(2,intent);
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
