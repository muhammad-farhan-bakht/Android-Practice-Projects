package com.example.farhan.assignment_20_augest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeFragment extends Fragment {

    ArrayList<DataSource> dataSources;

    public AnimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentlayout, container, false);

        ListView listView = (ListView) v.findViewById(R.id.list);

        dataSources = new ArrayList<>();
        dataSources.add(new DataSource(R.drawable.gto, "Great Teacher Onizuka", "Great Teacher Onizuka officially abbreviated as GTO, The story focuses on 22-year-old ex-bōsōzoku member Eikichi Onizuka, who becomes a teacher at a private middle school, Holy Forest Academy, in Tokyo, Japan.", "8.75", "PG-18+, Comedy, Drama, School, Shounen, Slice of Life."));
        dataSources.add(new DataSource(R.drawable.boruto, "Boruto", "Several years after the end of the Shinobi War, Naruto son Boruto is about to enter the Chûnin exams alongside Sarada Uchiha and the mysterious Mitsuki.", "7.40", "PG-15+, Action, Adventure, Super Power, Martial Arts, Shounen"));
        dataSources.add(new DataSource(R.drawable.dragonballsuper, "Dragon Ball Super", "ragon Ball Super is an ongoing Japanese anime television series produced by Toei Animation that began airing on July 5, 2015. ... Dragon Ball Super follows the adventures of the protagonist Goku after defeating Majin Buu and bringing peace to Earth once again.", "7.45", "PG-15+, Action, Adventure, Comedy, Super Power, Martial Arts, Fantasy, Shounen"));
        dataSources.add(new DataSource(R.drawable.onepunchman, "One Punch Man", "One-Punch Man tells the story of Saitama, an extremely overpowered superhero, who has grown bored by the absence of challenge in his fight against evil and seeks to find a worthy opponent.", "8.76", "PG-18+, Action, Sci-Fi, Comedy, Parody, Super Power, Supernatural, Seinen"));
        dataSources.add(new DataSource(R.drawable.swordartonline, "Sword Art Online", "Plot. In 2022, a Virtual Reality Massively Multiplayer Online Role-Playing Game (VRMMORPG) called Sword Art Online (SAO) is released. With the NerveGear, a helmet that stimulates the users five senses via their brain, players can experience and control their in-game characters with their minds.", "7.72", "PG-14+, Action, Adventure, Fantasy, Game, Romance"));

        CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(getContext(), dataSources);
        listView.setAdapter(customListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                int mImage = dataSources.get(position).getImage();
                String mTextViewName = dataSources.get(position).getName();
                String mTextViewDec = dataSources.get(position).getdescription();
                String mTextViewRating = dataSources.get(position).getRating();
                String mTextViewGenre = dataSources.get(position).getGenre();

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("mImage", mImage);
                intent.putExtra("mTextViewName", mTextViewName);
                intent.putExtra("mTextViewDec", mTextViewDec);
                intent.putExtra("mTextViewRating", mTextViewRating);
                intent.putExtra("mTextViewGenre", mTextViewGenre);

                startActivity(intent);
            }
        });

        return v;
    }

}
