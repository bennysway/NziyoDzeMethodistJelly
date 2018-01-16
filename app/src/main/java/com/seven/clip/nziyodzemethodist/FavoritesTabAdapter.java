package com.seven.clip.nziyodzemethodist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by bennysway on 23.11.17.
 */

public class FavoritesTabAdapter extends FragmentPagerAdapter {
    public FavoritesTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return FavoriteListFragment.newInstance();
            case 1:
                return UnderlineListFragment.newInstance();
            default:
                return FavoriteListFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
