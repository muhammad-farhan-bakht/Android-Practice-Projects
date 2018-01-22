package com.example.farhan.pizzatask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddStudentData extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    private byte[] bArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_data);

        imageView = (ImageView) findViewById(R.id.mImage);
        final EditText editTextName = (EditText) findViewById(R.id.etName);
        final EditText editTextEmail = (EditText) findViewById(R.id.etMail);
        final EditText editTextPhone = (EditText) findViewById(R.id.etNo);
        Button buttonCamera = (Button) findViewById(R.id.btnTakePic);
        Button buttonInsert = (Button) findViewById(R.id.btnInsert);


        //Open Camera to take Picture..
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        //Insert data in DataBase and Also check if all Fields are Filled or not and in last if all Okay Clears all fields after Inserting.
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();


                // getting and image from ImageView and Store in Bitmap then store in Byte Array.
                try {
                    Bitmap photo = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    bArray = bos.toByteArray();

                    // Check if All Fields are filled or empty.
                    if (!name.isEmpty() && !email.isEmpty() && isValidEmail(email) && !phone.isEmpty() && bArray != null) {
                        DataSource dSObj = new DataSource(name, email, phone, bArray);

                        dSObj.save();

                        Toast.makeText(AddStudentData.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                        // Clearing all text after complete insert.
                        bArray = null;
                        imageView.setImageResource(0);
                        editTextName.setText("");
                        editTextEmail.setText("");
                        editTextPhone.setText("");

                    } else {

                        if (name.isEmpty()) {
                            editTextName.setError("This Field can not be blank");
                        }

                        if (email.isEmpty()) {
                            editTextEmail.setError("This Field can not be blank");

                        } else {
                            editTextEmail.setError("Email format is not correct");
                        }

                        if (phone.isEmpty()) {
                            editTextPhone.setError("This Field can not be blank");
                        }

                    }
                } catch (NullPointerException e) {
                    Toast.makeText(AddStudentData.this, "Please Insert Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * set Validation on email EditText
     **/
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Method of Intent to call Camera and Take pic and save accordingly to key
     **/
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Call Back Factory Method which Holds the Intent Object when intent brings some data from other Activity
     **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

}
