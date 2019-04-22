package com.seven.clip.nziyodzemethodist.fragments.pages.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.interfaces.FabMenuListener;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.Theme;

import java.util.ArrayList;

import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;
import static com.seven.clip.nziyodzemethodist.util.ColorThemes.addBackgroundFilter;
import static com.seven.clip.nziyodzemethodist.util.ColorThemes.addDrawableFilter;

import static android.graphics.Color.parseColor;

public class ChurchTab extends NDMFragment {
    private Context context;
    private FabMenuListener fabMenu;
    private View rootView;

    private ImageView aboutButton;
    private ImageView churchHymnListButton;
    private ImageView favoritesButton;
    private ImageView recentButton;
    private ImageView postsButton;
    private ImageView eventsButton;
    private ArrayList<ImageView> imageViews;

    private TextView aboutButtonButtonText;
    private TextView churchHymnListText;
    private TextView favoritesButtonText;
    private TextView recentButtonText;
    private TextView postsButtonText;
    private TextView eventsButtonText;
    private ArrayList<TextView> textViews;

    public ChurchTab() {
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.fabMenu = (FabMenuListener) context;
    }


    public static ChurchTab newInstance() { return new ChurchTab(); }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_church_tab, container, false);
        initViewIds();
        applyTheme();

        return rootView;
    }


    @Override
    public void initOnClicks() {

    }

    @Override
    public void initViewIds() {
        aboutButton = rootView.findViewById(R.id.aboutChurchButton);
        churchHymnListButton = rootView.findViewById(R.id.churchHymnListButton);
        favoritesButton = rootView.findViewById(R.id.churchFavoritesButton);
        recentButton = rootView.findViewById(R.id.churchRecentButton);
        postsButton = rootView.findViewById(R.id.churchPostsButton);
        eventsButton = rootView.findViewById(R.id.churchEventsButton);

        aboutButtonButtonText = rootView.findViewById(R.id.aboutChurchButtonText);
        churchHymnListText = rootView.findViewById(R.id.churchHymnListButtonText);
        favoritesButtonText = rootView.findViewById(R.id.churchFavoritesButtonText);
        recentButtonText = rootView.findViewById(R.id.churchRecentButtonText);
        postsButtonText = rootView.findViewById(R.id.churchPostsButtonText);
        eventsButtonText = rootView.findViewById(R.id.churchEventsButtonText);
    }

    @Override
    public void initViewFunctions() {

    }

    @Override
    public void applyTheme() {
        addDrawableFilter(aboutButton.getDrawable(),currentTheme.getIconColor());
        addDrawableFilter(churchHymnListButton.getDrawable(),currentTheme.getIconColor());
        addDrawableFilter(favoritesButton.getDrawable(),currentTheme.getIconColor());
        addDrawableFilter(recentButton.getDrawable(),currentTheme.getIconColor());
        addDrawableFilter(postsButton.getDrawable(),currentTheme.getIconColor());
        addDrawableFilter(eventsButton.getDrawable(),currentTheme.getIconColor());

        addBackgroundFilter(aboutButton,currentTheme.getIconBackgroundColor());
        addBackgroundFilter(churchHymnListButton,currentTheme.getIconBackgroundColor());
        addBackgroundFilter(favoritesButton,currentTheme.getIconBackgroundColor());
        addBackgroundFilter(recentButton,currentTheme.getIconBackgroundColor());
        addBackgroundFilter(postsButton,currentTheme.getIconBackgroundColor());
        addBackgroundFilter(eventsButton,currentTheme.getIconBackgroundColor());

        aboutButtonButtonText.setTextColor(parseColor(currentTheme.getTextColor()));
        churchHymnListText.setTextColor(parseColor(currentTheme.getTextColor()));
        favoritesButtonText.setTextColor(parseColor(currentTheme.getTextColor()));
        recentButtonText.setTextColor(parseColor(currentTheme.getTextColor()));
        postsButtonText.setTextColor(parseColor(currentTheme.getTextColor()));
        eventsButtonText.setTextColor(parseColor(currentTheme.getTextColor()));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkThemeChange();
                }
            }, 400);

        }
    }

    private void checkThemeChange() {
        //Todo
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public FabPackage getMenu() {
        return null;
    }

    @Override
    public void transform(Theme previousTheme, Theme newTheme) {

    }
}
