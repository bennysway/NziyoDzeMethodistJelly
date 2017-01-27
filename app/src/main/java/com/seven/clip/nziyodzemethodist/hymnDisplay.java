package com.seven.clip.nziyodzemethodist;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
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
    int bound,totalHeight,clength,textcolor,capColor,hymnnumColor;
    Boolean chorusAvail = false;
    ImageView bg;
    RelativeLayout display;
    TextView captionShow;
    String [] captionStrings;
    Button showCaption;
    View loadCaption;
    boolean favBool,shareBool,optBool;
    long starttime = 0;
    String AudioSavePathInDevice = null,RandomAudioFileName = "ABCDEFGHIJKLMNOP",hymnNum,capStoreKey;;
    MediaRecorder mediaRecorder ;
    Random random;
    public static final int RequestPermissionCode = 1;
    Data favList,recordFlag,color;


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

        final String s = getIntent().getStringExtra("hymnNum");
        final String safe = NumToWord.convert(StrToInt(s)) + "key";
        capStoreKey = safe;
        final String t = "hymn" + s + "firstline";
        String h = "hymn" + s ;
        String c = "hymn" + s + "caption";

        String [] hymn;
        final TextView [] pairs;
        final TextView chorus = (TextView) findViewById(R.id.chorusFloat);

        captionShow = (TextView) findViewById(R.id.caption);
        final TextView num = (TextView) findViewById(R.id.hymnDisplayNum);
        TextView title = (TextView) findViewById(R.id.hymnTitle);
        final TextView hymnpop = (TextView) findViewById(R.id.hymnDisplayNumlight);
        LinearLayout hymnDispl = (LinearLayout) findViewById(R.id.hymnLayout);
        myScroll = (ScrollView) findViewById(R.id.scrollHymn);
        showCaption = (Button) findViewById(R.id.showCaption);
        loadCaption =findViewById(R.id.loadCaption);
        final Intent toPasteBin = new Intent(this,ShareCustom.class);
        bg = (ImageView) findViewById(R.id.imageView);
        display = (RelativeLayout) findViewById(R.id.activity_hymn_display);

        final View favToggle = findViewById(R.id.hymnFavBut);
        final View favToggle_on = findViewById(R.id.hymnFavBut_on);
        final View shareBut = findViewById(R.id.hymnShareBut);
        final View shareBut_on = findViewById(R.id.hymnShareBut_on);
        final View butopt =findViewById(R.id.hymnMoreOptions);
        final View butopt_on =findViewById(R.id.hymnMoreOptions_on);
        final RelativeLayout opt = (RelativeLayout) findViewById(R.id.hymnMoreOptionsLayout);
        final RelativeLayout fav = (RelativeLayout) findViewById(R.id.hymnFavButLayout);
        final RelativeLayout shr = (RelativeLayout) findViewById(R.id.hymnShareButLayout);
        final RelativeLayout optfont = (RelativeLayout) findViewById(R.id.hymnMoreOptionsFontLayout);
        final RelativeLayout shrapp = (RelativeLayout) findViewById(R.id.hymnShareButAppLayout);
        final RelativeLayout optnight = (RelativeLayout) findViewById(R.id.hymnMoreOptionsNightLayout);
        final RelativeLayout shrstanza = (RelativeLayout) findViewById(R.id.hymnShareButStanzaLayout);
        final RelativeLayout optcaptions = (RelativeLayout) findViewById(R.id.hymnMoreOptionsCaptionsLayout);
        final RelativeLayout shrwhole = (RelativeLayout) findViewById(R.id.hymnShareButWholeLayout);

        opt.setVisibility(View.INVISIBLE);
        fav.setVisibility(View.INVISIBLE);
        shr.setVisibility(View.INVISIBLE);
        optcaptions.setVisibility(View.GONE);

        opt.setY(130);
        fav.setY(130);
        shr.setY(130);


        int resourceId = getResourceId(h,"array",getPackageName());
        int captionResourceId = getResourceId(c,"array",getPackageName());
        title.setText(getResourceId(t,"string",getPackageName()));
        captionStrings = getResources().getStringArray(captionResourceId);
        hymn = getResources().getStringArray(resourceId);
        final int length =getResources().getStringArray(getResourceId(h,"array",getPackageName())).length;
        clength =getResources().getStringArray(getResourceId(c,"array",getPackageName())).length;
        num.setText(s);
        hymnpop.setText(s);
        float textSize;

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        title.setTypeface(custom_font);
        num.setTypeface(custom_font);
        hymnpop.setTypeface(custom_font);




        recList.pushFront(s);
        switch (color.get()){
            case "night":
                bg.setImageDrawable(getResources().getDrawable(R.color.black));
                display.setBackground(getResources().getDrawable(R.color.black));
                hymnnumColor = parseColor("#50ffffff");
                textcolor= capColor = WHITE;
                break;
            case "day":
                bg.setImageDrawable(getResources().getDrawable(R.color.white));
                display.setBackground(getResources().getDrawable(R.color.white));
                textcolor= capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
                break;
            default:
                bg.setImageDrawable(getResources().getDrawable(R.drawable.natured));
                textcolor = WHITE;
                capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
        }
        String g = textSizeData.get();
        if(g.equals(""))
            textSize = 40f;
        else
            textSize = Float.valueOf(g);
        String g1 = recordFlag.get();
        if (g1.equals("true")){
            inflateBottomRecordingToolbar(getIntent().getStringExtra("hymnNumWord"));
        }



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

        num.setTextColor(hymnnumColor);

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

        showCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCaption();
                showCaption.setVisibility(View.INVISIBLE);
                loadCaption.setVisibility(View.INVISIBLE);

            }
        });

        if(length==1){
            Handler viewDrawer = new Handler();
            viewDrawer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    opt.setVisibility(View.VISIBLE);
                    opt.animate().y(0f);
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
                }
            },200);

        }

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
                if(diff==0){
                    Handler viewDrawer = new Handler();
                    viewDrawer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            opt.setVisibility(View.VISIBLE);
                            opt.animate().y(0f);
                            if(optBool){
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

                }
                else{
                    Handler viewDrawer = new Handler();
                    viewDrawer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            floatDownButtons(optcaptions,0);
                            floatDownButtons(optnight,0);
                            floatDownButtons(optfont,0);
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
                }

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

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favBool){
                    vis(favToggle);
                    invis(favToggle_on);
                    favList.delete(s);
                    favBool = false;
                }
                else {
                    vis(favToggle_on);
                    invis(favToggle);
                    favList.pushBack(s);
                    favBool = true;
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
                    floatDownButtons(optcaptions,0);
                    floatDownButtons(optnight,100);
                    floatDownButtons(optfont,200);
                    optBool=false;
                }
                else {
                    vis(butopt_on);
                    invis(butopt);
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
                startActivity(toColor);
                invis(butopt_on);
                vis(butopt);
                floatDownButtons(optcaptions,0);
                floatDownButtons(optnight,100);
                floatDownButtons(optfont,200);

                optBool=false;
            }
        });

        optfont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTextSize = new Intent(hymnDisplay.this, AdjustTextSize.class);
                startActivity(toTextSize);
                invis(butopt_on);
                vis(butopt);
                floatDownButtons(optcaptions,0);
                floatDownButtons(optnight,100);
                floatDownButtons(optfont,200);
                optBool=false;

            }
        });

        optcaptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCaptions = new Intent(hymnDisplay.this,Captions.class);
                toCaptions.putExtra("hymnNumWord",safe);
                toCaptions.putExtra("hymnNum",s);
                invis(butopt_on);
                vis(butopt);
                floatDownButtons(optcaptions,0);
                floatDownButtons(optnight,100);
                floatDownButtons(optfont,200);
                optBool=false;
                startActivity(toCaptions);
            }
        });

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
        Handler but = new Handler();
        for(int f = 0; f<clength; f++){
            Handler timer = new Handler();
            final int finalF = f;
            timer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    captionShow.setY(-50f);
                    captionShow.setText(captionStrings[finalF]);
                    captionShow.animate().y(0f).setStartDelay(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            captionShow.animate().y(50f).setStartDelay(2000);
                        }
                    });
                }
            },(f*3000));
        }
        but.postDelayed(new Runnable() {
            @Override
            public void run() {
                showCaption.setVisibility(View.VISIBLE);
                loadCaption.setVisibility(View.VISIBLE);
            }
        },(clength*3000));
    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    public int StrToInt(String s){
        return Integer.valueOf(s);
    }
    public int getResourceId(String pVariableName, String pResourcename, String pPackageName)
    {
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
    //////////
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
            finish();
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

                mediaRecorder.stop();
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

                if(checkPermission()) {

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recordings/" +
                                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";
                    MainActivity.userData(hymnDisplay.this,key,"pushBack",timeStamp());
                    MainActivity.userData(hymnDisplay.this,key,"pushBack","recording");
                    MainActivity.userData(hymnDisplay.this,key,"pushBack",AudioSavePathInDevice);

                    MediaRecorderReady();


                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException | IOException e) {
                        // TODO Auto-generated catch block
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
    public void onResume()
    {
        // After a pause OR at startup
        super.onResume();

        switch (color.get()){
            case "night":
                bg.setImageDrawable(getResources().getDrawable(R.color.black));
                display.setBackground(getResources().getDrawable(R.color.black));
                hymnnumColor = parseColor("#50ffffff");
                textcolor= capColor = WHITE;
                break;
            case "day":
                bg.setImageDrawable(getResources().getDrawable(R.color.white));
                display.setBackground(getResources().getDrawable(R.color.white));
                textcolor= capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
                break;
            default:
                bg.setImageDrawable(getResources().getDrawable(R.drawable.natured));
                textcolor = WHITE;
                capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
        }
    }
}


