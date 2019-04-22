package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bennysway on 23.11.17.
 */

public class FavoriteListFragment extends Fragment {
    @Override
    public Context getContext() {
        return super.getContext();
    }

    public FavoriteListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static FavoriteListFragment newInstance() { return new FavoriteListFragment(); }

    RecyclerView mRecyclerView;
    MyFavoriteListRVAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        TextView noFavoriteText = view.findViewById(R.id.nofavoriteText);
        //Recycler View Handling
        mRecyclerView = view.findViewById(R.id.favoriteListRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyFavoriteListRVAdapter(((FavList)getActivity()).getFavorites(),getContext());
        mRecyclerView.setAdapter(mAdapter);

        if (((FavList)getActivity()).getFavorites().isEmpty()) {
            noFavoriteText.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        } else {
            noFavoriteText.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
