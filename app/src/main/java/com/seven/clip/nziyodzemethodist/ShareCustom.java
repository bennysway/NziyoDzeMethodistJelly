package com.seven.clip.nziyodzemethodist;

import android.content.ClipData;
import android.content.ClipboardManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShareCustom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_custom);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width),(int)(height*.8));


        final String s = getIntent().getStringExtra("text");
        final TextView text = (TextView) findViewById(R.id.pasteBin);
        final Button copybut = (Button) findViewById(R.id.copyBut);
        text.setText(s);
        text.setAlpha(0f);
        text.animate().alpha(1f);


        copybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringYouExtracted =text.getText().toString();
                int startIndex = text.getSelectionStart();
                int endIndex = text.getSelectionEnd();
                stringYouExtracted = stringYouExtracted.substring(startIndex,endIndex);
                copy(stringYouExtracted);
                finish();
            }
        });

    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    public void copy(String s){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Hymn", s);
        clipboard.setPrimaryClip(clip);
        QuickToast("Text copied to clipboard");
    }
}
