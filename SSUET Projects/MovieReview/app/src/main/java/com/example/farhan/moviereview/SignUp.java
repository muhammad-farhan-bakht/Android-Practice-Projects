package com.example.farhan.moviereview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDb;
    private int checkAdmin = 0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Logging please wait");

        final EditText etName = findViewById(R.id.etName);
        final EditText etEmail = findViewById(R.id.etEmailSignUp);
        final EditText etPass = findViewById(R.id.etSignUpPass);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        final CheckBox checkBox = findViewById(R.id.checkbox);

        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();

        // Sign-up on button click
        // Checking if Any Field is empty
        // Creating User Node and Adding User Data in it
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String etNameStr = etName.getText().toString();
                String etEmailStr = etEmail.getText().toString();
                String etPasswordStr = etPass.getText().toString();

                if (checkBox.isChecked()) {
                    checkAdmin = 1;
                }

                if (!etNameStr.isEmpty() && !etEmailStr.isEmpty() && !etPasswordStr.isEmpty()) {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(etEmailStr, etPasswordStr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String uid = mAuth.getCurrentUser().getUid();
                                        mDb.getReference().child("users/" + uid).setValue(new User(etName.getText().toString(), uid, checkAdmin));

                                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                                        progressDialog.cancel();
                                        startActivity(intent);

                                        Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUp.this, "Error while Registering User " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    if (etNameStr.isEmpty()) {
                        etName.setError("This Field can not be blank");
                    }

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
