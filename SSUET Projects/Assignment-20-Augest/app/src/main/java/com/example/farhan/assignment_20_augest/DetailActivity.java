package com.example.farhan.assignment_20_augest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.farhan.assignment_20_augest.R.id.mTextViewName;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = (ImageView) findViewById(R.id.mImage);
        TextView textViewName = (TextView) findViewById(mTextViewName);
        TextView textViewDec = (TextView) findViewById(R.id.mTextViewDecription);
        TextView textViewRating = (TextView) findViewById(R.id.mTextViewRating);
        TextView textViewGenre = (TextView) findViewById(R.id.mTextViewGenre);

        Intent i = getIntent();

        int mImage = i.getIntExtra("mImage",0);
        String mTextviewName = i.getStringExtra("mTextViewName");
        String mTextViewDec = i.getStringExtra("mTextViewDec");
        String mTextViewRating = i.getStringExtra("mTextViewRating");
        String mTextViewGenre = i.getStringExtra("mTextViewGenre");

        imageView.setImageResource(mImage);
        textViewName.setText(mTextviewName);
        textViewDec.setText(mTextViewDec);
        textViewRating.append(mTextViewRating);
        textViewGenre.setText(mTextViewGenre);

    }

}
