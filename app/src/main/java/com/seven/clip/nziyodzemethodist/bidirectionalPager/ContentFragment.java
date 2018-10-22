package com.seven.clip.nziyodzemethodist.bidirectionalPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seven.clip.nziyodzemethodist.R;


public class ContentFragment extends Fragment {

    public HorizontalViewPager viewPager;
    public HorizontalViewPagerAdapter horizontalViewPagerAdapter;
    public String parentInd;

    public ContentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        parentInd=getArguments().getString("parent");

        viewPager =  view.findViewById(R.id.vpHorizontal);
        horizontalViewPagerAdapter = new HorizontalViewPagerAdapter(getChildFragmentManager());
        horizontalViewPagerAdapter.setParentID(parentInd);
        viewPager.setAdapter(horizontalViewPagerAdapter);
        Helper.log("Card Created : " + parentInd);
        viewPager.setCurrentItem(1);
        return view;
    }

}
