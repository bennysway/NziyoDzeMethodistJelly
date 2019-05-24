package com.seven.clip.nziyodzemethodist.adapters.recyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.seven.clip.nziyodzemethodist.HymnDisplay;
import com.seven.clip.nziyodzemethodist.MakeFavDialog;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.fragments.pages.HymnDisplayPage;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile.Hymn;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by bennysway on 06.11.17.
 */

public class HymnListRecyclerViewAdapter extends RecyclerView.Adapter<HymnListRecyclerViewAdapter.ViewHolder> implements SectionTitleProvider {
    private List<Hymn> dataSet;
    Context context;


    public HymnListRecyclerViewAdapter(List<Hymn> _dataSet, Context _context) {
        dataSet = _dataSet;
        context = _context;
    }

    @Override
    public String getSectionTitle(int position) {
        return dataSet.get(position).header.hymnName.substring(0, 1);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        View mView;
        TextView mTitle;
        ViewHolder(View v) {
            super(v);
            mView = v;
            mTitle = v.findViewById(R.id.shonaListTextView);
        }
    }


    @Override
    public HymnListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View layout =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.right_swipe_shona_list_layout, parent, false);
        // set the view'underLine size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(layout);
        return vh;

    }

    @Override
    public void onBindViewHolder(final HymnListRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.mTitle.setText(dataSet.get(position).header.hymnName);
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("hymnId",dataSet.get(position).header.id);
                ((NDMActivity)context).pushFragment(new HymnDisplayPage(),bundle);
            }
        });
        holder.mTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MakeFavDialog dialog = new MakeFavDialog(context, String.valueOf(dataSet.get(position).attributes.hymnNumber));
                dialog.getWindow().getAttributes().windowAnimations = R.style.TransparentDialogAnimation;
                dialog.show();
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
        return dataSet.size();
    }


}
