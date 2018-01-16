package com.seven.clip.nziyodzemethodist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by bennysway on 23.11.17.
 */

public class RecentTabAdapter extends FragmentPagerAdapter {
    public RecentTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RecentListFragment.newInstance();
            case 1:
                return SplashListFragment.newInstance();
            default:
                return RecentListFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
