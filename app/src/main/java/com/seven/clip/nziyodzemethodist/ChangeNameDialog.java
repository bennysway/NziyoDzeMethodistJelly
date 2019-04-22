package com.seven.clip.nziyodzemethodist;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangeNameDialog extends Dialog {

    EditText textField;
    InputMethodManager imm;
    Context context;
    UserDataIO userData;

    public ChangeNameDialog(@NonNull Context passedContext) {
        super(passedContext);
        context = passedContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name_dialog);

        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, (int) (height * .3));

        textField = findViewById(R.id.usernameEditText);
        ImageView accept = findViewById(R.id.acceptName);
        ImageView deny = findViewById(R.id.cancelName);

        userData = new UserDataIO(getContext());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
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
                cancel();
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickToast("No changes made");
                cancel();
            }
        });

    }

    public void QuickToast(String s){
        Toast.makeText(context, s,
                Toast.LENGTH_SHORT).show();
    }
}
