package com.seven.clip.nziyodzemethodist;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.graphics.Color.parseColor;

public class SelectStanzaDialog extends Dialog {

    String text,title,number;
    boolean isEnglish;
    int position;
    TextSwitcher captionShow;
    Context context;
    View cardBut,textBut,bookmarkBut,underlineBut;
    TextView cardText,firstOpenText,underlineText,hymnNumberText;
    TextView textView;
    TableLayout tableLayout;
    UserDataIO userData;

    SelectStanzaDialog(@NonNull Context passedContext,UserDataIO _userdata,int _position,String _text,String _title, String _number,boolean _isEnglish) {
        super(passedContext);
        position = _position;
        isEnglish = _isEnglish;
        text = _text;
        title = _title;
        number = _number;
        context = passedContext;
        userData = _userdata;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select_stanza_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);

        captionShow = findViewById(R.id.stanzaShow);
        createCaption();


        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, (int) (height * .8));
        getWindow().setDimAmount(.2f);

        cardBut = findViewById(R.id.selectStanzaCardImageView);
        textBut = findViewById(R.id.selectStanzaTextImageView);
        bookmarkBut = findViewById(R.id.selectStanzaBookmarkImageView);
        underlineBut = findViewById(R.id.selectStanzaUnderlineImageView);
        firstOpenText = findViewById(R.id.selectStanzaFirstOpenTextView);
        underlineText = findViewById(R.id.selectStanzaUnderlineTextView);
        hymnNumberText = findViewById(R.id.selectStanzaHymnNumberView);
        textView = findViewById(R.id.stanzaEditText);
        tableLayout = findViewById(R.id.stanzaTableOptions);

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/bh.ttf");
        hymnNumberText.setTypeface(custom_font);

        textView.setText(text);

        text +=" \n";
        final String[] wordsArray = text.split("\n");
        for (int f = 0; f < wordsArray.length; f++) {
            Handler timer = new Handler();
            final int finalF = f;
            timer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    captionShow.setText(wordsArray[finalF%wordsArray.length]);
                }
            }, (f * 3500));
        }

        String _number_ = " " + number + " ";
        hymnNumberText.setText(_number_);

        View.OnClickListener cardClickListener,textClickListener,bookmarkClickListener,underlineClickListener,numberClickListener;
        cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MakeCard.class);
                intent.putExtra("text",text);
                intent.putExtra("title",title);
                intent.putExtra("number",number);
                getContext().startActivity(intent);
                cancel();
            }
        };
        textClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy(text,number);
                cancel();
            }
        };
        bookmarkClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userData.getBookmark().isEmpty()){
                    userData.setBookmark(number);
                    QuickToast("Added hymn " + number + " bookmark");
                } else if(userData.getBookmark().equals(number)){
                    userData.setBookmark("");
                    QuickToast("Removed hymn " + number + " as bookmark");
                } else {
                    QuickToast("Replaced hymn " + userData.getBookmark() + " with " + number + " as bookmark");
                    userData.setBookmark(number);
                }
            }
        };
        underlineClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tableHeight = tableLayout.getHeight(),
                        buttonHeight = underlineBut.getHeight()+underlineText.getHeight();
                int traveldist = tableHeight - buttonHeight;
                cardBut.animate().alpha(0f);
                textBut.animate().alpha(0f);
                bookmarkBut.animate().alpha(0f);
                firstOpenText.animate().alpha(0f);
                hymnNumberText.animate().alpha(0f);
                textView.setVisibility(View.VISIBLE);
                textView.animate().alpha(1f);
                tableLayout.animate().yBy(-traveldist);
                textView.animate().yBy(-traveldist);
                underlineText.setText("Clear UnderLine");
                underlineBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QuickToast(textView.getSelectionStart() + " to " + textView.getSelectionEnd());
                    }
                });




                boolean found=false;
//                for(UserData.UserFavoriteStanza line:userData.getFavoriteStanza()){
//                    if(line.getHymnNum().equals(number)&&line.isEnglish()==isEnglish){
//                        if(!line.getStanza().contains(position)){
//                            line.getStanza().add(position);
//                            quickToast("Underlined stanza " + position);
//                        } else {
//                            line.getStanza().remove(position);
//                            quickToast("Removed underline");
//                        }
//                        found = true;
//                    }
//                }
//                if(!found){
//                    ArrayList<Integer> arrayList = new ArrayList<>();
//                    arrayList.add(position);
//                    userData.getFavoriteStanza().add(new UserData.UserFavoriteStanza(number,isEnglish,arrayList));
//                    quickToast("Underlined stanza " + position);
//
//                }

            }
        };

        cardBut.setOnClickListener(cardClickListener);
        textBut.setOnClickListener(textClickListener);
        bookmarkBut.setOnClickListener(bookmarkClickListener);
        underlineBut.setOnClickListener(underlineClickListener);
        underlineText.setOnClickListener(underlineClickListener);


    }

    public void createCaption() {
        captionShow.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView captionView = new TextView(getContext());
                captionView.setTextColor(parseColor("#ff000000"));
                captionView.setShadowLayer(10,0,0,Color.BLACK);
                captionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                captionView.setGravity(Gravity.CENTER);
                return captionView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        captionShow.setInAnimation(in);
        captionShow.setOutAnimation(out);
    }

    public void copy(String s, String num) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
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
            getContext().startActivity(Intent.createChooser(i, "Share Hymn via:"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void QuickToast(String s) {
        Toast.makeText(getContext(), s,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancel() {
        userData.save();
        super.cancel();
    }
}
