package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MakeFav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_fav);

        final String s = getIntent().getStringExtra("hymnNum");
        TextView prompt = (TextView) findViewById(R.id.makeFavPrompt);
        Button yes = (Button) findViewById(R.id.makeFavYesBut);
        Button no = (Button) findViewById(R.id.makeFavNoBut);
        final Intent addFav = new Intent(this,FavList.class);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.2));

        prompt.setText("Add hymn " + s + " to favourites?" );


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFav.putExtra("hymnNum",s);
                addFav.putExtra("push","yes");
                startActivity(addFav);
            }
        });


    }

}
