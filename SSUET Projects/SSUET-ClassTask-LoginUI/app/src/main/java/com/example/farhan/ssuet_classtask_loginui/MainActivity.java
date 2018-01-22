package com.example.farhan.ssuet_classtask_loginui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String userEmail = "abc@xyz.com";
    String userPassword = "user123";

    public void login(View view) {
        EditText etLogin = (EditText) findViewById(R.id.editTextLogin);
        EditText etSignUp = (EditText) findViewById(R.id.editTextSignUp);


        String login = etLogin.getText().toString();
        String SignUp = etSignUp.getText().toString();

        if (login.equals("")) {
            etLogin.setHintTextColor(getResources().getColor(R.color.textHintEmpty));
        } else {
            etLogin.setHintTextColor(getResources().getColor(R.color.textHint));
        }

        if (SignUp.equals("")) {
            etSignUp.setHintTextColor(getResources().getColor(R.color.textHintEmpty));
        } else {
            etSignUp.setHintTextColor(getResources().getColor(R.color.textHint));
        }

        if (login.equals(userEmail) || SignUp.equals(userPassword)) {
            Toast.makeText(this, "You have been logged in Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "The username or password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void signUp(View v) {
        Intent navToSignUp = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(navToSignUp);
    }
}
