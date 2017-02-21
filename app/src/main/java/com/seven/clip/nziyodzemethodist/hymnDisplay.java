package com.seven.clip.nziyodzemethodist;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.adcolony.sdk.AdColony;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.parseColor;

public class hymnDisplay extends AppCompatActivity {
    ScrollView myScroll;
    int bound,totalHeight,clength,textcolor,capColor,hymnnumColor,length,buttonLayoutBg,type,hasOptions=0,optionsSet=0;
    Boolean chorusAvail = false, isInEnglish = false,isRecording = false;
    ImageView bg;
    float textSize;
    RelativeLayout display;
    TextView captionShow,number;
    TextView [] pairs;
    String [] captionStrings;
    Button showCaption;
    View loadCaption;
    boolean favBool,shareBool,optBool,textSizeChanged,menuOpen,favInit, chorusTrasparent;
    long starttime = 0;
    String AudioSavePathInDevice = null,RandomAudioFileName = "ABCDEFGHIJKLMNOP",hymnNum,capStoreKey,s,t,safe;
    MediaRecorder mediaRecorder;
    Random random;
    public static final int RequestPermissionCode = 1;
    Data favList,recordFlag,color;
    Zvinokosha access;
    ScaleGestureDetector scaleGestureDetector;
    Intent hymnDisplayIntent;

