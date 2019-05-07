package com.seven.clip.nziyodzemethodist.fragments.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seven.clip.nziyodzemethodist.MyHymnListRVAdapter;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.Theme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HymnListPage extends NDMFragment {

    RecyclerView mRecyclerView;
    MyHymnListRVAdapter mAdapter;
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
