package com.seven.clip.nziyodzemethodist.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.CaptionList;
import com.seven.clip.nziyodzemethodist.FavList;
import com.seven.clip.nziyodzemethodist.HymnList;
import com.seven.clip.nziyodzemethodist.PickNumDialog;
import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.RecList;


public class HomeTab extends android.support.v4.app.Fragment {
    @Override
    public Context getContext() {
        return super.getContext();
    }
    public HomeTab(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static HomeTab newInstance() { return new HomeTab(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        final Intent toHymnList = new Intent(getContext(), HymnList.class);
        final Intent toFavList = new Intent(getContext(), FavList.class);
        final Intent toRecList = new Intent(getContext(), RecList.class);
        final Intent toCaptionList = new Intent(getContext(),CaptionList.class);

        LinearLayout listLayout = view.findViewById(R.id.top_list);
        LayoutInflater linearInflater = LayoutInflater.from(getContext());
        String[] labels = getResources().getStringArray(R.array.home_labels);
        TypedArray icons = getResources().obtainTypedArray(R.array.home_icons);
        for (int i=0; i<5; i++){
            View menuItem = linearInflater.inflate(R.layout.list_item_main_item,listLayout,false);
            menuItem.setId(i);
            ImageView icon = menuItem.findViewById(R.id.main_list_item_icon);
            TextView itemName = menuItem.findViewById(R.id.main_list_item_text);
            icon.setImageResource(icons.getResourceId(i,-1));
            itemName.setText(labels[i]);
            switch (i){
                case 0:
                    menuItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PickNumDialog pickNumDialogue = new PickNumDialog(getContext());
                            pickNumDialogue.getWindow().getAttributes().windowAnimations = R.style.TransparentDialogAnimation;
                            pickNumDialogue.show();
                        }
                    });
                    break;
                case 1:
                    menuItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(toHymnList);
                        }
                    });
                    break;
                case 2:
                    menuItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(toFavList);
                        }
                    });
                    break;
                case 3:
                    menuItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(toRecList);
                        }
                    });
                    break;
                case 4:
                    menuItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(toCaptionList);
                        }
                    });
                    break;
                default:
                    menuItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //QuickToast("And so on...");
                        }
                    });
                    break;
            }
            //
            listLayout.addView(menuItem);
        }
        //Todo : Goto readings

        return view;
    }
}
