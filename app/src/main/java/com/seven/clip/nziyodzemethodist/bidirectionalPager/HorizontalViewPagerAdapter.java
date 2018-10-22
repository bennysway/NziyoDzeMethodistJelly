package com.seven.clip.nziyodzemethodist.bidirectionalPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.seven.clip.nziyodzemethodist.FavoriteListFragment;
import com.seven.clip.nziyodzemethodist.RecentListFragment;
import com.seven.clip.nziyodzemethodist.SplashListFragment;
import com.seven.clip.nziyodzemethodist.UnderlineListFragment;


public class HorizontalViewPagerAdapter extends FragmentStatePagerAdapter{

    public String parentId;

    public void setParentID(String parentID){
        this.parentId = parentID;
    }

    public HorizontalViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //values are x: position and y: parentId
        /*ChildFragment childFragment = new ChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("parent",parentId);
        bundle.putString("child",String.valueOf(position));
        childFragment.setArguments(bundle);
        return childFragment;*/

        switch (position){
            case 0:
                switch(parentId){
                    case "0":
                        return FavoriteListFragment.newInstance();
                    case "1":
                        return RecentListFragment.newInstance();
                    case "2":
                        return FavoriteListFragment.newInstance();
                    default:
                            return FavoriteListFragment.newInstance();
                }
            case 1:
                switch(parentId){
                    case "0":
                        return UnderlineListFragment.newInstance();
                    case "1":
                        return SplashListFragment.newInstance();
                    case "2":
                        return FavoriteListFragment.newInstance();
                    default:
                        return FavoriteListFragment.newInstance();
                }
            case 2:
                switch(parentId){
                    case "0":
                        return FavoriteListFragment.newInstance();
                    case "1":
                        return FavoriteListFragment.newInstance();
                    case "2":
                        return SplashListFragment.newInstance();
                    default:
                        return FavoriteListFragment.newInstance();
                }
            default:
                switch(parentId){
                    case "0":
                        return UnderlineListFragment.newInstance();
                    case "1":
                        return FavoriteListFragment.newInstance();
                    case "2":
                        return FavoriteListFragment.newInstance();
                    default:
                        return FavoriteListFragment.newInstance();
                }
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
