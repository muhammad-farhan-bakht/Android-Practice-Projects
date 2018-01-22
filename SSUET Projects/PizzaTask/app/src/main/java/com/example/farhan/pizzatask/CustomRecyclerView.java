package com.example.farhan.pizzatask;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Farhan on 9/27/2017.
 */


public class CustomRecyclerView extends RecyclerView.Adapter<CustomViewHolder> {

    private ArrayList<DataSource> dataSources;
    Context context;


    CustomRecyclerView(ArrayList<DataSource> dataSources, Context context) {
        this.dataSources = dataSources;
        this.context = context;
    }

    public CustomRecyclerView() {
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final DataSource ds = dataSources.get(position);

        Bitmap bitmap = BitmapFactory.decodeByteArray(ds.getbArray(), 0, ds.getbArray().length);
        holder.imageView.setImageBitmap(bitmap);
        holder.textName.setText(ds.getName());
        holder.textEmail.setText(ds.getEmail());
        holder.textPhone.setText(ds.getPhone());


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title);
                // Add the buttons
                builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        DataSource.deleteInTx(DataSource.class, dataSources.get(position));
                        dataSources.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();

                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSources.size();
    }
}
