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
public class GamesFragment extends Fragment {

    ArrayList<DataSource> dataSources;

    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragmentlayout, container, false);

        dataSources = new ArrayList<>();
        dataSources.add(new DataSource(R.drawable.witcher, "Witcher 3", "The Witcher 3: Wild Hunt is a 2015 action role-playing video game developed by CD Projekt RED and published by CD Projekt The games central story features multiple endings that are determined by Geralts choices made by the player during certain points of the story", " 9.9", "PG-18+, RPG, Blood Gore, violence, Mature, Monsters, Nudity, Magic, Dark Fantasy."));
        dataSources.add(new DataSource(R.drawable.assasincreeds, "Assassin Creed Origins", "Ancient Egypt, a land of majesty and intrigue, is disappearing in a ruthless fight for power. Unveil dark secrets and forgotten myths as you go back to the one founding moment: The Origins of the Assassin’s Brotherhood.", " 9.5", "PG-18+, Action, Open World, History, Stealth, Mature, Co-Op, Multiplier."));
        dataSources.add(new DataSource(R.drawable.naruto, "Naruto Ultimate Ninja Strom 4", "The latest opus in the acclaimed STORM series is taking you on a colourful and breathtaking ride. Take advantage of the totally revamped battle system and prepare to dive into the most epic fights you’ve ever seen in the NARUTO SHIPPUDEN: Ultimate Ninja STORM series!", " 9.2", "PG-15+, Action, Anime, Fighting, Multiplier, Co-Op, Open World, Ninja."));
        dataSources.add(new DataSource(R.drawable.hitman, "Hitman", "Become the master assassin in an intense spy-thriller story across a world of assassination.Travel the world from France, Italy , Morocco to Thailand, USA , Japan to take out powerful, high-profile targets.", " 9.0", "PG-18+, Stealth, Assassin, Tactical, Open World, Crime, Strategy, Shooter."));
        dataSources.add(new DataSource(R.drawable.darksouls, "Dark Souls 3", "As fires fade and the world falls into ruin, journey into a universe filled with more colossal enemies and environments. Players will be immersed into a world of epic atmosphere and darkness through faster gameplay and amplified combat intensity.", " 8.7", "PG-18+, Dark Fantasy, RPG, Story Rich, Open World, Multiplier, Horror, Advanture."));

        CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(getContext(), dataSources);

        final ListView listView = (ListView) v.findViewById(R.id.list);

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
