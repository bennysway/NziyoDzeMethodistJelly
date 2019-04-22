package com.seven.clip.nziyodzemethodist;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by bennysway on 09.01.18.
 */

public class CaptionsTabAdapter extends FragmentPagerAdapter {
    public CaptionsTabAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return NotesCaptionListFragment.newInstance();
            case 1:
                return RecordingsCaptionListFragment.newInstance();
            default:
                return NotesCaptionListFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
