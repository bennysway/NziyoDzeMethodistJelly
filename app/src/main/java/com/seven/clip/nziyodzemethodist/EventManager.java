package com.seven.clip.nziyodzemethodist;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.seven.clip.nziyodzemethodist.interfaces.BundleListener;
import com.seven.clip.nziyodzemethodist.interfaces.DialogListener;
import com.seven.clip.nziyodzemethodist.interfaces.FabMenuListener;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.Reading;
import com.seven.clip.nziyodzemethodist.pageAdapters.EventPageAdapter;
import com.seven.clip.nziyodzemethodist.util.Firebase;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.graphics.Color.parseColor;
import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;

public class EventManager extends AppCompatActivity implements FabMenuListener,DialogListener,BundleListener {

    TextSwitcher year, month, day;
    TextView gotoTodayButton;
    Switch calendarToggle;
    Date selectedDate,today;
    String yearText,selectedYear;
    String monthText,selectedMonth;
    String dayText,selectedDay;
    CompactCalendarView calendar;
    View calendarInclude;
    RelativeLayout calendarHeader;
    RelativeLayout parent;
    ViewPager viewPager;
    EventPageAdapter pageAdapter;
    BundleListener bundleListener;
    DialogListener dialogListener;
    List<Event> events;
    Page page;

    @Override
    public FabPackage getMenu() {
        return null;
    }


    private enum Page{
        ReadingsTab,EventsTab
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manager);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        calendarInclude = findViewById(R.id.include);
        calendar = findViewById(R.id.calendar);
        year = findViewById(R.id.yearSwitcher);
        month = findViewById(R.id.monthSwitcher);
        day = findViewById(R.id.daySwitcher);
        calendarHeader = findViewById(R.id.calendarHeader);
        calendarToggle = findViewById(R.id.calendarToggleButton);
        gotoTodayButton = findViewById(R.id.todayButton);
        viewPager = findViewById(R.id.viewPager);
        parent = findViewById(R.id.parent);
        events = new ArrayList<>();
        Locale locale = Locale.ENGLISH;
        dialogListener = this;

        parent.setBackgroundColor(parseColor(currentTheme.getTextBackgroundColor()));


        selectedDate = today = Calendar.getInstance().getTime();
        //Apply Color Theme
        //Calendar color
        calendar.setCalendarBackgroundColor(parseColor(currentTheme.getContextBackgroundColor()));
        calendarHeader.setBackgroundColor(parseColor(currentTheme.getContextBackgroundColor()));

        Calendar javaCalendar = Calendar.getInstance();
        final SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy",locale);
        final SimpleDateFormat monthFormatter = new SimpleDateFormat("MMMM",locale);
        final SimpleDateFormat dayFormatter = new SimpleDateFormat("d",locale);

        yearText = selectedYear = javaCalendar.get(Calendar.YEAR) + "";
        monthText = selectedMonth = javaCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
        dayText = selectedDay = javaCalendar.get(Calendar.DATE) + "";

