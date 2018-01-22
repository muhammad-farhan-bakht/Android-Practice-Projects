package com.example.farhan.pizzatask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchStudentData extends AppCompatActivity {

    EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student_data);

        editTextSearch = (EditText) findViewById(R.id.etSearch);

        Button buttonSearch = (Button) findViewById(R.id.btnSearch);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        final ArrayList<DataSource> dataSources = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);

        final CustomRecyclerView customRecyclerView = new CustomRecyclerView(dataSources, SearchStudentData.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customRecyclerView);

        //Search From table where name the value we put in EditText...
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = " SELECT * FROM DATA_SOURCE WHERE name LIKE '%" + editTextSearch.getText().toString() + "%' ";

                // Get text from editText and search that text in table if founded then stores in List.
                try {
                    List<DataSource> contactsDb = DataSource.findWithQuery(DataSource.class, query);
                    dataSources.clear();

                    // Getting all text from List and add in that ArrayList which we pass in our Adapter.
                    if (!contactsDb.isEmpty()) {
                        for (int i = 0; i < contactsDb.size(); i++) {
                            dataSources.add(contactsDb.get(i));
                        }
                        customRecyclerView.notifyDataSetChanged();
                    } else {
                        Toast.makeText(SearchStudentData.this, "Name Not Found", Toast.LENGTH_SHORT).show();
                    }
                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(SearchStudentData.this, "Name Not Found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}