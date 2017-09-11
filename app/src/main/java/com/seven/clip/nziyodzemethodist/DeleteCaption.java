package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class DeleteCaption extends AppCompatActivity {

    String key,path,record,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_caption);

        path = getIntent().getStringExtra("path");
        key = getIntent().getStringExtra("key");
        record = getIntent().getStringExtra("record");
        type = getIntent().getStringExtra("type");
        TextView prompt = findViewById(R.id.DeleteCaptionPrompt);
        Button yes = findViewById(R.id.DeleteCaptionYesBut);
        Button no = findViewById(R.id.DeleteCaptionNoBut);
        final Intent intent = new Intent();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.2));


        prompt.setText("Delete this caption?" );
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        prompt.setTypeface(custom_font);


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("note")){
                    MainActivity.userData(DeleteCaption.this,key,"deleteRecord",record);
                    MainActivity.userData(DeleteCaption.this,path,"deleteAll","");
                }
                else if(type.equals("recording")){
                    MainActivity.userData(DeleteCaption.this,key,"deleteRecord",record);
                    File file = new File(path);
                    boolean deleted = file.delete();
                    if(deleted)
                        QuickToast("File deleted");
                }
                intent.putExtra("data","true");
                setResult(1,intent);
                finish();
            }
        });



    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

}
