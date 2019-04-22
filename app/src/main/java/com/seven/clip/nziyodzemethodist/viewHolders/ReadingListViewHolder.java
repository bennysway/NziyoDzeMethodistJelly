package com.seven.clip.nziyodzemethodist.viewHolders;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;

public class ReadingListViewHolder extends RecyclerView.ViewHolder {
    public TextView mTitle;
    public TextView mDate;
    public TextView mCaption;
    public ImageView mIcon;
    public CardView mCard;

    public ReadingListViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.readingListTitle);
        mDate = itemView.findViewById(R.id.readingListDate);
        mCaption = itemView.findViewById(R.id.readingListCaption);
        mIcon = itemView.findViewById(R.id.weekPassedIndicator);
        mCard = itemView.findViewById(R.id.cardView);
    }
}
