package com.example.farhan.pizzatask;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Farhan on 9/27/2017.
 */

public class CustomViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textName;
    TextView textEmail;
    TextView textPhone;
    Button btnDelete;

    public CustomViewHolder(View itemView ) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.myImage);
        textName = (TextView) itemView.findViewById(R.id.textName);
        textEmail = (TextView) itemView.findViewById(R.id.textMail);
        textPhone = (TextView) itemView.findViewById(R.id.textNo);
        btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

    }
}
