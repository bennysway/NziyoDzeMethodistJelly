package com.seven.clip.nziyodzemethodist.fragments.pages.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.customViews.MenuItemView;
import com.seven.clip.nziyodzemethodist.fragments.pages.HymnNumberPage;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.graphics.Color.parseColor;
import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;


public class HomeTab extends NDMFragment{

    private Context context;

    private MenuItemView hymnNumberButton;
    private MenuItemView hymnListButton;
    private MenuItemView searchButton;
    private MenuItemView favoritesButton;
    private MenuItemView recentButton;
    private MenuItemView captionsButton;
    private MenuItemView eventsButton;
    private MenuItemView prayersButton;
    private MenuItemView churchButton;
    private MenuItemView organizationButton;
    private MenuItemView profileButton;

    public HomeTab(){}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    //checkThemeChange();
                    ((NDMActivity)context).changeTitleName("N");
                }
            }, 400);

        }
    }

    private void checkThemeChange() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUserVisibleHint(false);
        rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);
        fragmentName = "Nziyo DzeMethodist";
        initViewIds();
        applyTheme();
        initOnClicks();
        String mainColor;
        int[] mainColors = new int[5];

        mainColor = currentTheme.getIconBackgroundColor();
        for(int i=0; i<5 ;i++) {
            mainColors[i] = parseColor(mainColor);
            mainColor = currentTheme.isInDayMode() ? ColorThemes.getLowerHue(mainColor) : ColorThemes.getDarkerColor(mainColor);
        }

        LayoutInflater summaryInflater = LayoutInflater.from(getContext());
        return rootView;
    }
    @Override
    public void initOnClicks() {
        hymnNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NDMActivity)getContext()).pushFragment(new HymnNumberPage());
            }
        });
    }

    @Override
    public void initViewIds() {
        hymnNumberButton = rootView.findViewById(R.id.hymnNumberButton);
        hymnListButton = rootView.findViewById(R.id.hymnListButton);
        searchButton = rootView.findViewById(R.id.searchButton);
        favoritesButton = rootView.findViewById(R.id.favoritesButton);
        recentButton = rootView.findViewById(R.id.recentButton);
        captionsButton = rootView.findViewById(R.id.captionsButton);
        eventsButton = rootView.findViewById(R.id.eventsButton);
        prayersButton = rootView.findViewById(R.id.prayersButton);
        churchButton = rootView.findViewById(R.id.churchButton);
        organizationButton = rootView.findViewById(R.id.organizationButton);
        profileButton = rootView.findViewById(R.id.profileButton);
    }

    @Override
    public void initViewFunctions() {

    }

    @Override
    public void applyTheme(){
    }
    @Override
    public void transform(final Theme previousTheme, final Theme newTheme) {
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 200);
    }
    @Override
    public FabPackage getMenu() {
        FabPackage fabPackage = new FabPackage();
        for(int id: getResources().getIntArray(R.array.home_colors))
            fabPackage.colorResources.add(id);
        TypedArray array = getResources().obtainTypedArray(R.array.home_icons);
        for(int i=0;i<fabPackage.colorResources.size(); i++){
            fabPackage.iconResources.add(array.getResourceId(i,-1));
        }
        Runnable run0 = new Runnable() {
            @Override
            public void run() {
                Util.openExternalIntent(getContext(),Util.Intents.AppFacebookPage);
            }
        };
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                Util.openExternalIntent(getContext(),Util.Intents.AppYouTubePage);
            }
        };
        fabPackage.runnables.append(0,run0);
        fabPackage.runnables.append(1,run1);
        array.recycle();
        return fabPackage;
    }
}
