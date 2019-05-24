package com.seven.clip.nziyodzemethodist.fragments.pages;

import android.os.Bundle;
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
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    }

    @Override
    public void initViewFunctions() {

    }

    @Override
    public void initOnClicks() {

    }

    @Override
    public void applyTheme() {

    }

    @Override
    public void transform(Theme previousTheme, Theme newTheme) {

    }

    @Override
    public FabPackage getMenu() {
        return null;
    }
}
