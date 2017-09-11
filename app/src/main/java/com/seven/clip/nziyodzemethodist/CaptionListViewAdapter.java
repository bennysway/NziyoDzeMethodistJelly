package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CaptionListViewAdapter extends BaseAdapter {
    private static ArrayList<CaptionStorage> searchArrayList;

    private LayoutInflater mInflater;

    public CaptionListViewAdapter(Context context, ArrayList<CaptionStorage> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final CaptionListViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.caption_list_layout, null);
            holder = new CaptionListViewAdapter.ViewHolder();
            holder.txtName = convertView.findViewById(R.id.captionDate);
            holder.txtCityState = convertView
                    .findViewById(R.id.captionType);

            convertView.setTag(holder);
        } else {
            holder = (CaptionListViewAdapter.ViewHolder) convertView.getTag();
        }


        holder.txtName.setText(searchArrayList.get(position).getTitle());
        holder.txtCityState.setText(searchArrayList.get(position)
                .getType());


        return convertView;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtCityState;

    }
}