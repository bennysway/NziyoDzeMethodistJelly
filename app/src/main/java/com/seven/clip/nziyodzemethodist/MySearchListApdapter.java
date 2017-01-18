package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MySearchListApdapter extends BaseAdapter {
    private static ArrayList<SearchResults> searchArrayList;

    private LayoutInflater mInflater;

    public MySearchListApdapter(Context context, ArrayList<SearchResults> results) {
        searchArrayList = results;
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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_list, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.searchLinebut);
            holder.txtCityState = (TextView) convertView
                    .findViewById(R.id.subsearchLinebut);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.searchHymnNum);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(searchArrayList.get(position).getTitle());
        holder.txtCityState.setText(searchArrayList.get(position)
                .getCaption());
        holder.txtPhone.setText(searchArrayList.get(position).getHymnNum());

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtCityState;
        TextView txtPhone;
    }
}