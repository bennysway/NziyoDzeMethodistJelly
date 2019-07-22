package com.seven.clip.nziyodzemethodist.adapters.recyclerViewAdapters;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.clip.nziyodzemethodist.MakeFavDialog;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.fragments.pages.HymnDisplayPage;
import com.seven.clip.nziyodzemethodist.models.HymnDatabaseFile.Hymn;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by bennysway on 06.11.17.
 */

public class HymnListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> dataSet;
    private Context context;


    public HymnListRecyclerViewAdapter(List<Hymn> _dataSet, SparseArray<String> _index, Context _context) {
        dataSet = new ArrayList<>();
        for(int i = 0; i<_dataSet.size(); i++){
            if(!_index.get(i,"").equals("")){
                dataSet.add(new Header(_index.get(i)));
            }
            dataSet.add(_dataSet.get(i));
        }
        context = _context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mTitle;
        ViewHolder(View v) {
            super(v);
            mView = v;
            mTitle = v.findViewById(R.id.title);
        }
    }
    public static class ViewHolderHeader extends RecyclerView.ViewHolder {
        View mView;
        TextView mTitle;
        ViewHolderHeader(View v) {
            super(v);
            mView = v;
            mTitle = v.findViewById(R.id.header);
        }
    }
    public class Header{
        String title;
        Header(String _title){
            title = _title;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.simple_list_item, parent, false));
        } else {
            return new ViewHolderHeader(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.simple_list_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder){
            ((ViewHolder)holder).mTitle.setText(((Hymn)dataSet.get(position)).header.hymnName);
            ((ViewHolder)holder).mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("hymnId",((Hymn)dataSet.get(position)).header.id);
                    ((NDMActivity)context).pushFragment(new HymnDisplayPage(),bundle);
                }
            });
            ((ViewHolder)holder).mTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MakeFavDialog dialog = new MakeFavDialog(context, String.valueOf(((Hymn)dataSet.get(position)).attributes.hymnNumber));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.TransparentDialogAnimation;
                    dialog.show();
                    return true;
                }
            });
        } else if(holder instanceof ViewHolderHeader){
            ((ViewHolderHeader)holder).mTitle.setText(((Header)dataSet.get(position)).title);
        }
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
    public int getItemViewType(int position) {
        if(dataSet.get(position) instanceof Header) return 1;
        else return 0;
    }
}
