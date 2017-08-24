package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangeNameDialog extends AppCompatActivity {

    EditText textField;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name_dialog);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.3));

        textField = (EditText) findViewById(R.id.usernameEditText);
        ImageView accept = (ImageView) findViewById(R.id.acceptName);
        ImageView deny = (ImageView) findViewById(R.id.cancelName);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String name = preferences.getString("example_text","");
        final SharedPreferences.Editor editor = preferences.edit();

        textField.setText(name);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changes = textField.getText().toString();
                editor.putString("example_text",changes);
                editor.apply();
                editor.commit();
                if(changes.equals(""))
                    QuickToast("Name cleared");
                else if(changes.equals(name))
                    QuickToast("No changes made");
                else
                    QuickToast("Name changed");
                finish();
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickToast("No changes made");
                finish();
            }
        });

    }

    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
}
