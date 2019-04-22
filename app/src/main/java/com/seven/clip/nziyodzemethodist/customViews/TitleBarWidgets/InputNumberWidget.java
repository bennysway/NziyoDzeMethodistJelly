package com.seven.clip.nziyodzemethodist.customViews.TitleBarWidgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.seven.clip.nziyodzemethodist.HymnNumberFilter;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.util.Util;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class InputNumberWidget extends RelativeLayout {
    View rootView;
    EditText editText;
    String suffix;
    Intent intent;
    InputMethodManager imm;


    public InputNumberWidget(Context context) {
        super(context);
        init(context);
        initFunctions();
    }

    private void initFunctions() {
        imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Util.quickToast(getContext(),suffix + editText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        editText.requestFocus();
        initFunctions();
    }

    @Override
    protected void onDetachedFromWindow() {
        imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getRootView().getWindowToken(), 0);
        super.onDetachedFromWindow();
    }

    private void init(Context context){
        rootView = inflate(context,R.layout.view_input_number_widget,this);
        editText = rootView.findViewById(R.id.editText);

    }

    public void setHint(String hint){
        editText.setHint(hint);
    }

    public void setLimit(int min, int max){
        editText.setFilters(new InputFilter[]{ new HymnNumberFilter("" + min, "" + max)});
    }
    public void setIntentSuffix(String suffix){
        this.suffix = suffix;
    }

    public void setIntent(Intent intent){
        this.intent = intent;
    }

}
