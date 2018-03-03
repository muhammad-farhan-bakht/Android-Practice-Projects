package com.example.farhan.socialappfeature;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farhan.socialappfeature.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDb;
    ProgressDialog progressDialog;

    EditText userName;
    EditText userEmail;
    EditText userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Logging please wait");

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();

        userName = findViewById(R.id.etSignUpUserName);
        userEmail = findViewById(R.id.etSignUpEmail);
        userPass = findViewById(R.id.etSignUpPass);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp() {

        String etNameStr = userName.getText().toString();
        String etEmailStr = userEmail.getText().toString();
        String etPasswordStr = userPass.getText().toString();

        if (!etNameStr.isEmpty()) {
            if (!etEmailStr.isEmpty()) {
                if (!etPasswordStr.isEmpty()) {

                    progressDialog.show();

                    mAuth.createUserWithEmailAndPassword(etEmailStr, etPasswordStr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String uid = mAuth.getCurrentUser().getUid();
                                        mDb.getReference().child("SocialAppFeaturesUsers/" + uid).setValue(new User(userName.getText().toString(),uid));
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        mAuth.signOut();
                                        progressDialog.cancel();
                                        startActivity(intent);

                                        Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Error while Registering User " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    userPass.setError("Password Required");
                }

            } else {
                userEmail.setError("Email Required");
            }
        } else {
            userName.setError("Username Required");
        }

    }
}
