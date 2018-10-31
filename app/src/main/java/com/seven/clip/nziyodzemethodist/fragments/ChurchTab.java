package com.seven.clip.nziyodzemethodist.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;

public class ChurchTab extends android.support.v4.app.Fragment {
    public ChurchTab() {
    }

    public static ChurchTab newInstance() { return new ChurchTab(); }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_church_tab, container, false);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }
}
