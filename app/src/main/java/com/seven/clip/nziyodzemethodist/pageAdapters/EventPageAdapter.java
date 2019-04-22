package com.seven.clip.nziyodzemethodist.pageAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.seven.clip.nziyodzemethodist.fragments.pages.eventManager.EventsTab;
import com.seven.clip.nziyodzemethodist.fragments.pages.eventManager.ReadingsTab;

public class EventPageAdapter extends PagerAdapter {
    private FragmentManager fragmentManager;
    private Fragment[] fragments;

    public EventPageAdapter(FragmentManager fm) {
        fragmentManager = fm;
        fragments = new Fragment[2];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        assert(0 <= position && position < fragments.length);
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.remove(fragments[position]);
        trans.commit();
        fragments[position] = null;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment = getItem(position);
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.add(container.getId(),fragment,"fragment:"+position);
        trans.commit();
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object fragment) {
        return ((Fragment) fragment).getView() == view;
    }
    public Fragment getItem(int position){
        assert(0 <= position && position < fragments.length);
        if(fragments[position] == null){
            switch (position){
                case 0:
                    fragments[0] = ReadingsTab.newInstance();
                    return fragments[0];
                case 1:
                    fragments[1] = EventsTab.newInstance();
                    return  fragments[1];
            }
        }
        return fragments[position];
    }
}
