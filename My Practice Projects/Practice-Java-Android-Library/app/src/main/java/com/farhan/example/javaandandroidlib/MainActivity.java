package com.farhan.example.javaandandroidlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.farhan.example.androidjokeslibrary.AndroidLibraryMainActivity;
import com.udacity.gradle.jokes.Joker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewJava = findViewById(R.id.tv_from_java);
        Joker myJoker = new Joker();
        textViewJava.setText(myJoker.getJoke());

        TextView textViewAndroid = findViewById(R.id.tv_from_android);
        textViewAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AndroidLibraryMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
