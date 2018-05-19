package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.slider.LightnessSlider;

import static android.graphics.Color.parseColor;

public class ThemeChooser extends AppCompatActivity {

    //Data color,theme,themeName;
    GradientDrawable gd;
    int lastSave;
    UserDataIO userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_chooser);

        RelativeLayout themeChooserLayout = findViewById(R.id.themeChooserLayout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (1 * scale + 0.5f);

        userData = new UserDataIO(this);

        final Intent intent = new Intent();

        final ColorPickerView colorPickerView = new ColorPickerView(this);
        LightnessSlider lightnessSlider = new LightnessSlider(this);
        RelativeLayout.LayoutParams colorPickParams= new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        colorPickParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        RelativeLayout.LayoutParams lightnessSliderParams= new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 48 * pixels);
        lightnessSliderParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        lightnessSliderParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lightnessSliderParams.setMargins(0, 0, 0, 60 * pixels);
        lightnessSlider.setLayoutParams(lightnessSliderParams);

        colorPickerView.setLayoutParams(colorPickParams);
        colorPickerView.setLightnessSlider(lightnessSlider);

        themeChooserLayout.addView(colorPickerView);
        themeChooserLayout.addView(lightnessSlider);

        Button accept = findViewById(R.id.themeAcceptBut);
        Button deny = findViewById(R.id.themeRejectBut);
        final TextView themeTitle = findViewById(R.id.themeName);
        final ColorToName colorToName = new ColorToName();
        gd = new GradientDrawable();
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setCornerRadius(100f);

        if(userData.getTheme().equals("")){
            colorPickerView.setInitialColor(getResources().getColor(R.color.burn),false);
            themeTitle.setText("DefaultHymnTheme");
        } else {
            colorPickerView.setInitialColor(parseColor(userData.getTheme()),false);
            themeTitle.setText(colorToName.getColorNameFromHex(userData.getTheme()));
            gd.setColors(new int[] {getLighterColor(userData.getTheme()),parseColor(userData.getTheme())});
            themeTitle.setBackground(gd);
            if(isColorDark(userData.getTheme()))
                themeTitle.setTextColor(parseColor("#ffffff"));
            else
                themeTitle.setTextColor(parseColor("#000000"));

        }
        try{
            colorPickerView.addOnColorChangedListener(new OnColorChangedListener() {
                @Override
                public void onColorChanged(int i) {
                    String text = "Theme : " + colorToName.getColorNameFromHex(intToColor(i));
                    themeTitle.setText(text);
                    gd.setColors(new int[] {getLighterColor(intToColor(i)),i});
                    themeTitle.setBackground(gd);
                    lastSave = i;
                    if(isColorDark(intToColor(i)))
                        themeTitle.setTextColor(parseColor("#ffffff"));
                    else
                        themeTitle.setTextColor(parseColor("#000000"));

                }
            });

        } catch (Exception e){
            QuickToast("Theme picker is not yet stable, but the Theme : " + themeTitle.getText().toString() + " was set");
            userData.setTheme(intToColor(lastSave));
            userData.setUserColor(intToColor(lastSave));
            userData.setThemeName(themeTitle.getText().toString());
            intent.putExtra("mode",userData.getTheme());
            setResult(3,intent);
            finish();
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userData.setTheme(intToColor(colorPickerView.getSelectedColor()));
                userData.setUserColor(intToColor(colorPickerView.getSelectedColor()));
                userData.setThemeName(themeTitle.getText().toString());
                intent.putExtra("mode",userData.getTheme());
                setResult(3,intent);
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

    @Override
    protected void onPause() {
        super.onPause();
        userData.save();
    }

    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    public int getLighterColor(String test){
        float[] hsv = new float[3];
        int color = parseColor(test);
        Color.colorToHSV(color, hsv);
        hsv[0] = 1.0f - 0.8f * (1.0f - hsv[0]); // value component
        color = Color.HSVToColor(hsv);
        return color;
    }
    public boolean isColorDark(String test){
        int color = parseColor(test);
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return darkness >= 0.333;
    }

    public String intToColor(int a){
        return String.format("#%06X", (0xFFFFFF & a));
    }

}
