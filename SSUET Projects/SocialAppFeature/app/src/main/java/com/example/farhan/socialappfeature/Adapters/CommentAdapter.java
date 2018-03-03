package com.example.farhan.socialappfeature.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.farhan.socialappfeature.Models.Comment;
import com.example.farhan.socialappfeature.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 3/1/2018.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {

    private ArrayList<Comment> commentArrayList;

    public CommentAdapter(@NonNull Context context, ArrayList<Comment> commentArrayList) {
        super(context, 0, commentArrayList);
        this.commentArrayList = commentArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item_view, parent, false);
        }

        TextView userName = convertView.findViewById(R.id.txtUserName);
        TextView txtComment = convertView.findViewById(R.id.txtComment);
        TextView txtShowDate = convertView.findViewById(R.id.txtShowDate);

        Comment comment = commentArrayList.get(position);

        userName.setText(comment.getUserName());
        txtComment.setText(comment.getComment());
        txtShowDate.setText(comment.getDate());

        return convertView;
    }

}
