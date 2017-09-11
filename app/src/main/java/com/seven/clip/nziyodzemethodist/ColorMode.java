package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


public class ColorMode extends AppCompatActivity {

    RelativeLayout bg;
    Data color,theme,themeName;
    RadioButton customColorRadioBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mode);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bg = findViewById(R.id.activity_color_mode);
        final RadioGroup colorList = findViewById(R.id.colorModeRadioGroup);
        Button yes = findViewById(R.id.colorApply);
        Button no = findViewById(R.id.colorCancel);
        color = new Data(this,"color");
        theme = new Data(this,"themecolor");
        themeName = new Data(this,"themename");
        final Intent intent = new Intent();

        customColorRadioBut = findViewById(R.id.customColorCheck);
        Button setCustomColorBut = findViewById(R.id.pickColorBut);

        if(!theme.get().equals("")){
            customColorRadioBut.setEnabled(true);
            customColorRadioBut.setText(themeName.get());
        }

        setCustomColorBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toThemer = new Intent(ColorMode.this,ThemeChooser.class);
                startActivity(toThemer);
            }
        });

        String selector = color.get();
        switch (selector){
            case "":
                colorList.check(colorList.getChildAt(0).getId());
                break;
            case "day":
                colorList.check(colorList.getChildAt(1).getId());
                break;
            case "night":
                colorList.check(colorList.getChildAt(2).getId());
                break;
            default:
                colorList.check(colorList.getChildAt(3).getId());

        }



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
                RadioButton s = findViewById(choosen);
                String t = s.getText().toString();
                switch (t){
                    case "Default Hymnbook Theme":
                        color.deleteAll();
                        intent.putExtra("mode","");
                        break;
                    case "Day":
                        color.update("day");
                        intent.putExtra("mode","day");
                        break;
                    case "Night":
                        color.update("night");
                        intent.putExtra("mode","night");
                        break;
                    default:
                        if(!t.isEmpty()){
                            color.update(theme.get());
                            intent.putExtra("mode",theme.get());
                        }

                }
                setResult(3,intent);
                finish();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!theme.get().equals("")){
            customColorRadioBut.setEnabled(true);
            customColorRadioBut.setText(themeName.get());
        }
    }
}
