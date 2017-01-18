package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


class MyNumAdapter extends ArrayAdapter {
    MyNumAdapter(Context context, String[] values) {
        super(context,R.layout.num_list ,values);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater theInflater = (LayoutInflater.from(getContext()));
        View theView = theInflater.inflate(R.layout.num_list,parent,false);
        String hymnEntry = (String) getItem(position);

        TextView theTextView = (TextView) theView.findViewById(R.id.hymnbutton);
        theTextView.setText(hymnEntry);
        Data favList = new Data(MyNumAdapter.super.getContext(),"favlist");
        String list = favList.get();
        String[] favs = list.split(",");
        int counter=0;

        for( int i=0; i<list.length(); i++ ) {
            if (list.charAt(i) == ',') {
                counter++;
            }
        }
        for(int i=0;i<counter;i++){
            if(Integer.parseInt(favs[i])==position+1){
                theTextView.setBackgroundResource(R.drawable.grad_but_is_fav);
                theTextView.setShadowLayer(10.0f,0.0f,0.0f, Color.WHITE);
            }
        }






        return theView;
    }

    }
