package com.example.farhan.assignment_20_augest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.mViewPager);

        fragments = new ArrayList<>();
        fragments.add(new GamesFragment());
        fragments.add(new AnimeFragment());
        fragments.add(new MoviesFragment());

        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(customViewPagerAdapter);



    }
}
