package com.example.farhan.pizzatask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.ArrayList;
import java.util.List;

public class ViewAllData extends AppCompatActivity {

    ArrayList<DataSource> dataSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_data);

        dataSources = new ArrayList<>();

        ListView myListView = (ListView) findViewById(R.id.myList);
        final CustomListViewAdapter listAdapter = new CustomListViewAdapter(this, dataSources);
        myListView.setAdapter(listAdapter);

        // Getting all data from Class and stores in LIst to show.
        List<DataSource> contactsDb = DataSource.listAll(DataSource.class);
        dataSources.clear();
        dataSources.addAll(contactsDb);

        //Destroy SugarContext Class and Re-Made it with new DataBase and new Table.
        Button btnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SugarContext.terminate();
                SchemaGenerator schemaGenerator = new SchemaGenerator(ViewAllData.this);
                schemaGenerator.deleteTables(new SugarDb(ViewAllData.this).getDB());
                SugarContext.init(ViewAllData.this);
                schemaGenerator.createDatabase(new SugarDb(ViewAllData.this).getDB());

                dataSources.clear();
                listAdapter.notifyDataSetChanged();

            }
        });
    }
}
