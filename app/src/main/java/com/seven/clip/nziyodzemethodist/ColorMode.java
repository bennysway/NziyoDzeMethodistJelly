package com.seven.clip.nziyodzemethodist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ColorMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mode);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final RadioGroup colorList = (RadioGroup) findViewById(R.id.colorModeRadioGroup);
        Button yes = (Button) findViewById(R.id.colorApply);
        Button no = (Button) findViewById(R.id.colorCancel);
        final Data color = new Data(this,"color");

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
                        break;
                    case "Night":
                        color.update("night");
                        break;
                    default:
                        color.deleteAll();
                }
                finish();
            }
        });
    }
}
