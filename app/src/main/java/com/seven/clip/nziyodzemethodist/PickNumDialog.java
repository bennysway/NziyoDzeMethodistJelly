package com.seven.clip.nziyodzemethodist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.graphics.Color.parseColor;

public class PickNumDialog extends Dialog {

    private EditText numberField;
    private Intent toHymn;
    private Data favList;
    private ImageView makeFavBut;
    RealtimeBlurView blurView;
    private int pressCounter = 0;
    Context context;

    PickNumDialog(@NonNull Context passedContext) {
        super(passedContext);
        context = passedContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.pick_num_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);

        numberField = findViewById(R.id.pickNumSearchBox);
        ImageView openHymn = findViewById(R.id.pickNumButton);
        makeFavBut = findViewById(R.id.pickNumMakeFavButton);
        blurView = findViewById(R.id.pickNumBlur);
        toHymn = new Intent(context, HymnDisplay.class);


        Data recordFlag = new Data(context, "recordflag");
        favList = new Data(context, "favlist");
        Data color = new Data(context, "color");



        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, (int) (height * .3));




        recordFlag.deleteAll();

        numberField.setFilters(new InputFilter[]{ new HymnNumberFilter("1", "321")});
        String tintColor = color.get();
        if(tintColor.contains("#")){
            numberField.getBackground().mutate().setColorFilter(parseColor(tintColor), PorterDuff.Mode.SRC_ATOP);
            openHymn.setColorFilter(parseColor(tintColor));
            makeFavBut.setColorFilter(parseColor(tintColor));
            int bgColor = ColorUtils.setAlphaComponent(parseColor(tintColor), 5);

            if (isColorDark(tintColor))
                bgColor = ColorUtils.blendARGB(bgColor, parseColor("#ffffff"), .1f);
            else
                bgColor = ColorUtils.blendARGB(bgColor, parseColor("#000000"), .1f);
            blurView.setOverlayColor(bgColor);
        }

        openHymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHymn();
            }
        });

        numberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String num = s.toString();
                if(num.length()>0){
                    favAni(num);
                }
            }
        });


        makeFavBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberField.getText().length()>0){
                    if(pressCounter==0){
                        String s = numberField.getText().toString();
                        if(favList.find(s)){
                            QuickToast("Hymn " + s + " is a favourite. Press again to remove.");
                            pressCounter++;
                        }
                        else {
                            QuickToast("Hymn " + s + " is not a favourite. Press again to add.");
                            pressCounter++;
                        }
                    }
                    else if(pressCounter==1){
                        String s = numberField.getText().toString();
                        if(favList.find(s)){
                            favList.delete(s);
                            QuickToast("Hymn " + s + " removed.");
                            pressCounter=0;
                            favAni(s);
                        }
                        else {
                            favList.pushBack(s);
                            QuickToast("Hymn " + s + " added.");
                            pressCounter=0;
                            favAni(s);
                        }
                    }
                }
            }
        });

        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        numberField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    gotoHymn();
                    hideSoftKeyboard();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void dismiss() {
        hideSoftKeyboard();
        super.dismiss();

    }

    public void QuickToast(String s) {
        Toast.makeText(context, s,
                Toast.LENGTH_SHORT).show();
    }
    public void gotoHymn(){
        String aS = numberField.getText().toString();
        int aI=0;
        if(aS.length()>0)
            aI = Integer.valueOf(aS);
        if(aS.equals("")){
            QuickToast("Please put hymn number 1-321...");
        }
        else if(aI<1||aI>321)
            QuickToast("Please don't try to be smart. :) enter a number out of the range 1 to 321");
        else {
            toHymn.putExtra("hymnNum",numberField.getText().toString());
            context.startActivity(toHymn);
        }

    }

    private void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void favAni(String num) {
        if(favList.find(num)){
            makeFavBut.animate().scaleX(1.5f).scaleY(1.5f).setDuration(100).withEndAction(new Runnable() {
                @Override
                public void run() {
                    makeFavBut.animate().scaleY(1f).scaleX(1f).alpha(1f);
                }
            });
        }
        else {
            makeFavBut.setAlpha(.5f);
        }
    }

    private boolean isColorDark(String test) {
        int color = parseColor(test);
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return darkness >= 0.333;
    }

}
