package com.seven.clip.nziyodzemethodist;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    int bound,en_bound,totalHeight,en_totalHeight,clength,textcolor,capColor,hymnnumColor,length,en_length,buttonLayoutBg,hasOptions=0,optionsSet=0;
    int menuSlideStep = 0,windowWidth,windowHeight;
    Boolean chorusAvail = false,historyChanged = false, isInEnglish = false,isRecording = false,isEnglishAvailable = false,
    isRecordingSaved=false;
    ImageView bg,auxBut;
    ImageView micBut,editBut,enBut,heartBut,fontBut,colorBut,shareBut,nextBut,prevBut;
    TableRow micRow,editRow,enRow,heartRow,fontRow,colorRow,shareRow,nextRow,prevRow;
    TableLayout menuLayout;
    boolean micButBool,editButBool,enButBool,heartButBool,fontButBool,colorButBool,shareButBool,nextButBool,prevButBool,
            helpOptions = false;
    float textSize,buttonSize,prevSize;
    RelativeLayout display;
    TextView captionShow,number,title,chorus,hymnpop;
    TextView [] pairs,en_pairs;
    String [] captionStrings;
    Button showCaption;
    boolean textSizeChanged,menuOpen,favInit, chorusTrasparent, canSlideRight,canSlideLeft;
    long starttime = 0;
    String AudioSavePathInDevice = null,RandomAudioFileName = "ABCDEFGHIJKLMNOP",hymnNum,capStoreKey,c,h,en_h,s,t,en_t,safe,hymnName,en_hymnName;
    MediaRecorder mediaRecorder;
    Random random;
    public static final int RequestPermissionCode = 1;
    Data favList,recordFlag,color,textSizeData,recList,favIterator,recIterator,withCaption;
    Zvinokosha access;
    ScaleGestureDetector scaleGestureDetector;
    Intent hymnDisplayIntent;
    LinearLayout hymnDispl;
    hymnNavigate navigate;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_display);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        windowWidth = dm.widthPixels;
        windowHeight = dm.heightPixels;




        Handler adHandler = new Handler();
        adHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MobileAds.initialize(getApplicationContext(), "ca-app-pub-2945410942325181~3930353152");
                AdView mAdView = (AdView) findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                        .addTestDevice("37AF7727CC5B392008ACD1C889F79F4E")  // An example device ID
                        .build();
                mAdView.loadAd(adRequest);
            }
        },5000);



        favList = new Data(this,"favlist");
        recList = new Data(this,"reclist");
        color = new Data(this,"color");
        textSizeData = new Data(this,"textsize");
        recordFlag = new Data(this,"recordflag");
        favIterator = new Data(this,"faviterator");
        recIterator = new Data(this,"reciterator");
        withCaption = new Data(this,"withcaption");
        navigate = new hymnNavigate(favList.get(),recList.get(),favIterator.get(),recIterator.get());
        hymnDisplayIntent = new Intent();
        final EnResource engCheck = new EnResource(this);
        access = new Zvinokosha(this);

        s = getIntent().getStringExtra("hymnNum");
        recList.pushFront(s);

        safe = NumToWord.convert(StrToInt(s)) + "key";
        capStoreKey = safe;
        t = "hymn" + s + "firstline";
        h = "hymn" + s ;
        c = "hymn" + s + "caption";

        favIterator.update("0");
        recIterator.update("0");

        String [] hymn;
        chorus = (TextView) findViewById(R.id.chorusFloat);

        captionShow = (TextView) findViewById(R.id.caption);
        number = (TextView) findViewById(R.id.hymnDisplayNum);
        title = (TextView) findViewById(R.id.hymnTitle);
        hymnpop = (TextView) findViewById(R.id.hymnDisplayNumlight);
        hymnDispl = (LinearLayout) findViewById(R.id.hymnLayout);
        myScroll = (ScrollView) findViewById(R.id.scrollHymn);
        showCaption = (Button) findViewById(R.id.showCaption);
        final Intent toPasteBin = new Intent(this,ShareCustom.class);
        bg = (ImageView) findViewById(R.id.imageView);
        display = (RelativeLayout) findViewById(R.id.activity_hymn_display);
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());

        micBut = (ImageView) findViewById(R.id.hymnRecordBut);
        editBut = (ImageView) findViewById(R.id.hymnWriteNoteBut);
        enBut = (ImageView) findViewById(R.id.hymnEnBut);
        heartBut = (ImageView) findViewById(R.id.hymnFavBut);
        fontBut = (ImageView) findViewById(R.id.hymnFontBut);
        colorBut = (ImageView) findViewById(R.id.hymnColorBut);
        shareBut = (ImageView) findViewById(R.id.hymnShareBut);
        nextBut = (ImageView) findViewById(R.id.hymnForwardBut);
        prevBut = (ImageView) findViewById(R.id.hymnBackBut);

        auxBut = (ImageView) findViewById(R.id.helpIcon);

        micRow = (TableRow) findViewById(R.id.menuRow1);
        editRow = (TableRow) findViewById(R.id.menuRow2);
        enRow = (TableRow) findViewById(R.id.menuRow3);
        heartRow = (TableRow) findViewById(R.id.menuRow4);
        fontRow = (TableRow) findViewById(R.id.menuRow5);
        colorRow = (TableRow) findViewById(R.id.menuRow6);
        shareRow = (TableRow) findViewById(R.id.menuRow7);
        prevRow = (TableRow) findViewById(R.id.menuRow8);
        nextRow = (TableRow) findViewById(R.id.menuRow9);

        menuLayout = (TableLayout) findViewById(R.id.hymnMenuLayout);



        textSizeChanged = false;
        menuOpen = false;
        chorusTrasparent = false;
        isEnglishAvailable = engCheck.isEn(s);


        int resourceId = getResourceId(h,"array",getPackageName());
        int captionResourceId = getResourceId(c,"array",getPackageName());
        title.setText(getResourceId(t,"string",getPackageName()));
        hymnName = title.getText().toString();
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
            prepareRecording(getIntent().getStringExtra("hymnNumWord"));
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

            }
        });


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
            }
        });


        myScroll.setOnTouchListener(new OnSwipeTouchListener(this){
            public void onSwipeRight(){
                if(canSlideRight){
                    slideRight(micBut,buttonSize,50,100);
                    slideRight(editBut,buttonSize,100,100);
                    slideRight(enBut,buttonSize,150,100);
                    slideRight(heartBut,buttonSize,200,100);
                    slideRight(fontBut,buttonSize,250,100);
                    slideRight(colorBut,buttonSize,300,100);
                    slideRight(shareBut,buttonSize,350,100);
                    slideRight(prevBut,buttonSize,400,100);
                    slideRight(nextBut,buttonSize,450,100);
                    auxBut.setVisibility(View.VISIBLE);
                    canSlideLeft = true;
                    canSlideRight = false;
                    menuOpen = true;
                }
            }
            public void onSwipeLeft(){
                if(canSlideLeft&&!helpOptions){
                    if(menuOpen){
                        retractMenu();
                    }else {
                        slideLeft(micBut,-buttonSize,50,400);
                        slideLeft(editBut,-buttonSize,50,400);
                        slideLeft(enBut,-buttonSize,50,400);
                        slideLeft(heartBut,-buttonSize,50,400);
                        slideLeft(fontBut,-buttonSize,50,400);
                        slideLeft(colorBut,-buttonSize,50,400);
                        slideLeft(shareBut,-buttonSize,50,400);
                        slideLeft(prevBut,-buttonSize,50,400);
                        slideLeft(nextBut,-buttonSize,50,400);
                        auxBut.setVisibility(View.INVISIBLE);
                        canSlideRight = true;
                        canSlideLeft = false;
                        menuOpen = false;
                    }
                }
            }
        });

        if(favList.find(s)){
            Handler heartNotificationHandler = new Handler();
            heartRow.addView(createFadedButton("isFav"));
            heartBut.setImageResource(R.drawable.heart_icon_on);
            heartNotificationHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    heartRow.removeViewAt(1);
                }
            },4000);
            heartButBool = true;
        }
        favInit = heartButBool;

        heartBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(heartButBool){
                    flipHeartIcon();
                    heartButBool = false;
                }
                else {
                    flipHeartIcon();
                    heartButBool = true;
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

        //micBut,editBut,enBut,heartBut,fontBut,colorBut,shareBut,nextBut,prevBut;
        micButBool=editButBool=fontButBool=colorButBool=shareButBool=nextButBool=prevButBool=canSlideRight=canSlideLeft=false;
        micBut.setAlpha(0f);
        editBut.setAlpha(0f);
        enBut.setAlpha(0f);
        micBut.setAlpha(0f);
        heartBut.setAlpha(0f);
        fontBut.setAlpha(0f);
        colorBut.setAlpha(0f);
        shareBut.setAlpha(0f);
        prevBut.setAlpha(0f);
        nextBut.setAlpha(0f);
        introSideBarAnimation();

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonSize = (float) micBut.getWidth();
                resetSideBarPosition();
            }
        },4000);

        auxBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(whichMenuRow()!=0){
                    inflateHelpLayout(whichMenuRow());
                }
                else {
                    TextView [] helpCaptions = new TextView[9];
                    if(!helpOptions){
                        helpCaptions[0] = createMenuHelpCaption("record");
                        helpCaptions[1] = createMenuHelpCaption("note");
                        helpCaptions[2] = createMenuHelpCaption("english");
                        helpCaptions[3] = createMenuHelpCaption("fav");
                        helpCaptions[4] = createMenuHelpCaption("fontSize");
                        helpCaptions[5] = createMenuHelpCaption("colors");
                        helpCaptions[6] = createMenuHelpCaption("share");
                        helpCaptions[7] = createMenuHelpCaption("prev");
                        helpCaptions[8] = createMenuHelpCaption("next");
                        micRow.addView(helpCaptions[0]);
                        editRow.addView(helpCaptions[1]);
                        enRow.addView(helpCaptions[2]);
                        heartRow.addView(helpCaptions[3]);
                        fontRow.addView(helpCaptions[4]);
                        colorRow.addView(helpCaptions[5]);
                        shareRow.addView(helpCaptions[6]);
                        prevRow.addView(helpCaptions[7]);
                        nextRow.addView(helpCaptions[8]);
                        helpOptions = true;
                        flipHelpIcon();
                        obfuscateHymn();
                    }
                    else
                    {
                        micRow.removeViewAt(1);
                        editRow.removeViewAt(1);
                        enRow.removeViewAt(1);
                        heartRow.removeViewAt(1);
                        fontRow.removeViewAt(1);
                        colorRow.removeViewAt(1);
                        shareRow.removeViewAt(1);
                        prevRow.removeViewAt(1);
                        nextRow.removeViewAt(1);
                        helpOptions = false;
                        flipHelpIcon();
                        deobfuscateHymn();
                    }
                }
            }
        });

        micBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnyMenuOpen()) {
                    retractMenu();
                }else {
                    final ImageView [] micOptions = new ImageView[3];
                    micOptions[0] = createFadedButton("record");
                    micOptions[1] = createFadedButton("stop");
                    micOptions[2] = createFadedButton("folder");
                    TextView micText = createMenuHelpCaption("date");
                    micOptions[0].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            prepareRecording(capStoreKey);
                            micOptions[0].setColorFilter(parseColor("#ffffff"));
                            micOptions[1].setColorFilter(parseColor("#ffffff"));
                            micOptions[2].setColorFilter(parseColor("#ffffff"));
                        }
                    });
                    micOptions[2].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retractMenu();
                            retractMenu();
                            Intent toCaptions = new Intent(hymnDisplay.this,Captions.class);
                            toCaptions.putExtra("hymnNumWord",safe);
                            toCaptions.putExtra("hymnNum",s);
                            toCaptions.putExtra("hymnName",hymnName);
                            startActivity(toCaptions);
                        }
                    });
                    micRow.addView(micOptions[0]);
                    micRow.addView(micOptions[1]);
                    micRow.addView(micOptions[2]);
                    micRow.addView(micText);
                    micButBool = true;

                    micOptions[0].setColorFilter(parseColor("#ff5555"));
                    micOptions[1].setColorFilter(parseColor("#ff5555"));

                }
            }
        });

        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnyMenuOpen()) {
                    retractMenu();
                }else {
                    ImageView [] editOptions = new ImageView[2];
                    editOptions[0] = createFadedButton("new");
                    editOptions[1] = createFadedButton("folder");
                    TextView editText = createMenuHelpCaption("date");
                    editRow.addView(editOptions[0]);
                    editRow.addView(editOptions[1]);
                    editRow.addView(editText);
                    editButBool = true;

                    editOptions[0].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent toNote = new Intent(hymnDisplay.this,makeNote.class);
                            toNote.putExtra("captionKey",capStoreKey);
                            toNote.putExtra("captionType","note");
                            toNote.putExtra("hymnNum",s);
                            startActivity(toNote);
                        }
                    });

                    editOptions[1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retractMenu();
                            retractMenu();
                            Intent toCaptions = new Intent(hymnDisplay.this,Captions.class);
                            toCaptions.putExtra("hymnNumWord",safe);
                            toCaptions.putExtra("hymnNum",s);
                            toCaptions.putExtra("hymnName",hymnName);
                            startActivity(toCaptions);
                        }
                    });
                }
            }
        });
        if(isEnglishAvailable&&!isInEnglish){
            Handler engNotificationHandler = new Handler();
            final ImageView imageHasEn = createFadedButton("hasEn");
            imageHasEn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeEnglishHymn();
                    imageHasEn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            });
            enRow.addView(imageHasEn);
            enBut.setImageResource(R.drawable.en_icon_avail);
            engNotificationHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enRow.removeViewAt(1);
                }
            },4000);
        }

        enBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!historyChanged)
                    makeEnglishHymn();
            }
        });
        fontBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!historyChanged){
                    if(isAnyMenuOpen()) {
                        retractMenu();
                    }else {
                        final SeekBar track = addSeekBar();
                        final TextView size = createMenuHelpCaption("textSize");
                        ImageView reset = createFadedButton("reset");

                        reset.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                changeTextSizeBySeekBar((float)40);
                                size.setText("40");
                                track.setProgress(15);
                                flipResetIcon();
                            }
                        });

                        fontRow.addView(track);
                        fontRow.addView(size);
                        fontRow.addView(reset);
                        fontButBool = true;
                    }
                }
            }
        });

        colorBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!historyChanged){
                    if(isAnyMenuOpen()) {
                        retractMenu();
                    }else {
                        ImageView [] colorOptions = new ImageView[4];
                        colorOptions[0] = createFadedButton("sun");
                        colorOptions[1] = createFadedButton("moon");
                        colorOptions[2] = createFadedButton("background");
                        colorOptions[3] = createFadedButton("reset");
                        colorRow.addView(colorOptions[0]);
                        colorRow.addView(colorOptions[1]);
                        colorRow.addView(colorOptions[2]);
                        colorRow.addView(colorOptions[3]);
                        colorButBool = true;

                        colorOptions[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                color.update("day");
                                changeColorMode("day");
                                fadeInHymn();

                            }
                        });

                        colorOptions[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                color.update("night");
                                changeColorMode("night");
                                fadeInHymn();
                            }
                        });

                        colorOptions[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent toThemer = new Intent(hymnDisplay.this,ThemeChooser.class);
                                startActivityForResult(toThemer,3);
                            }
                        });

                        colorOptions[3].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bg.setColorFilter(parseColor("#00000000"));
                                color.update("");
                                changeColorMode("");
                                fadeInHymn();
                                flipBgResetIcon();
                            }
                        });
                    }
                }
            }
        });

        shareBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!historyChanged){
                    if(isAnyMenuOpen()) {
                        retractMenu();
                    }else {
                        ImageView[] shareOptions = new ImageView[4];
                        shareOptions[0] = createFadedButton("app");
                        shareOptions[1] = createFadedButton("stanza");
                        shareOptions[2] = createFadedButton("whole");
                        shareRow.addView(shareOptions[0]);
                        shareRow.addView(shareOptions[1]);
                        shareRow.addView(shareOptions[2]);
                        shareButBool = true;

                        shareOptions[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareAppFunc();
                            }
                        });

                        shareOptions[1].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String text = "";
                                if(isInEnglish){
                                    for(int l=0; l<en_length; l++)
                                        text = text+String.valueOf(l+1)+". "+en_pairs[l].getText()+"\n";
                                } else {
                                    for(int l=0; l<length; l++)
                                        text = text+String.valueOf(l+1)+". "+pairs[l].getText()+"\n";
                                }
                                toPasteBin.putExtra("text",text);
                                startActivity(toPasteBin);
                            }
                        });

                        shareOptions[2].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String text = "";
                                if(isInEnglish){
                                    for(int l=0; l<en_length; l++)
                                        text = text+String.valueOf(l+1)+". "+en_pairs[l].getText()+"\n";
                                } else {
                                    for(int l=0; l<length; l++)
                                        text = text+String.valueOf(l+1)+". "+pairs[l].getText()+"\n";
                                }
                                copy(text,hymnNum);
                            }
                        });
                    }
                }
            }
        });

        prevBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigate.prevAccess()){
                    historyChanged = true;
                    setContent(navigate.prev());
                    if(navigate.changes()){
                        if(navigate.isFavChanged())
                            favIterator.update(String.valueOf(Integer.valueOf(favIterator.get())-1));
                        else
                            recIterator.update(String.valueOf(Integer.valueOf(recIterator.get())+1));
                        navigate.update(favIterator.get(),recIterator.get());
                        prevRow.addView(createMenuHelpCaption("prev"));
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                prevRow.removeViewAt(1);
                            }
                        },1000);
                    }
                }
            }
        });

        nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigate.nextAccess()){
                    historyChanged = true;
                    setContent(navigate.next());
                    if(navigate.changes()){
                        if(navigate.isFavChanged())
                            favIterator.update(String.valueOf(Integer.valueOf(favIterator.get())+1));
                        else
                            recIterator.update(String.valueOf(Integer.valueOf(recIterator.get())-1));
                        navigate.update(favIterator.get(),recIterator.get());
                        nextRow.addView(createMenuHelpCaption("next"));
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                nextRow.removeViewAt(1);
                            }
                        },1000);
                    }
                }
            }
        });
        ////TODO
        //end of functions
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
                fadeInHymn();
                //// TODO: 23.03.17
                //// set button bg refresh

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
        if(favInit!=heartButBool) {
            saveFavState();
            hymnDisplayIntent.putExtra("hymnNum",s);
            setResult(1,hymnDisplayIntent);
        }
        favInit = heartButBool;
        favIterator.update("0");
        recIterator.update("0");
    }

    @Override
    public void onResume(){
        super.onResume();
        if(hasOptions==1)
            finish();
    }

    public void fadeInOut(final View v, int delay, final int duration){
        v.animate().alpha(1f).setDuration(duration).setStartDelay(delay).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.animate().setStartDelay(0).alpha(0f).setDuration(duration);
            }
        });
    }

    public void fadeIn(final View v, int delay, final int duration){
        v.animate().alpha(1f).setDuration(duration).setStartDelay(delay);
    }

    public void fadeOut(final View v, int delay, final int duration){
        v.animate().alpha(0f).setDuration(duration).setStartDelay(delay);
    }

    public void slideRight(View v,float distance, int delay, final int duration){
        v.animate().alpha(1f).translationXBy(distance).setDuration(duration+3*delay).setStartDelay(delay);
    }

    public void slideLeft(View v,float distance, int delay, final int duration){
        v.animate().alpha(1f).translationXBy(distance).setDuration(duration).setStartDelay(delay);
    }

    public ImageView createFadedButton(String name){
        ImageView button = new ImageView(this);
        button.setPadding(10,0,10,0);
        button.setBackgroundDrawable(getResources().getDrawable(R.color.transBlack));
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        switch (name){
            case "record":
                button.setImageResource(R.drawable.record_icon);
                break;
            case "stop":
                button.setImageResource(R.drawable.stop_icon);
                break;
            case "folder":
                button.setImageResource(R.drawable.ic_folder_black_24dp);
                break;
            case "new":
                button.setImageResource(R.drawable.add_caption_icon);
                break;
            case "check":
                button.setImageResource(R.drawable.ic_check_black_24dp);
                break;
            case "reset":
                button.setImageResource(R.drawable.ic_settings_backup_restore_black_24dp);
                break;
            case "sun":
                button.setImageResource(R.drawable.ic_wb_sunny_black_24dp);
                break;
            case "moon":
                button.setImageResource(R.drawable.ic_brightness_2_black_24dp);
                break;
            case "background":
                button.setImageResource(R.drawable.ic_color_lens_black_24dp);
                break;
            case "fontColor":
                button.setImageResource(R.drawable.ic_format_color_text_black_24dp);
                break;
            case "line":
                button.setImageResource(R.drawable.share_line_icon);
                break;
            case "app":
                button.setImageResource(R.drawable.app_icon);
                break;
            case "stanza":
                button.setImageResource(R.drawable.share_stanza_icon);
                break;
            case "whole":
                button.setImageResource(R.drawable.share_whole_icon);
                break;
            //special cases
            case "hasEn":
                button.setImageResource(R.drawable.en_icon_avail);
                button.setPadding(20,0,20,0);
                button.setBackgroundDrawable(getResources().getDrawable(R.drawable.faded_black_center));
                break;
            case "isFav":
                button.setImageResource(R.drawable.heart_normal);
                button.setPadding(20,0,20,0);
                button.setBackgroundDrawable(getResources().getDrawable(R.drawable.faded_black_center));
                break;
        }
        return button;
    }

    public TextView createMenuHelpCaption(String name){
        TextView textView = new TextView(this);
        textView.setBackground(getResources().getDrawable(R.drawable.to_left_black));
        textView.setTextColor(parseColor("#ffffff"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        textView.setPadding(10,0,10,0);
        textView.setShadowLayer(7,0,0,Color.BLACK);
        switch (name){
            case "record":
                textView.setText("Make a recording");
                break;
            case "note":
                textView.setText("Write or open notes");
                break;
            case "english":
                if(isInEnglish)
                    textView.setText("Open Shona version");
                else
                    textView.setText("Open English version");
                break;
            case "fav":
                textView.setText("Favourites");
                break;
            case "fontSize":
                textView.setText("Set text size");
                break;
            case "colors":
                textView.setText("Set Colors and Background");
                break;
            case "share":
                textView.setText("Sharing options");
                break;
            case "prev":
                textView.setText(navigate.prevText());
                break;
            case "next":
                textView.setText(navigate.nextText());
                break;
            case "date":
                Calendar calendar = Calendar.getInstance();
                String format = new SimpleDateFormat("E, MMM d, yyyy",Locale.getDefault()).format(calendar.getTime());
                textView.setText(format);
                break;
            case "textSize":
                textView.setText(String.valueOf((int) textSize));
                break;
            default:
                textView.setText(name);
                break;

        }
        return textView;
    }

    public SeekBar addSeekBar(){
        SeekBar track = new SeekBar(this);
        track.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prevSize = textSize;
                TextView sample = (TextView) fontRow.getChildAt(2);
                sample.setText(String.valueOf(progress+25));
                changeTextSizeBySeekBar((float)(progress+25));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                flipResetIcon();
            }
        });
        track.setLayoutParams(new LayoutParams(windowWidth/2, LayoutParams.MATCH_PARENT));
        track.setBackgroundDrawable(getResources().getDrawable(R.color.transBlack));
        return track;
    }

    public void introSideBarAnimation(){
        fadeInOut(micBut,1000,700);
        fadeInOut(editBut,1100,700);
        fadeInOut(enBut,1200,700);
        fadeInOut(heartBut,1300,700);
        fadeInOut(fontBut,1400,700);
        fadeInOut(colorBut,1500,700);
        fadeInOut(shareBut,1600,700);
        fadeInOut(prevBut,1700,700);
        fadeInOut(nextBut,1800,700);
    }

    public void resetSideBarPosition(){
        micBut.setTranslationX(-buttonSize);
        enBut.setTranslationX(-buttonSize);
        editBut.setTranslationX(-buttonSize);
        heartBut.setTranslationX(-buttonSize);
        fontBut.setTranslationX(-buttonSize);
        colorBut.setTranslationX(-buttonSize);
        shareBut.setTranslationX(-buttonSize);
        prevBut.setTranslationX(-buttonSize);
        nextBut.setTranslationX(-buttonSize);
        canSlideRight = true;
    }

    public void flipHelpIcon(){
        if(helpOptions){
            auxBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    auxBut.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                    auxBut.animate().scaleX(1f).setDuration(100);
                }
            });
        }
        else {
            auxBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    auxBut.setImageDrawable(getResources().getDrawable(R.drawable.ic_question_mark));
                    auxBut.animate().setDuration(100).scaleX(1f);
                }
            });
        }
    }

    public void flipEnglishIcon(){
        if(isInEnglish){
            enBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    enBut.setImageDrawable(getResources().getDrawable(R.drawable.sh_icon));
                    enBut.animate().scaleX(1f).setDuration(100);
                }
            });
        }
        else {
            enBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    enBut.setImageDrawable(getResources().getDrawable(R.drawable.en_icon_on));
                    enBut.animate().setDuration(100).scaleX(1f);
                }
            });
        }
    }
    public void flipHeartIcon(){
        if(heartButBool){
            heartBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    heartBut.setImageDrawable(getResources().getDrawable(R.drawable.heart_icon));
                    heartBut.animate().scaleX(1f).setDuration(100);
                }
            });
        }
        else {
            heartBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    heartBut.setImageDrawable(getResources().getDrawable(R.drawable.heart_icon_on));
                    heartBut.animate().setDuration(100).scaleX(1f);
                }
            });
        }
    }

    public void flipRecIcon(){
        final ImageView button1 = (ImageView) micRow.getChildAt(1);
        final ImageView button2 = (ImageView) micRow.getChildAt(3);
        if(isRecording){
            button1.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    if(isRecordingSaved) {
                        button1.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                        button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.faded_white_center));
                    }
                    else
                        button1.setImageDrawable(getResources().getDrawable(R.drawable.close_icon));
                    button1.animate().scaleX(1f).setDuration(100);
                }
            });
        }
        else {
            button1.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    button1.setImageDrawable(getResources().getDrawable(R.drawable.ic_record_circle_red_24dp));
                    button1.animate().scaleX(1f).setDuration(100);
                }
            });
        }
    }

    public void flipResetIcon(){
        final ImageView v = (ImageView) fontRow.getChildAt(3);
        v.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                v.animate().scaleX(1f).setDuration(100);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textSizeData.update(String.valueOf(textSize));
                        textSizeChanged = false;
                        retractMenu();
                    }
                });

            }
        });
    }

    public void flipBgResetIcon(){
        final ImageView v = (ImageView) colorRow.getChildAt(4);
        v.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.setImageDrawable(getResources().getDrawable(R.drawable.ic_hourglass_empty_black_24dp));
                v.animate().scaleX(1f).setDuration(100);
                Handler handlerTimer = new Handler();
                handlerTimer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                v.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                                v.animate().scaleX(1f).setDuration(100).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        retractMenu();
                                    }
                                });
                            }
                        });
                    }
                }, 1400);

            }
        });
    }


    public boolean isAnyMenuOpen(){
            return (micButBool||editButBool||enButBool||fontButBool||colorButBool||shareButBool||nextButBool||prevButBool||helpOptions);
    }
    public boolean retractMenu(){
        boolean menuRetracted = false;
        if(helpOptions){
            micRow.removeViewAt(1);
            editRow.removeViewAt(1);
            enRow.removeViewAt(1);
            heartRow.removeViewAt(1);
            fontRow.removeViewAt(1);
            colorRow.removeViewAt(1);
            shareRow.removeViewAt(1);
            prevRow.removeViewAt(1);
            nextRow.removeViewAt(1);
            helpOptions = false;
            flipHelpIcon();
            menuRetracted = true;
        }
        else if(micButBool){
            micRow.removeViewAt(1);
            micRow.removeViewAt(1);
            micRow.removeViewAt(1);
            micRow.removeViewAt(1);
            micButBool = false;
            menuRetracted = true;
        }
        else if(editButBool){
            editRow.removeViewAt(1);
            editRow.removeViewAt(1);
            editRow.removeViewAt(1);
            editButBool = false;
            menuRetracted = true;

        }
        else if(fontButBool){
            fontRow.removeViewAt(1);
            fontRow.removeViewAt(1);
            fontRow.removeViewAt(1);
            fontButBool = false;
            menuRetracted = true;
        }
        else if(colorButBool){
            colorRow.removeViewAt(1);
            colorRow.removeViewAt(1);
            colorRow.removeViewAt(1);
            colorRow.removeViewAt(1);
            colorButBool = false;
            menuRetracted = true;
        }
        else if(shareButBool){
            shareRow.removeViewAt(1);
            shareRow.removeViewAt(1);
            shareRow.removeViewAt(1);
            shareButBool = false;
            menuRetracted = true;
        }
        /**/
        else if(menuOpen){
            slideLeft(micBut,-buttonSize,50,400);
            slideLeft(editBut,-buttonSize,50,400);
            slideLeft(enBut,-buttonSize,50,400);
            slideLeft(heartBut,-buttonSize,50,400);
            slideLeft(fontBut,-buttonSize,50,400);
            slideLeft(colorBut,-buttonSize,50,400);
            slideLeft(shareBut,-buttonSize,50,400);
            slideLeft(prevBut,-buttonSize,50,400);
            slideLeft(nextBut,-buttonSize,50,400);
            Handler adjuster = new Handler();
            adjuster.postDelayed(new Runnable() {
                @Override
                public void run() {
                    micBut.setTranslationX(-buttonSize);
                    editBut.setTranslationX(-buttonSize);
                    enBut.setTranslationX(-buttonSize);
                    heartBut.setTranslationX(-buttonSize);
                    fontBut.setTranslationX(-buttonSize);
                    colorBut.setTranslationX(-buttonSize);
                    shareBut.setTranslationX(-buttonSize);
                    prevBut.setTranslationX(-buttonSize);
                    nextBut.setTranslationX(-buttonSize);
                }
            },600);
            auxBut.setVisibility(View.INVISIBLE);
            canSlideRight = true;
            canSlideLeft = false;
            menuOpen = false;
            menuRetracted = true;
        }
        deobfuscateHymn();
        return menuRetracted;
    }

    public int whichMenuRow(){
        int result;
        if(micButBool)
            result = 1;
        else if(editButBool)
            result = 2;
        else if(fontButBool)
            result = 5;
        else if(colorButBool)
            result = 6;
        else if(shareButBool)
            result = 7;
        else result = 0;

        return result;
    }

    public void changeTextSizeBySeekBar(float value){
        if(isInEnglish){
            for(int l=0; l<en_length; l++)
                en_pairs[l].setTextSize(value);
            textSize = en_pairs[0].getTextSize();
        } else {
            for(int l=0; l<length; l++)
                pairs[l].setTextSize(value);
            textSize = pairs[0].getTextSize();
        }
        textSizeChanged=true;
    }

    public void changeTextSize(float value){
        if(isInEnglish){
            for(int l=0; l<en_length; l++)
            {
                en_pairs[l].setAlpha(0f);
                en_pairs[l].setTextSize(value);
                en_pairs[l].animate().alpha(1f).setDuration(700*l);
            }
        } else {
            for(int l=0; l<length; l++)
            {
                pairs[l].setAlpha(0f);
                pairs[l].setTextSize(value);
                pairs[l].animate().alpha(1f).setDuration(700*l);
            }
        }

    }
    public void changeTextSize(int unit,float value){

        if(isInEnglish){
            for(int l=0; l<en_length; l++){
                if(value>1f&&value<700f){
                    if(isInEnglish)
                        en_pairs[l].setTextSize(unit, value);
                }
            }
            textSize = en_pairs[0].getTextSize();
        } else {
            for(int l=0; l<length; l++){
                if(value>1f&&value<700f){
                    pairs[l].setTextSize(unit, value);
                }
            }
            textSize = pairs[0].getTextSize();
        }
        textSizeChanged=true;

    }

    //Color Management
    public void changeColorMode(String mode){
        switch (mode){
            case "night":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setColorFilter(parseColor("#000000"));
                //display.setBackground(getResources().getDrawable(R.color.black));
                hymnnumColor = parseColor("#50ffffff");
                buttonLayoutBg = parseColor("#70ffffff");
                textcolor= capColor = WHITE;
                break;
            case "day":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setColorFilter(parseColor("#ffffff"));
                textcolor= capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
                buttonLayoutBg = parseColor("#70000000");
                break;
            case "":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setImageDrawable(getResources().getDrawable(R.drawable.natured));
                textcolor = WHITE;
                capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
                buttonLayoutBg = parseColor("#70000000");
                break;
            default:
                bg.animate().alpha(0f).setDuration(2000);
                if(isColorDark(mode)){
                    hymnnumColor = parseColor("#50ffffff");
                    buttonLayoutBg = parseColor("#70ffffff");
                    textcolor= capColor = WHITE;
                } else {
                    textcolor= capColor = BLACK;
                    hymnnumColor = parseColor("#50000000");
                    buttonLayoutBg = parseColor("#70000000");
                }
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[] {getLighterColor(mode),parseColor(mode),parseColor(mode),parseColor(mode),getDarkerColor(mode)});
                gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                display.setBackgroundDrawable(gd);
                capColor = BLACK;
        }
        number.setTextColor(hymnnumColor);
    }

    public boolean isColorDark(String test){
        int color = parseColor(test);
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        if(darkness<0.333){
            return false; // It's a light color
        }else{
            return true; // It's a dark color
        }
    }

    public void fadeInHymn(){

            if(isInEnglish){
                for(int l=0; l<en_length; l++) {
                    en_pairs[l].setAlpha(0f);
                    en_pairs[l].setTextColor(textcolor);
                    en_pairs[l].animate().alpha(1f).setDuration(700*l);
                }
            }
            else {
                for(int l=0; l<length; l++) {
                    pairs[l].setAlpha(0f);
                    pairs[l].setTextColor(textcolor);
                    pairs[l].animate().alpha(1f).setDuration(700 * l);
                }
            }
    }

    public int getDarkerColor(String test){
        float[] hsv = new float[3];
        int color = parseColor(test);
        Color.colorToHSV(color, hsv);
        hsv[0] += (hsv[0] * 0.2f); // value component
        color = Color.HSVToColor(hsv);
        return color;
    }

    public int getLighterColor(String test){
        float[] hsv = new float[3];
        int color = parseColor(test);
        Color.colorToHSV(color, hsv);
        hsv[0] = 1.0f - 0.8f * (1.0f - hsv[0]); // value component
        color = Color.HSVToColor(hsv);
        return color;
    }


    public void saveFavState(){
        if(heartButBool)
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

                }
            },(clength*3000));
        }
    }

    public void makeEnglishHymn(){
        if(isEnglishAvailable&&!isInEnglish){
            isInEnglish = true;
            flipEnglishIcon();
            hymnDispl.removeAllViews();
            en_h = "en" + h;
            en_t = "en" + t;
            String [] en_hymn;
            int en_resourceId = getResourceId(en_h,"array",getPackageName());
            title.setText(getResourceId(en_t,"string",getPackageName()));
            en_hymn = getResources().getStringArray(en_resourceId);
            en_length =getResources().getStringArray(getResourceId(en_h,"array",getPackageName())).length;

            String en_g = textSizeData.get();
            if(en_g.equals(""))
                textSize = 40f;
            else
                textSize = Float.valueOf(en_g);
            String en_g1 = recordFlag.get();
            if (en_g1.equals("true")){
                prepareRecording(getIntent().getStringExtra("hymnNumWord"));
            }
            changeColorMode(color.get());
            en_pairs=new TextView[en_length];
            for(int l=0; l<en_length; l++)
            {
                en_pairs[l] = new TextView(hymnDisplay.this);
                en_pairs[l].setTextSize(textSize);
                en_pairs[l].setId(l);
                en_pairs[l].setGravity(Gravity.CENTER);
                en_pairs[l].setTextColor(textcolor);
                en_pairs[l].setText(en_hymn[l]);
                hymnDispl.addView(en_pairs[l]);
            }
            final Handler en_handler = new Handler();
            en_handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    en_totalHeight = myScroll.getChildAt(0).getHeight();
                    en_bound =  (totalHeight/en_length);
                    String check =en_pairs[0].getText().toString();
                    if(check.contains("chorus")){
                        chorus.setText(check.substring(check.lastIndexOf("chorus")));
                        chorusAvail = true;
                    }
                    else
                        chorusAvail = false;
                }
            },300);
        }
        else if(isInEnglish&&isEnglishAvailable){
            isInEnglish = false;
            flipEnglishIcon();
            hymnDispl.removeAllViews();
            for(int l=0; l<length; l++)
                hymnDispl.addView(pairs[l]);

            title.setText(getResourceId(t,"string",getPackageName()));

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
        }
    }

    public void setContent(String hNum){
        hymnDispl.removeAllViews();
        s = hNum;
        safe = NumToWord.convert(StrToInt(s)) + "key";
        capStoreKey = safe;
        t = "hymn" + s + "firstline";
        h = "hymn" + s ;
        //c = "hymn" + s + "caption";
        String [] hymn;
        int resourceId = getResourceId(h,"array",getPackageName());
        //int captionResourceId = getResourceId(c,"array",getPackageName());
        title.setText(getResourceId(t,"string",getPackageName()));
        hymnName = title.getText().toString();
        //captionStrings = getResources().getStringArray(captionResourceId);
        hymn = getResources().getStringArray(resourceId);
        length =getResources().getStringArray(getResourceId(h,"array",getPackageName())).length;
        //clength =getResources().getStringArray(getResourceId(c,"array",getPackageName())).length;
        number.setText(s);
        hymnpop.setText(s);
        String g = textSizeData.get();
        if(g.equals(""))
            textSize = 40f;
        else
            textSize = Float.valueOf(g);
        changeColorMode(color.get());
        final TextView [] newPairs=new TextView[length];
        for(int l=0; l<length; l++)
        {
            newPairs[l] = new TextView(this);
            newPairs[l].setTextSize(textSize);
            newPairs[l].setId(l);
            newPairs[l].setGravity(Gravity.CENTER);
            newPairs[l].setTextColor(textcolor);
            newPairs[l].setText(hymn[l]);
            hymnDispl.addView(newPairs[l]);
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

    }

    public void inflateHelpLayout(int menu){
        final RelativeLayout parent = (RelativeLayout) findViewById(R.id.activity_hymn_display);
        final View helpPopup = getLayoutInflater().inflate(R.layout.help_popup, parent,false);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, helpPopup.getId());
        helpPopup.setLayoutParams(params);
        parent.addView(helpPopup);
        final ImageView icon = (ImageView) findViewById(R.id.helpDisplayIcon);
        final TextView helpCaption = (TextView) findViewById(R.id.helpText);
        Handler timer = new Handler();
        obfuscateHymn();

        switch (menu){
            case 1:
                icon.setImageDrawable(getResources().getDrawable(R.drawable.record_icon));
                helpCaption.setText("Click to start recording audio...");
                icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        icon.animate().alpha(0f).setDuration(1000);
                    }
                });
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_record_circle_red_24dp));
                        helpCaption.setText("Recording will be in progress...");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000);
                            }
                        });
                    }
                },2000);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.stop_icon));
                        helpCaption.setText("Click to stop current recording");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000);
                            }
                        });
                    }
                },4000);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_folder_black_24dp));
                        helpCaption.setText("Open the folder containing all captions.");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        parent.removeView(helpPopup);
                                        deobfuscateHymn();
                                    }
                                });
                            }
                        });
                    }
                },6000);
                break;
            case 2:
                icon.setImageDrawable(getResources().getDrawable(R.drawable.add_caption_icon));
                helpCaption.setText("Create a new note as caption...");
                icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        icon.animate().alpha(0f).setDuration(1000);
                    }
                });
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_folder_black_24dp));
                        helpCaption.setText("Open the folder containing all captions.");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        parent.removeView(helpPopup);
                                        deobfuscateHymn();
                                    }
                                });
                            }
                        });
                    }
                },2000);
                break;
            case 5:
                icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_track_bar_black_24dp));
                helpCaption.setText("Adjust text size... You can also pinch to zoom on hymnbook.");
                icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        icon.animate().alpha(0f).setDuration(1000);
                    }
                });
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_number_black_24dp));
                        helpCaption.setText("Shows the number of the current text size");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000);
                            }
                        });
                    }
                },2000);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_backup_restore_black_24dp));
                        helpCaption.setText("Reset the text size to default (40)");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        parent.removeView(helpPopup);
                                        deobfuscateHymn();
                                    }
                                });
                            }
                        });
                    }
                },4000);
                break;
            case 6:
                icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_wb_sunny_black_24dp));
                helpCaption.setText("Set hymnbook to day mode.");
                icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        icon.animate().alpha(0f).setDuration(1000);
                    }
                });
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_brightness_2_black_24dp));
                        helpCaption.setText("Set hymnbook to night mode.");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000);
                            }
                        });
                    }
                },2000);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_backup_restore_black_24dp));
                        helpCaption.setText("Set a custom color for the hymnbook Theme");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000);
                            }
                        });
                    }
                },4000);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_format_color_text_black_24dp));
                        helpCaption.setText("Reset Theme");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        parent.removeView(helpPopup);
                                        deobfuscateHymn();
                                    }
                                });
                            }
                        });
                    }
                },6000);
                break;
            case 7:
                icon.setImageDrawable(getResources().getDrawable(R.drawable.app_icon));
                helpCaption.setText("Share the hymnbook to someone.");
                icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        icon.animate().alpha(0f).setDuration(1000);
                    }
                });
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.share_stanza_icon));
                        helpCaption.setText("Share a selection or a part for this hymn.");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000);
                            }
                        });
                    }
                },2000);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.share_whole_icon));
                        helpCaption.setText("Share the whole hymn to someone.");
                        icon.animate().alpha(1f).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                icon.animate().alpha(0f).setDuration(1000).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        parent.removeView(helpPopup);
                                        deobfuscateHymn();
                                    }
                                });
                            }
                        });
                    }
                },4000);
                break;

        }
    }

    public void obfuscateHymn(){
        if(!historyChanged){
            if(isInEnglish){
                for(int q=0; q<en_length;q++){
                    en_pairs[q].animate().scaleX(.8f).scaleY(.8f).alpha(.1f);
                }
            } else {
                for(int q=0; q<length;q++)
                    pairs[q].animate().scaleX(.8f).scaleY(.8f).alpha(.1f);
            }
        }
    }

    public void deobfuscateHymn(){
        if(!historyChanged){
            if(isInEnglish){
                for(int q=0; q<en_length;q++)
                    en_pairs[q].animate().scaleX(1f).scaleY(1f).alpha(1f);
            } else {
                for(int q=0; q<length;q++)
                    pairs[q].animate().scaleX(1f).scaleY(1f).alpha(1f);
            }
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
            if(retractMenu()){
                return true;
            }
            if(favInit!=heartButBool) {
                saveFavState();
                hymnDisplayIntent.putExtra("isFav",heartButBool);
                hymnDisplayIntent.putExtra("hymnNum",s);
                setResult(1,hymnDisplayIntent);
            }
            favInit = heartButBool;
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
            //// TODO: 23.03.17
            //side drawer open on menu
        }
        return true;
    }
    public String timeStamp(){
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy HH:mm", Locale.US);
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }
    public void prepareRecording(final String key){

        if(!withCaption.find(s))
            withCaption.pushFront(s);

        final ImageView rec,stop;
        final TextView text = (TextView) micRow.getChildAt(4);
        rec = (ImageView) micRow.getChildAt(1);
        stop = (ImageView) micRow.getChildAt(2);

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
                if(isRecording){
                    h2.removeCallbacks(run);
                    try {
                        mediaRecorder.stop();
                        QuickToast("Recording saved.");
                        isRecordingSaved = true;
                        rec.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prepareRecording(capStoreKey);

                            }
                        });
                    } catch (Exception e) {
                        QuickToast("The application had difficulty in recording and saving.");
                        isRecordingSaved = false;
                    }

                    recordFlag.deleteAll();
                    flipRecIcon();
                    isRecording = false;
                }
                else
                    QuickToast("Click record first.");
            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRecording){
                    flipRecIcon();
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

            }
        });
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
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

