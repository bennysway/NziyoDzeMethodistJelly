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

public class MyRecentListRVAdapter extends RecyclerView.Adapter<MyRecentListRVAdapter.ViewHolder> {
    private ArrayList<String> dataSet;
    Context context;
    private xHymns hymns;

    public MyRecentListRVAdapter(ArrayList<String> _dataSet, Context _context) {
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
        ViewHolder(View v) {
            super(v);
            mView = (LinearLayout) v;
            mTitle = v.findViewById(R.id.entryListTextView);
        }
    }


    @Override
    public MyRecentListRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View layout =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_list_layout, parent, false);
        // set the view'underLine size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(layout);
        return vh;

    }

    @Override
    public void onBindViewHolder(final MyRecentListRVAdapter.ViewHolder holder,int position){
        String title = hymns.getTitle(dataSet.get(position),false,true);
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


    public void deleteItem(int number) {
        if (number == -1) {
            return;
        }
        dataSet.remove(number);
        notifyItemRemoved(number);
        notifyItemRangeChanged(number, getItemCount());
    }
    public void deleteSimilarItems(int number) {
        if (number == -1) {
            return;
        }
        String target = dataSet.get(number);
        while (dataSet.contains(target)){
            int targetPos = dataSet.indexOf(target);
            dataSet.remove(targetPos);
            notifyItemRemoved(targetPos);
            notifyItemRangeChanged(targetPos, getItemCount());
        }
    }
}
