package com.example.demoreadfromrealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private TextView showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_get_all).setOnClickListener(view -> readAll());

        showData = findViewById(R.id.tv_show_data);
        realm = Realm.getDefaultInstance();
    }

    private void readAll() {
        RealmResults<Student> students = realm.where(Student.class).findAll();
        showData.setText("");
        StringBuilder data = new StringBuilder();

        for (Student studentObj : students) {
            try {

                data.append("\n").append("\n").append(studentObj.toString());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        showData.setText(data);
    }
}
