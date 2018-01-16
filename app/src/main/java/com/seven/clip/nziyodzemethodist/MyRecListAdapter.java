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

import java.util.ArrayList;


class MyRecListAdapter extends ArrayAdapter {
    MyRecListAdapter(Context context, ArrayList<String> values) {
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

        final ArrayList<String> list = new UserDataIO(MyRecListAdapter.super.getContext()).getRecentList();

        theTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHymn = new Intent(MyRecListAdapter.super.getContext(),HymnDisplay.class);
                toHymn.putExtra("hymnNum",list.get(position));
                getContext().startActivity(toHymn);
            }
        });

        theTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MakeFavDialog dialog = new MakeFavDialog(MyRecListAdapter.super.getContext(),list.get(position));
                dialog.getWindow().getAttributes().windowAnimations = R.style.TransparentDialogAnimation;
                dialog.show();
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

