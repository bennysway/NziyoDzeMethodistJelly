package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalpreloaders.widgets.CrystalPreloader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ReadingList extends AppCompatActivity {

    ArrayList<Reading> readings;
    DatabaseReference databaseReadings;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        databaseReadings = Utils.getDatabase().getReference("readings");

        View backBut = findViewById(R.id.readingListBackBut);
        final ListView readingListView = (ListView) findViewById(R.id.readingListListView);
        final TextView readingsTextView = (TextView) findViewById(R.id.noReadingListText);
        TextView topTitle = (TextView) findViewById(R.id.readingListTopTitle);
        final CrystalPreloader preloader = (CrystalPreloader) findViewById(R.id.readingListLoadingAnimation);


        readings = new ArrayList<>();


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        topTitle.setTypeface(custom_font);

        databaseReadings.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectData((Map<String,Object>) dataSnapshot.getValue());

                if(readings.size()>0){
                    Collections.sort(readings, new Comparator<Reading>() {
                        @Override
                        public int compare(Reading o1, Reading o2) {
                            return Long.valueOf(o1.getReading_id()).compareTo(o2.getReading_id());
                        }
                    });
                    MyReadingListAdapter readingListAdapter = new MyReadingListAdapter(ReadingList.this,readings);
                    readingListView.setAdapter(readingListAdapter);
                    readingListView.setVisibility(View.VISIBLE);
                    readingsTextView.setVisibility(View.INVISIBLE);
                    preloader.setVisibility(View.INVISIBLE);

                    readingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent toReadingDisplay = new Intent(ReadingList.this,ReadingDisplay.class);
                            toReadingDisplay.putExtra("reading",readings.get(position));
                            startActivity(toReadingDisplay);
                        }
                    });
                } else {
                    readingsTextView.setText("Could not retrieve database.");
                    preloader.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void collectData(Map<String,Object> weeks){
        //iterate through each week, ignoring their UID
        for (Map.Entry<String, Object> entry : weeks.entrySet()){

            //Get weeks map
            Map singleWeek = (Map) entry.getValue();
            Reading reading = new Reading();
            //Get fields and append to list
            reading.setDate(singleWeek.get("date").toString());
            reading.setDatename(singleWeek.get("date_name").toString());
            reading.setEnglish_theme(singleWeek.get("english_theme").toString());
            reading.setShona_theme(singleWeek.get("shona_theme").toString());
            reading.setTitle(singleWeek.get("title").toString());
            reading.setOt(singleWeek.get("ot").toString());
            reading.setNt(singleWeek.get("nt").toString());
            reading.setGospel(singleWeek.get("gospel").toString());
            reading.setPsalm((long) singleWeek.get("psalm"));
            reading.setReading_id((long) singleWeek.get("reading_id"));
            readings.add(reading);
        }
    }
}
