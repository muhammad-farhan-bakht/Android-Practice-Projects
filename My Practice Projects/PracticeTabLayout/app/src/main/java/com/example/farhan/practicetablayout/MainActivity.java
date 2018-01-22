package com.example.farhan.practicetablayout;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.mViewPager);
        ArrayList<BaseFragment> fragments = new ArrayList<>();

        BaseFragment fragmentOne = new MyFragment();
        fragmentOne.setTitle("Tab 1");
        fragments.add(fragmentOne);

        BaseFragment fragmentTwo = new MyFragment();
        fragmentTwo.setTitle("Tab 2");
        fragments.add(fragmentTwo);

        BaseFragment fragmentThree = new MyFragment();
        fragmentThree.setTitle("Tab 3");
        fragments.add(fragmentThree);

        BaseFragment fragmentFour = new MyFragment();
        fragmentFour.setTitle("Tab 4");
        fragments.add(fragmentFour);

        BaseFragment fragmentFive = new MyFragment();
        fragmentFive.setTitle("Tab 5");
        fragments.add(fragmentFive);

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(customPagerAdapter);

        
    }
}
