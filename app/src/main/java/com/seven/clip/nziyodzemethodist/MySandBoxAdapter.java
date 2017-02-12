package com.seven.clip.nziyodzemethodist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bennysway on 02.02.17.
 */

public class MySandBoxAdapter extends RecyclerView.Adapter<MySandBoxAdapter.MyViewHolder> {
    private List<SandboxData> mainlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (Button) view.findViewById(R.id.sandboxText);

        }
    }


    public MySandBoxAdapter(List<SandboxData> list) {
        this.mainlist = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sandbox_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SandboxData movie = mainlist.get(position);
        holder.title.setText(movie.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.title.setText("Pressed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainlist.size();
    }
}