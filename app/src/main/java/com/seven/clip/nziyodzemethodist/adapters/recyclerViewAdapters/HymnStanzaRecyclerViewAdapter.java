package com.seven.clip.nziyodzemethodist.adapters.recyclerViewAdapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.seven.clip.nziyodzemethodist.MakeFavDialog;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.fragments.pages.HymnDisplayPage;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile.Hymn;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;
import com.seven.clip.nziyodzemethodist.util.Util;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by bennysway on 06.11.17.
 */

public class HymnStanzaRecyclerViewAdapter extends RecyclerView.Adapter<HymnStanzaRecyclerViewAdapter.ViewHolder> implements SectionTitleProvider {
    private Hymn hymn;
    Context context;


    public HymnStanzaRecyclerViewAdapter(Hymn _data, Context _context) {
        hymn = _data;
        context = _context;
    }

    @Override
    public String getSectionTitle(int position) {
        return "Stanza " + position;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        View mView;
        TextView stanza;
        TextView stanzaNumber;
        ViewHolder(View v) {
            super(v);
            mView = v;
            stanza = v.findViewById(R.id.stanzaVerse);
            stanzaNumber = v.findViewById(R.id.stanzaNumber);
        }
    }


    @Override
    public HymnStanzaRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View layout =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_stanza, parent, false);
        // set the view'underLine size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(layout);
        return vh;

    }

    @Override
    public void onBindViewHolder(final HymnStanzaRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.stanza.setText(hymn.content.stanzas[position]);
        holder.stanza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Util.quickToast(String.valueOf(position));
            }
        });
        holder.stanza.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Util.quickToast("yey");
                return true;
            }
        });

    }


    public void QuickToast(String s){
        Toast.makeText(context, s,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return hymn.content.stanzas.length;
    }


}
