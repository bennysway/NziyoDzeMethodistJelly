package com.seven.clip.nziyodzemethodist;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class CaptionList extends AppCompatActivity {
    ListView ls;
    MyCaptionListAdapter adapter;
    String[] names;
    int counter = 0;
    TextView noCapsText;

    UserDataIO userData;
    xHymns hymns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caption_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        noCapsText = findViewById(R.id.noCaptionsText);
        TextView capTitle = findViewById(R.id.captionsTitle);
        ls = findViewById(R.id.captionsListView);
        View back = findViewById(R.id.captionsBackButton);
        userData = new UserDataIO(this);
        hymns = new xHymns(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        capTitle.setTypeface(custom_font);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(userData.getCaptionList().size()<1){
            noCapsText.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        }
        else {
            String []names = new String[userData.getCaptionList().size()];
            for(int i=0;i<userData.getCaptionList().size();i++){
                names[i] = hymns.getTitle(userData.getCaptionList().get(i),false, true);
            }
            adapter =
                    new MyCaptionListAdapter(
                            this,
                            names
                    );
            ls.setAdapter(adapter);
            ls.setVisibility(View.VISIBLE);
            noCapsText.setVisibility(View.INVISIBLE);
        }



    }
    @Override
    public void onResume(){
        super.onResume();
        if(ls.getAdapter()!=null){
            ls.invalidateViews();
        }
    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
}
