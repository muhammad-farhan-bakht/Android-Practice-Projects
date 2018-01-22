package com.example.farhan.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    String editKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.etTask);
        final Button btnAdd = (Button) findViewById(R.id.btnAdd);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference("ToDo");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataModel dataModel = new DataModel(editText.getText().toString());

                if (editKey == null) {
                    String key = myRef.push().getKey();
                    dataModel.setKey(key);
                    myRef.child(key).setValue(dataModel);
                } else {
                    dataModel.setKey(editKey);
                    myRef.child(editKey).setValue(dataModel);
                    editKey = null;
                }
                editText.setText("");
            }
        });

        ListView listView = (ListView) findViewById(R.id.showList);

        dataModels = new ArrayList<>();

        final CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(this, dataModels, new TaskListListener() {
            @Override
            public void onData(DataModel dataModel) {
                editText.setText(dataModel.getTask());
                editKey = dataModel.getKey();

            }
        }, new TaskListListener() {
            @Override
            public void onData(DataModel dataModel) {
                myRef.child(dataModel.getKey()).removeValue();
            }
        });

        listView.setAdapter(customArrayAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DataModel dataModel = dataSnapshot.getValue(DataModel.class);
                dataModels.add(dataModel);
                customArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DataModel dataModel = dataSnapshot.getValue(DataModel.class);
                int index = dataModels.indexOf(dataModel);
                dataModels.set(index, dataModel);
                customArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DataModel dataModel = dataSnapshot.getValue(DataModel.class);
                dataModels.remove(dataModel);
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
