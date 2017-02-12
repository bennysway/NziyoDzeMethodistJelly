package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


class MyRecListAdapter extends ArrayAdapter {
    MyRecListAdapter(Context context, String[] values) {
        super(context, R.layout.hymn_list2, values);
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater theInflater = (LayoutInflater.from(getContext()));
        View theView = theInflater.inflate(R.layout.hymn_list2, parent, false);
        String hymnEntry = (String) getItem(position);

        Button theTextView = (Button) theView.findViewById(R.id.hymnFirstLinebut);
        theTextView.setText(hymnEntry);
        final Data recList = new Data(MyRecListAdapter.super.getContext(), "reclist");
        String list = recList.get();
        final String[] recs = list.split(",");

        theTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHymn = new Intent(MyRecListAdapter.super.getContext(),hymnDisplay.class);
                toHymn.putExtra("hymnNum",String.valueOf(recs[position]));
                getContext().startActivity(toHymn);
            }
        });

        return theView;
    }

    public void QuickToast(String s) {
        Toast.makeText(getContext(), s,
                Toast.LENGTH_SHORT).show();
    }
}

