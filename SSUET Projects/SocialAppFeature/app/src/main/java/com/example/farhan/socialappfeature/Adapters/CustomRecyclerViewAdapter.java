package com.example.farhan.socialappfeature.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farhan.socialappfeature.Models.Post;
import com.example.farhan.socialappfeature.R;

import java.util.ArrayList;

/**
 * Created by Farhan on 2/28/2018.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.RvCustomViewHolder> {

    private ArrayList<Post> postArrayList;
    private Context context;
    private customButtonListener customCommentListener;
    private customButtonListener customLikeListener;

    // To Cr8 Custom OnClickListener on RCView
    public interface customButtonListener {
        void onCommentButtonClickListener(int position);
        void onLikeButtonClickListener(int position);
    }

    public void setCustomCommentButtonListener(customButtonListener listener) {
        this.customCommentListener = listener;
    }

    public void setCustomLikeButtonListener(customButtonListener customLikeListener) {
        this.customLikeListener = customLikeListener;
    }


    public class RvCustomViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView postText;
        ImageView postImage;
        ImageButton btnLike;
        ImageButton btnComment;

        public RvCustomViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.postUserName);
            postText = itemView.findViewById(R.id.postText);
            postImage = itemView.findViewById(R.id.postImage);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnComment = itemView.findViewById(R.id.btnComment);
        }
    }

    public CustomRecyclerViewAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @Override
    public RvCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_item_view, parent, false);
        return new RvCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RvCustomViewHolder holder, final int position) {

        Post postObj = postArrayList.get(position);
        holder.userName.setText(postObj.getUserName());
        holder.postText.setText(postObj.getPostText());
        Glide.with(context).load(postObj.getPostImgURL()).into(holder.postImage);

        if (postObj.isCheckLike().equals("t")) {
            holder.btnLike.setBackground(context.getResources().getDrawable(R.drawable.is_like_true));
        } else {
            holder.btnLike.setBackground(context.getResources().getDrawable(R.drawable.is_like_false));
        }

        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customCommentListener != null) {
                    customCommentListener.onCommentButtonClickListener(position);
                }
            }
        });

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customLikeListener != null) {
                    customLikeListener.onLikeButtonClickListener(position);
                    holder.btnLike.setImageResource(R.drawable.is_like_true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

}
