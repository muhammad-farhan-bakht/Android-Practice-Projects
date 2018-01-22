package com.example.farhan.practicecrudwithfriebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Farhan on 10/24/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter<DataSource> {

    private ArrayList<DataSource> dataSources;
    private StudentItemListener delListener;
    private StudentItemListener editListener;


    public CustomArrayAdapter(@NonNull Context context, ArrayList<DataSource> dataSources, StudentItemListener delListener, StudentItemListener editListener ) {
        super(context,0,dataSources);
        this.dataSources = dataSources;
        this.delListener = delListener;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view,parent,false);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtAge = (TextView) convertView.findViewById(R.id.txtAge);
        TextView txtEmail = (TextView) convertView.findViewById(R.id.txtEmail);
        TextView txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        Button btnSelect = (Button) convertView.findViewById(R.id.btnSelect);

        final DataSource ds = dataSources.get(position);

        txtName.setText("Name: "+ds.getName());
        txtAge.setText("Age: "+ds.getAge());
        txtEmail.setText("Email: "+ds.getEmail());
        txtPhone.setText("Phone: "+ds.getPhone());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delListener.onData(ds);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editListener.onData(ds);
            }
        });

        return convertView;
    }
}
