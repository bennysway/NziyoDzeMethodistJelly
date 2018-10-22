package com.seven.clip.nziyodzemethodist.pageAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.seven.clip.nziyodzemethodist.fragments.ChurchTab;
import com.seven.clip.nziyodzemethodist.fragments.HomeTab;

public class HomePageAdapter extends FragmentPagerAdapter {
    public HomePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return HomeTab.newInstance();
            case 1:
                return ChurchTab.newInstance();
            default:
                return HomeTab.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
