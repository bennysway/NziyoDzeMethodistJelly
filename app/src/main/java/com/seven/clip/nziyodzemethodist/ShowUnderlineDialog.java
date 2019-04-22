package com.seven.clip.nziyodzemethodist;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;

import static android.graphics.Color.parseColor;

public class ShowUnderlineDialog extends Dialog {

    RealtimeBlurView blurView;
    UserData.UserFavoriteStanza underLine;
    Context context;

    ShowUnderlineDialog(@NonNull Context passedContext, UserData.UserFavoriteStanza underline) {
        super(passedContext);
        this.underLine = underline;
        context = passedContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.underline_dialog);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);

        blurView = findViewById(R.id.underlineSimpleBlur);
        TextView number = findViewById(R.id.underlineHymnNum);
        LinearLayout layout = findViewById(R.id.underlineLinearHolder);
        xHymns hymn = new xHymns(context);
        String [] stanzas = hymn.getHymn(underLine.getHymnNum(),underLine.isEnglish());

        for(int i=0;i<underLine.getStanza().size();i++){
            TextView textView = new TextView(context);
            //todo
            //textView.setText(stanzas[underLine.getStanza().get(i)]);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            textView.setPadding(10,10,10,10);
            textView.setGravity(Gravity.CENTER);
            layout.addView(textView);
        }

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/bh.ttf");
        number.setTypeface(custom_font);

        String tintColor = new UserDataIO(context).getUserColor();
        if(tintColor.contains("#")){
            int bgColor = ColorUtils.setAlphaComponent(parseColor(tintColor), 5);

            if (isColorDark(tintColor))
                bgColor = ColorUtils.blendARGB(bgColor, parseColor("#ffffff"), .1f);
            else
                bgColor = ColorUtils.blendARGB(bgColor, parseColor("#000000"), .1f);
            blurView.setOverlayColor(bgColor);
        }
    }

    public void QuickToast(String s) {
        Toast.makeText(getContext(), s,
                Toast.LENGTH_SHORT).show();
    }

    private boolean isColorDark(String test) {
        int color = parseColor(test);
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return darkness >= 0.333;
    }
}
