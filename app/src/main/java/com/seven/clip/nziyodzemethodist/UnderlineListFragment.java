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

public class UnderlineListFragment extends android.support.v4.app.Fragment{
    @Override
    public Context getContext() {
        return super.getContext();
    }

    public UnderlineListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static UnderlineListFragment newInstance() { return new UnderlineListFragment(); }

    RecyclerView mRecyclerView;
    MyFavoriteStanzaListRVAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_underline_list, container, false);
        TextView noUnderlinesText = view.findViewById(R.id.noUnderlinesTextView);
        //Recycler View Handling
        mRecyclerView = view.findViewById(R.id.underlineListRecyclerView);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyFavoriteStanzaListRVAdapter(((FavList)getActivity()).getFavoriteStanza(),getContext());
        mRecyclerView.setAdapter(mAdapter);

        if (((FavList)getActivity()).getFavoriteStanza().isEmpty()) {
            noUnderlinesText.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        } else {
            noUnderlinesText.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
