package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Captions extends AppCompatActivity {

    ListView ls;
    ArrayList<CaptionStorage> list;
    String[] rawArray;
    long starttime = 0;
    int barId;
    boolean barAvail=false;
    RelativeLayout parentLayout;
    String capStoreKey;
    View bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captions);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Data recordFlag = new Data(this,"recordflag");
        Data withCaption = new Data(this,"withcaption");
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        TextView cap = (TextView) findViewById(R.id.captionTitle);
        cap.setTypeface(custom_font);

        recordFlag.deleteAll();


        final String hymnNumWord = getIntent().getStringExtra("hymnNumWord");
        final String hymnNum = getIntent().getStringExtra("hymnNum");
        capStoreKey = hymnNumWord;
        Data storedKey = new Data(this,capStoreKey);

        ls = (ListView) findViewById(R.id.captionListView);
        final View addCaption =findViewById(R.id.addCaptionBut);
        list = new ArrayList<>();



        final String raw = storedKey.get();
        if(!raw.equals("")){
            if(!withCaption.find(capStoreKey))
                withCaption.pushBack(capStoreKey);

            rawArray = raw.split(",");

            int size =rawArray.length;
            for(int i=0;i<size;i+=3){
                CaptionStorage item = new CaptionStorage();
                item.setTitle(rawArray[i]);
                item.setType(rawArray[i+1]);
                item.setPath(rawArray[i+2]);
                item.setHymnNum(hymnNum);
                list.add(item);
            }

            Collections.reverse(list);

            ls.setAdapter(new CaptionListViewAdapter(this,list));
            ls.setVisibility(View.VISIBLE);

            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(list.get(position).getType().equals("recording")){
                        String deleteString = list.get(position).getTitle()+
                                ","+list.get(position).getType()+
                                ","+list.get(position).getPath()+",";
                        inflateBottomRecordingToolbar(list.get(position).getPath(),deleteString);

                    }
                    if(list.get(position).getType().equals("note")){
                        Intent toReadNote = new Intent(Captions.this,readNote.class);
                        toReadNote.putExtra("key",hymnNumWord);
                        toReadNote.putExtra("fullFile",list.get(position).getPath());
                        String deleteString = list.get(position).getTitle()+
                                ","+list.get(position).getType()+
                                ","+list.get(position).getPath()+",";
                        toReadNote.putExtra("record",deleteString);
                        startActivity(toReadNote);
                    }
                }
            });
        }
        else {
            QuickToast("No captions available");
            if(withCaption.find(capStoreKey))
                withCaption.delete(capStoreKey);


        }

        addCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent askType = new Intent(Captions.this,addCaption.class);
                askType.putExtra("hymnNum",hymnNum);
                askType.putExtra("hymnNumWord",hymnNumWord);
                startActivity(askType);
            }
        });

        parentLayout = (RelativeLayout) findViewById(R.id.activity_captions);
        bar = getLayoutInflater().inflate(R.layout.record_bar, parentLayout,false);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, bar.getId());
        bar.setLayoutParams(params);
        bar.setVisibility(View.INVISIBLE);
        parentLayout.addView(bar);



    }
    public String IntToStr(int i){
        return Integer.toString(i);
    }
    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
    public void inflateBottomRecordingToolbar(final String path, final String deleteRecord){
        vis(bar);
        starttime = System.currentTimeMillis();
        final TextView text = (TextView) findViewById(R.id.recordingBarText);
        text.setText("Play");
        final Handler h2 = new Handler();
        final Handler h3 = new Handler();
        final playRec recording = new playRec(path);
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                if(recording.isPlaying()){
                    long millis = System.currentTimeMillis() - starttime;
                    int seconds = (int) (millis / 1000);
                    int minutes = seconds / 60;
                    seconds     = seconds % 60;
                    text.setText(String.format("%d:%02d", minutes, seconds));
                    h2.postDelayed(this, 500);
                }
            }
        };



        h2.removeCallbacks(run);


        bar.setAlpha(0f);
        bar.animate().alpha(1f).setDuration(500);

        final View rec,play,stop,delete,noRec,noDel;

        rec =  findViewById(R.id.recordBut);
        play =  findViewById(R.id.playRecordingBut);
        stop =  findViewById(R.id.stopRecordingBut);
        delete =  findViewById(R.id.deleteRecording);
        noRec =  findViewById(R.id.recordUnavailBut);
        noDel = findViewById(R.id.deleteUnavailRecording);



        invis(rec);
        vis(noRec);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invis(play);
                vis(stop);
                invis(delete);
                vis(noDel);
                h2.postDelayed(run, 0);
                starttime = System.currentTimeMillis();
                recording.play();

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invis(stop);
                vis(play);
                vis(delete);
                invis(noDel);
                h2.removeCallbacks(run);
                recording.stop();
                starttime = System.currentTimeMillis();
                bar.animate().alpha(0f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        invis(bar);

                    }
                });

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDelete = new Intent(Captions.this,DeleteCaption.class);
                toDelete.putExtra("key",capStoreKey);
                toDelete.putExtra("record",deleteRecord);
                toDelete.putExtra("path",path);
                toDelete.putExtra("type","recording");
                startActivity(toDelete);
                finish();
            }
        });
    }
    public void vis(View v){
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        v.animate().alpha(1f);
    }
    public void invis(final View v) {
        v.animate().alpha(0f).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.setVisibility(View.INVISIBLE);
            }
        });
    }
}
