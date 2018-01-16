package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchEnglishFragment extends Fragment {


    public SearchEnglishFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static SearchEnglishFragment newInstance() {
        return new SearchEnglishFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_english, container, false);
        TextView noResults = (TextView) view.findViewById(R.id.noEnglishResultText);
        ListView ls = (ListView) view.findViewById(R.id.searchEnglishListView);

        final ArrayList<SearchResults> englishResults = ((Search)getActivity()).getEnglishResults();
        if(englishResults.size()==0){
            noResults.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        } else {
            ls.setAdapter(new MySearchEnglishListAdapter((getActivity()), englishResults));
            ls.setVisibility(View.VISIBLE);
            noResults.setVisibility(View.INVISIBLE);

            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent toHymn = new Intent(getContext(), HymnDisplay.class);
                    toHymn.putExtra("hymnNum",englishResults.get(i).getHymnNum());
                    toHymn.putExtra("isInEnglish",englishResults.get(i).getIsInEnglish());
                    startActivity(toHymn);
                }
            });
        }
        return view;
    }

}
