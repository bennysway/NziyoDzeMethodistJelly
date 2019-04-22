package com.seven.clip.nziyodzemethodist;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.parseColor;

public class HymnDisplay extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static final int RequestPermissionCode = 1;
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 102;


    ScrollView myScroll;
    int bound, totalHeight, clength, textcolor, capColor, hymnnumColor, buttonLayoutBg, hasOptions = 0;
    int windowWidth, windowHeight, click = 0, backgroundResource;
    Boolean chorusAvail = false, chorusShown = false, isInEnglish = false, isRecording = false, isEnglishAvailable = false,
            isRecordingSaved = false;
    ImageView bg, auxBut, loadBar;
    ImageView micBut, editBut, enBut, heartBut, fontBut, colorBut, shareBut, nextBut, prevBut;
    TableRow micRow, editRow, enRow, heartRow, fontRow, colorRow, shareRow, nextRow, prevRow;
    TableLayout menuLayout;
    boolean micButBool, editButBool, enButBool, heartButBool, fontButBool, colorButBool, shareButBool, nextButBool, prevButBool,
            helpOptions = false, canShowDialog = true;
    float textSize, buttonSize, prevSize;
    RelativeLayout display;
    TextView number, title, chorus, hymnpop;
    TextSwitcher captionShow;
    Button showCaption;
    boolean textSizeChanged, menuOpen, favInit, chorusTrasparent, canSlideRight, canSlideLeft;
    long starttime = 0;
    String AudioSavePathInDevice, RandomAudioFileName = "ABCDEFGHIJKLMNOP", capStoreKey, s, safe, hymnName;
    MediaRecorder mediaRecorder;
    Random random;
    //Data favList, recordFlag, color, textSizeData, recList, favIterator, recIterator, withCaption;
    //Data recordFlag;
    UserDataIO userData;
    EnResource engCheck;
    ScaleGestureDetector scaleGestureDetector;
    Intent hymnDisplayIntent;
    LinearLayout hymnDispl;
    //stanza handling
    ArrayList<Integer> stanzaBarStack;
    ArrayList<TextView> stanzas;
    Runnable passId, clickDuration;
    Handler tapTimer;
    View.OnTouchListener tapStanza;
    View.OnLongClickListener longTapStanza;
    xHymns hymns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hymn_display);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        windowWidth = dm.widthPixels;
        windowHeight = dm.heightPixels;


        Handler adHandler = new Handler();
        adHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.ADMOB_APP_ID));
                AdView mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                        .addTestDevice("37AF7727CC5B392008ACD1C889F79F4E")  // An example device ID
                        .addTestDevice("6064FEE7D614C18C447097456EC84AC6")  // An example device ID
                        .build();
                mAdView.loadAd(adRequest);
            }
        }, 500);


        hymnDisplayIntent = new Intent();
        engCheck = new EnResource(this);
        hymns = new xHymns(this);
        userData = new UserDataIO(this);

        s = getIntent().getStringExtra("hymnNum");
        boolean setToEnglish = getIntent().getBooleanExtra("isInEnglish", false);
        userData.addToRecentList(s);

        safe = NumToWord.convert(StrToInt(s)) + "key";
        capStoreKey = safe;

        chorus = findViewById(R.id.chorusFloat);

        captionShow = findViewById(R.id.caption);
        number = findViewById(R.id.hymnDisplayNum);
        title = findViewById(R.id.hymnTitle);
        hymnpop = findViewById(R.id.hymnDisplayNumlight);
        hymnDispl = findViewById(R.id.hymnLayout);
        myScroll = findViewById(R.id.scrollHymn);
        showCaption = findViewById(R.id.showCaption);
        final Intent toPasteBin = new Intent(this, ShareCustom.class);
        bg = findViewById(R.id.hymnDisplayBg);
        display = findViewById(R.id.activity_hymn_display);
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());

        micBut = findViewById(R.id.hymnRecordBut);
        editBut = findViewById(R.id.hymnWriteNoteBut);
        enBut = findViewById(R.id.hymnEnBut);
        heartBut = findViewById(R.id.hymnFavBut);
        fontBut = findViewById(R.id.hymnFontBut);
        colorBut = findViewById(R.id.hymnColorBut);
        shareBut = findViewById(R.id.hymnShareBut);
        nextBut = findViewById(R.id.hymnForwardBut);
        prevBut = findViewById(R.id.hymnBackBut);

        auxBut = findViewById(R.id.helpIcon);
        loadBar = findViewById(R.id.loadBar);

        micRow = findViewById(R.id.menuRow1);
        editRow = findViewById(R.id.menuRow2);
        enRow = findViewById(R.id.menuRow3);
        heartRow = findViewById(R.id.menuRow4);
        fontRow = findViewById(R.id.menuRow5);
        colorRow = findViewById(R.id.menuRow6);
        shareRow = findViewById(R.id.menuRow7);
        prevRow = findViewById(R.id.menuRow8);
        nextRow = findViewById(R.id.menuRow9);

        menuLayout = findViewById(R.id.hymnMenuLayout);

        stanzaBarStack = new ArrayList<>();
        stanzas = new ArrayList<>();


        textSizeChanged = false;
        menuOpen = false;
        chorusTrasparent = false;
        isEnglishAvailable = engCheck.isEn(s);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/bh.ttf");
        title.setTypeface(custom_font);
        number.setTypeface(custom_font);
        hymnpop.setTypeface(custom_font);

        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = this.obtainStyledAttributes(attrs);
        backgroundResource = typedArray.getResourceId(0, 0);
        typedArray.recycle();

        String g = userData.getTextSize();
        if (g.equals(""))
            textSize = 40f;
        else
            textSize = Float.valueOf(g);

        tapTimer = new Handler();
        createCaption();
        loadBar.animate().scaleX(0.0f);


        tapStanza = new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View stanza, MotionEvent event) {
                passId = new Runnable() {
                    @Override
                    public void run() {
                        int position = stanzas.indexOf(stanza);
                        addBar(position,((TextView) stanza).getText().toString());
                    }
                };
                clickDuration = new Runnable() {
                    @Override
                    public void run() {
                        click = 0;
                        loadBar.animate().scaleX(0.0f);
                    }
                };
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (event.getX() < 50) {
                            if (canSlideRight) {
                                openNavigationDrawer();
                                return true;
                            }
                        } else if (event.getX() > 50) {
                            if (menuOpen) {
                                retractMenu();
                            } else if (canSlideLeft) {
                                closeNavigationDrawer();
                            } else if (stanzaBarStack.isEmpty()) {
                                return false;
                            } else {
                                int pop = stanzaBarStack.remove(0);
                                return false;
                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }
        };

        longTapStanza = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View stanza) {
                if(stanzaBarStack.isEmpty()){
                    int position = stanzas.indexOf(stanza);
                    addBar(position,((TextView) stanza).getText().toString());
                    stanzaBarStack.add(stanza.getId());
                    return true;
                } else
                    return false;
            }
        };


        changeColorMode(userData.getUserColor());

        populateHymn(s, setToEnglish);

        myScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = myScroll.getScrollY(); // For ScrollView
                View view = myScroll.getChildAt(myScroll.getChildCount() - 1);
                int diff = (view.getBottom() - (myScroll.getHeight() + myScroll.getScrollY()));

                if (scrollY > bound && chorusAvail) {
                    chorus.setVisibility(View.VISIBLE);
                    hymnpop.setVisibility(View.VISIBLE);
                    chorusShown = true;

                } else {
                    chorus.setVisibility(View.INVISIBLE);
                    hymnpop.setVisibility(View.INVISIBLE);
                    chorusShown = false;
                }
            }
        });


        if (userData.getFavoriteList().contains(s)) {
            heartBut.setImageResource(R.drawable.heart_icon_on);
            heartButBool = true;
        }
        favInit = heartButBool;

        heartBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heartButBool) {
                    flipHeartIcon();
                    heartButBool = false;
                } else {
                    flipHeartIcon();
                    heartButBool = true;
                }
            }
        });

        chorus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chorusTrasparent) {
                    chorus.animate().alpha(1f);
                    chorusTrasparent = false;
                } else {
                    chorus.animate().alpha(.0f);
                    chorusTrasparent = true;
                }
            }
        });

        //micBut,editBut,enBut,heartBut,fontBut,colorBut,shareBut,nextBut,prevBut;
        micButBool = editButBool = fontButBool = colorButBool = shareButBool = nextButBool = prevButBool = canSlideRight = canSlideLeft = false;
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
        }, 4000);

        auxBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whichMenuRow() != 0) {
                    inflateHelpLayout(whichMenuRow());
                } else {
                    TextView[] helpCaptions = new TextView[9];
                    if (!helpOptions) {
                        helpCaptions[0] = createMenuHelpCaption("record");
                        helpCaptions[1] = createMenuHelpCaption("note");
                        helpCaptions[2] = createMenuHelpCaption("english");
                        helpCaptions[3] = createMenuHelpCaption("fav");
                        helpCaptions[4] = createMenuHelpCaption("fontSize");
                        helpCaptions[5] = createMenuHelpCaption("colors");
                        helpCaptions[6] = createMenuHelpCaption("shareBut");
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
                    } else {
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
                if (isAnyMenuOpen()) {
                    retractMenu();
                } else {
                    final ImageView[] micOptions = new ImageView[3];
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
                            Intent toCaptions = new Intent(HymnDisplay.this, Captions.class);
                            toCaptions.putExtra("hymnNumWord", safe);
                            toCaptions.putExtra("hymnNum", s);
                            toCaptions.putExtra("hymnName", hymnName);
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
                if (isAnyMenuOpen()) {
                    retractMenu();
                } else {
                    ImageView[] editOptions = new ImageView[2];
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
                            Intent toNote = new Intent(HymnDisplay.this, makeNote.class);
                            toNote.putExtra("captionKey", capStoreKey);
                            toNote.putExtra("captionType", "note");
                            toNote.putExtra("hymnNum", s);
                            startActivity(toNote);
                        }
                    });

                    editOptions[1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retractMenu();
                            retractMenu();
                            Intent toCaptions = new Intent(HymnDisplay.this, Captions.class);
                            toCaptions.putExtra("hymnNumWord", safe);
                            toCaptions.putExtra("hymnNum", s);
                            toCaptions.putExtra("hymnName", hymnName);
                            startActivity(toCaptions);
                        }
                    });
                }
            }
        });
        if (isEnglishAvailable && !isInEnglish) {
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
            }, 4000);
        }

        enBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeEnglishHymn();
            }
        });
        fontBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnyMenuOpen()) {
                    retractMenu();
                } else {
                    final SeekBar track = addSeekBar();
                    final TextView size = createMenuHelpCaption("textSize");
                    ImageView reset = createFadedButton("reset");

                    reset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            changeTextSizeBySeekBar((float) 40);
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
        });

        colorBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnyMenuOpen()) {
                    retractMenu();
                } else {
                    ImageView[] colorOptions = new ImageView[4];
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
                            userData.setUserColor("day");
                            changeColorMode("day");
                            fadeInHymn();

                        }
                    });

                    colorOptions[1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userData.setUserColor("night");
                            changeColorMode("night");
                            fadeInHymn();
                        }
                    });

                    colorOptions[2].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent toThemer = new Intent(HymnDisplay.this, ThemeChooser.class);
                            startActivityForResult(toThemer, 3);
                        }
                    });

                    colorOptions[3].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bg.setColorFilter(parseColor("#00000000"));
                            userData.setUserColor("");
                            changeColorMode("");
                            fadeInHymn();
                            flipBgResetIcon();
                        }
                    });
                }

            }
        });

        shareBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnyMenuOpen()) {
                    retractMenu();
                } else {
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
                            for (int l = 0; l < stanzas.size(); l++)
                                text = text + String.valueOf(l + 1) + ". " + stanzas.get(l).getText().toString() + "\n";

                            toPasteBin.putExtra("text", text);
                            startActivity(toPasteBin);
                        }
                    });

                    shareOptions[2].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "";
                            for (int l = 0; l < stanzas.size(); l++)
                                text = text + String.valueOf(l + 1) + ". " + stanzas.get(l).getText().toString() + "\n";

                            copy(text, s);
                        }
                    });
                }

            }
        });

        prevBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(s) > 1) {
                    nextBut.setColorFilter(null);
                    finalizeFavoriteState();
                    populateHymn(String.valueOf(Integer.valueOf(s) - 1), false);
                    checkFavorite();
                    checkEnglish();
                } else {
                    prevBut.setColorFilter(GRAY);
                }
            }
        });

        nextBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(s) < 321) {
                    prevBut.setColorFilter(null);
                    finalizeFavoriteState();
                    populateHymn(String.valueOf(Integer.valueOf(s) + 1), false);
                    checkFavorite();
                    checkEnglish();
                } else {
                    nextBut.setColorFilter(GRAY);
                }
            }
        });
        ////TODO
        //end of functions
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (null != data) {
                String fromData = data.getStringExtra("mode");
                changeColorMode(fromData);
                fadeInHymn();
                //// TODO: 23.03.17
                //// set button bg refresh

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finalizeFavoriteState();
        favInit = heartButBool;
        userData.save();
    }

    @Override
    public void onResume() {
        if(userData!=null)
            userData = new UserDataIO(this);
        super.onResume();
        if (hasOptions == 1)
            finish();
    }

    public void fadeInOut(final View v, int delay, final int duration) {
        v.animate().alpha(1f).setDuration(duration).setStartDelay(delay).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.animate().setStartDelay(0).alpha(0f).setDuration(duration);
            }
        });
    }

    public void slideRight(View v, float distance, int delay, final int duration) {
        v.animate().alpha(1f).translationXBy(distance).setDuration(duration + 3 * delay).setStartDelay(delay);
    }

    public void slideLeft(View v, float distance, int delay, final int duration) {
        v.animate().alpha(1f).translationXBy(distance).setDuration(duration).setStartDelay(delay);
    }

    public ImageView createFadedButton(String name) {
        ImageView button = new ImageView(this);
        button.setPadding(10, 0, 10, 0);
        button.setBackgroundDrawable(getResources().getDrawable(R.color.transBlack));
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        switch (name) {
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
                button.setPadding(20, 0, 20, 0);
                button.setBackgroundDrawable(getResources().getDrawable(R.drawable.faded_black_center));
                break;
            case "isFav":
                button.setImageResource(R.drawable.heart_normal);
                button.setPadding(20, 0, 20, 0);
                button.setBackgroundDrawable(getResources().getDrawable(R.drawable.faded_black_center));
                break;
        }
        return button;
    }

    public TextView createMenuHelpCaption(String name) {
        TextView textView = new TextView(this);
        textView.setBackground(getResources().getDrawable(R.drawable.to_left_black));
        textView.setTextColor(parseColor("#ffffff"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        textView.setPadding(10, 0, 10, 0);
        textView.setShadowLayer(7, 0, 0, Color.BLACK);
        switch (name) {
            case "record":
                textView.setText("Make a recording");
                break;
            case "note":
                textView.setText("Write or open notes");
                break;
            case "english":
                if (isInEnglish)
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
            case "shareBut":
                textView.setText("Sharing options");
                break;
            case "prev":
                textView.setText("Previous Hymn");
                break;
            case "next":
                textView.setText("Next Hymn");
                break;
            case "date":
                Calendar calendar = Calendar.getInstance();
                String format = new SimpleDateFormat("E, MMM d, yyyy", Locale.getDefault()).format(calendar.getTime());
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

    public SeekBar addSeekBar() {
        SeekBar track = new SeekBar(this);
        track.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prevSize = textSize;
                TextView sample = (TextView) fontRow.getChildAt(2);
                sample.setText(String.valueOf(progress + 25));
                changeTextSizeBySeekBar((float) (progress + 25));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                flipResetIcon();
            }
        });
        track.setLayoutParams(new LayoutParams(windowWidth / 2, LayoutParams.MATCH_PARENT));
        track.setBackgroundDrawable(getResources().getDrawable(R.color.transBlack));
        return track;
    }

    public void introSideBarAnimation() {
        fadeInOut(micBut, 1000, 700);
        fadeInOut(editBut, 1100, 700);
        fadeInOut(enBut, 1200, 700);
        fadeInOut(heartBut, 1300, 700);
        fadeInOut(fontBut, 1400, 700);
        fadeInOut(colorBut, 1500, 700);
        fadeInOut(shareBut, 1600, 700);
        fadeInOut(prevBut, 1700, 700);
        fadeInOut(nextBut, 1800, 700);
    }

    public void resetSideBarPosition() {
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

    public void flipHelpIcon() {
        if (helpOptions) {
            auxBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    auxBut.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
                    auxBut.animate().scaleX(1f).setDuration(100);
                }
            });
        } else {
            auxBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    auxBut.setImageDrawable(getResources().getDrawable(R.drawable.ic_question_mark));
                    auxBut.animate().setDuration(100).scaleX(1f);
                }
            });
        }
    }

    public void flipEnglishIcon() {
        if (isEnglishAvailable) {
            if (isInEnglish) {
                enBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        enBut.setImageDrawable(getResources().getDrawable(R.drawable.sh_icon));
                        enBut.animate().scaleX(1f).setDuration(100);
                    }
                });
            } else {
                enBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        enBut.setImageDrawable(getResources().getDrawable(R.drawable.en_icon_on));
                        enBut.animate().setDuration(100).scaleX(1f);
                    }
                });
            }
        } else {
            enBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    enBut.setImageDrawable(getResources().getDrawable(R.drawable.en_icon));
                    enBut.animate().scaleX(1f).setDuration(100);
                }
            });
        }

    }

    public void flipHeartIcon() {
        if (heartButBool) {
            heartBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    heartBut.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_icon));
                    heartBut.animate().scaleX(1f).setDuration(100);
                }
            });
        } else {
            heartBut.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    heartBut.setImageDrawable(getResources().getDrawable(R.drawable.heart_icon_on));
                    heartBut.animate().setDuration(100).scaleX(1f);
                }
            });
        }
    }

    public void flipRecIcon() {
        final ImageView button1 = (ImageView) micRow.getChildAt(1);
        final ImageView button2 = (ImageView) micRow.getChildAt(3);
        if (isRecording) {
            button1.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    if (isRecordingSaved) {
                        button1.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                        button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.faded_white_center));
                    } else
                        button1.setImageDrawable(getResources().getDrawable(R.drawable.close_icon));
                    button1.animate().scaleX(1f).setDuration(100);
                }
            });
        } else {
            button1.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    button1.setImageDrawable(getResources().getDrawable(R.drawable.ic_record_circle_red_24dp));
                    button1.animate().scaleX(1f).setDuration(100);
                }
            });
        }
    }

    public void flipResetIcon() {
        final ImageView v = (ImageView) fontRow.getChildAt(3);
        v.animate().scaleX(0f).setDuration(100).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));
                v.animate().scaleX(1f).setDuration(100);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //textSizeData.update(String.valueOf(textSize));
                        textSizeChanged = true;
                        retractMenu();
                    }
                });

            }
        });
    }

    public void flipBgResetIcon() {
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

    public boolean isAnyMenuOpen() {
        return (micButBool || editButBool || enButBool || fontButBool || colorButBool || shareButBool || nextButBool || prevButBool || helpOptions);
    }

    public boolean retractMenu() {
        boolean menuRetracted = false;
        if (helpOptions) {
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
        } else if (micButBool) {
            micRow.removeViewAt(1);
            micRow.removeViewAt(1);
            micRow.removeViewAt(1);
            micRow.removeViewAt(1);
            micButBool = false;
            menuRetracted = true;
        } else if (editButBool) {
            editRow.removeViewAt(1);
            editRow.removeViewAt(1);
            editRow.removeViewAt(1);
            editButBool = false;
            menuRetracted = true;

        } else if (fontButBool) {
            fontRow.removeViewAt(1);
            fontRow.removeViewAt(1);
            fontRow.removeViewAt(1);
            fontButBool = false;
            menuRetracted = true;
        } else if (colorButBool) {
            colorRow.removeViewAt(1);
            colorRow.removeViewAt(1);
            colorRow.removeViewAt(1);
            colorRow.removeViewAt(1);
            colorButBool = false;
            menuRetracted = true;
        } else if (shareButBool) {
            shareRow.removeViewAt(1);
            shareRow.removeViewAt(1);
            shareRow.removeViewAt(1);
            shareButBool = false;
            menuRetracted = true;
        }
        /**/
        else if (menuOpen) {
            slideLeft(micBut, -buttonSize, 50, 400);
            slideLeft(editBut, -buttonSize, 50, 400);
            slideLeft(enBut, -buttonSize, 50, 400);
            slideLeft(heartBut, -buttonSize, 50, 400);
            slideLeft(fontBut, -buttonSize, 50, 400);
            slideLeft(colorBut, -buttonSize, 50, 400);
            slideLeft(shareBut, -buttonSize, 50, 400);
            slideLeft(prevBut, -buttonSize, 50, 400);
            slideLeft(nextBut, -buttonSize, 50, 400);
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
            }, 600);
            auxBut.setVisibility(View.INVISIBLE);
            canSlideRight = true;
            canSlideLeft = false;
            menuOpen = false;
            menuRetracted = true;
        }
        deobfuscateHymn();
        return menuRetracted;
    }

    public int whichMenuRow() {
        int result;
        if (micButBool)
            result = 1;
        else if (editButBool)
            result = 2;
        else if (fontButBool)
            result = 5;
        else if (colorButBool)
            result = 6;
        else if (shareButBool)
            result = 7;
        else result = 0;

        return result;
    }

    public void changeTextSizeBySeekBar(float value) {

        for (int l = 0; l < stanzas.size(); l++)
            (stanzas.get(l)).setTextSize(value);
        textSize = (stanzas.get(0)).getTextSize();
        textSizeChanged = true;
    }


    public void changeTextSize(int unit, float value) {

        for (int l = 0; l < stanzas.size(); l++) {
            if (value > 1f && value < 700f) {
                (stanzas.get(l)).setTextSize(unit, value);
            }
        }
        textSize = (stanzas.get(0)).getTextSize();
        textSizeChanged = true;

    }

    //Color Management
    public void changeColorMode(String mode) {
        switch (mode) {
            case "night":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setColorFilter(parseColor("#000000"));
                //display.setBackground(getResources().getDrawable(R.color.black));
                hymnnumColor = parseColor("#50ffffff");
                buttonLayoutBg = parseColor("#70ffffff");
                textcolor = capColor = WHITE;
                break;
            case "day":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setColorFilter(parseColor("#ffffff"));
                textcolor = capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
                buttonLayoutBg = parseColor("#70000000");
                break;
            case "":
                bg.animate().alpha(1f).setDuration(2000);
                bg.setImageDrawable(getResources().getDrawable(R.drawable.natured_small));
                textcolor = WHITE;
                capColor = BLACK;
                hymnnumColor = parseColor("#50000000");
                buttonLayoutBg = parseColor("#70000000");
                break;
            default:
                bg.animate().alpha(0f).setDuration(2000);
                if (isColorDark(mode)) {
                    hymnnumColor = parseColor("#50ffffff");
                    buttonLayoutBg = parseColor("#70ffffff");
                    textcolor = capColor = WHITE;
                } else {
                    textcolor = capColor = BLACK;
                    hymnnumColor = parseColor("#50000000");
                    buttonLayoutBg = parseColor("#70000000");
                }
                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[]{getLighterColor(mode), parseColor(mode), parseColor(mode), parseColor(mode), getDarkerColor(mode)});
                gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                display.setBackgroundDrawable(gd);
                capColor = BLACK;
        }
        number.setTextColor(hymnnumColor);
    }

    public boolean isColorDark(String test) {
        int color = parseColor(test);
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.333;
    }

    public void fadeInHymn() {
        for (int l = 0; l < stanzas.size(); l++) {
            stanzas.get(l).setAlpha(0f);
            stanzas.get(l).setTextColor(textcolor);
            stanzas.get(l).animate().alpha(1f).setDuration(700 * l);
        }

    }

    public int getDarkerColor(String hexCode) {
        float[] hsv = new float[3];
        int color = parseColor(hexCode);
        Color.colorToHSV(color, hsv);
        hsv[0] += (hsv[0] * 0.2f); // value component
        color = Color.HSVToColor(hsv);
        return color;
    }

    public int getLighterColor(String hexCode) {
        float[] hsv = new float[3];
        int color = parseColor(hexCode);
        Color.colorToHSV(color, hsv);
        hsv[0] = 1.0f - 0.8f * (1.0f - hsv[0]); // value component
        color = Color.HSVToColor(hsv);
        return color;
    }

    public void saveFavState() {
        if (heartButBool)
            userData.addToFavorites(s);
        else
            userData.removeFromFavorites(s);
    }


    public void makeEnglishHymn() {
        changeColorMode(userData.getUserColor());
        if (isEnglishAvailable && !isInEnglish) {
            isInEnglish = true;
            flipEnglishIcon();
            populateHymn(s, true);

            final Handler en_handler = new Handler();
            en_handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    totalHeight = myScroll.getChildAt(0).getHeight();
                    bound = (totalHeight / stanzas.size());
                    String check = stanzas.get(0).getText().toString();
                    if (check.contains("chorus")) {
                        chorus.setText(check.substring(check.lastIndexOf("chorus")));
                        chorusAvail = true;
                    } else
                        chorusAvail = false;
                }
            }, 300);
        } else if (isInEnglish && isEnglishAvailable) {
            isInEnglish = false;
            flipEnglishIcon();
            populateHymn(s, false);
            changeColorMode(userData.getUserColor());
            final Handler en_handler = new Handler();
            en_handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    totalHeight = myScroll.getChildAt(0).getHeight();
                    bound = (totalHeight / stanzas.size());
                    String check = stanzas.get(0).getText().toString();
                    if (check.contains("chorus")) {
                        chorus.setText(check.substring(check.lastIndexOf("chorus")));
                        chorusAvail = true;
                    } else
                        chorusAvail = false;
                }
            }, 300);
        }
    }

    public void inflateHelpLayout(int menu) {
        final RelativeLayout parent = findViewById(R.id.activity_hymn_display);
        final View helpPopup = getLayoutInflater().inflate(R.layout.help_popup, parent, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, helpPopup.getId());
        helpPopup.setLayoutParams(params);
        parent.addView(helpPopup);
        final ImageView icon = findViewById(R.id.helpDisplayIcon);
        final TextView helpCaption = findViewById(R.id.helpText);
        Handler timer = new Handler();
        obfuscateHymn();

        switch (menu) {
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
                }, 2000);
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
                }, 4000);
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
                }, 6000);
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
                }, 2000);
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
                }, 2000);
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
                }, 4000);
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
                }, 2000);
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
                }, 4000);
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_settings_backup_restore_black_24dp));
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
                }, 6000);
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
                }, 2000);
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
                }, 4000);
                break;

        }
    }

    public void obfuscateHymn() {
        for (int q = 0; q < stanzas.size(); q++) {
            stanzas.get(q).animate().scaleX(.8f).scaleY(.8f).alpha(.1f);
        }
    }

    public void deobfuscateHymn() {
        for (int q = 0; q < stanzas.size(); q++) {
            stanzas.get(q).animate().scaleX(1f).scaleY(1f).alpha(1f);
        }
    }

    public void QuickToast(String s) {
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    public int StrToInt(String s) {
        return Integer.valueOf(s);
    }

    public int getResourceId(String pVariableName, String pResourcename, String pPackageName) {
        try {
            return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void shareAppFunc() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Nziyo DzeMethodist");
            String sAux = "Methodist Church in Zimbabwe\nNziyo dzeMethodist\nHymnBook\nNow available on Google Play\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.seven.clip.nziyodzemethodist \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share the Gospel:"));
        } catch (Exception e) {
            //e.toString();
        }

    }

    public void copy(String s, String num) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Hymn", s);
        clipboard.setPrimaryClip(clip);
        QuickToast("Hymn copied to clipboard");
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Nziyo DzeMethodist");
            i.putExtra(Intent.EXTRA_TEXT, "Hymn " + num + "\n" +
                    s + "\n" +
                    "\n" +
                    "Shared via Nziyo DzeMethodist App\n" +
                    "https://play.google.com/store/apps/details?id=com.seven.clip.nziyodzemethodist");
            startActivity(Intent.createChooser(i, "Share Hymn via:"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public boolean MediaRecorderReady() {
        boolean canRecord;
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            canRecord = true;
        } catch (IOException e) {
            Log.e("Recorder", "prepare() failed");
            canRecord = false;
        }


        return canRecord;
    }

    public String CreateRandomAudioFileName(int string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(HymnDisplay.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
        requestMorePermission(android.Manifest.permission.RECORD_AUDIO,
                RECORD_REQUEST_CODE);
        requestMorePermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(HymnDisplay.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(HymnDisplay.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,
                            "Record permission required",
                            Toast.LENGTH_LONG).show();
                }
            }
            case STORAGE_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                            "External Storage permission required",
                            Toast.LENGTH_LONG).show();
                }
            }
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
            if (retractMenu()) {
                return true;
            }
            if (favInit != heartButBool) {
                saveFavState();
                hymnDisplayIntent.putExtra("isFav", heartButBool);
                hymnDisplayIntent.putExtra("hymnNum", s);
                setResult(1, hymnDisplayIntent);
            }
            favInit = heartButBool;
            if (textSizeChanged) {
                userData.setUserTextSize(String.valueOf(pixelsToSp((stanzas.get(0)).getTextSize())));
            }
            if (isRecording) {
                try {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;

                    QuickToast("Recording saved.");
                    isRecordingSaved = true;
                } catch (Exception e) {
                    QuickToast("The application had difficulty in recording and saving.");
                    isRecordingSaved = false;
                }
            }
            finish();

        }
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            //// TODO: 23.03.17

        }
        return true;
    }

    public String timeStamp() {
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy HH:mm", Locale.US);
        Date resultdate = new Date(yourmilliseconds);
        return sdf.format(resultdate);
    }

    public void prepareRecording(final String key) {
        final ImageView rec, stop;
        final TextView text = (TextView) micRow.getChildAt(4);
        rec = (ImageView) micRow.getChildAt(1);
        stop = (ImageView) micRow.getChildAt(2);

        random = new Random();
        final long[] millis = new long[1];

        final Handler h2 = new Handler();
        final Runnable run = new Runnable() {

            @Override
            public void run() {
                millis[0] = System.currentTimeMillis() - starttime;
                int seconds = (int) (millis[0] / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                text.setText(String.format(Locale.US, "%d:%02d", minutes, seconds));
                h2.postDelayed(this, 500);
            }
        };

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rec.setAlpha(1f);
                if (isRecording) {
                    h2.removeCallbacks(run);
                    try {
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;

                        userData.addRecording(s,timeStamp(),AudioSavePathInDevice,millis[0]);

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
                    flipRecIcon();
                    isRecording = false;
                } else
                    QuickToast("Click record first.");
            }
        });
        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    flipRecIcon();
                    rec.setAlpha(.4f);

                    StorageManager sm = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        StorageVolume volume = sm.getPrimaryStorageVolume();
                        Intent intent = null;
                        intent = volume.createAccessIntent(getRecordingDir().getPath());
                        //startActivityForResult(intent, 1);
                    }

                    AudioSavePathInDevice = getRecordingDir().getPath() + File.separator + CreateRandomAudioFileName(5) + "AudioRecording.3gp";


                    if (checkPermission()) {
                        if (MediaRecorderReady()) {
                            Toast.makeText(HymnDisplay.this, "Recording started",
                                    Toast.LENGTH_LONG).show();
                            isRecording = true;
                            h2.postDelayed(run, 0);
                            starttime = System.currentTimeMillis();
                            //save.pushBack(timeStamp());
                            //save.pushBack("recording");
                            //save.pushBack(AudioSavePathInDevice);
                        } else {
                            Toast.makeText(HymnDisplay.this, "Recording cannot start...",
                                    Toast.LENGTH_LONG).show();
                            isRecording = false;
                        }
                    } else {
                        requestPermission();
                    }
                }

            }
        });
    }

    public File getRecordingDir() {
        File previewDir = new File(Environment.getExternalStorageDirectory(), "Recordings");
        if (!previewDir.exists()) previewDir.mkdir();
        return previewDir;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


    //Android 6+ Recording and playback permissions

    protected void requestMorePermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }

    //Scale Handler
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return scaleGestureDetector.onTouchEvent(ev);
    }

    public class simpleOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float size = (stanzas.get(0)).getTextSize();
            canShowDialog = false;

            Log.d("TextSizeStart", String.valueOf(size));
            float factor = detector.getScaleFactor();
            Log.d("Factor", String.valueOf(factor));
            float product = size * factor;
            Log.d("TextSize", String.valueOf(product));
            changeTextSize(TypedValue.COMPLEX_UNIT_PX, product);

            size = (stanzas.get(0)).getTextSize();
            Log.d("TextSizeEnd", String.valueOf(size));
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            canShowDialog = false;
            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            canShowDialog = true;
            textSize = pixelsToSp((stanzas.get(0)).getTextSize());
            userData.setUserTextSize(String.valueOf(pixelsToSp(textSize)));
            super.onScaleEnd(detector);
        }
    }

    public void checkFavorite() {
        if (userData.isFavorite(s) != heartButBool) {
            flipHeartIcon();
            heartButBool = userData.isFavorite(s);
            favInit = heartButBool;
        }
    }

    public void finalizeFavoriteState() {
        if (favInit != heartButBool) {
            saveFavState();
            hymnDisplayIntent.putExtra("isFav", heartButBool);
            hymnDisplayIntent.putExtra("hymnNum", s);
            setResult(1, hymnDisplayIntent);
        }
    }

    public void checkEnglish() {
        if (isEnglishAvailable != engCheck.isEn(s)) {
            isEnglishAvailable = engCheck.isEn(s);
            flipEnglishIcon();

        }
    }

    public float pixelsToSp(float px) {
        float scaledDensity = getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public void populateHymn(String num, final boolean isInEnglish) {
        stanzas.clear();
        if (!num.equals("")) {
            hymnDispl.removeAllViews();
            hymnName = hymns.getTitle(num, isInEnglish,false);
            title.setText(hymnName);
            populateCaption(hymns.getCaption(num));
            number.setText(num);
            hymnpop.setText(num);

            getStanzaStrings(hymns.getHymn(num, isInEnglish));
            for (TextView hymnStanza : stanzas) hymnDispl.addView(hymnStanza);
            if (chorusShown) {
                chorus.setVisibility(View.INVISIBLE);
                hymnpop.setVisibility(View.INVISIBLE);
                chorusShown = false;
            }
        } else {
            String[] errorMessages = {"There has been a problem", "loading the hymn", "Please pressback"};
            hymnDispl.removeAllViews();
            title.setText("Error");
            populateCaption(errorMessages);
            number.setText(num);
            hymnpop.setText(num);
        }
        s = num;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                totalHeight = myScroll.getChildAt(0).getHeight();
                bound = (totalHeight / stanzas.size());
                String check = stanzas.get(0).getText().toString();
                if (hymns.hasChorus(s,isInEnglish)) {
                    chorus.setText(hymns.getChorus(s,isInEnglish));
                    chorusAvail = true;
                } else {
                    chorus.setText("");
                    chorusAvail = false;
                }

            }
        }, 300);
    }

    public void getStanzaStrings(String[] arr) {
        int size = arr.length;
        for (int i = 0; i < size; i++){
            TextView textView = new TextView(HymnDisplay.this);
            textView.setText(arr[i]);
            stanzas.add(textView);
        }
        formatTextViewsToHymnStyle();
    }

    public void formatTextViewsToHymnStyle() {
        int size = stanzas.size();
        for (int i = 0; i < size; i++) {
            stanzas.get(i).setTextSize(textSize);
            stanzas.get(i).setId(i);
            stanzas.get(i).setGravity(Gravity.CENTER);
            stanzas.get(i).setTextColor(textcolor);
            stanzas.get(i).setOnTouchListener(tapStanza);
            stanzas.get(i).setOnLongClickListener(longTapStanza);
            stanzas.get(i).setClickable(true);
            stanzas.get(i).setBackgroundResource(backgroundResource);
        }
        /*for(UserData.UserFavoriteStanza line:userData.getFavoriteStanza()){
            if(line.getHymnNum().equals(s)&&line.isEnglish()==isInEnglish){
                for(int position:line.getStanza()){
                    SpannableString content = new SpannableString(stanzas.get(position).getTextColor().toString());
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    stanzas.get(position).setText(content);
                }
            }
        }*/
    }

    public void populateCaption(final String[] caps) {
        clength = caps.length;
        showCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCaption.setVisibility(View.INVISIBLE);
                captionShow.setVisibility(View.VISIBLE);
                number.animate().scaleY(.5f);
                number.animate().scaleX(.5f);
                for (int f = 0; f < clength; f++) {
                    Handler but = new Handler();
                    Handler timer = new Handler();
                    final int finalF = f;
                    timer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            captionShow.setText(caps[finalF]);
                        }
                    }, (f * 3000));
                    but.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showCaption.setVisibility(View.VISIBLE);
                            captionShow.setVisibility(View.INVISIBLE);
                            number.animate().scaleY(1f);
                            number.animate().scaleX(1f);
                        }
                    }, (clength * 3000));
                }

            }
        });

        number.animate().scaleY(.5f);
        number.animate().scaleX(.5f);
        for (int f = 0; f < clength; f++) {
            Handler but = new Handler();
            Handler timer = new Handler();
            final int finalF = f;
            timer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    captionShow.setText(caps[finalF]);
                }
            }, (f * 3000));
            but.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showCaption.setVisibility(View.VISIBLE);
                    captionShow.setVisibility(View.INVISIBLE);
                    number.animate().scaleY(1f);
                    number.animate().scaleX(1f);
                }
            }, (clength * 3000));
        }
    }

    public void createCaption() {
        captionShow.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView captionView = new TextView(HymnDisplay.this);
                captionView.setTextColor(BLACK);
                captionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                captionView.setGravity(Gravity.CENTER);
                return captionView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_likes_counter);
        captionShow.setInAnimation(in);
        captionShow.setOutAnimation(out);
    }

    public void openNavigationDrawer() {
        if (canSlideRight) {
            slideRight(micBut, buttonSize, 50, 100);
            slideRight(editBut, buttonSize, 100, 100);
            slideRight(enBut, buttonSize, 150, 100);
            slideRight(heartBut, buttonSize, 200, 100);
            slideRight(fontBut, buttonSize, 250, 100);
            slideRight(colorBut, buttonSize, 300, 100);
            slideRight(shareBut, buttonSize, 350, 100);
            slideRight(prevBut, buttonSize, 400, 100);
            slideRight(nextBut, buttonSize, 450, 100);
            auxBut.setVisibility(View.VISIBLE);
            canSlideLeft = true;
            canSlideRight = false;
            menuOpen = true;
        }
    }

    public void closeNavigationDrawer() {
        if (canSlideLeft) {
            slideLeft(micBut, -buttonSize, 50, 400);
            slideLeft(editBut, -buttonSize, 50, 400);
            slideLeft(enBut, -buttonSize, 50, 400);
            slideLeft(heartBut, -buttonSize, 50, 400);
            slideLeft(fontBut, -buttonSize, 50, 400);
            slideLeft(colorBut, -buttonSize, 50, 400);
            slideLeft(shareBut, -buttonSize, 50, 400);
            slideLeft(prevBut, -buttonSize, 50, 400);
            slideLeft(nextBut, -buttonSize, 50, 400);
            auxBut.setVisibility(View.INVISIBLE);
            canSlideRight = true;
            canSlideLeft = false;
            menuOpen = false;
        }
    }

    public void addBar(int position,String text) {
        if(canShowDialog){
            SelectStanzaDialog selectStanzaDialog = new SelectStanzaDialog(HymnDisplay.this,userData,position,text,hymnName,s,isInEnglish);
            selectStanzaDialog.getWindow().getAttributes().windowAnimations = R.style.TransparentDialogAnimation;
            selectStanzaDialog.show();
        }
    }

}

