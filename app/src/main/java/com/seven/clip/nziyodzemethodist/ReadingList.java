package com.seven.clip.nziyodzemethodist;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalpreloaders.widgets.CrystalPreloader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.seven.clip.nziyodzemethodist.adapters.ReadingListAdapter;
import com.seven.clip.nziyodzemethodist.models.Reading;
import com.seven.clip.nziyodzemethodist.util.Firebase;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class ReadingList extends AppCompatActivity {

    ArrayList<Reading> readings;
    CollectionReference readingsCollection;
    View backBut;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView readingListView;
    TextView readingsTextView,topTitle;
    CrystalPreloader preloader;
    RelativeLayout topHolder, activityHolder,notificationHolder;
    private int _yDelta,_defaultHeight,_height,_travelDist;
    boolean notified=false;
    float startPos;
    private String TAG = "ReadingListActivity";

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


        readingsCollection = Firebase.getFirestoreDatabase().collection("readings");
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);


        _height = dm.heightPixels;


        backBut = findViewById(R.id.readingListBackBut);
        //notify = findViewById(R.id.readingListNotificationInclude);
        readingListView = findViewById(R.id.readingListListView);
        readingsTextView = findViewById(R.id.noReadingListText);
        topTitle = findViewById(R.id.readingListTopTitle);
        topHolder = findViewById(R.id.readingListTopHolder);
        preloader = findViewById(R.id.readingListLoadingAnimation);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshReadingList);
        activityHolder = findViewById(R.id.reading_list_activity_holder);
        notificationHolder = findViewById(R.id.readingListNotificationHolder);

        readings = new ArrayList<>();
        swipeRefreshLayout.setProgressViewOffset(true,150,200);
        _defaultHeight = topHolder.getLayoutParams().height;
        _travelDist = (_height/2)-_defaultHeight;



        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/bh.ttf");
        topTitle.setTypeface(custom_font);


        View.OnTouchListener topDrag = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        //_xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.height;
                        startPos = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        if(startPos-event.getY()<0)
                            animateSize(view,layoutParams2.height,_height/2);
                        else
                            animateSize(view,layoutParams2.height,_defaultHeight);
                        view.setLayoutParams(layoutParams2);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        //layoutParams.leftMargin = X - _xDelta;
                        layoutParams.height = Y - _yDelta;
                        //layoutParams.rightMargin = (-1*250);
                        //layoutParams.bottomMargin = (-1*250);
                        sizeChangeListener((float) (Y-_yDelta-_defaultHeight)/_travelDist);
                        view.setLayoutParams(layoutParams);
                        break;
                }
                //_root.invalidate();
                return true;
            }
        };

        topHolder.setOnTouchListener(topDrag);

        //Get readings for current month only
        Calendar calendar = Calendar.getInstance();
        queryReading(calendar.get(Calendar.MONTH));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Firebase.setNetwork(true);
                readings.clear();
                Calendar calendar = Calendar.getInstance();
                queryReading(calendar.get(Calendar.MONTH));
            }
        });

        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topHolder.getLayoutParams().height==_defaultHeight)
                    finish();
                else
                    animateSize(topHolder,topHolder.getLayoutParams().height,_defaultHeight);
            }
        });



    }

    public void queryReading(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        final String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        Query query = readingsCollection.whereEqualTo("monthId", monthName);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    int counter = 0;
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Reading reading = document.toObject(Reading.class);
                        readings.add(reading);
                        counter++;
                    }
                    if(counter<1)
                        Util.quickToast(ReadingList.this,"No readings were found");
                    Log.d(TAG,"Entries added from Query:" + counter + " from month: " + monthName);
                    loadData();
                }
            }
        });

    }

    private void loadData(){
        if(readings.size()>0){
            Collections.sort(readings, new Comparator<Reading>() {
                @Override
                public int compare(Reading o1, Reading o2) {
                    return Util.getDate(o1.getDate()).compareTo(Util.getDate(o2.getDate()));
                }
            });
            ReadingListAdapter readingListAdapter = new ReadingListAdapter(ReadingList.this,readings);
            readingListView.setAdapter(readingListAdapter);
            readingListView.setVisibility(View.VISIBLE);
            readingsTextView.setVisibility(View.INVISIBLE);
            preloader.setVisibility(View.INVISIBLE);

            //Todo to fix
            /*readingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent toReadingDisplay = new Intent(ReadingList.this,ReadingDisplay.class);
                    toReadingDisplay.putExtra("reading",readings.get(position));
                    startActivity(toReadingDisplay);
                }
            });*/
            checkValidity();
        } else {
            readingsTextView.setText("Could not retrieve database.");
            preloader.setVisibility(View.INVISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onStop(){
        super.onStop();
        Firebase.setNetwork(false);
    }

    private void animateSize(final View view,int beginValue,int endValue){
        ValueAnimator animator = ValueAnimator.ofInt(beginValue, endValue);
        animator.setDuration(500);
        animator.setInterpolator(new OvershootInterpolator(1f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) view.getLayoutParams();
                //layoutParams.width = (Integer) animation.getAnimatedValue();
                layoutParams.height = (Integer) animation.getAnimatedValue();
                sizeChangeListener(((Integer) animation.getAnimatedValue()-_defaultHeight)/(float)_travelDist);
                view.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }

    public void sizeChangeListener(float value){
        backBut.setRotation(value*90);
    }
    public void QuickToast(String s) {
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }

    public void notificationHandler(int notify){
        if(!notified){
            switch(notify){
                case 0:
                    TextView noNotifications = new TextView(this);
                    noNotifications.setText("No Notifications");
                    noNotifications.setPadding(10,10,10,10);
                    noNotifications.setTextColor(Color.BLACK);
                    noNotifications.setBackground(getResources().getDrawable(R.drawable.but_three));
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                    noNotifications.setLayoutParams(layoutParams);
                    notificationHolder.addView(noNotifications);
                    notified=true;
                    break;
                case 1:
                    View uploadLayout = getLayoutInflater().inflate(R.layout.reading_notification_upload_layout, null);
                    notificationHolder.addView(uploadLayout);
                    notified=true;
                    break;
                case 2:
                    break;
            }
        }

    }

    public void checkValidity(){
        //Date extraction
        DateFormat dateFormat = new SimpleDateFormat("yyyy MMMM dd", Locale.getDefault());
        String ss_date = readings.get(readings.size()-1).getDate();
        Date date, today;
        Calendar c = Calendar.getInstance();
        today = c.getTime();

        try {
            date = dateFormat.parse(ss_date);
        } catch (ParseException e) {
            date = today;
            e.printStackTrace();
        }

        if(date.before(today)){
            Handler handler = new Handler();
            notificationHandler(1);
            animateSize(topHolder,topHolder.getLayoutParams().height,_height/2);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animateSize(topHolder,topHolder.getLayoutParams().height,_defaultHeight);
                }
            },4000);

        }
        else
            notificationHandler(0);

    }
}
