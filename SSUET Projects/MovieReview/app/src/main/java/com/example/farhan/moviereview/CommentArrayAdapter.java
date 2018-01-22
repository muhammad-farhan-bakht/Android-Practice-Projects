package com.example.farhan.moviereview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Farhan on 12/20/2017.
 */

public class CommentArrayAdapter extends ArrayAdapter<Comment> {

    private ArrayList<Comment> commentArrayList;

    public CommentArrayAdapter(@NonNull Context context, ArrayList<Comment> commentArrayList) {
        super(context, 0, commentArrayList);
        this.commentArrayList = commentArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_listview, parent, false);
        }

        CardView cardView = convertView.findViewById(R.id.rootCardView);
        TextView userName = convertView.findViewById(R.id.txtUserName);
        TextView txtComment = convertView.findViewById(R.id.txtComment);
        TextView txtShowDate = convertView.findViewById(R.id.txtShowDate);

        int customBlack = Color.parseColor("#212121");
        cardView.setCardBackgroundColor(customBlack);

        Comment comment = commentArrayList.get(position);

        userName.setText(comment.getUserName());
        txtComment.setText(comment.getComment());
        txtShowDate.setText(comment.getDate());

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
