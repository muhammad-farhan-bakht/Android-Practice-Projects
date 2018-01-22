package com.example.farhan.ssuet_classtask_loginui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.farhan.ssuet_classtask_loginui.R.id.editTextE;
import static com.example.farhan.ssuet_classtask_loginui.R.id.editTextName;
import static com.example.farhan.ssuet_classtask_loginui.R.id.editTextPass;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    public void createAccount(View v) {

        EditText etName = (EditText) findViewById(editTextName);
        EditText etEmail = (EditText) findViewById(editTextE);
        EditText etPass = (EditText) findViewById(editTextPass);


        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {

            if (name.equals("")) {
                etName.setHintTextColor(getResources().getColor(R.color.textHintEmpty));
            } else {
                etName.setHintTextColor(getResources().getColor(R.color.textHint));
            }

            if (email.equals("")) {
                etEmail.setHintTextColor(getResources().getColor(R.color.textHintEmpty));
            } else {
                etEmail.setHintTextColor(getResources().getColor(R.color.textHint));
            }

            if (password.equals("")) {
                etPass.setHintTextColor(getResources().getColor(R.color.textHintEmpty));
            } else {
                etPass.setHintTextColor(getResources().getColor(R.color.textHint));
            }

            Toast.makeText(this, "Please Complete All Fields", Toast.LENGTH_SHORT).show();

            /*Intent intent = new Intent(Main2Activity.this, MainActivity.class);
            intent.putExtra("email", editTextE);
            intent.putExtra("password", editTextPass);
            startActivity(intent);*/
        }else {
            Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToLogin(View v) {
        Intent navToLogin = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(navToLogin);
    }
}
