package com.seven.clip.nziyodzemethodist.fragments.pages;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.google.common.collect.Lists;
import com.seven.clip.nziyodzemethodist.NziyoDzeMethodist;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.adapters.recyclerViewAdapters.HymnListRecyclerViewAdapter;
import com.seven.clip.nziyodzemethodist.adapters.recyclerViewAdapters.HymnStanzaRecyclerViewAdapter;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile.Hymn;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HymnDisplayPage extends NDMFragment {

    RecyclerView mRecyclerView;
    HymnStanzaRecyclerViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Hymn currentHymn;
    List<String> stanzaStrings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stanzaStrings = new ArrayList<>();
        if(getArguments() == null){
            Util.quickToast("Hymn not found");
            return;
        }
        String id = getArguments().getString("hymnId");
        currentHymn = NziyoDzeMethodist.databaseFile.get(id);
        if(currentHymn == null){
            Util.quickToast("Hymn not found");
            return;
        }
        stanzaStrings = Arrays.asList(currentHymn.content.stanzas);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hymn_display_page, container, false);
        initViewIds();
        initViewFunctions();
        initOnClicks();
        fragmentName = currentHymn.header.hymnName;
        return rootView;
    }

    @Override
    public void initViewIds() {
        mRecyclerView = rootView.findViewById(R.id.hymnDisplayRecyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
    }

    @Override
    public void initViewFunctions() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HymnStanzaRecyclerViewAdapter(currentHymn,getContext());
        mRecyclerView.setAdapter(mAdapter);
        ((NDMActivity) getContext()).pushNotification(currentHymn.captions);
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
        for(int id: getResources().getIntArray(R.array.hymn_display_colors))
            fabPackage.colorResources.add(id);
        TypedArray array = getResources().obtainTypedArray(R.array.hymn_display_icons);
        for(int i=0;i<fabPackage.colorResources.size(); i++){
            fabPackage.iconResources.add(array.getResourceId(i,-1));
        }
        Runnable run0 = new Runnable() {
            @Override
            public void run() {
                ((NDMActivity) getContext()).pushNotification(currentHymn.captions);
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
