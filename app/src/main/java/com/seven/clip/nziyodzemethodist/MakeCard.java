package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.parseColor;

public class MakeCard extends AppCompatActivity {

    String text,title,number,RandomAudioFileName = "ABCDEFGHIJKLMNOP", yourimagename,fullPath;
    Data color,textSizeData;
    float textSize;
    TextView cardText,hymnNum,hymnTitle;
    Button shareBut;
    ImageView bg;
    RelativeLayout cardHolder;
    ScaleGestureDetector scaleGestureDetector;
    Random random;
    int click;
    private InterstitialAd mInterstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_card);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2945410942325181/4881702358");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("37AF7727CC5B392008ACD1C889F79F4E")  // An example device ID
                .addTestDevice("6064FEE7D614C18C447097456EC84AC6")
                .build());



        text = getIntent().getStringExtra("text");
        title = getIntent().getStringExtra("title");
        number = getIntent().getStringExtra("number");

        click = 0;

        color = new Data(this,"color");
        textSizeData = new Data(this,"textsize");
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());
        random = new Random();


        cardText = findViewById(R.id.cardText);
        hymnNum = findViewById(R.id.cardHymnNumber);
        hymnTitle = findViewById(R.id.cardHymnName);
        bg = findViewById(R.id.cardDisplayBg);
        cardHolder = findViewById(R.id.cardLayout);
        shareBut = findViewById(R.id.shareCardBut);

        String g = textSizeData.get();
        if(g.equals(""))
            textSize = 40f;
        else
            textSize = Float.valueOf(g);

        number = "Hymn " + number;

        cardText.setText(text);
        hymnNum.setText(number);
        hymnTitle.setText(title);

        cardText.setTextSize(textSize);
        changeColorMode(color.get());

        shareBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                click++;
                share.setType("image/png");
                yourimagename = CreateRandomAudioFileName(5) ;
                cardHolder.setDrawingCacheEnabled(true);
                Bitmap bitmap = cardHolder.getDrawingCache();
                fullPath = getCardDir().getPath() + File.separator + yourimagename + ".png";

                File file = new File(fullPath);
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                    ostream.close();
                    cardHolder.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cardHolder.setDrawingCacheEnabled(false);
                }
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" +fullPath));
                startActivity(Intent.createChooser(share, "Share Image"));
            }
        });
    }

    public File getCardDir() {
        File previewDir = new File(Environment.getExternalStorageDirectory(),"HymnCards");
        if (!previewDir.exists()) previewDir.mkdir();
        return previewDir;
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    public void changeColorMode(String mode){
        switch (mode){
            case "night":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setColorFilter(parseColor("#000000"));
                //display.setBackground(getResources().getDrawable(R.color.black));
                cardText.setTextColor(WHITE);
                hymnNum.setTextColor(WHITE);
                hymnTitle.setTextColor(WHITE);
                break;
            case "day":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setColorFilter(parseColor("#ffffff"));
                cardText.setTextColor(BLACK);
                hymnNum.setTextColor(BLACK);
                hymnTitle.setTextColor(BLACK);
                break;
            case "":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setImageDrawable(getResources().getDrawable(R.drawable.natured_small));
                cardText.setTextColor(WHITE);
                hymnNum.setTextColor(WHITE);
                hymnTitle.setTextColor(WHITE);
                break;
            default:
                bg.animate().alpha(0f).setDuration(2000);
                if(isColorDark(mode)){
                    cardText.setTextColor(WHITE);
                    hymnNum.setTextColor(WHITE);
                    hymnTitle.setTextColor(WHITE);
                } else {
                    cardText.setTextColor(BLACK);
                    hymnNum.setTextColor(BLACK);
                    hymnTitle.setTextColor(BLACK);
                }
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[] {getLighterColor(mode),parseColor(mode),parseColor(mode),parseColor(mode),getDarkerColor(mode)});
                gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                cardHolder.setBackgroundDrawable(gd);
        }
    }
    public boolean isColorDark(String test){
        int color = parseColor(test);
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return darkness >= 0.333;
    }

    public int getLighterColor(String test){
        float[] hsv = new float[3];
        int color = parseColor(test);
        Color.colorToHSV(color, hsv);
        hsv[0] = 1.0f - 0.8f * (1.0f - hsv[0]); // value component
        color = Color.HSVToColor(hsv);
        return color;
    }
    public int getDarkerColor(String test){
        float[] hsv = new float[3];
        int color = parseColor(test);
        Color.colorToHSV(color, hsv);
        hsv[0] += (hsv[0] * 0.2f); // value component
        color = Color.HSVToColor(hsv);
        return color;
    }

    public void changeTextSize(int unit,float value){

        if(value>1f&&value<700f){
            cardText.setTextSize(unit, value);
        }
        textSize = cardText.getTextSize();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return scaleGestureDetector.onTouchEvent(ev);
    }

    public class simpleOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

                float size = cardText.getTextSize();

                Log.d("TextSizeStart", String.valueOf(size));
                float factor = detector.getScaleFactor();
                Log.d("Factor", String.valueOf(factor));
                float product = size*factor;
                Log.d("TextSize", String.valueOf(product));
                changeTextSize(TypedValue.COMPLEX_UNIT_PX, product);

                size = cardText.getTextSize();
                Log.d("TextSizeEnd", String.valueOf(size));

            return true;
        }
    }

    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume(){
        super.onResume();
        if(click>0) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

        }
    }

}
