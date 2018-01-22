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
public class MoviesFragment extends Fragment {

    ArrayList<DataSource> dataSources;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragmentlayout, container, false);
        dataSources = new ArrayList<>();
        dataSources.add(new DataSource(R.drawable.resident, "Resident Evil Vendetta", "After a long time since last zombie apocalypse there is again some one trying to spread the virus for revenge, so now again Sergent Clark the specialist of this kinds of case need the help of Leon an freelance Agent to stop this chaos", "7.29", "PG-18+, Action, Sci-Fi, Horror, Shooting, Rich Story."));
        dataSources.add(new DataSource(R.drawable.spiritedaway, "Spirited Away", "Spirited Away is produced by most popular studio gibli its a story of a girl who's life gone change after she step in a spirit realm while her parents kept in prison as a punishment for eating food of spirits with asking them, so its upto girl to work in that spirit inn and help their parents which change her life forever.", "8.93", "PG-7+, Adventure, Supernatural, Drama, Lesson, Slice of Life."));
        dataSources.add(new DataSource(R.drawable.kminonawa, "Your Name", "Your Name (Kimi no na wa) is story of a high school boy and girl who bind with a fate of stars to avoid certain tragedy and fall in love to each other as they start to shift their souls in each other body.", "9.26", "PG-15+, Supernatural, Drama, Romance, School, Slice of Life."));
        dataSources.add(new DataSource(R.drawable.koenokatachi, "A Silent Voice", "As a wild youth, elementary school student Shouya Ishida sought to beat boredom in the cruelest ways. When the deaf Shouko Nishimiya transfers into his class, Shouya and the rest of his class thoughtlessly bully her for fun. However, when her mother notifies the school, he is singled out and blamed for everything done to her.Shouya is left at the mercy of his classmates.", "9.08", "PG-15+, Drama, School, Slice of Life, Shounen."));
        dataSources.add(new DataSource(R.drawable.wordsofgarden, "Words Of Garden", "Words of garden is short film of a high school boy and a teacher they both are found of nature and one day they meet each other then after some other meetings the boy knows the truth about teacher story and try to help her", "8.31", "Slice of Life, Psychological, Drama, Romance, Shounen."));

        CustomListViewAdapter customListViewAdapter = new CustomListViewAdapter(getContext(), dataSources);

        ListView listView = (ListView) v.findViewById(R.id.list);

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
