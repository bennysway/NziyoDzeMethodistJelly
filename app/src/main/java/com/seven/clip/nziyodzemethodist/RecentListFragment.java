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
import android.widget.Toast;

/**
 * Created by bennysway on 23.11.17.
 */

public class RecentListFragment extends Fragment {
    @Override
    public Context getContext() {
        return super.getContext();
    }

    public RecentListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static RecentListFragment newInstance() { return new RecentListFragment(); }

    RecyclerView mRecyclerView;
    MyRecentListRVAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_list, container, false);
        TextView noRecentText = view.findViewById(R.id.norecText);

        //Recycler View Handling
        mRecyclerView = view.findViewById(R.id.recentListRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecentListRVAdapter(((RecList)getActivity()).getData(0),getContext());
        mRecyclerView.setAdapter(mAdapter);

        if (((RecList)getActivity()).getData(0).isEmpty()) {
            noRecentText.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        } else {
            noRecentText.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        return view;
    }
    public void QuickToast(String s) {
        Toast.makeText(getContext(), s,
                Toast.LENGTH_SHORT).show();
    }
}
