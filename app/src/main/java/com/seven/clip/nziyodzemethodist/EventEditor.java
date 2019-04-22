package com.seven.clip.nziyodzemethodist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.seven.clip.nziyodzemethodist.fragments.ReadingEditorFragment;
import com.seven.clip.nziyodzemethodist.interfaces.BundleListener;
import com.seven.clip.nziyodzemethodist.interfaces.DialogListener;
import com.seven.clip.nziyodzemethodist.util.Util;

public class EventEditor extends AppCompatActivity implements DialogListener {

    private DialogListener dialogListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_editor);
        dialogListener = (ReadingEditorFragment) getSupportFragmentManager().findFragmentById(R.id.readingsEditorFragment);
        BundleListener bundleListener = (ReadingEditorFragment) getSupportFragmentManager().findFragmentById(R.id.readingsEditorFragment);
        assert bundleListener != null;
        String date = getIntent().getStringExtra("date");
        bundleListener.sendLiveObject("monthId",getIntent().getStringExtra("monthId"));
        bundleListener.sendLiveObject("date",date);
        bundleListener.sendLiveObject("time",getIntent().getLongExtra("time",Util.getDate(date).getTime()));
    }

    @Override
    public void onPositiveResult(String key, Object object) {
        dialogListener.onPositiveResult(key, object);
    }


    @Override
    public void onNegativeResult() {
        dialogListener.onNegativeResult();
    }
}
