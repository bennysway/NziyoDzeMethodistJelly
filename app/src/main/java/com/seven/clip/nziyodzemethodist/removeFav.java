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

public class removeFav extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_fav);
        intent = new Intent(this,FavList.class);
        final Data favList = new Data(this,"favlist");

        final String s = getIntent().getStringExtra("hymnNum");
        TextView prompt = (TextView) findViewById(R.id.removeFavPrompt);
        Button yes = (Button) findViewById(R.id.removeFavYesBut);
        Button no = (Button) findViewById(R.id.removeFavNoBut);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        prompt.setTypeface(custom_font);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.2));

        prompt.setText("Remove hymn " + s + " from favourites?" );


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favList.delete(s);
                setResult(2,intent);
                finish();
            }
        });


    }

}
