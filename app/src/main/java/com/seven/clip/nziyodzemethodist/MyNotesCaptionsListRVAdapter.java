package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class MyNotesCaptionsListRVAdapter extends RecyclerView.Adapter<MyNotesCaptionsListRVAdapter.ViewHolder> {
    private ArrayList<UserData.UserCaption.UserNote> dataSet;
    Context context;
    private xHymns hymns;

    public MyNotesCaptionsListRVAdapter(ArrayList<UserData.UserCaption.UserNote> _dataSet, Context _context) {
        dataSet = _dataSet;
        context = _context;
        hymns = new xHymns(context);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        LinearLayout mView;
        TextView mTitle;
        TextView mDescription;
        ViewHolder(View v) {
            super(v);
            mView = (LinearLayout) v;
            mTitle = v.findViewById(R.id.title);
            mDescription = v.findViewById(R.id.description);
        }
    }


    @Override
    public MyNotesCaptionsListRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View layout =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_list_layout, parent, false);
        // set the view'underLine size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(layout);
        return vh;

    }

    @Override
    public void onBindViewHolder(final MyNotesCaptionsListRVAdapter.ViewHolder holder, int position) {
        holder.mTitle.setText(dataSet.get(position).getDate());
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
        holder.mTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //todo
                return false;
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
