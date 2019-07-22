package com.seven.clip.nziyodzemethodist.customViews;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.models.NDMRoundedRelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class StanzaView extends NDMRoundedRelativeLayout {
    private View rootView;
    private TextView verseTextView;
    private TextView numberTextView;


    public StanzaView(Context context) {
        super(context);
        init(context,null);
        initFunctions();
        applyColors();
    }


    public StanzaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
        initFunctions();
        applyColors();
    }

    public StanzaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
        initFunctions();
        applyColors();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StanzaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
        initFunctions();
        applyColors();
    }

    private void applyColors() {
    }

    private void initFunctions() {

    }
    private void init(Context context, AttributeSet attributeSet) {
        rootView = inflate(context, R.layout.view_stanza, this);
    }
    public void setVerse(int verseNumber, String verse){
        numberTextView.setText(String.valueOf(verseNumber));
        verseTextView.setText(verse);
    }
}
