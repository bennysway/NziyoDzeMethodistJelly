package com.seven.clip.nziyodzemethodist.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.Settings;
import com.seven.clip.nziyodzemethodist.dialogs.InputDialog;
import com.seven.clip.nziyodzemethodist.interfaces.BundleListener;
import com.seven.clip.nziyodzemethodist.interfaces.DialogListener;
import com.seven.clip.nziyodzemethodist.interfaces.FabMenuListener;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.models.Reading;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Firebase;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.parseColor;
import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;

public class ReadingEditorFragment extends NDMFragment implements DialogListener,BundleListener {


    @Override
    public void transform(Theme previousTheme, Theme newTheme) {

    }

    @Override
    public void initViewIds() {

    }

    @Override
    public void initViewFunctions() {

    }

    @Override
    public void initOnClicks() {

    }

    @Override
    public void applyTheme() {

    }

    class Card {

        CardView card;

        TextView textView;
        String title;
        String hint;
        boolean edited = false;
    }
    Context context;
    CardView saveButton;
    String date, monthId;
    Long time;
    Card titleCard,otCard,ntCard,gospelCard,psalmCard,shonaCard,englishCard;
    List<Card> cards;
    //TextView titleText, otText,ntText,gospelText,psalmText,shonaText,englishText;
    private FabMenuListener fabMenu;
    @Override
    public Context getContext() {
        return super.getContext();
    }

    public ReadingEditorFragment(){}

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static ReadingEditorFragment getInstance() {
        return new ReadingEditorFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUserVisibleHint(false);
        View view = inflater.inflate(R.layout.fragment_reading_editor, container, false);

        titleCard = new Card();
        otCard = new Card();
        ntCard = new Card();
        psalmCard = new Card();
        gospelCard = new Card();
        shonaCard = new Card();
        englishCard = new Card();
        cards = new ArrayList<>();

        titleCard.card = view.findViewById(R.id.titleButton);
        otCard.card = view.findViewById(R.id.oldTestamentButton);
        ntCard.card = view.findViewById(R.id.newTestamentButton);
        psalmCard.card = view.findViewById(R.id.psalmButton);
        gospelCard.card = view.findViewById(R.id.gospelButton);
        shonaCard.card = view.findViewById(R.id.shonaThemeButton);
        englishCard.card = view.findViewById(R.id.englishThemeButton);
        saveButton = view.findViewById(R.id.saveButtonCard);

        titleCard.textView = view.findViewById(R.id.titleButtonContent);
        otCard.textView= view.findViewById(R.id.oldTestamentButtonContent);
        ntCard.textView= view.findViewById(R.id.newTestamentButtonContent);
        psalmCard.textView= view.findViewById(R.id.psalmButtonContent);
        gospelCard.textView= view.findViewById(R.id.gospelButtonContent);
        shonaCard.textView= view.findViewById(R.id.shonaThemeButtonContent);
        englishCard.textView= view.findViewById(R.id.englishThemeButtonContent);

        titleCard.title=  getResources().getString(R.string.mainTitleTitle);
        otCard.title= getResources().getString(R.string.oldTestamentTitle);
        ntCard.title= getResources().getString(R.string.newTestamentTitle);
        psalmCard.title= getResources().getString(R.string.psalmTitle);
        gospelCard.title= getResources().getString(R.string.gospelTitle);
        shonaCard.title= getResources().getString(R.string.shonaThemeTitle);
        englishCard.title= getResources().getString(R.string.englishThemeTitle);

        titleCard.hint=  getResources().getString(R.string.mainTitleTitle);
        otCard.hint= getResources().getString(R.string.oldTestamentHint);
        ntCard.hint= getResources().getString(R.string.newTestamentHint);
        psalmCard.hint= getResources().getString(R.string.psalmHint);
        gospelCard.hint= getResources().getString(R.string.gospelHint);
        shonaCard.hint= getResources().getString(R.string.shonaThemeHint);
        englishCard.hint= getResources().getString(R.string.englishThemeHint);

        cards.add(titleCard);
        cards.add(otCard);
        cards.add(ntCard);
        cards.add(psalmCard);
        cards.add(gospelCard);
        cards.add(shonaCard);
        cards.add(englishCard);

        for(Card card : cards) card.card.setOnClickListener(cardClickListener(card));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo
                //Create confirm dialog with prompt:
                //Do you really want to save this Reading? Are they accurate, for they will be published globally
                save();
            }
        });

        return view;
    }

    private View.OnClickListener cardClickListener(final Card card){

        Bundle args = new Bundle();
        args.putString("title",card.title);
        args.putString("hint",card.hint);
        args.putBoolean("edit",card.edited);
        final InputDialog dialog = new InputDialog();
        dialog.setArguments(args);
        if(card.title.equals("Psalm")) {
            args.putString("type","number");
            args.putString("begin" , "1");
            args.putString("end" , "150");
        }
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("pick_num_dialog");
                if (prev != null) ft.remove(prev);
                ft.addToBackStack(null);
                dialog.show(ft,"input_dialog");
            }
        };
    }
    @Override
    public void onPositiveResult(String key, Object object) {
        switch (key){
            case "card":
                Bundle args = (Bundle) object;
                for(Card card: cards){
                    if(card.title.equals(args.getString("title", "Edit"))){
                        card.textView.setText(args.getString("input"));
                        card.textView.setAlpha(.9f);
                        card.hint = args.getString("input");
                        card.edited = true;
                        break;
                    }
                }
                break;
        }

    }

    @Override
    public void onNegativeResult() {

    }

    @Override
    public void sendLiveObject(String key, Object object) {
        switch (key){
            case "date":
                date = (String) object;
                break;
            case "monthId":
                monthId = (String) object;
                break;
            case "time":
                time = (Long) object;
                break;
        }
    }

    public void save(){
        Reading reading = new Reading();
        for(Card card : cards){
            if(!card.edited){
                Util.quickToast(context,"Not all fields are saved");
                return;
            }
        }
        //Create reading
        if(monthId.isEmpty()){
            Util.quickToast(context,"Unable to get date from calender");
            saveButton.setEnabled(false);
        }
        reading.setTime(time);
        reading.setDate(date);
        reading.setMonthId(monthId);
        reading.setEnglishTheme(englishCard.textView.getText().toString());
        reading.setShonaTheme(shonaCard.textView.getText().toString());
        reading.setTitle(titleCard.textView.getText().toString());
        reading.setOt(otCard.textView.getText().toString());
        reading.setNt(ntCard.textView.getText().toString());
        reading.setGospel(gospelCard.textView.getText().toString());
        reading.setPsalm(Integer.valueOf(psalmCard.textView.getText().toString()));
        reading.setLocked("false");
        CollectionReference readingsRef = Firebase.getFirestoreDatabase().collection("readings");
        readingsRef.document(String.valueOf(reading.getTime())).set(reading)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Util.quickToast(context,"Saved");
                        saveButton.setEnabled(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Util.quickToast(context, "Failed to save, try again later");
                    }
                });

    }

    @Override
    public FabPackage getMenu() {
        FabPackage fabPackage = new FabPackage();
        int color = parseColor(currentTheme.getIconBackgroundColor());
        fabPackage.iconResources.add(R.drawable.ic_help);
        fabPackage.iconResources.add(R.drawable.ic_save);
        for(int i=0; i<2; i++){
            fabPackage.colorResources.add(color);
            color = currentTheme.isInDayMode()? ColorThemes.getHigherHue(color) : ColorThemes.getDarkerColor(color);
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
                startActivity(new Intent(context,Settings.class));
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

}
