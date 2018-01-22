package com.example.farhan.assignment_20_augest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class CustomListViewAdapter extends ArrayAdapter {

    ArrayList<DataSource> dataSources;

    public CustomListViewAdapter(@NonNull Context context, ArrayList<DataSource> dataSources) {
        super(context, 0, dataSources);
        this.dataSources = dataSources;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        if( convertView == null ){

            view = LayoutInflater.from(getContext()).inflate(R.layout.listview,parent,false);

        }else {
            view = convertView;
        }

        DataSource ds = dataSources.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.mImage);
        TextView textViewName = (TextView) view.findViewById(R.id.mTextViewName);
        TextView textViewDec = (TextView) view.findViewById(R.id.mTextViewDecription);

        imageView.setImageResource(ds.getImage());
        textViewName.setText(ds.getName());
        textViewDec.setText(ds.getdescription());


        return view;
    }
}
