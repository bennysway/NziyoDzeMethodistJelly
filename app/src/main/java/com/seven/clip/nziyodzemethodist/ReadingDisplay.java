package com.seven.clip.nziyodzemethodist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
/*
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
*/
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.graphics.Color.WHITE;

public class ReadingDisplay extends AppCompatActivity {

    private static final String TAG = "ReadingDisplayActivity";
    private ProgressDialog pDialog;
    TextSwitcher themeTitle, psalmReading, otReading, ntReading, gospelReading, psalmFadedText, otFadedText, ntFadedText, gospelFadedText;
    String en_psalm_reading, en_ot_reading, en_nt_reading, en_gospel_reading, en_theme,
            sh_psalm_reading, sh_ot_reading, sh_nt_reading, sh_gospel_reading, sh_theme,
            en_psalm, en_ot, en_nt, en_gospel,
            sh_psalm, sh_ot, sh_nt, sh_gospel,
            date, title,
            url, verseUrl, jsonVerse;
    TextView readingBarText;
    ArrayList<Verse> verses;
    ArrayList<TextView> textViews;
    RelativeLayout psalmLayout, otLayout, ntLayout, gospelLayout;
    int iterator = 1, textColor = WHITE, length, notification_id = 101;
    boolean isLayoutShown = false, isCustomBible, isCustomBibleSet, textSizeChanged;
    ImageView languageIcon, openExternalBut;
    ScrollView summaryLayout, contentScrollLayout;
    View displayLayout;
    LinearLayout contentDisplay, bar;
    float textSize;
    Data textSizeData, bibleOption, biblePickerFirstTime;
    //FloatingActionButton psalmFloatBut, otFloatBut, ntFloatBut, gospelFloatBut;
    //FloatingActionMenu readingsFloatingMenuBut;
    ScaleGestureDetector scaleGestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_display);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView bg = findViewById(R.id.readingDisplayBg);
        themeTitle = findViewById(R.id.readingDisplayThemeTitle);
        languageIcon = findViewById(R.id.readingLangBut);
        openExternalBut = findViewById(R.id.openExternalReadingBut);
        summaryLayout = findViewById(R.id.readingListSummaryScrollView);
        //
        psalmReading = findViewById(R.id.psalmReading);
        otReading = findViewById(R.id.otReading);
        ntReading = findViewById(R.id.ntReading);
        gospelReading = findViewById(R.id.gospelReading);
        //
        psalmFadedText = findViewById(R.id.psalmFadedText);
        otFadedText = findViewById(R.id.otFadedText);
        ntFadedText = findViewById(R.id.ntFadedText);
        gospelFadedText = findViewById(R.id.gospelFadedText);
        //
        psalmLayout = findViewById(R.id.psalmLayout);
        otLayout = findViewById(R.id.otLayout);
        ntLayout = findViewById(R.id.ntLayout);
        gospelLayout = findViewById(R.id.gospelLayout);
        //
        displayLayout = findViewById(R.id.readingInclude);
        contentDisplay = displayLayout.findViewById(R.id.readingDisplayContent);
        bar = displayLayout.findViewById(R.id.readingDisplayBar);
        contentScrollLayout = displayLayout.findViewById(R.id.readingListContentScrollView);
        //
        readingBarText = displayLayout.findViewById(R.id.readingsBarText);
        /*
        psalmFloatBut = findViewById(R.id.psalmFloatBut);
        otFloatBut = findViewById(R.id.otFloatBut);
        ntFloatBut = findViewById(R.id.ntFloatBut);
        gospelFloatBut = findViewById(R.id.gospelFloatBut);
        //
        readingsFloatingMenuBut = findViewById(R.id.readingFloatingBut);
        */


        ReadingsTranslator translator = new ReadingsTranslator();
        textSizeData = new Data(this, "textsize");
        bibleOption = new Data(this, "bibleoption");
        biblePickerFirstTime = new Data(this, "biblepickerfirsttime");
        textSizeChanged = false;
        verses = new ArrayList<>();
        textViews = new ArrayList<>();
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());


        Bundle data = getIntent().getExtras();
        Reading reading = data.getParcelable("reading");

        //Setting summary
        en_gospel = "Gospel";
        sh_gospel = "Vhangeri";
        en_ot = "Old";
        sh_ot = "Yekare";
        en_nt = "New";
        sh_nt = "Itsva";
        en_psalm = "Psalm";
        sh_psalm = "Mapisarema";
        //
        url = "http://bible-api.com/";
        if (reading != null) {
            en_gospel_reading = reading.getGospel();
            en_ot_reading = reading.getOt();
            en_nt_reading = reading.getNt();
            en_theme = reading.getEnglish_theme();
            sh_theme = reading.getShona_theme();
            date = reading.getDatename();
            title = reading.getTitle();
            en_psalm_reading = "Psalm " + String.valueOf(reading.getPsalm());
            //
            sh_psalm_reading = "Vangeri " + String.valueOf(reading.getPsalm());
            sh_gospel_reading = translator.toShona(en_gospel_reading);
            sh_ot_reading = translator.toShona(en_ot_reading);
            sh_nt_reading = translator.toShona(en_nt_reading);

        } else {
            en_gospel_reading = "N/A";
            en_ot_reading = "N/A";
            en_nt_reading = "N/A";
            en_nt = "N/A";
            en_theme = "N/A";
            sh_theme = "N/A";
            date = "N/A";
            title = "N/A";
            sh_gospel_reading = "N/A";
            sh_ot_reading = "N/A";
            sh_nt_reading = "N/A";
        }

        themeTitle = createSwitcher(themeTitle, 1);
        psalmReading = createSwitcher(psalmReading, 2);
        otReading = createSwitcher(otReading, 2);
        ntReading = createSwitcher(ntReading, 2);
        gospelReading = createSwitcher(gospelReading, 2);
        psalmFadedText = createSwitcher(psalmFadedText, 3);
        otFadedText = createSwitcher(otFadedText, 3);
        ntFadedText = createSwitcher(ntFadedText, 3);
        gospelFadedText = createSwitcher(gospelFadedText, 3);

        themeTitle.setText(en_theme);
        //
        psalmReading.setText(en_psalm_reading);
        otReading.setText(en_ot_reading);
        ntReading.setText(en_nt_reading);
        gospelReading.setText(en_gospel_reading);
        //
        psalmFadedText.setText(en_psalm);
        otFadedText.setText(en_ot);
        ntFadedText.setText(en_nt);
        gospelFadedText.setText(en_gospel);
        //
        languageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageIcon.setAlpha(1f);
                languageIcon.animate().alpha(.1f).setDuration(500).setStartDelay(1000);
                if (iterator % 2 != 0) {
                    themeTitle.setText(sh_theme);
                    //
                    psalmReading.setText(sh_psalm_reading);
                    otReading.setText(sh_ot_reading);
                    ntReading.setText(sh_nt_reading);
                    gospelReading.setText(sh_gospel_reading);
                    //
                    psalmFadedText.setText(sh_psalm);
                    otFadedText.setText(sh_ot);
                    ntFadedText.setText(sh_nt);
                    gospelFadedText.setText(sh_gospel);
                    //
                    languageIcon.setImageDrawable(getResources().getDrawable(R.drawable.en_icon_on));
                } else {
                    themeTitle.setText(en_theme);
                    //
                    psalmReading.setText(en_psalm_reading);
                    otReading.setText(en_ot_reading);
                    ntReading.setText(en_nt_reading);
                    gospelReading.setText(en_gospel_reading);
                    //
                    psalmFadedText.setText(en_psalm);
                    otFadedText.setText(en_ot);
                    ntFadedText.setText(en_nt);
                    gospelFadedText.setText(en_gospel);
                    //
                    languageIcon.setImageDrawable(getResources().getDrawable(R.drawable.sh_icon));
                }
                iterator++;
            }
        });

        languageIcon.animate().alpha(.1f).setDuration(2000);


        Handler adHandler = new Handler();
        adHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MobileAds.initialize(getApplicationContext(), "ca-app-pub-2945410942325181~6133597558");
                AdView mAdView = findViewById(R.id.readingAdView);
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                        .addTestDevice("37AF7727CC5B392008ACD1C889F79F4E")  // An example device ID
                        .addTestDevice("6064FEE7D614C18C447097456EC84AC6")  // An example device ID, X4
                        .build();
                mAdView.loadAd(adRequest);
            }
        }, 500);

        String g = textSizeData.get();
        if (g.equals(""))
            textSize = 40f;
        else
            textSize = Float.valueOf(g);

        g = bibleOption.get();
        switch (g) {
            case "":
                isCustomBibleSet = false;
                isCustomBible = false;
                break;
            case "customBible":
                isCustomBible = true;
                isCustomBibleSet = true;
                break;
            default:
                isCustomBibleSet = true;
                isCustomBible = false;
                break;
        }

        g = biblePickerFirstTime.get();
        isCustomBibleSet = !g.equals("");

        //readingsFloatingMenuBut.setClosedOnTouchOutside(true);

        psalmLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(1);
            }
        });

        otLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(2);
            }
        });

        ntLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(3);
            }
        });

        gospelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(4);
            }
        });

        /*psalmFloatBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(1);
                readingsFloatingMenuBut.close(true);
            }
        });

        otFloatBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(2);
                readingsFloatingMenuBut.close(true);
            }
        });

        ntFloatBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(3);
                readingsFloatingMenuBut.close(true);
            }
        });

        gospelFloatBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(4);
                readingsFloatingMenuBut.close(true);
            }
        });*/

        openExternalBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBibleSelectInterface();
            }
        });

        openExternalBut.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isCustomBibleSet = false;
                bibleOption.deleteAll();
                QuickToast("Bible application cleared.");
                return true;
            }
        });


    }

    public void downloadVerses(int index) {
        switch (index) {
            case 1:
                verseUrl = url + en_psalm_reading;
                jsonVerse = spacesToUnderscores(en_psalm_reading);
                readingBarText.setText(en_psalm_reading);
                break;
            case 2:
                verseUrl = url + en_ot_reading;
                jsonVerse = spacesToUnderscores(en_ot_reading);
                readingBarText.setText(en_ot_reading);
                break;
            case 3:
                verseUrl = url + en_nt_reading;
                jsonVerse = spacesToUnderscores(en_nt_reading);
                readingBarText.setText(en_nt_reading);
                break;
            case 4:
                verseUrl = url + en_gospel_reading;
                jsonVerse = spacesToUnderscores(en_gospel_reading);
                readingBarText.setText(en_gospel_reading);
                break;
        }
        new GetVerses().execute();
    }

    public void toggleLayout(int index) {
        if (!isLayoutShown && !isCustomBible && isCustomBibleSet) {
            inContentOutBars(index);


        } else if (!isLayoutShown && !isCustomBibleSet) {
            inContentOutBars(index);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pickerIntro();
                }
            }, 1000);
            isCustomBibleSet = true;
            //Check bible and Launch

        } else {
            inContentOutBars(index);
        }
    }

    public void inContentOutBars(final int index) {
        displayLayout.setAlpha(1f);
        displayLayout.setX(0f);
        psalmLayout.animate().x(-100f).alpha(0f).setDuration(100).setStartDelay(indexAnimationTime(index, 1));
        otLayout.animate().x(-100f).alpha(0f).setDuration(100).setStartDelay(indexAnimationTime(index, 2));
        ntLayout.animate().x(-100f).alpha(0f).setDuration(100).setStartDelay(indexAnimationTime(index, 3));
        gospelLayout.animate().x(-100f).alpha(0f).setDuration(100).setStartDelay(indexAnimationTime(index, 4));
        summaryLayout.animate().alpha(0f).setStartDelay(500).setDuration(100);
        downloadVerses(index);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                summaryLayout.setVisibility(View.INVISIBLE);
                displayLayout.setVisibility(View.VISIBLE);
                isLayoutShown = true;
                // Display content
                contentDisplay.removeAllViews();
                View[] hymnStanzas = getTextviews();
                for (View hymnStanza : hymnStanzas) contentDisplay.addView(hymnStanza);
                contentScrollLayout.smoothScrollTo(0, 0);
            }
        }, 600);
    }

    public void inBarsOutContent(final int index) {
        displayLayout.animate().alpha(0f).x(100f).setDuration(100);
        displayLayout.setVisibility(View.INVISIBLE);
        summaryLayout.setVisibility(View.VISIBLE);
        summaryLayout.setAlpha(1f);
        //push layouts right
        psalmLayout.setX(100f);
        otLayout.setX(100f);
        ntLayout.setX(100f);
        gospelLayout.setX(100f);
        //set transparent
        psalmLayout.setAlpha(0f);
        otLayout.setAlpha(0f);
        ntLayout.setAlpha(0f);
        gospelLayout.setAlpha(0f);
        //
        psalmLayout.animate().x(0f).alpha(1f).setDuration(100).setStartDelay(200);
        otLayout.animate().x(0f).alpha(1f).setDuration(100).setStartDelay(300);
        ntLayout.animate().x(0f).alpha(1f).setDuration(100).setStartDelay(400);
        gospelLayout.animate().x(0f).alpha(1f).setDuration(100).setStartDelay(500);
        isLayoutShown = false;
    }

    public View[] getTextviews() {
        int size = verses.size();
        textViews.clear();
        View[] views = new View[size]; // Creating an instance for View Object
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < size; i++) {
            views[i] = inflater.inflate(R.layout.reading_verse_layout, null);
            TextView r_num = views[i].findViewById(R.id.readingNum);
            TextView r_verse = views[i].findViewById(R.id.readingVerse);
            r_num.setText((String.valueOf(verses.get(i).getVerse())));
            r_verse.setText(verses.get(i).getText());
            r_verse.setTextSize(textSize);
            r_verse.setId(i);
            textViews.add(r_verse);
        }
        return views;
    }

    public long indexAnimationTime(int index, int click) {
        long[][] table = new long[][]{
                {300, 0, 100, 200},
                {0, 300, 100, 200},
                {200, 0, 300, 100},
                {200, 100, 0, 300}
        };

        return table[index - 1][click - 1];
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if (isLayoutShown) {
                inBarsOutContent(1);
                if (textSizeChanged) {
                    textSizeData.update(String.valueOf(pixelsToSp(textSize)));
                    textSize = Float.valueOf(textSizeData.get());
                    textSizeChanged = false;
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (textSizeChanged) {
            textSizeData.update(String.valueOf(pixelsToSp(textSize)));
            textSizeChanged = false;
        }
    }

    public String spacesToUnderscores(String input) {
        return input.replace(" ", "_");
    }

    public float pixelsToSp(float px) {
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public void showBibleSelectInterface() {
        if (isCustomBibleSet) {
            Intent i;
            PackageManager manager = getPackageManager();
            try {
                i = manager.getLaunchIntentForPackage(bibleOption.get());
                if (i == null)
                    throw new PackageManager.NameNotFoundException();
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                createNotification(readingBarText.getText().toString());
                startActivity(i);
            } catch (PackageManager.NameNotFoundException e) {
                startActivityForResult(new Intent(this, BiblePicker.class), 1);
            }
        } else {
            startActivityForResult(new Intent(this, BiblePicker.class), 1);
        }
    }

    public void QuickToast(String s) {
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (null != data) {
                isCustomBibleSet = true;
                QuickToast("Bible set.");
                bibleOption.update(data.getStringExtra("requestCode"));
                deletePickerIntro();
            }
        }
    }

    public void pickerIntro() {
        biblePickerFirstTime.update("false");
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.openExternalReadingBut), "Open Verse externally", "Click here to use your favorite Bible application")
                        // All options below are optional
                        .outerCircleColor(R.color.burn)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(30)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(12)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.burn)  // Specify the color of the description text
                        .textColor(R.color.white)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view'underLine color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        //.icon(Drawable)                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        //doSomething();
                    }
                });
    }

    public void deletePickerIntro() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.openExternalReadingBut), "Also remove application setting", "Long click here to clear the application you use as your Bible.")
                        // All options below are optional
                        .outerCircleColor(R.color.burn)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(30)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(12)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.burn)  // Specify the color of the description text
                        .textColor(R.color.white)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view'underLine color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        //.icon(Drawable)                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        //doSomething();
                    }
                });
    }

    public TextSwitcher createSwitcher(final TextSwitcher view, final int mode) {
        view.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView captionView = new TextView(ReadingDisplay.this);
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/bh.ttf");
                switch (mode) {
                    case 1:
                        //Top title
                        captionView.setTextColor(WHITE);
                        captionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
                        captionView.setGravity(Gravity.CENTER);
                        captionView.setPadding(5, 5, 5, 5);
                        captionView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                        captionView.setTypeface(custom_font);
                        break;
                    case 2:
                        //Reading in summary view
                        captionView.setTextColor(WHITE);
                        captionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        captionView.setPadding(10, 10, 10, 10);
                        captionView.setGravity(Gravity.START);
                        break;
                    case 3:
                        //Faded label
                        captionView.setTextColor(WHITE);
                        captionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
                        captionView.setPadding(10, 10, 10, 10);
                        captionView.setGravity(Gravity.END);
                        break;
                }


                return captionView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_likes_counter);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_likes_counter);
        view.setInAnimation(in);
        view.setOutAnimation(out);
        return view;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return scaleGestureDetector.onTouchEvent(ev);
    }

    public class simpleOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (textViews.size() > 0 && isLayoutShown) {
                float size = textViews.get(0).getTextSize();

                Log.d("TextSizeStart", String.valueOf(size));
                float factor = detector.getScaleFactor();
                Log.d("Factor", String.valueOf(factor));
                float product = size * factor;
                Log.d("TextSize", String.valueOf(product));
                changeTextSize(TypedValue.COMPLEX_UNIT_PX, product);

                size = textViews.get(0).getTextSize();
                Log.d("TextSizeEnd", String.valueOf(size));
            }

            return true;
        }
    }

    public void changeTextSize(int unit, float value) {

        for (int l = 0; l < textViews.size(); l++) {
            if (value > 1f && value < 500f) {
                textViews.get(l).setTextSize(unit, value);
            }
        }
        textSize = textViews.get(0).getTextSize();
        textSizeChanged = true;

    }

    public void createNotification(String text) {
        PendingIntent notifyPIntent =
                PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(text)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(text)
                .setContentText("Today'underLine Reading")
                .setAutoCancel(true)
                .setContentIntent(notifyPIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notification_id, notification);
    }


    @Override
    protected void onDestroy() {
        NotificationManager nMgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancel(notification_id);
        super.onDestroy();
    }

    private class GetVerses extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ReadingDisplay.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr;

            // Making a request to url and getting response
            if (new ReadWriteJsonVerseFileUtils(ReadingDisplay.this).fileExists(jsonVerse)) {
                jsonStr = new ReadWriteJsonVerseFileUtils(ReadingDisplay.this).readJsonFileData(jsonVerse);
                Log.e(TAG, "Response from storage" + jsonStr);
            } else {
                jsonStr = sh.makeServiceCall(verseUrl);
                Log.e(TAG, "Response from url" + jsonStr);
            }

            verses.clear();

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray jsonVerses = jsonObj.getJSONArray("verses");

                    // looping through All verses
                    for (int i = 0; i < jsonVerses.length(); i++) {
                        JSONObject c = jsonVerses.getJSONObject(i);
                        Verse jVerse = new Verse();

                        jVerse.setBook_id();
                        jVerse.setBook_name();
                        jVerse.setChapter();
                        jVerse.setVerse(c.getLong("verse"));
                        jVerse.setText(c.getString("text"));
                        verses.add(jVerse);
                    }
                    if (!(new ReadWriteJsonVerseFileUtils(ReadingDisplay.this).fileExists(jsonVerse))) {
                        new ReadWriteJsonVerseFileUtils(ReadingDisplay.this).createJsonFileData(jsonVerse, jsonStr);
                        Log.e(TAG, "Added json to storage from url: " + jsonVerse);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get verses from server.",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /*
             * Updating parsed JSON data into ListView
             */

        }

    }
}

