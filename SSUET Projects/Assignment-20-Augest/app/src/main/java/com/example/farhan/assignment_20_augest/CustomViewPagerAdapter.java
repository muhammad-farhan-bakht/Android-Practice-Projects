package com.example.farhan.assignment_20_augest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class CustomViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments;
    private String tabTitles[] = new String[] { "Games", "Anime", "Movies" };

    public CustomViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {

        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
