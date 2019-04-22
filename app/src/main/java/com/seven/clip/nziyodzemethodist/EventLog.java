package com.seven.clip.nziyodzemethodist;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class EventLog extends AppCompatActivity {

    private static final String EVENT_LOGS = "event_logs";
    TextView events;

    private FirebaseRemoteConfig remoteConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_log);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();

        remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.setConfigSettings(remoteConfigSettings);
        remoteConfig.setDefaults(R.xml.remote_config_defaults);
        events = findViewById(R.id.logs);

        Runnable get = new Runnable() {
            @Override
            public void run() {
                long cacheExpiration = 3600;
                if (remoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
                    cacheExpiration = 0;
                }

                remoteConfig.fetch(cacheExpiration)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Once the config is successfully fetched it must be activated before newly fetched
                                    // values are returned.
                                    remoteConfig.activateFetched();
                                }
                                String text = remoteConfig.getString(EVENT_LOGS);
                                events.setText(text);
                            }
                        });
            }
        };

        get.run();
    }
}