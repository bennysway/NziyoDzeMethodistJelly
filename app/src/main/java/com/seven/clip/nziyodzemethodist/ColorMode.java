package com.seven.clip.nziyodzemethodist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import static android.graphics.Color.parseColor;

public class ColorMode extends AppCompatActivity {

    RelativeLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mode);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bg = (RelativeLayout) findViewById(R.id.activity_color_mode);
        final RadioGroup colorList = (RadioGroup) findViewById(R.id.colorModeRadioGroup);
        Button yes = (Button) findViewById(R.id.colorApply);
        Button no = (Button) findViewById(R.id.colorCancel);
        final Data color = new Data(this,"color");
        final Data theme = new Data(this,"themecolor");
        final Intent intent = new Intent();

        RadioButton customColorRadioBut = (RadioButton) findViewById(R.id.customColorCheck);
        Button setCustomColorBut = (Button) findViewById(R.id.pickColorBut);

        if(!theme.get().equals("")){
            customColorRadioBut.setEnabled(true);
        }

        setCustomColorBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int choosen = colorList.getCheckedRadioButtonId();
                RadioButton s = (RadioButton) findViewById(choosen);
                String t = s.getText().toString();
                switch (t){
                    case "Day":
                        color.update("day");
                        intent.putExtra("mode","day");
                        break;
                    case "Night":
                        color.update("night");
                        intent.putExtra("mode","night");
                        break;
                    default:
                        color.deleteAll();
                        intent.putExtra("mode","");
                }
                setResult(3,intent);
                finish();
            }
        });
    }
}
