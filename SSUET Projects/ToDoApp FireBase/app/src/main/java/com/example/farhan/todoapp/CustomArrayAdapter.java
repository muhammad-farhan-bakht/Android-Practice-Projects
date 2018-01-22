package com.example.farhan.todoapp;

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
 * Created by Farhan on 10/30/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter<DataModel> {

    private ArrayList<DataModel> dataModels;
    private TaskListListener editListener;
    private TaskListListener delListener;

    public CustomArrayAdapter(@NonNull Context context, ArrayList<DataModel> dataModels, TaskListListener editListener, TaskListListener delListener) {
        super(context, 0, dataModels);
        this.dataModels = dataModels;
        this.editListener = editListener;
        this.delListener = delListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.tvTask);
        Button btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);


        final DataModel dm = dataModels.get(position);

        textView.setText(dm.getTask());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editListener.onData(dm);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delListener.onData(dm);
            }
        });

        return convertView;
    }
}
