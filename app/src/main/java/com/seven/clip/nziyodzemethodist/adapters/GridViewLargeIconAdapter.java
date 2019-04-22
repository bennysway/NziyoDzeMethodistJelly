package com.seven.clip.nziyodzemethodist.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.models.CustomMenuItem;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;
import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;
import static com.seven.clip.nziyodzemethodist.util.ColorThemes.addBackgroundFilter;
import static com.seven.clip.nziyodzemethodist.util.ColorThemes.addDrawableFilter;

public class GridViewLargeIconAdapter extends RecyclerView.Adapter<GridViewLargeIconAdapter.ViewHolder> {
    private ArrayList<CustomMenuItem> customMenuItems;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public GridViewLargeIconAdapter(Context context, ArrayList<CustomMenuItem> items) {
        inflater = LayoutInflater.from(context);
        customMenuItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_large_icon_with_label,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text.setText(customMenuItems.get(i).title);
        viewHolder.icon.setImageResource(customMenuItems.get(i).itemIconRes);
        addBackgroundFilter(viewHolder.icon,currentTheme.getIconBackgroundColor());
        addDrawableFilter(viewHolder.icon.getDrawable(),currentTheme.getIconColor());
        addDrawableFilter(viewHolder.icon.getBackground(),currentTheme.getIconBackgroundColor());
        viewHolder.text.setTextColor(parseColor(currentTheme.getTextColor()));
    }

    @Override
    public int getItemCount() {
        return customMenuItems.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        AppCompatImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            icon = itemView.findViewById(R.id.icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
