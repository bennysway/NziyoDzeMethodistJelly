package com.seven.clip.nziyodzemethodist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
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
        TextView prompt = (TextView) findViewById(R.id.DeleteCaptionPrompt);
        Button yes = (Button) findViewById(R.id.DeleteCaptionYesBut);
        Button no = (Button) findViewById(R.id.DeleteCaptionNoBut);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.2));


        prompt.setText("Delete this caption?" );


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
                finish();
            }
        });



    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

}
