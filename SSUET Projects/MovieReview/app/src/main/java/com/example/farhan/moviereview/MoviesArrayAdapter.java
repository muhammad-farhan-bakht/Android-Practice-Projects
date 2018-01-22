package com.example.farhan.moviereview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Farhan on 12/9/2017.
 */

public class MoviesArrayAdapter extends ArrayAdapter<Movies> {

    private ArrayList<Movies> moviesArrayList;

    public MoviesArrayAdapter(@NonNull Context context, ArrayList<Movies> moviesArrayList) {
        super(context, 0, moviesArrayList);
        this.moviesArrayList = moviesArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.viewmovies_listview, parent, false);
        }
        CardView cardView = convertView.findViewById(R.id.viewMovieListCardView);
        ImageView imageView = convertView.findViewById(R.id.mImage);
        TextView textViewName = convertView.findViewById(R.id.mTextViewName);
        TextView textViewDec = convertView.findViewById(R.id.mTextViewDecription);
        TextView textViewShowRating = convertView.findViewById(R.id.showRatingInList);
        RatingBar rating = convertView.findViewById(R.id.rating);

        int customBlack = Color.parseColor("#262626");
        cardView.setCardBackgroundColor(customBlack);

        int customGoldActive = Color.parseColor("#EDB842");
        int customGoldBack = Color.parseColor("#fffde7");

        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(1).setColorFilter(customGoldActive, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(customGoldActive, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(customGoldBack, PorterDuff.Mode.SRC_ATOP);

        Movies movieObj = moviesArrayList.get(position);

        Glide.with(getContext()).load(movieObj.getImage()).into(imageView);
        textViewName.setText(movieObj.getName());
        textViewDec.setText(movieObj.getDescription());
        rating.setRating(movieObj.getRating());
        textViewShowRating.setText(String.valueOf(movieObj.getRating() * 2) + "/10");

        return convertView;

    }

}
