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
import android.widget.Toast;

public class MakeFav extends AppCompatActivity {

    Intent MakeFavIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_fav);

        final String s = getIntent().getStringExtra("hymnNum");
        TextView prompt = (TextView) findViewById(R.id.makeFavPrompt);
        Button yes = (Button) findViewById(R.id.makeFavYesBut);
        Button no = (Button) findViewById(R.id.makeFavNoBut);
        final Intent addFav = new Intent(this,FavList.class);
        MakeFavIntent = new Intent();
        final Data favList = new Data(this,"favlist");

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        prompt.setTypeface(custom_font);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.2));

        if(favList.find(s)){
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
                    MakeFavIntent.putExtra("isFav",false);
                    MakeFavIntent.putExtra("hymnNum",s);
                    setResult(1,MakeFavIntent);
                    favList.delete(s);
                    QuickToast("Removed hymn " + s);
                    finish();
                }
            });
        }
        else {
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
                    MakeFavIntent.putExtra("isFav",true);
                    MakeFavIntent.putExtra("hymnNum",s);
                    setResult(1,MakeFavIntent);
                    favList.pushBack(s);
                    QuickToast("Added hymn " + s);
                    finish();
                }
            });
        }








    }
    public void QuickToast(String s) {
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }


}
