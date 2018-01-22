package com.example.farhan.moviereview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Logging please wait");

        final EditText etEmail = findViewById(R.id.etEmailSignIn);
        final EditText etPass = findViewById(R.id.etLoginPass);
        TextView btnSignUp = findViewById(R.id.SignUp);
        Button btnSignIn = findViewById(R.id.btnSignIn);

        mAuth = FirebaseAuth.getInstance();

        // Checking if user is Sign-in or not
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, ViewMovies.class));
        }

        // Open's Sign-Up Activity
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        // Login on Button click
        // Checking if Any Field is empty
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etEmailStr = etEmail.getText().toString();
                String etPasswordStr = etPass.getText().toString();

                if (!etEmailStr.isEmpty() && !etPasswordStr.isEmpty()) {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(etEmailStr, etPasswordStr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.cancel();
                                        Toast.makeText(MainActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, ViewMovies.class);
                                        startActivity(intent);

                                    } else {
                                        progressDialog.cancel();
                                        Toast.makeText(MainActivity.this, "Error while Signing In User " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    if (etEmailStr.isEmpty()) {
                        etEmail.setError("This Field can not be blank");
                    }
                    if (etPasswordStr.isEmpty()) {
                        etPass.setError("This Field can not be blank");
                    }
                }
            }
        });
    }

}
