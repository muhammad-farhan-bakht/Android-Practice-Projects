package com.example.farhan.practicecrudwithfriebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String editKey;
    ArrayList<DataSource> dataSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtName = (TextView) findViewById(R.id.nameEditText);
        final TextView txtAge = (TextView) findViewById(R.id.ageEditText);
        final TextView txtEmail = (TextView) findViewById(R.id.emailEditText);
        final TextView txtPhone = (TextView) findViewById(R.id.phoneEditText);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Students_Part_4");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txtName.getText().toString();
                String age = txtAge.getText().toString();
                String email = txtEmail.getText().toString();
                String phone = txtPhone.getText().toString();

                DataSource dataSource = new DataSource(name, age, email, phone);

                if (editKey == null) {
                    String key = myRef.push().getKey();
                    dataSource.setKey(key);
                    myRef.child(key).setValue(dataSource);
                }else {
                    dataSource.setKey(editKey);
                    myRef.child(editKey).setValue(dataSource);
                    editKey = null;
                }

                txtName.setText("");
                txtAge.setText("");
                txtEmail.setText("");
                txtPhone.setText("");
            }
        });

        ListView listView = (ListView) findViewById(R.id.mListView);

        dataSources = new ArrayList<>();

        final CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(this, dataSources, new StudentItemListener() {
            @Override
            public void onData(DataSource dataSource) {
                myRef.child(dataSource.getKey()).removeValue();
            }
        }, new StudentItemListener() {
            @Override
            public void onData(DataSource dataSource) {
                txtName.setText(dataSource.getName());
                txtAge.setText(dataSource.getAge());
                txtEmail.setText(dataSource.getEmail());
                txtPhone.setText(dataSource.getPhone());
                editKey = dataSource.getKey();
            }
        }
        );

        listView.setAdapter(customArrayAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataSource dataSource = dataSnapshot.getValue(DataSource.class);
                dataSources.add(dataSource);
                customArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DataSource dataSource = dataSnapshot.getValue(DataSource.class);
                int index = dataSources.indexOf(dataSource);
                dataSources.set(index,dataSource);
                customArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DataSource dataSource = dataSnapshot.getValue(DataSource.class);
                dataSources.remove(dataSource);
                customArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
