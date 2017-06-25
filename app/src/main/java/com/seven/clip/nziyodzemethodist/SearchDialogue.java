package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SearchDialogue extends AppCompatActivity {
    EditText input;
    ImageView startSearch;
    Intent toSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dialogue);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //getWindow().setBackgroundDrawable(new ColorDrawable(0));
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.3));

        input = (EditText) findViewById(R.id.searchBox);
        startSearch = (ImageView) findViewById(R.id.searchDialogueButton);
        toSearch = new Intent(this,Search.class);

        startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearching();
            }
        });
        input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    startSearching();
                    return true;
                }
                return false;
            }
        });



    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    public void startSearching(){
        if(input.getText().toString().equals("")){
            QuickToast("Nothing to search...");
        }
        else {
            toSearch.putExtra("search",input.getText().toString());
            startActivity(toSearch);
        }

    }
}
