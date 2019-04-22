package com.seven.clip.nziyodzemethodist.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.ReadingDisplay;
import com.seven.clip.nziyodzemethodist.models.Reading;
import com.seven.clip.nziyodzemethodist.viewHolders.ReadingListViewHolder;
import com.simmorsal.recolor_project.ReColor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;


public class ReadingListAdapter extends RecyclerView.Adapter<ReadingListViewHolder> {
    private static ArrayList<Reading> readings;
    private Context mContext;


    public ReadingListAdapter(Context context, ArrayList<Reading> results) {
        readings = results;
        mContext = context;
    }

    @NonNull
    @Override
    public ReadingListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReadingListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reading_list_view_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReadingListViewHolder holder, int position) {
        //Date extraction
        DateFormat dateFormat = new SimpleDateFormat("yyyy MMMM dd", Locale.getDefault());
        String ss_date = readings.get(position).getDate();
        String s_today;
        Date date, today;
        Calendar c = Calendar.getInstance();
        today = c.getTime();
        try {
            date = dateFormat.parse(ss_date);
        } catch (ParseException e) {
            date = today;
            e.printStackTrace();
        }
        String s_date = dateFormat.format(date);
        s_today = dateFormat.format(today);
        if(date.before(today)){
            Drawable icon = AppCompatResources.getDrawable(mContext, R.drawable.ic_date_passed_icon);
            holder.mIcon.setImageDrawable(icon);
            holder.mIcon.setVisibility(View.VISIBLE);
            if(s_date.equals(s_today)) {
                icon = AppCompatResources.getDrawable(mContext,R.drawable.ic_date_icon);
                holder.mIcon.setImageDrawable(icon);
            }
        }
        else holder.mIcon.setVisibility(View.INVISIBLE);

        holder.mTitle.setText(readings.get(position).getTitle());
        holder.mDate.setText(readings.get(position).getDate());
        holder.mCaption.setText(readings.get(position).getEnglishTheme());

        String textColor = currentTheme.getTextColor();
        String textBgColor =  currentTheme.getTextBackgroundColor();
        String contextColor =  currentTheme.getContextColor();
        String contextBgColor =  currentTheme.getContextBackgroundColor();

        //Apply color
        new ReColor(mContext).setTextViewColor(holder.mTitle, contextColor, textColor,500);
        new ReColor(mContext).setTextViewColor(holder.mDate, contextColor, textColor,500);
        new ReColor(mContext).setTextViewColor(holder.mCaption, contextColor, textColor,500);
        new ReColor(mContext).setImageViewColorFilter(holder.mIcon, contextColor, textColor,500);

        //Apply on Click
        holder.mCard.setOnClickListener(clickListener(readings.get(position)));
    }



    @Override
    public int getItemCount() {
        return readings.size();
    }

    private View.OnClickListener clickListener(final Reading reading){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toReadingDisplay = new Intent(mContext,ReadingDisplay.class);
                toReadingDisplay.putExtra("reading",reading);
                mContext.startActivity(toReadingDisplay);
            }
        };
    }


}