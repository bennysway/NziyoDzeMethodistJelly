package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by bennysway on 06.11.17.
 */

public class MyFavoriteListRVAdapter extends RecyclerView.Adapter<MyFavoriteListRVAdapter.ViewHolder> {
    private ArrayList<String> dataSet;
    Context context;
    private xHymns hymns;

    public MyFavoriteListRVAdapter(ArrayList<String> _dataSet, Context _context) {
        dataSet = _dataSet;
        context = _context;
        hymns = new xHymns(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mView;
        TextView mTitle;
        ViewHolder(View v) {
            super(v);
            mView = (LinearLayout) v;
            mTitle = v.findViewById(R.id.entryListTextView);

        }
    }
    @Override
    public MyFavoriteListRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorites_list_layout, parent, false);
        ViewHolder vh = new ViewHolder(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyFavoriteListRVAdapter.ViewHolder holder, int position) {
        String title =hymns.getTitle(dataSet.get(position),false,true);
        holder.mTitle.setText(title);
        holder.mTitle.setAlpha(1f);
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHymn = new Intent(context,HymnDisplay.class);
                toHymn.putExtra("hymnNum",dataSet.get(holder.getAdapterPosition()));
                context.startActivity(toHymn);
            }
        });
        holder.mTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MakeFavDialog dialog = new MakeFavDialog(context,dataSet.get(holder.getAdapterPosition()));
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
