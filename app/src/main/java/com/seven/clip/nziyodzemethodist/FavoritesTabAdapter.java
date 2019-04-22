package com.seven.clip.nziyodzemethodist;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
