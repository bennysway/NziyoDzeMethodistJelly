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

        LinearLayout listLayout = view.findViewById(R.id.top_list);
        LayoutInflater listInflater = LayoutInflater.from(getContext());

        //Church Hymn Number's Menu
        View churchHymnNumberMenuItem = listInflater.inflate(R.layout.list_item_main_item,listLayout,false);
        TextView churchHymnNumberText = churchHymnNumberMenuItem.findViewById(R.id.main_list_item_text);
        ImageView churchHymnNumberIcon = churchHymnNumberMenuItem.findViewById(R.id.main_list_item_icon);
        churchHymnNumberText.setText("Church Hymn Number");
        churchHymnNumberIcon.setImageResource(R.drawable.ic_numpad);
        listLayout.addView(churchHymnNumberMenuItem);

        //Church Hymn List's Menu
        View churchHymnListMenuItem = listInflater.inflate(R.layout.list_item_main_item,listLayout,false);
        TextView churchHymnListText = churchHymnListMenuItem.findViewById(R.id.main_list_item_text);
        ImageView churchHymnListIcon = churchHymnListMenuItem.findViewById(R.id.main_list_item_icon);
        churchHymnListText.setText("Church Hymn Number");
        churchHymnListIcon.setImageResource(R.drawable.ic_list);
        listLayout.addView(churchHymnListMenuItem);



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
