package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;
import com.xenione.libs.swipemaker.SwipeLayout;

/**
 * Created by bennysway on 06.11.17.
 */

public class MyHymnListRVAdapter extends RecyclerView.Adapter<MyHymnListRVAdapter.ViewHolder> implements SectionTitleProvider {
    private String [] dataSet;
    Context context;


    public MyHymnListRVAdapter(String [] _dataSet,Context _context) {
        dataSet = _dataSet;
        context = _context;
    }

    @Override
    public String getSectionTitle(int position) {
        return dataSet[position].substring(0, 1);
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
    public MyHymnListRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View layout =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.right_swipe_shona_list_layout, parent, false);
        // set the view'underLine size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(layout);
        return vh;

    }

    @Override
    public void onBindViewHolder(final MyHymnListRVAdapter.ViewHolder holder, int position) {
        holder.mTitle.setText(dataSet[position]);
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHymn = new Intent(context,HymnDisplay.class);
                toHymn.putExtra("hymnNum",cvt(v));
                context.startActivity(toHymn);
            }
        });
        holder.mTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MakeFavDialog dialog = new MakeFavDialog(context,cvt(v));
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
        return dataSet.length;
    }

    private String cvt(View z){
        String x = ((TextView)z).getText().toString();
        String [] hymns = (new Hymns(context)).getAllHymns();
        int pos = -1;

        for (int i = 0; i < hymns.length; i++) {
            if (hymns[i].equals(x)) {
                pos = i;
                break;
            }
        }
        pos++;
        return String.valueOf(pos);
    }

}
