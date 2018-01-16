package com.seven.clip.nziyodzemethodist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bennysway on 09.01.18.
 */

public class NotesCaptionListFragment extends android.support.v4.app.Fragment {
    @Override
    public Context getContext() {
        return super.getContext();
    }

    public NotesCaptionListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static NotesCaptionListFragment newInstance() { return new NotesCaptionListFragment(); }

    RecyclerView mRecyclerView;
    MyNotesCaptionsListRVAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_captions_list, container, false);
        TextView noNotesCaption = view.findViewById(R.id.noNotesCaptionsTextView);
        //Recycler View Handling
        mRecyclerView = view.findViewById(R.id.notesCaptionsRecyclerView);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyNotesCaptionsListRVAdapter(((Captions)getActivity()).getNotes(),getContext());
        mRecyclerView.setAdapter(mAdapter);

        if (((Captions)getActivity()).getNotes().isEmpty()) {
            noNotesCaption.animate().scaleY(2f).scaleX(2f).setDuration(100000);
        } else {
            noNotesCaption.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
