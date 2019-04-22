package com.seven.clip.nziyodzemethodist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.seven.clip.nziyodzemethodist.models.ChurchEvent;

import java.util.ArrayList;

public class EventListAdapter extends BaseAdapter {
    private static ArrayList<ChurchEvent> events;
    private Context mContext;

    private LayoutInflater mInflater;

    EventListAdapter(Context context, ArrayList<ChurchEvent> mEvents) {
        events = mEvents;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
