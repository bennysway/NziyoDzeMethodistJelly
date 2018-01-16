package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bennysway on 23.11.17.
 */

public class SplashListFragment extends android.support.v4.app.Fragment implements MySplashListRVAdapter.OnItemDismissListener {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_list, container, false);
        //Recycler View Handling
        mRecyclerView = view.findViewById(R.id.splashListRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MySplashListRVAdapter(((RecList)getActivity()).getData(1),getContext());
        mAdapter.setOnItemDismissListener(this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onRightItemDismissed(int number) {
        mAdapter.deleteItem(number);
    }

    @Override
    public void onLeftItemDismissed(int number) {
        mAdapter.deleteSimilarItems(number);
    }
}
