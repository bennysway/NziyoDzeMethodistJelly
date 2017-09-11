package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


class MyReadingListAdapter extends BaseAdapter {
    private static ArrayList<Reading> readings;
    private Context mContext;

    private LayoutInflater mInflater;

    MyReadingListAdapter(Context context, ArrayList<Reading> results) {
        readings = results;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return readings.size();
    }

    public Object getItem(int position) {
        return readings.get(position).getReading_id();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.reading_list_view_layout,null);
            holder = new ViewHolder();
            holder.mTitle = convertView.findViewById(R.id.readingListTitle);
            holder.mDate = convertView.findViewById(R.id.readingListDate);
            holder.mCaption = convertView
                    .findViewById(R.id.readingListCaption);
            holder.mIcon = convertView.findViewById(R.id.weekPassedIndicator);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Date extraction
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
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


        DateFormat outputFormatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String s_date = outputFormatter.format(date);
        s_today = outputFormatter.format(today);


        if(date.before(today)){
            holder.mIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_date_passed_icon));
            holder.mIcon.setVisibility(View.VISIBLE);
            if(s_date.equals(s_today))
                holder.mIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_date_icon));
        }
        else
            holder.mIcon.setVisibility(View.INVISIBLE);



        holder.mTitle.setText(readings.get(position).getTitle());
        holder.mDate.setText(readings.get(position).getDatename());
        holder.mCaption.setText(readings.get(position).getEnglish_theme());

        return convertView;
    }

    private static class ViewHolder {
        TextView mTitle;
        TextView mDate;
        TextView mCaption;
        ImageView mIcon;
    }
}