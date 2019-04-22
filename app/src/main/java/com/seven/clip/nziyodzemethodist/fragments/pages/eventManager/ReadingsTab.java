package com.seven.clip.nziyodzemethodist.fragments.pages.eventManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.seven.clip.nziyodzemethodist.EventEditor;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.adapters.ReadingListAdapter;
import com.seven.clip.nziyodzemethodist.dialogs.AlertDialog;
import com.seven.clip.nziyodzemethodist.interfaces.BundleListener;
import com.seven.clip.nziyodzemethodist.interfaces.FabMenuListener;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.models.Reading;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Firebase;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;
import com.simmorsal.recolor_project.ReColor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.graphics.Color.parseColor;
import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;

public class ReadingsTab extends NDMFragment implements BundleListener {

    RecyclerView listView;
    ReadingListAdapter adapter;
    Context context;
    private FabMenuListener fabMenu;
    ViewFlipper flipper;
    TextView unavailableTextView;
    View rootView;
    Date date;
    int highlightedIndex=-1;
    ArrayList<Reading> readings;

    private String TAG = "ReadingsTab";
    private BundleListener bundleListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.fabMenu = (FabMenuListener) context;
    }
    @Override
    public Context getContext() {
        return super.getContext();
    }
    public ReadingsTab(){
        setRetainInstance(true);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static ReadingsTab newInstance() {
        return new ReadingsTab();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUserVisibleHint(false);
        rootView = inflater.inflate(R.layout.fragment_readings_tab, container, false);
        context = getContext();
        initViewIds();
        initOnClicks();
        initUI();
        return rootView;
    }

    private void initUI() {



        //Apply color
        unavailableTextView.setTextColor(parseColor(currentTheme.getContextColor()));
    }
    public void updateReadings(ArrayList<Reading> readings, Date date){
        Log.d("ReadingsTab",readings.size() + " at" + date.toString());
        this.date = date;
        if(readings.size()>0){
            Firebase.setNetwork(false);
            adapter = new ReadingListAdapter(context,readings);
            listView.setAdapter(adapter);
            flipper.setDisplayedChild(1);
        } else {
            Firebase.setNetwork(true);
            flipper.setDisplayedChild(2);
        }
    }
    public void openEditor(){
        Intent toEditor = new Intent(context,EventEditor.class);
        toEditor.putExtra("time",date.getTime());
        toEditor.putExtra("date",Util.getDate(date));
        toEditor.putExtra("monthId",Util.getDate(date).split(" ")[0] + Util.getDate(date).split(" ")[1] );
        toEditor.putExtra("type","reading");
        startActivity(toEditor);
    }

    private void checkForMutipleReading(){
        if(highlightedIndex != -1){
            AlertDialog dialog = new AlertDialog();
            //result:boolean
            Bundle args = new Bundle();
            args.putString("title","Multiple Readings");
            args.putString("hint","Add another reading to the same day?");
            args.putString("key","alertDialog:multipleReadings");
            dialog.setArguments(args);
            assert getFragmentManager() != null;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("alert_dialog");
            if (prev != null) ft.remove(prev);
            ft.addToBackStack(null);
            dialog.show(ft,"alert_dialog");
        } else
            openEditor();

    }

    @Override
    public void sendLiveObject(String key, Object object) {
        switch (key){
            case "readings":
                readings = (ArrayList<Reading>) object;
                break;
            case "date":
                date = (Date) object;
                break;
            case "updateToken":
                if((Boolean) object)
                    updateReadings(readings,date);
                break;
            case "highlightToken":
                highlight((int) object);
                break;
            case "alertDialog:multipleReadings":
                //Todo increment date
                date.setTime(date.getTime()+1);
                openEditor();
                break;
            case "monthSwipe":
                flipper.setDisplayedChild(0);
                break;
        }
    }

    private void highlight(final int pos) {
        highlightedIndex = pos;
        if(pos != -1) {
            listView.smoothScrollToPosition(pos);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        View view = listView.findViewHolderForAdapterPosition(pos).itemView;
                        setActive(view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 500);
        }

    }

    public void setActive(View view) {
        CardView cardView = view.findViewById(R.id.cardView);
        TextView title = view.findViewById(R.id.readingListTitle);
        TextView date = view.findViewById(R.id.readingListDate);
        TextView caption = view.findViewById(R.id.readingListCaption);
        ImageView icon = view.findViewById(R.id.weekPassedIndicator);


        cardView.setScaleX(1.02f);
        cardView.setScaleY(1.02f);
        new ReColor(context).setTextViewColor(title,currentTheme.getContextColor(),currentTheme.getTextColor(),2000);
        new ReColor(context).setTextViewColor(date,currentTheme.getContextColor(),currentTheme.getTextColor(),2000);
        new ReColor(context).setTextViewColor(caption,currentTheme.getContextColor(),currentTheme.getTextColor(),2000);
        new ReColor(context).setImageViewColorFilter(icon,currentTheme.getContextColor(),currentTheme.getTextColor(),2000);
        cardView.animate().scaleX(1f).scaleY(1f);
    }

    @Override
    public FabPackage getMenu() {
        FabPackage fabPackage = new FabPackage();
        int color = parseColor(currentTheme.getIconBackgroundColor());
        fabPackage.iconResources.add(R.drawable.ic_help);
        fabPackage.iconResources.add(R.drawable.ic_edit_black_24dp);
        fabPackage.iconResources.add(R.drawable.delete_icon);
        for(int i=0; i<3; i++){
            fabPackage.colorResources.add(color);
            color = currentTheme.isInDayMode() ? ColorThemes.getLowerHue(color) : ColorThemes.getDarkerColor(color);
        }
        Runnable run0 = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,"Opening Help",Toast.LENGTH_LONG).show();
            }
        };
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                ReadingsTab.newInstance().checkForMutipleReading();
            }
        };
        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                ColorThemes.setMode(context,currentTheme);
                Toast.makeText(context,"Change mode",Toast.LENGTH_LONG).show();
            }
        };
        fabPackage.runnables.append(0,run0);
        fabPackage.runnables.append(1,run1);
        fabPackage.runnables.append(2,run2);
        return fabPackage;
    }

    @Override
    public void transform(Theme previousTheme, Theme newTheme) {

    }

    @Override
    public void initOnClicks() {

    }

    @Override
    public void initViewIds() {
        listView = rootView.findViewById(R.id.homeTabRecyclerView);
        flipper = rootView.findViewById(R.id.viewFlipper);
        unavailableTextView = rootView.findViewById(R.id.notFoundText);
    }

    @Override
    public void initViewFunctions() {
        listView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        adapter = new ReadingListAdapter(context,new ArrayList<Reading>());
        listView.setAdapter(adapter);
        readings = new ArrayList<>();
        flipper.setDisplayedChild(0);
        date = Calendar.getInstance().getTime();
    }

    @Override
    public void applyTheme() {

    }
}
