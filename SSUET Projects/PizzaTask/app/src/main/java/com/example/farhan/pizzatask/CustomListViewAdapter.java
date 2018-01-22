package com.example.farhan.pizzatask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Farhan on 9/27/2017.
 */

public class CustomListViewAdapter extends ArrayAdapter<DataSource> {
    ArrayList<DataSource> dataSources;
    Context context;

    public CustomListViewAdapter(@NonNull Context context, ArrayList<DataSource> dataSources) {
        super(context, 0, dataSources);
        this.dataSources = dataSources;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_list, parent, false);
        }

        DataSource ds = dataSources.get(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.myImage);
        TextView textId = (TextView) convertView.findViewById(R.id.textId);
        TextView textName = (TextView) convertView.findViewById(R.id.textName);
        TextView textEmail = (TextView) convertView.findViewById(R.id.textMail);
        TextView textPhone = (TextView) convertView.findViewById(R.id.textNo);


        Bitmap bitmap = BitmapFactory.decodeByteArray(ds.getbArray(), 0, ds.getbArray().length);
        imageView.setImageBitmap(bitmap);

        textId.setText("Id: " + ds.getId());
        textName.setText("Name: " + ds.getName());
        textEmail.setText("Email: " + ds.getEmail());
        textPhone.setText("Phone No: " + ds.getPhone());

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
