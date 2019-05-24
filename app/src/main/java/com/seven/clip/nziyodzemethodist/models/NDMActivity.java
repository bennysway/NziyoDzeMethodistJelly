package com.seven.clip.nziyodzemethodist.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.customViews.TitleBarView;
import com.seven.clip.nziyodzemethodist.fragments.pages.home.HomeTab;
import com.seven.clip.nziyodzemethodist.interfaces.ColorChangeListener;
import com.seven.clip.nziyodzemethodist.interfaces.TitleBar;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

public abstract class NDMActivity extends AppCompatActivity implements TitleBar,ColorChangeListener {
    private static final String TAG = NDMActivity.class.getSimpleName();
    public TitleBarView titleBarView;
    public Theme theme;
    @Override
    public void transform(Theme previousTheme, Theme newTheme) {
        Log.d(TAG, "transform: Theming titleBar...");
        titleBarView.transform(previousTheme,newTheme);
    }
    public void changeTitleName(final String title){
        titleBarView.setTitle(title);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView textView = (TextView) titleBarView.getTitleSwitcherView().getCurrentView();
                int height = 50 + ((textView.getLineCount() - 1) * 30);
                int defaultHeight = Util.convertDpToPixel(50);
                titleBarView.calculateTitleBarDimens(height);
                if(!titleBarView.isTitleBarExpanded){
                    if(height>50){
                        titleBarView.animateSize(titleBarView,
                                titleBarView.getHeight(),
                                Util.convertDpToPixel(height));
                    } else {
                        titleBarView.animateSize(titleBarView,
                                titleBarView.getHeight(),
                                defaultHeight);
                    }
                } else {
                    if(!titleBarView.menuLock)
                        titleBarView.showTabMenu();
                }

            }
        },1000);
    }
    public abstract NDMFragment getFragment();
    public void pushFragment(NDMFragment fragment, Bundle bundle) {
        FragmentTransaction ft;
        NDMFragment previousFragment =(NDMFragment) getSupportFragmentManager().findFragmentByTag("currentFragment");
        ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.frag_fade_zoom_in_enter,
                R.anim.frag_fade_zoom_in_exit,
                R.anim.frag_fade_zoom_out_enter,
                R.anim.frag_fade_zoom_out_exit);
        if(previousFragment != null) ft.hide(previousFragment);
        if(bundle != null) fragment.setArguments(bundle);
        ft.add(R.id.fragmentPlaceHolder, fragment, "currentFragment");
        if(previousFragment != null) ft.addToBackStack(null);
        ft.commit();
    }

}
