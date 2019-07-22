package com.seven.clip.nziyodzemethodist.fragments.pages;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seven.clip.nziyodzemethodist.HymnList;
import com.seven.clip.nziyodzemethodist.NziyoDzeMethodist;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.adapters.recyclerViewAdapters.HymnListRecyclerViewAdapter;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HymnListPage extends NDMFragment {

    RecyclerView mRecyclerView;
    HymnListRecyclerViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hymn_list_page, container, false);
        initViewIds();
        initViewFunctions();
        initOnClicks();

        fragmentName = "Hymn List";
        return rootView;
    }

    @Override
    public void initViewIds() {
        mRecyclerView = rootView.findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    public void initViewFunctions() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HymnListRecyclerViewAdapter(NziyoDzeMethodist.databaseFile.database,NziyoDzeMethodist.databaseFile.index,getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initOnClicks() {

    }

    @Override
    public void applyTheme() {

    }

    @Override
    public View getAdjustableView() {
        return mRecyclerView;
    }

    @Override
    public void transform(Theme previousTheme, Theme newTheme) {

    }

    @Override
    public FabPackage getMenu() {
        FabPackage fabPackage = new FabPackage();
        for(int id: getResources().getIntArray(R.array.hymn_list_colors))
            fabPackage.colorResources.add(id);
        TypedArray array = getResources().obtainTypedArray(R.array.hymn_list_icons);
        for(int i=0;i<fabPackage.colorResources.size(); i++){
            fabPackage.iconResources.add(array.getResourceId(i,-1));
        }
        Runnable run0 = new Runnable() {
            @Override
            public void run() {
                Util.openExternalIntent(getContext(),Util.Intents.AppFacebookPage);
            }
        };
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                Util.openExternalIntent(getContext(),Util.Intents.AppYouTubePage);
            }
        };
        fabPackage.runnables.append(0,run0);
        fabPackage.runnables.append(1,run1);
        array.recycle();
        return fabPackage;
    }
}
