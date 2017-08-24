package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


class MySearchShonaListAdapter extends BaseAdapter {
    private static ArrayList<SearchResults> searchArrayList;
    private Context mContext;

    private LayoutInflater mInflater;

    MySearchShonaListAdapter(Context context, ArrayList<SearchResults> results) {
        searchArrayList = results;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position).getHymnNum();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_list,null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.searchLinebut);
            holder.mCaption = (TextView) convertView
                    .findViewById(R.id.subsearchLinebut);
            holder.mNumber = (TextView) convertView.findViewById(R.id.searchHymnNum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitle.setText(searchArrayList.get(position).getTitle());
        holder.mCaption.setText(searchArrayList.get(position)
                .getCaption());
        holder.mNumber.setText(searchArrayList.get(position).getHymnNum());
        holder.isInEnglish = searchArrayList.get(position).getIsInEnglish();



        return convertView;
    }

    static class ViewHolder {
        TextView mTitle;
        TextView mCaption;
        TextView mNumber;
        boolean isInEnglish;
    }
}