package com.seven.clip.nziyodzemethodist;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


class SearchTabAdapter extends FragmentPagerAdapter {
    SearchTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SearchShonaFragment.newInstance();
            case 1:
                return SearchEnglishFragment.newInstance();
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
