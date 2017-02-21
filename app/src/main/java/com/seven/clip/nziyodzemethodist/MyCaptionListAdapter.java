package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


class MyCaptionListAdapter extends ArrayAdapter{
    MyCaptionListAdapter(Context context, String[] values) {
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
        Data captList = new Data(MyCaptionListAdapter.super.getContext(), "withcaption");
        String list = captList.get();
        final String[] captions = list.split(",");

        theTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHymn = new Intent(MyCaptionListAdapter.super.getContext(),hymnDisplay.class);
                toHymn.putExtra("hymnNum",captions[position]);
                toHymn.putExtra("hymnType",2);
                getContext().startActivity(toHymn);
            }
        });

        theTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent toRemoveFav = new Intent(MyCaptionListAdapter.super.getContext(),removeFav.class);
                toRemoveFav.putExtra("hymnNum",captions[position]);
                getContext().startActivity(toRemoveFav);

                return true;
            }
        });

        return theView;
    }



    public void QuickToast(String s) {
        Toast.makeText(getContext(), s,
                Toast.LENGTH_SHORT).show();
    }
}

