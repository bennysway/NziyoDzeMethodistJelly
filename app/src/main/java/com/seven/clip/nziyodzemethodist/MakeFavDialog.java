package com.seven.clip.nziyodzemethodist;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mmin18.widget.RealtimeBlurView;

import static android.graphics.Color.parseColor;

public class MakeFavDialog extends Dialog {

    private Data favList;
    RealtimeBlurView blurView;
    String s;
    Context context;

    public MakeFavDialog(@NonNull Context passedContext,String hymnNum) {
        super(passedContext);
        s = hymnNum;
        context = passedContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.simple_dialog_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);

        blurView = findViewById(R.id.simpleBlur);


        TextView prompt = findViewById(R.id.simpleDialogTextView);
        Button yes = findViewById(R.id.simpleDialogAccept);
        Button no = findViewById(R.id.simpleDialogDeny);
        final Data favList = new Data(getContext(),"favlist");
        Data color = new Data(context, "color");

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/bh.ttf");
        prompt.setTypeface(custom_font);


        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.4));

        if(favList.find(s)){
            prompt.setText("Remove hymn " + s + " from favourites?" );
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favList.delete(s);
                    QuickToast("Removed hymn " + s);
                    cancel();
                }
            });
        }
        else {
            prompt.setText("Add hymn " + s + " to favourites?" );
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favList.pushBack(s);
                    QuickToast("Added hymn " + s);
                    cancel();
                }
            });
        }
        String tintColor = color.get();
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