    RelativeLayout opt,fav,shr,optfont,shrapp,optnight,shrstanza,optcaptions,shrwhole,opten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_display);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        favList = new Data(this,"favlist");
        Data recList = new Data(this,"reclist");
        color = new Data(this,"color");
        Data textSizeData = new Data(this,"textsize");
        recordFlag = new Data(this,"recordflag");
        hymnDisplayIntent = new Intent();
        final EnResource engCheck = new EnResource(this);
        access = new Zvinokosha(this);

        s = getIntent().getStringExtra("hymnNum");
        type = getIntent().getIntExtra("hymnType",0);

        safe = NumToWord.convert(StrToInt(s)) + "key";
        capStoreKey = safe;
        t = "hymn" + s + "firstline";
        String h = "hymn" + s ;
        String c = "hymn" + s + "caption";

        String [] hymn;
        final TextView chorus = (TextView) findViewById(R.id.chorusFloat);

        captionShow = (TextView) findViewById(R.id.caption);
        number = (TextView) findViewById(R.id.hymnDisplayNum);
        TextView title = (TextView) findViewById(R.id.hymnTitle);
        final TextView hymnpop = (TextView) findViewById(R.id.hymnDisplayNumlight);
        LinearLayout hymnDispl = (LinearLayout) findViewById(R.id.hymnLayout);
        myScroll = (ScrollView) findViewById(R.id.scrollHymn);
        showCaption = (Button) findViewById(R.id.showCaption);
        loadCaption =findViewById(R.id.loadCaption);
        final Intent toPasteBin = new Intent(this,ShareCustom.class);
        bg = (ImageView) findViewById(R.id.imageView);
        display = (RelativeLayout) findViewById(R.id.activity_hymn_display);
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());

        final View favToggle = findViewById(R.id.hymnFavBut);
        final View favToggle_on = findViewById(R.id.hymnFavBut_on);
        final View shareBut = findViewById(R.id.hymnShareBut);
        final View shareBut_on = findViewById(R.id.hymnShareBut_on);
        final View butopt =findViewById(R.id.hymnMoreOptions);
        final View butopt_on =findViewById(R.id.hymnMoreOptions_on);
        final ImageView engBut = (ImageView) findViewById(R.id.hymnMoreOptionsEnglish);
        opt = (RelativeLayout) findViewById(R.id.hymnMoreOptionsLayout);
        fav = (RelativeLayout) findViewById(R.id.hymnFavButLayout);
        shr = (RelativeLayout) findViewById(R.id.hymnShareButLayout);
        optfont = (RelativeLayout) findViewById(R.id.hymnMoreOptionsFontLayout);
        shrapp = (RelativeLayout) findViewById(R.id.hymnShareButAppLayout);
        optnight = (RelativeLayout) findViewById(R.id.hymnMoreOptionsNightLayout);
        shrstanza = (RelativeLayout) findViewById(R.id.hymnShareButStanzaLayout);
        optcaptions = (RelativeLayout) findViewById(R.id.hymnMoreOptionsCaptionsLayout);
        shrwhole = (RelativeLayout) findViewById(R.id.hymnShareButWholeLayout);
        opten = (RelativeLayout) findViewById(R.id.hymnMoreOptionsEnglishLayout);

        opt.setVisibility(View.INVISIBLE);
        fav.setVisibility(View.INVISIBLE);
        shr.setVisibility(View.INVISIBLE);
        optcaptions.setVisibility(View.GONE);

        opt.setY(130);
        fav.setY(130);
        shr.setY(130);

        textSizeChanged = false;
        menuOpen = false;
        chorusTrasparent = false;


        switch (type){
            case 1:
                h = "en" + h;
                t = "en" + t;
                isInEnglish = true;
                optionsSet=1;
                break;
            case 2:
                Intent toCaptions = new Intent(hymnDisplay.this,Captions.class);
                toCaptions.putExtra("hymnNumWord",safe);
                toCaptions.putExtra("hymnNum",s);
                finish();
                startActivity(toCaptions);
                break;
            default:
                recList.pushFront(s);
                break;

        }



        int resourceId = getResourceId(h,"array",getPackageName());
        int captionResourceId = getResourceId(c,"array",getPackageName());
        title.setText(getResourceId(t,"string",getPackageName()));
        captionStrings = getResources().getStringArray(captionResourceId);
        hymn = getResources().getStringArray(resourceId);
        length =getResources().getStringArray(getResourceId(h,"array",getPackageName())).length;
        clength =getResources().getStringArray(getResourceId(c,"array",getPackageName())).length;
        number.setText(s);
        hymnpop.setText(s);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        title.setTypeface(custom_font);
        number.setTypeface(custom_font);
        hymnpop.setTypeface(custom_font);





        String g = textSizeData.get();
        if(g.equals(""))
            textSize = 40f;
        else
            textSize = Float.valueOf(g);
        String g1 = recordFlag.get();
        if (g1.equals("true")){
            inflateBottomRecordingToolbar(getIntent().getStringExtra("hymnNumWord"));
        }


        changeColorMode(color.get());
        pairs=new TextView[length];
        for(int l=0; l<length; l++)
        {
            pairs[l] = new TextView(this);
            pairs[l].setTextSize(textSize);
            pairs[l].setId(l);
            pairs[l].setGravity(Gravity.CENTER);
            pairs[l].setTextColor(textcolor);
            pairs[l].setText(hymn[l]);
            hymnDispl.addView(pairs[l]);
        }



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                totalHeight = myScroll.getChildAt(0).getHeight();
                bound =  (totalHeight/length);
                String check =pairs[0].getText().toString();
                if(check.contains("chorus")){
                    chorus.setText(check.substring(check.lastIndexOf("chorus")));
                    chorusAvail = true;
                }

            }
        },300);


        makeCaption();
        showCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCaption();
                showCaption.setVisibility(View.INVISIBLE);
                loadCaption.setVisibility(View.INVISIBLE);

            }
        });

        if(engCheck.isEn(s))
            engBut.setBackgroundDrawable(getResources().getDrawable(R.drawable.en_avail));


        if(length==1)
            BottomMenu(menuOpen);

        myScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = myScroll.getScrollY(); // For ScrollView
                View view =  myScroll.getChildAt(myScroll.getChildCount() - 1);
                int diff = (view.getBottom() - (myScroll.getHeight() + myScroll.getScrollY()));

                if(scrollY>bound&&chorusAvail){
                    chorus.setVisibility(View.VISIBLE);
                    hymnpop.setVisibility(View.VISIBLE);
                }
                else {
                    chorus.setVisibility(View.INVISIBLE);
                    hymnpop.setVisibility(View.INVISIBLE);

                }
                if(diff==0)
                    BottomMenu(false);
                else
                    BottomMenu(true);
            }
        });


        if(favList.find(s)){
            invis(favToggle);
            vis(favToggle_on);
            favBool = true;
        }
        else {
            invis(favToggle_on);
            vis(favToggle);
            favBool = false;
        }
        favInit = favBool;

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favBool){
                    vis(favToggle);
                    invis(favToggle_on);
                    favBool = false;
                }
                else {
                    vis(favToggle_on);
                    invis(favToggle);
                    favBool = true;
                }
            }
        });

        chorus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chorusTrasparent){
                    chorus.animate().alpha(1f);
                    chorusTrasparent = false;
                }
                else {
                    chorus.animate().alpha(.0f);
                    chorusTrasparent = true;
                }
            }
        });

        shareBool = false;
        invis(shareBut_on);
        vis(shareBut);
        shr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shareBool){
                    vis(shareBut);
                    invis(shareBut_on);
                    floatDownButtons(shrapp,0);
                    floatDownButtons(shrstanza,100);
                    floatDownButtons(shrwhole,200);

                    shareBool=false;
                }
                else {
                    vis(shareBut_on);
                    invis(shareBut);
                    floatUpButtons(shrapp,100);
                    floatUpButtons(shrstanza,50);
                    floatUpButtons(shrwhole,0);
                    shareBool=true;
                }

            }
        });

        optBool = false;
        invis(butopt_on);
        vis(butopt);
        opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optBool){
                    invis(butopt_on);
                    vis(butopt);
                    floatDownButtons(opten,0);
                    floatDownButtons(optcaptions,100);
                    floatDownButtons(optnight,200);
                    floatDownButtons(optfont,300);
                    optBool=false;
                }
                else {
                    vis(butopt_on);
                    invis(butopt);
                    floatUpButtons(opten,150);
                    floatUpButtons(optcaptions,100);
                    floatUpButtons(optnight,50);
                    floatUpButtons(optfont,0);
                    optBool=true;
                }

            }
        });

        shrapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vis(shareBut);
                invis(shareBut_on);
                floatDownButtons(shrapp,0);
                floatDownButtons(shrstanza,100);
                floatDownButtons(shrwhole,200);
                shareBool=false;
                shareAppFunc();
            }
        });

        shrstanza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                vis(shareBut);
                invis(shareBut_on);
                floatDownButtons(shrapp,0);
                floatDownButtons(shrstanza,100);
                floatDownButtons(shrwhole,200);

                shareBool=false;
                for(int l=0; l<length; l++)
                    text = text+String.valueOf(l+1)+". "+pairs[l].getText()+"\n";
                toPasteBin.putExtra("text",text);
                startActivity(toPasteBin);

            }
        });

        shrwhole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                vis(shareBut);
                invis(shareBut_on);
                floatDownButtons(shrapp,0);
                floatDownButtons(shrstanza,100);
                floatDownButtons(shrwhole,200);

                shareBool=false;
                for(int l=0; l<length; l++)
                    text = text+String.valueOf(l+1)+". "+pairs[l].getText()+"\n";
                copy(text,s);
            }
        });

        optnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toColor = new Intent(hymnDisplay.this, ColorMode.class);
                startActivityForResult(toColor,3);
                invis(butopt_on);
                vis(butopt);
                floatDownButtons(opten,0);
                floatDownButtons(optcaptions,100);
                floatDownButtons(optnight,200);
                floatDownButtons(optfont,300);

                optBool=false;
            }
        });

        optfont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTextSize = new Intent(hymnDisplay.this, AdjustTextSize.class);
                startActivityForResult(toTextSize,2);
                invis(butopt_on);
                vis(butopt);
                floatDownButtons(opten,0);
                floatDownButtons(optcaptions,100);
                floatDownButtons(optnight,200);
                floatDownButtons(optfont,300);
                optBool=false;

            }
        });

        optcaptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaptions = new Intent(hymnDisplay.this,Captions.class);
                toCaptions.putExtra("hymnNumWord",safe);
                toCaptions.putExtra("hymnNum",s);
                toCaptions.putExtra("isEn",optionsSet);
                invis(butopt_on);
                vis(butopt);
                floatDownButtons(opten,0);
                floatDownButtons(optcaptions,100);
                floatDownButtons(optnight,200);
                floatDownButtons(optfont,300);
                optBool=false;
                startActivity(toCaptions);
            }
        });

        if(engCheck.isEn(s)){
            if(optionsSet==0){
                opten.setAlpha(1f);
                engBut.setAlpha(1f);
            }
        }
        opten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsSet==0){
                    if(engCheck.isEn(s)) {
                        if (access.hasAccess()) {
                            Intent enIntent = new Intent(hymnDisplay.this, hymnDisplay.class);
                            invis(butopt_on);
                            vis(butopt);
                            floatDownButtons(opten, 0);
                            floatDownButtons(optcaptions, 100);
                            floatDownButtons(optnight, 200);
                            floatDownButtons(optfont, 300);
                            optBool = false;
                            enIntent.putExtra("hymnType", 1);
                            enIntent.putExtra("hymnNum", s);
                            startActivity(enIntent);
                        }
                        if (!access.hasAccess()) {
                            Intent toDisplay = new Intent(hymnDisplay.this, MainDrawer.class);
                            toDisplay.putExtra("option", 1);
                            QuickToast("Activate this feature in the menu on the Main Screen of the hymnbook");
                            startActivity(toDisplay);
                            hasOptions = 1;
                        }
                    }
                    else
                        QuickToast("Hymn not available in english yet");
                }
            }
        });

        opt.setBackgroundColor(buttonLayoutBg);
        fav.setBackgroundColor(buttonLayoutBg);
        shr.setBackgroundColor(buttonLayoutBg);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==3)
        {
            if(null!=data)
            {
                String fromData=data.getStringExtra("mode");
                changeColorMode(fromData);
                for(int l=0; l<length; l++)
                {
                    pairs[l].setAlpha(0f);
                    pairs[l].setTextColor(textcolor);
                    pairs[l].animate().alpha(1f).setDuration(700*l);
                }
                opt.setBackgroundColor(buttonLayoutBg);
                fav.setBackgroundColor(buttonLayoutBg);
                shr.setBackgroundColor(buttonLayoutBg);

            }
        }

        if(requestCode==2)
        {
            if(null!=data)
            {
                int fromData=data.getIntExtra("size",40);
                changeTextSize(fromData);
                textSizeChanged = false;
            }
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(favInit!=favBool) {
            saveFavState();
            hymnDisplayIntent.putExtra("hymnNum",s);
            setResult(1,hymnDisplayIntent);
        }
        favInit = favBool;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(hasOptions==1)
            finish();
    }

    public void changeTextSize(float value){
        for(int l=0; l<length; l++)
        {
            pairs[l].setAlpha(0f);
            pairs[l].setTextSize(value);
            pairs[l].animate().alpha(1f).setDuration(700*l);
        }
    }
    public void changeTextSize(int unit,float value){
        for(int l=0; l<length; l++){
            if(value>1f&&value<700f)
                pairs[l].setTextSize(unit, value);
        }
        textSizeChanged=true;
        textSize = pairs[0].getTextSize();
    }
    public void changeColorMode(String mode){
        switch (mode){
            case "night":
                bg.setImageDrawable(getResources().getDrawable(R.color.black));
                display.setBackground(getResources().getDrawable(R.color.black));
                hymnnumColor = parseColor("#50ffffff");
                buttonLayoutBg = parseColor("#70ffffff");
                textcolor= capColor = WHITE;
                break;
            case "day":
                bg.setImageDrawable(getResources().getDrawable(R.color.white));
                display.setBackground(getResources().getDrawable(R.color.white));
                textcolor= capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
                buttonLayoutBg = parseColor("#70000000");
                break;
            default:
                bg.setImageDrawable(getResources().getDrawable(R.drawable.natured));
                textcolor = WHITE;
                capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
                buttonLayoutBg = parseColor("#70000000");

        }
        number.setTextColor(hymnnumColor);
    }
    public void floatUpButtons(final View v, int time){
        Handler vHandle = new Handler();
        vHandle.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setAlpha(0f);
                v.setY(70);
                vis(v);
                v.animate().alpha(1f).y(-5).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        v.animate().y(0);
                    }
                });
            }
        },time);

    }
    public void floatDownButtons(final View v, int time){
        Handler vHandle = new Handler();
        vHandle.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setAlpha(1f);
                v.setY(0);
                v.animate().y(-10).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        v.animate().alpha(0f).y(70).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                invis(v);
                            }
                        });
                    }
                });
            }
        },time);
    }
    public void BottomMenu(boolean state){
        if(state){
            Handler viewDrawer = new Handler();
            viewDrawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    floatDownButtons(optcaptions,0);
                    floatDownButtons(optnight,0);
                    floatDownButtons(optfont,0);
                    floatDownButtons(opten,0);
                    opt.setVisibility(View.VISIBLE);
                    opt.animate().y(100f);
                }
            },0);
            viewDrawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fav.setVisibility(View.VISIBLE);
                    fav.animate().y(100f);
                }
            },100);
            viewDrawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    floatDownButtons(shrapp,0);
                    floatDownButtons(shrstanza,0);
                    floatDownButtons(shrwhole,0);
                    shr.setVisibility(View.VISIBLE);
                    shr.animate().y(100f);
                }
            },200);
            menuOpen = false;
        }
        else
        {
            Handler viewDrawer = new Handler();
            viewDrawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    opt.setVisibility(View.VISIBLE);
                    opt.animate().y(0f);
                    if(optBool){
                        floatUpButtons(opten,150);
                        floatUpButtons(optcaptions,100);
                        floatUpButtons(optnight,50);
                        floatUpButtons(optfont,0);
                    }
                }
            },0);
            viewDrawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fav.setVisibility(View.VISIBLE);
                    fav.animate().y(0f);
                }
            },100);
            viewDrawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    shr.setVisibility(View.VISIBLE);
                    shr.animate().y(0f);
                    if(shareBool){
                        floatUpButtons(shrapp,100);
                        floatUpButtons(shrstanza,50);
                        floatUpButtons(shrwhole,0);
                    }
                }
            },200);
            menuOpen = true;
        }

    }
    public void saveFavState(){
        if(favBool)
            favList.pushBack(s);
        else
            favList.delete(s);
    }
    public static void vis(View v){
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        v.animate().alpha(1f);
    }
    public static void invis(final View v){
        v.animate().alpha(0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.INVISIBLE);
            }
        });


    }
    public void makeCaption(){
        for(int f = 0; f<clength; f++){
            Handler but = new Handler();
            Handler timer = new Handler();
            final int finalF = f;
            timer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    captionShow.setY(-50f);
                    captionShow.setText(captionStrings[finalF]);
                    captionShow.setTextColor(capColor);
                    captionShow.animate().y(0f).setStartDelay(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            captionShow.animate().y(50f).setStartDelay(2000);
                        }
                    });

                }
            },(f*3000));
            but.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showCaption.setVisibility(View.VISIBLE);
                    loadCaption.setVisibility(View.VISIBLE);

                }
            },(clength*3000));
        }
    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    public int StrToInt(String s){
        return Integer.valueOf(s);
    }
    public int getResourceId(String pVariableName, String pResourcename, String pPackageName){
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void shareAppFunc(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Nziyo DzeMethodist");
            String sAux = "Methodist Church in Zimbabwe\nNziyo dzeMethodist\nHymnBook\nNow available on Google Play\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.seven.clip.nziyodzemethodist \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share the Gospel:"));
        } catch(Exception e) {
            //e.toString();
        }

    }
    public void copy(String s,String num){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Hymn", s);
        clipboard.setPrimaryClip(clip);
        QuickToast("Hymn copied to clipboard");
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Nziyo DzeMethodist");
            i.putExtra(Intent.EXTRA_TEXT,"Hymn "+num+"\n" +
                    s+ "\n" +
                    "\n" +
                    "Shared via Nziyo DzeMethodist\n" +
                    "https://play.google.com/store/apps/details?id=com.seven.clip.nziyodzemethodist");
            startActivity(Intent.createChooser(i, "Share Hymn via:"));
        } catch(Exception e) {
            //e.toString();
        }
    }
    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
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
    private void requestPermission() {
        ActivityCompat.requestPermissions(hymnDisplay.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(hymnDisplay.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(hymnDisplay.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(favInit!=favBool) {
                saveFavState();
                hymnDisplayIntent.putExtra("isFav",favBool);
                hymnDisplayIntent.putExtra("hymnNum",s);
                setResult(1,hymnDisplayIntent);
            }
            favInit = favBool;
            if(textSizeChanged){
                Intent toTextPrompt = new Intent(this,TextSizePrompt.class);
                toTextPrompt.putExtra("size",textSize);
                startActivity(toTextPrompt);
            }
            if(isRecording){
                mediaRecorder.stop();
                isRecording = false;
            }
            recordFlag.deleteAll();
            finish();

        }
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0){
            BottomMenu(menuOpen);
        }
        return true;
    }
    public String timeStamp(){
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy HH:mm", Locale.US);
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }
    public void inflateBottomRecordingToolbar(final String key){

        RelativeLayout parent = (RelativeLayout) findViewById(R.id.activity_hymn_display);
        View bar = getLayoutInflater().inflate(R.layout.record_bar, parent,false);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, bar.getId());
        bar.setLayoutParams(params);
        parent.addView(bar);

        bar.setAlpha(0f);
        bar.animate().alpha(1f).setDuration(500);


        final ImageView rec,play,stop,delete,noRec,noDel;
        final TextView text = (TextView) findViewById(R.id.recordingBarText);
        rec = (ImageView) findViewById(R.id.recordBut);
        play = (ImageView) findViewById(R.id.playRecordingBut);
        stop = (ImageView) findViewById(R.id.stopRecordingBut);
        delete = (ImageView) findViewById(R.id.deleteRecording);
        noRec = (ImageView) findViewById(R.id.recordUnavailBut);
        noDel = (ImageView) findViewById(R.id.deleteUnavailRecording);

        invis(play);
        invis(delete);
        vis(noDel);
        random = new Random();

        final Handler h2 = new Handler();
        final Runnable run = new Runnable() {

            @Override
            public void run() {
                long millis = System.currentTimeMillis() - starttime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds     = seconds % 60;
                text.setText(String.format(Locale.US,"%d:%02d", minutes, seconds));
                h2.postDelayed(this, 500);
            }
        };

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invis(stop);
                vis(play);
                vis(rec);
                invis(noRec);
                vis(delete);
                invis(noDel);
                h2.removeCallbacks(run);
                isRecording = false;
                mediaRecorder.stop();
                QuickToast("Recording saved.");
                recordFlag.deleteAll();
                finish();

            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invis(rec);
                vis(noRec);
                invis(play);
                vis(stop);
                invis(delete);
                vis(noDel);
                h2.postDelayed(run, 0);
                starttime = System.currentTimeMillis();
                isRecording = true;

                if(checkPermission()) {
                    Data save = new Data(hymnDisplay.this,key);

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recordings/" +
                                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";
                    //MainActivity.userData(hymnDisplay.this,key,"pushBack",timeStamp());
                    save.pushBack(timeStamp());
                    //MainActivity.userData(hymnDisplay.this,key,"pushBack","recording");
                    save.pushBack("recording");
                    //MainActivity.userData(hymnDisplay.this,key,"pushBack",AudioSavePathInDevice);
                    save.pushBack(AudioSavePathInDevice);

                    MediaRecorderReady();


                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(hymnDisplay.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }



            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickToast("preparing to delete");
            }
        });



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
            float size = pairs[0].getTextSize();
            Log.d("TextSizeStart", String.valueOf(size));

            float factor = detector.getScaleFactor();
            Log.d("Factor", String.valueOf(factor));


            float product = size*factor;
            Log.d("TextSize", String.valueOf(product));
            changeTextSize(TypedValue.COMPLEX_UNIT_PX, product);

            size = pairs[0].getTextSize();
            Log.d("TextSizeEnd", String.valueOf(size));
            return true;
        }
    }
}


