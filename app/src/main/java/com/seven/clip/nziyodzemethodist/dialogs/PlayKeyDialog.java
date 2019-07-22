package com.seven.clip.nziyodzemethodist.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.NziyoDzeMethodist;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;
import com.seven.clip.nziyodzemethodist.models.SnackPackage;

import org.billthefarmer.mididriver.MidiDriver;

import java.util.LinkedList;
import java.util.Queue;

import androidx.annotation.NonNull;

import static android.content.Context.AUDIO_SERVICE;

public class PlayKeyDialog extends Dialog implements View.OnTouchListener,MidiDriver.OnMidiStartListener {
    private String key;
    private MidiDriver midiDriver;
    private byte[] event;
    private byte keyByte;
    private Context context;

    public PlayKeyDialog(@NonNull Context context,String key) {
        super(context);
        this.context = context;
        this.key = key;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setContentView(R.layout.alert_dialog_layout);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/bh.ttf");
        TextView prompt = findViewById(R.id.simpleDialogTextView);
        Button yes = findViewById(R.id.simpleDialogAccept);


        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.4));
        prompt.setTypeface(custom_font);



        yes.setText("Play Note");
        prompt.setText(key);
        makeKeyByte();
        yes.setOnTouchListener(this);
        midiDriver = new MidiDriver();
        midiDriver.setOnMidiStartListener(this);
    }

    private void makeKeyByte() {
        String checkKey = key.replace("Doh is ","");
        checkKey = checkKey.toLowerCase();
        switch (checkKey){
            case "a":
                keyByte = (byte) 0x39;
                break;
            case "a flat":
                keyByte = (byte) 0x38;
                break;
            case "b":
                keyByte = (byte) 0x3B;
                break;
            case "b flat":
                keyByte = (byte) 0x3A;
                break;
            case "c":
                keyByte = (byte) 0x3C;
                break;
            case "d":
                keyByte = (byte) 0x3E;
                break;
            case "d flat":
                keyByte = (byte) 0x3D;
                break;
            case "e":
                keyByte = (byte) 0x40;
                break;
            case "e flat":
                keyByte = (byte) 0x3F;
                break;
            case "f":
                keyByte = (byte) 0x41;
                break;
            case "g":
                keyByte = (byte) 0x43;
                break;
            case "g flat":
                keyByte = (byte) 0x42;
                break;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        midiDriver.stop();
        Log.d(this.getClass().getName(), "Stopped Midi");
    }

    @Override
    protected void onStart() {
        super.onStart();
        midiDriver.start();
        int[] config = midiDriver.config();
        Log.d(this.getClass().getName(), "maxVoices: " + config[0]);
        Log.d(this.getClass().getName(), "numChannels: " + config[1]);
        Log.d(this.getClass().getName(), "sampleRate: " + config[2]);
        Log.d(this.getClass().getName(), "mixBufferSize: " + config[3]);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Log.d(this.getClass().getName(), "Motion event: " + event);

        if (view.getId() == R.id.simpleDialogAccept) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d(this.getClass().getName(), "MotionEvent.ACTION_DOWN");
                playNote();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Log.d(this.getClass().getName(), "MotionEvent.ACTION_UP");
                stopNote();
            }
        }

        return false;
    }

    private void setStrings(){
        event = new byte[2];
        event[0] = (byte) (0xC0);  // 0x90 = note On, 0x00 = channel 1
        event[1] = (byte) 0x30;
        midiDriver.write(event);

    }

    private void playNote() {

        final AudioManager am = (AudioManager) NziyoDzeMethodist.getAppContext().getSystemService(AUDIO_SERVICE);
        final int volume_level= am.getStreamVolume(AudioManager.STREAM_MUSIC);
        if(volume_level==0){
            Queue<SnackPackage> queue = new LinkedList<>();
            Runnable volumeRunnable = new Runnable() {
                @Override
                public void run() {
                    am.setStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            4,
                            0);
                    show();

                }
            };
            queue.offer(new SnackPackage("Volume is too low"));
            queue.offer(new SnackPackage("Increase volume now?", "Yes",volumeRunnable));
            ((NDMActivity)context).titleBarView.showSnack(queue);
            dismiss();
            return;
        }
        // Construct a note ON message for the middle C at maximum velocity on channel 1:
        event = new byte[3];
        event[0] = (byte) (0x90);  // 0x90 = note On, 0x00 = channel 1
        event[1] = keyByte;
        event[2] = (byte) 0x7F;  // 0x7F = the maximum velocity (127)
        midiDriver.write(event);

    }

    private void stopNote() {

        // Construct a note OFF message for the middle C at minimum velocity on channel 1:
        event = new byte[3];
        event[0] = (byte) (0x80);  // 0x80 = note Off, 0x00 = channel 1
        event[1] = keyByte;
        event[2] = (byte) 0x00;  // 0x00 = the minimum velocity (0)
        midiDriver.write(event);

    }

    @Override
    public void onMidiStart() {
        setStrings();
    }
}
