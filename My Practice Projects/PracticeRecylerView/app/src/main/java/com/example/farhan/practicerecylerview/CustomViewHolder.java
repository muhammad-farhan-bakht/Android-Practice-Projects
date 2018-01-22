package com.example.farhan.practicerecylerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView1;
    TextView textView2;


    public CustomViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.mImage);
        textView1 = (TextView) itemView.findViewById(R.id.mTextView1);
        textView2 = (TextView) itemView.findViewById(R.id.mTextView2);

    }
}
