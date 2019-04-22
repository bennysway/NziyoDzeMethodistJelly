package com.seven.clip.nziyodzemethodist.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.seven.clip.nziyodzemethodist.customViews.MenuItemView;
import com.seven.clip.nziyodzemethodist.models.CustomMenuItem;
import com.seven.clip.nziyodzemethodist.util.Util;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuItemRecyclerViewAdapter extends RecyclerView.Adapter<MenuItemRecyclerViewAdapter.ViewHolder> {
    private ArrayList<CustomMenuItem> customMenuItems;
    private Context context;
    public MenuItemRecyclerViewAdapter(ArrayList<CustomMenuItem> customMenuItems, Context context) {
        super();
        this.customMenuItems = customMenuItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        MenuItemView itemView = new MenuItemView(viewGroup.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                Util.convertDpToPixel(100),
                Util.convertDpToPixel(150)
        ));
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.menuItemView.setText(customMenuItems.get(position).title,
                customMenuItems.get(position).subtitle);
        holder.menuItemView.setIcon(customMenuItems.get(position).itemIconRes,300 * position);
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return customMenuItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MenuItemView menuItemView;
        public ViewHolder(View itemView) {
            super(itemView);
            menuItemView = (MenuItemView) itemView;
        }
        public MenuItemView getCustomView() {
            return menuItemView;
        }
    }
}
