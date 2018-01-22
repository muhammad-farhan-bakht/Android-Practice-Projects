package com.example.farhan.practicerecylerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataSource> dataSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSources = new ArrayList<>();
        dataSources.add(new DataSource(0,"Goku",R.drawable.goku));
        dataSources.add(new DataSource(1,"Vegeta",R.drawable.vegeta));
        dataSources.add(new DataSource(2,"Naruto",R.drawable.naruto));
        dataSources.add(new DataSource(3,"Sasuke",R.drawable.sasuke));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecyleView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);

        CustomRecylearView customRecylearView = new CustomRecylearView(dataSources);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customRecylearView);

    }
}
