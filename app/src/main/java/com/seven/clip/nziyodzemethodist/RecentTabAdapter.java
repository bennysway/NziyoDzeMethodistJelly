package com.seven.clip.nziyodzemethodist;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
            default:
                return RecentListFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
