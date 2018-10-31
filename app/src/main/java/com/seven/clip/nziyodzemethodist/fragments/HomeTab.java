package com.seven.clip.nziyodzemethodist.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seven.clip.nziyodzemethodist.CaptionList;
import com.seven.clip.nziyodzemethodist.FavList;
import com.seven.clip.nziyodzemethodist.HymnList;
import com.seven.clip.nziyodzemethodist.PickNumDialog;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.ReadingList;
import com.seven.clip.nziyodzemethodist.RecList;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Firebase;
import com.seven.clip.nziyodzemethodist.util.Util;

import static android.graphics.Color.parseColor;
import static com.facebook.AccessTokenManager.TAG;


public class HomeTab extends android.support.v4.app.Fragment {

    boolean loggedIn;

    @Override
    public Context getContext() {
        return super.getContext();
    }
    public HomeTab(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static HomeTab newInstance() { return new HomeTab(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        ImageView hymnNumberButton = view.findViewById(R.id.pickNumButton);
        ImageView favoritesButton = view.findViewById(R.id.favoritesButton);
        ImageView hymnListButton = view.findViewById(R.id.hymnListButton);
        ImageView captionsButton = view.findViewById(R.id.captionsButton);
        ImageView recentButton = view.findViewById(R.id.recentButton);
        ImageView readingsButton = view.findViewById(R.id.readingsButton);
        ImageView curveDivider = view.findViewById(R.id.curvedDivider);

        TextView thisWeekText = view.findViewById(R.id.thisWeekText);

        ViewFlipper thisWeekViewFlipper = view.findViewById(R.id.thisWeekViewFlipper);

        View logInIncludeView = view.findViewById(R.id.loginInclude);
        View readingSummaryIncludeView = view.findViewById(R.id.readingsInclude);

        //Color Theme Apply
        //Icon Background
        addColorFilter(hymnNumberButton,ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.iconBackground));
        addColorFilter(hymnListButton,ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.iconBackground));
        addColorFilter(favoritesButton,ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.iconBackground));
        addColorFilter(captionsButton,ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.iconBackground));
        addColorFilter(recentButton,ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.iconBackground));
        addColorFilter(readingsButton,ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.iconBackground));
        //Icon Color
        setIconColor(hymnNumberButton.getDrawable(),ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.icon));
        setIconColor(hymnListButton.getDrawable(),ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.icon));
        setIconColor(favoritesButton.getDrawable(),ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.icon));
        setIconColor(captionsButton.getDrawable(),ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.icon));
        setIconColor(recentButton.getDrawable(),ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.icon));
        setIconColor(readingsButton.getDrawable(),ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.icon));
        //Context Color
        thisWeekText.setTextColor(parseColor(ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.icon)));
        //Context Background
        thisWeekText.setBackgroundColor(parseColor(ColorThemes.getColor(getContext(),Util.Element.home,Util.Component.iconBackground)));

        hymnNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new PickNumDialog(getContext())).show();
            }
        });
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FavList.class));
            }
        });
        hymnListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HymnList.class));
            }
        });
        captionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CaptionList.class));
            }
        });
        recentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RecList.class));
            }
        });
        readingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ReadingList.class));
            }
        });

        //Firebase check user is logged in

        loggedIn = Firebase.init();

        if(loggedIn){
            //Setup Summary TODO
            //Show summary
            thisWeekViewFlipper.setDisplayedChild(1);
        }
        else{
            //Show Log in panel
            thisWeekViewFlipper.setDisplayedChild(0);
        }









        return view;
    }

    private void addColorFilter(View view, String color){
        view.getBackground().mutate().setColorFilter(parseColor(color), PorterDuff.Mode.SRC_ATOP);
    }
    private void setIconColor(Drawable drawable, String color){
        drawable.setColorFilter(parseColor(color),PorterDuff.Mode.SRC_ATOP);
    }


}
