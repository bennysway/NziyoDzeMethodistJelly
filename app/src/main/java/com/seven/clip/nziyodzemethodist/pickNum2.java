package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class pickNum2 extends AppCompatActivity {

    EditText numberField;
    Intent toHymn;
    InputMethodManager imm;
    Data favList;
    ImageView makeFavBut;
    int pressCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_num2);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.3));

        toHymn = new Intent(this,hymnDisplay.class);

        numberField = (EditText) findViewById(R.id.pickNumSearchBox);
        ImageView openHymn = (ImageView) findViewById(R.id.pickNumButton);
        makeFavBut = (ImageView) findViewById(R.id.pickNumMakeFavButton);

        Data recordFlag = new Data(this,"recordflag");
        favList = new Data(this,"favlist");
        recordFlag.deleteAll();

        numberField.setFilters(new InputFilter[]{ new HymnNumberFilter("1", "317")});

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

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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
    public Context getActivity() {
        return this;
    }
    public void QuickToast(String s) {
        Toast.makeText(getActivity(), s,
                Toast.LENGTH_SHORT).show();
    }
    public void gotoHymn(){
        if(numberField.getText().toString().equals("")){
            QuickToast("Please put hymn number 1-317...");
        }
        else {
            toHymn.putExtra("hymnNum",numberField.getText().toString());
            startActivity(toHymn);
        }

    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void favAni(String num){
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

}