        year.setCurrentText(yearText);
        month.setCurrentText(monthText);
        day.setCurrentText(dayText);
        checkForEvents(today);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date date) {
                selectedDay = dayFormatter.format(date);
                day.setText(selectedDay);
                selectedDate = date;
                checkIfToday();
                highlightEvents(date);
            }

            @Override
            public void onMonthScroll(Date date) {
                bundleListener.sendLiveObject("monthSwipe",null);
                selectedMonth = monthFormatter.format(date);
                selectedYear = yearFormatter.format(date);
                year.setText(selectedYear);
                month.setText(selectedMonth);
                selectedDate = date;
                checkIfToday();
                switch (page){
                    case EventsTab:
                        checkForEvents(date);
                        break;
                    case ReadingsTab:
                        checkForReadings(date);
                        break;
                }
            }
        });

        calendarToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                resizeCalender(b);
                calendarToggle.setEnabled(false);
            }
        });
        //Prevent calendarToggle from random clicks
        calendar.setAnimationListener(new CompactCalendarView.CompactCalendarAnimationListener() {
            @Override
            public void onOpened() {
                calendarToggle.setEnabled(true);
            }
            @Override
            public void onClosed() {
                calendarToggle.setEnabled(true);
            }
         });
        gotoTodayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.setCurrentDate(today);
                selectedDay = dayFormatter.format(today);
                day.setText(selectedDay);
                selectedMonth = monthFormatter.format(today);
                selectedYear = yearFormatter.format(today);
                year.setText(selectedYear);
                month.setText(selectedMonth);
                checkIfToday();
            }
        });

        //set viewpager
        pageAdapter = new EventPageAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bundleListener = (BundleListener) pageAdapter.getItem(i);
                switch (i){
                    case 0:
                        page = Page.ReadingsTab;
                        break;
                    case 1:
                        page = Page.EventsTab;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        page = Page.ReadingsTab;
        viewPager.setAdapter(pageAdapter);
        bundleListener = (BundleListener) pageAdapter.getItem(0);
        switch (page){
            case EventsTab:
                checkForEvents(selectedDate);
                break;
            case ReadingsTab:
                checkForReadings(selectedDate);
                break;
        }
    }

    private void highlightEvents(Date date) {
        int i = 0;
        for(Event event : calendar.getEventsForMonth(date)){
            if(event.getTimeInMillis() == date.getTime()){
                bundleListener.sendLiveObject("date",date);
                bundleListener.sendLiveObject("highlightToken",i);
                return;
            }
            i++;
        }
        bundleListener.sendLiveObject("date",date);
        bundleListener.sendLiveObject("highlightToken",-1);

    }

    private void checkForEvents(final Date date) {
        //Todo
    }

    private void checkForReadings(final Date date){
        final ArrayList<Reading> readings = new ArrayList<>();
        CollectionReference readingsRef = Firebase.getFirestoreDatabase().collection("readings");
        Query query = readingsRef.whereEqualTo("monthId",selectedYear + selectedMonth);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Log.d("EventManager", "is Cache? " + document.getMetadata().isFromCache());
                        Reading reading = document.toObject(Reading.class);
                        readings.add(reading);
                        Event event = new Event(parseColor("#000000"),reading.getTime());
                        if(!events.contains(event)){
                            events.add(event);
                            calendar.addEvent(event);
                            Log.d("EventManager", "added " + event.toString());
                        }
                    }
                    Log.d("EventManager", "Readings query: " + selectedMonth +":"+ selectedYear + ":" +readings.size());
                    bundleListener.sendLiveObject("readings", readings);
                    bundleListener.sendLiveObject("date", date);
                    bundleListener.sendLiveObject("updateToken",true);

                }
                else {
                    Log.d("EventManager", "Error getting readings query: " + selectedMonth + selectedYear, task.getException());
                }
            }
        });

    }

    public void enableTodayButton(boolean activate){
        if(activate){
            gotoTodayButton.animate().alpha(1f);
            gotoTodayButton.setText(getString(R.string.gotoToday));
        }
        else{
            gotoTodayButton.animate().alpha(.5f);
            gotoTodayButton.setText(getString(R.string.today));

        }
    }

    public void checkIfToday(){
        enableTodayButton(!((dayText.equals(selectedDay) && monthText.equals(selectedMonth) && yearText.equals(selectedYear))));
    }

    public void resizeCalender(boolean minimize){
        if(minimize) calendar.showCalendarWithAnimation();
        else calendar.hideCalendarWithAnimation();

    }

    @Override
    public void onPositiveResult(String key, Object object) {
        bundleListener.sendLiveObject(key,object);
    }

    @Override
    public void onNegativeResult() {
    }

    @Override
    public void sendLiveObject(String key, Object object) {
        switch (key){
            case "requestReadings":
                checkForReadings(selectedDate);
                break;
        }
    }
}
