package com.example.farhan.practicerecylerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CustomRecylearView extends RecyclerView.Adapter<CustomViewHolder> {

    ArrayList<DataSource> dataSources;

     CustomRecylearView(ArrayList<DataSource> dataSources) {
        this.dataSources = dataSources;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customrecylerview, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        DataSource ds = dataSources.get(position);
        holder.imageView.setImageResource(ds.getImage());
        holder.textView1.setText(String.valueOf(ds.getId()));
        holder.textView2.setText(ds.getName());
    }

    @Override
    public int getItemCount() {
        return dataSources.size();
    }
}
