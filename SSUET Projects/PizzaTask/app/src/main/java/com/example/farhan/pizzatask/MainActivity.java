package com.example.farhan.pizzatask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button) findViewById(R.id.bntAdd);
        Button btnView = (Button) findViewById(R.id.btnView);
        Button btnViewAll = (Button) findViewById(R.id.btnViewAll);


        //Button onClickListener to call AddStudentData Activity...
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddStudentData.class);
                startActivity(intent);
            }
        });

        //Button onClickListener to call SearchStudentData Activity...
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchStudentData.class);
                startActivity(intent);
            }
        });

        //Button onClickListener to call ViewAllData Activity...
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ViewAllData.class);
                startActivity(intent);
            }
        });

    }

}
