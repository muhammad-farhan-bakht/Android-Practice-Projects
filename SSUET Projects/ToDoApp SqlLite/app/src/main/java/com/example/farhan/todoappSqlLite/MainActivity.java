package com.example.farhan.todoappSqlLite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ArrayList<DataSource> dataSources;
    CustomArrayAdapter customArrayAdapter;
    private List<DataSource> contactsDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSources = new ArrayList<>();

        editText = (EditText) findViewById(R.id.etSearch);
        Button btnInsert = (Button) findViewById(R.id.btnInsert);

        ListView listView = (ListView) findViewById(R.id.mListView);

        customArrayAdapter = new CustomArrayAdapter(this, dataSources, editText);
        listView.setAdapter(customArrayAdapter);

        doAddDataInArrayList();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String etFieldData = editText.getText().toString();

                if(!etFieldData.isEmpty()){

                    DataSource ds = new DataSource(etFieldData);
                    ds.save();

                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                    doAddDataInArrayList();
                    customArrayAdapter.notifyDataSetChanged();
                }else {
                    editText.setError("This Field Can't Be Empty");
                }

            }
        });

    }

    // Getting all data from Class and stores in LIst to show.
    private void doAddDataInArrayList() {
        contactsDb = DataSource.listAll(DataSource.class);
        dataSources.clear();
        dataSources.addAll(contactsDb);

    }

}
