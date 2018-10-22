package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.clip.nziyodzemethodist.bidirectionalPager.VerticalViewPager;

/**
 * Created by bennysway on 23.11.17.
 */

public class SplashListFragment extends android.support.v4.app.Fragment {
    @Override
    public Context getContext() {
        return super.getContext();
    }

    public SplashListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SplashListFragment newInstance() { return new SplashListFragment(); }

    RecyclerView mRecyclerView;
    MySplashListRVAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    VerticalViewPager mViewPager;
    boolean mIsVisibleToUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_list, container, false);
        //Recycler View Handling
        mRecyclerView = view.findViewById(R.id.splashListRecyclerView);
        mRecyclerView.addOnScrollListener(scrollListener);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MySplashListRVAdapter(((RecList)getActivity()).getData(1),getContext());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!recyclerView.canScrollVertically(1)) {
                mViewPager.setPagingEnabled(true);
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        if (isResumed()) { // fragment have created
            if (mIsVisibleToUser) {
                onVisible();
            } else {
                onInvisible();
            }
        }
    }

    public void onVisible() {
        mViewPager.setPagingEnabled(false);
    }

    public void onInvisible() {
        mViewPager.setPagingEnabled(true);
    }
}
