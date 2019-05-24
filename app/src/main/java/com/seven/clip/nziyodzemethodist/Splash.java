package com.seven.clip.nziyodzemethodist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    ProgressBar progressBar;
    TextView databaseLanguageTextView;
    BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int percentage = intent.getIntExtra("percentage",0);
            if(percentage>100){
                String databaseName = intent.getStringExtra("databaseLanguageTextView");
                databaseLanguageTextView.setText(databaseName);
                startActivity(new Intent(Splash.this,MainDrawer.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            } else {
                databaseLanguageTextView.setAlpha((float) percentage / 100f);
                databaseLanguageTextView.setText(String.valueOf(percentage));
                progressBar.setProgress(percentage);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.splashProgressBar);
        databaseLanguageTextView = findViewById(R.id.databaseLanguageTextView);
        (new HymnsDB()).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(progressReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(progressReceiver,new IntentFilter("HymnDatabaseFile"));
    }




}
