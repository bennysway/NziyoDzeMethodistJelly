package com.seven.clip.nziyodzemethodist.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.models.HomeListItem;

import java.util.ArrayList;

public class HomeListAdapter extends ArrayAdapter<HomeListItem> {
    public HomeListAdapter(@NonNull Context context, ArrayList<HomeListItem> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HomeListItem listItem = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_main_item,parent,false);
        }
        ImageView icon = convertView.findViewById(R.id.main_list_item_icon);
        TextView itemName = convertView.findViewById(R.id.main_list_item_text);

        icon.setImageResource(listItem.itemIconRes);
        itemName.setText(listItem.itemName);
        return convertView;
    }
}
