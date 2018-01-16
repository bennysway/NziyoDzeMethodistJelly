package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xenione.libs.swipemaker.SwipeLayout;

import java.util.ArrayList;

/**
 * Created by bennysway on 06.11.17.
 */

public class MyFavoriteListRVAdapter extends RecyclerView.Adapter<MyFavoriteListRVAdapter.ViewHolder> {
    private ArrayList<String> dataSet;
    Context context;
    private xHymns hymns;
    private OnItemDismissListener mOnItemDismissListener;

    public interface OnItemDismissListener {
        void onItemDismissed(int number);
    }

    public MyFavoriteListRVAdapter(ArrayList<String> _dataSet, Context _context) {
        dataSet = _dataSet;
        context = _context;
        hymns = new xHymns(context);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        FavoriteCoordinatorLayout mView;
        TextView mTitle;
        TextView mDelete;
        SwipeLayout swipeLayout;
        ViewHolder(View v) {
            super(v);
            mView = (FavoriteCoordinatorLayout) v;
            mTitle = v.findViewById(R.id.swipeRightTextView);
            swipeLayout = v.findViewById(R.id.foregroundView);
            mDelete = v.findViewById(R.id.backgroundView);
        }
    }


    @Override
    public MyFavoriteListRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View layout =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorites_list_layout, parent, false);
        // set the view'underLine size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(layout);
        return vh;

    }

    @Override
    public void onBindViewHolder(final MyFavoriteListRVAdapter.ViewHolder holder, int position) {
        String title =hymns.getTitle(dataSet.get(position),false,true);
        holder.mTitle.setText(title);
        holder.mTitle.setAlpha(1f);
        holder.mDelete.setAlpha(0f);
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
        holder.mView.setOnDismissListener(new OnItemDismiss(position));

    }
    public void QuickToast(String s){
        Toast.makeText(context, s,
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.mView.sync();
        super.onViewRecycled(holder);
    }

    public void deleteItem(int number) {
        if (number == -1) {
            return;
        }
        dataSet.remove(number);
        notifyItemRemoved(number);
        notifyItemRangeChanged(number, getItemCount());
    }

    public void setOnItemDismissListener(OnItemDismissListener listener) {
        mOnItemDismissListener = listener;
    }

    public class OnItemDismiss implements FavoriteCoordinatorLayout.OnDismissListener {

        private int number;
        public OnItemDismiss(int number) {
            this.number = number;
        }
        @Override
        public void onDismissed() {
            if (mOnItemDismissListener != null) {
                mOnItemDismissListener.onItemDismissed(number);
            }
        }


    }
}
