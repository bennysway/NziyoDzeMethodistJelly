package com.seven.clip.nziyodzemethodist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.HymnsDB;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;
import com.seven.clip.nziyodzemethodist.models.NDMConstants;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;

public class Splash extends NDMActivity {
    ProgressBar progressBar;
    TextView databaseLanguageTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.splashProgressBar);
        databaseLanguageTextView = findViewById(R.id.databaseLanguageTextView);
        (new HymnsDB()).execute();
    }

    @Override
    public void broadcast(Bundle bundle) {
        if(bundle.get("bundleType") == NDMConstants.NDMBundleType.PROGRESS ){
            int percentage = bundle.getInt("percentage",0);
            if(percentage>100){
                String databaseName = bundle.getString("databaseLanguageTextView");
                databaseLanguageTextView.setText(databaseName);
                startActivity(new Intent(Splash.this,MainDrawer.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            } else {
                databaseLanguageTextView.setAlpha((float) percentage / 100f);
                databaseLanguageTextView.setText(String.valueOf(percentage));
                progressBar.setProgress(percentage);
            }
        }
    }

    @Override
    public NDMFragment getFragment() {
        return null;
    }

    @Override
    public Runnable onLeftIconClick() {
        return null;
    }

    @Override
    public void colapseTitleBar() {

    }

    @Override
    public void setRightIcon(ImageView icon) {

    }

    @Override
    public void setLeftClickMode(LeftClickMode mode) {

    }

    @Override
    public FabPackage getCircleMenu() {
        return null;
    }
}
