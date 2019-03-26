package com.example.reamlmdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reamlmdemo.R;
import com.example.reamlmdemo.model.Student;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText etName;
    private EditText etAge;
    private TextView tvShowData;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inti();
    }


    private void inti() {

        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        tvShowData = findViewById(R.id.tv_show_data);
        Button btnClear = findViewById(R.id.btn_Clear);
        btnClear.setOnClickListener(this);
        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(this);

        realm = Realm.getDefaultInstance();
        readAll();

    }

    private void readAll() {
        RealmResults<Student> students = realm.where(Student.class).findAll();
        tvShowData.setText("");
        StringBuilder data = new StringBuilder();

        for (Student studentObj : students) {
            try {

                data.append("\n").append("\n").append(studentObj.toString());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        tvShowData.setText(data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            ///////////////////////////////////SAVE BUTTON///////////////////////////////////////////////
            case R.id.btn_save: {

                if (!checkEmpty()) {
                    return;
                }

                realm.executeTransactionAsync(realm -> {
                    Student student = realm.createObject(Student.class, UUID.randomUUID().toString());
                    student.setName(etName.getText().toString());
                    student.setAge(Integer.parseInt(etAge.getText().toString()));
                }, () -> {
                    Toast.makeText(MainActivity.this, "Transaction onSuccess", Toast.LENGTH_SHORT).show();
                    emptyViews();
                    readAll();
                }, error -> {
                    Toast.makeText(MainActivity.this, "Transaction onError: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Transaction onError: " + error.getMessage());
                });

                break;
            }

            ///////////////////////////////////CLEAR BUTTON///////////////////////////////////////////////
            case R.id.btn_Clear: {
                realm.executeTransaction(realm -> {
                    realm.deleteAll();
                    Toast.makeText(MainActivity.this, "Transaction onSuccess Clear All", Toast.LENGTH_SHORT).show();
                    tvShowData.setText("");
                    emptyViews();
                });
                break;
            }

            ///////////////////////////////////UPDATE BUTTON///////////////////////////////////////////////
            case R.id.btn_update: {

                if (!checkEmpty()) {
                    return;
                }

                realm.executeTransaction(realm -> {
                    String oldName = getOldString(etName.getText().toString());
                    if (oldName.equals("")) {
                        return;
                    }

                    String oldAge = getOldString(etAge.getText().toString());
                    if (oldAge.equals("")) {
                        return;
                    }

                    Student student = realm.where(Student.class)
                            .equalTo("name", oldName)
                            .equalTo("age", Integer.parseInt(oldAge))
                            .findFirst();
                    if (student != null) {

                        String newName = getUpdatedString(etName.getText().toString());
                        if (newName.equals("")) {
                            return;
                        }

                        String newAge = getUpdatedString(etAge.getText().toString());
                        if (newAge.equals("")) {
                            return;
                        }

                        student.setName(newName);
                        student.setAge(Integer.parseInt(newAge));
                        emptyViews();
                        readAll();
                        Toast.makeText(MainActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Update Fails", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }

            ///////////////////////////////////DELETE BUTTON///////////////////////////////////////////////
            case R.id.btn_delete: {

                if (!checkEmpty()) {
                    return;
                }

                realm.executeTransaction(realm -> {
                    RealmResults<Student> students = realm.where(Student.class)
                            .equalTo("name", etName.getText().toString())
                            .equalTo("age", Integer.parseInt(etAge.getText().toString()))
                            .findAll();
                    if (students.size() != 0) {
                        students.deleteAllFromRealm();
                        emptyViews();
                        readAll();
                        Toast.makeText(MainActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Delete Fails", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }

    private String getOldString(String newValues) {
        if (newValues.contains(":")) {
            String[] oldValues = newValues.split(":");
            Log.e(TAG,"oldValues: "+oldValues[0]);
            return oldValues[0];
        } else {
            Toast.makeText(this, "String does not contains ':'", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    private String getUpdatedString(String oldValue) {
        if (oldValue.contains(":")) {
            String[] newValue = oldValue.split(":");
            Log.e(TAG,"newValues: "+newValue[1]);
            return newValue[1];
        } else {
            Toast.makeText(this, "String does not contains ':'", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    private Boolean checkEmpty() {
        if (etName.getText().toString().trim().equals("") || etAge.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void emptyViews(){
        etName.setText("");
        etAge.setText("");
    }
}
