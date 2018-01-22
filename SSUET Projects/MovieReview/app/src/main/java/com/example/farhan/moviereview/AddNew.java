package com.example.farhan.moviereview;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNew extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private DatabaseReference myRef;
    private ImageView imageView;
    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImageUri = null;
    private String key;
    private RatingBar mRating;
    private TextView showRating;
    private EditText etName;
    private EditText etDec;
    private EditText etGenre;
    private EditText etCast;
    private EditText etDirector;
    private EditText etUrl;
    private EditText etTime;
    private TextView imgError;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        // Enable Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(AddNew.this);
        progressDialog.setMessage("Loading please wait");

        imageView = findViewById(R.id.mImg);
        etName = findViewById(R.id.etMovieName);
        etDec = findViewById(R.id.etMovieDec);
        etGenre = findViewById(R.id.etMovieGenre);
        showRating = findViewById(R.id.showRating);
        mRating = findViewById(R.id.movieInitialRating);
        imgError = findViewById(R.id.imageViewSetError);
        etCast = findViewById(R.id.etMovieCast);
        etDirector = findViewById(R.id.etMovieDirector);
        etTime = findViewById(R.id.etMovieRunTime);
        etUrl = findViewById(R.id.etMovieUrl);
        Button btnAdd = findViewById(R.id.btnAdd);

        int customGoldActive = Color.parseColor("#FFC107");
        int customGoldBack = Color.parseColor("#fffde7");

        LayerDrawable stars = (LayerDrawable) mRating.getProgressDrawable();
        stars.getDrawable(1).setColorFilter(customGoldActive, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(customGoldActive, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(customGoldBack, PorterDuff.Mode.SRC_ATOP);

        showRating.setText(String.valueOf(mRating.getRating()));

        listenerForRatingBar();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Movies");

        // ImagePickerButton shows an image picker to upload a image for a message
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        // Add into FireStore
        // Checking if any field is empty
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String dec = etDec.getText().toString();
                String genre = etGenre.getText().toString();
                String cast = etCast.getText().toString();
                String director = etDirector.getText().toString();
                String url = etUrl.getText().toString();
                String time = etTime.getText().toString();

                if (selectedImageUri != null && !name.isEmpty() && !dec.isEmpty() && !genre.isEmpty() && !cast.isEmpty() && !director.isEmpty() && !url.isEmpty() && !time.isEmpty()) {
                    progressDialog.show();
                    uploadFile(selectedImageUri);

                } else {

                    if (selectedImageUri == null) {
                        imgError.setText(R.string.img_err0r);
                    }

                    if (name.isEmpty()) {
                        etName.setError("This Field can not be blank");
                    }

                    if (dec.isEmpty()) {
                        etDec.setError("This Field can not be blank");
                    }

                    if (genre.isEmpty()) {
                        etGenre.setError("This Field can not be blank");
                    }
                    if (cast.isEmpty()) {
                        etCast.setError("This Field can not be blank");
                    }
                    if (director.isEmpty()) {
                        etDirector.setError("This Field can not be blank");
                    }
                    if (url.isEmpty()) {
                        etUrl.setError("This Field can not be blank");
                    }
                    if (time.isEmpty()) {
                        etTime.setError("This Field can not be blank");
                    }
                }
            }
        });
    }

    // Changing the TextView Text when Rating Change's
    public void listenerForRatingBar() {
        mRating.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        showRating.setText(String.valueOf(rating * 2));
                    }
                }
        );
    }

    /**
     * Method of Intent to call Camera and Take pic and save accordingly to key
     **/
    private void dispatchTakePictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    /**
     * Call Back Factory Method which Holds the Intent Object when intent brings some data from other Activity
     **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {

                // Get the url from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Set the image in ImageView
                    imageView.setImageURI(selectedImageUri);
                    imgError.setVisibility(View.GONE);
                }
            }
        }
    }

    // Upload the Image in FireStore, if its successful it will bring url and then we make an Object and push in fireBase DataBase
    private void uploadFile(Uri file) {
        if (file != null) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference mountainsRef = storage.getReference().child(System.currentTimeMillis() + ".jpg");
            UploadTask uploadTask = mountainsRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(AddNew.this, "Fail to upload " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String downloadUrlStr = downloadUrl.toString();

                    String name = etName.getText().toString();
                    String dec = etDec.getText().toString();
                    String genre = etGenre.getText().toString();
                    String cast = etCast.getText().toString();
                    String director = etDirector.getText().toString();
                    String url = etUrl.getText().toString();
                    String time = etTime.getText().toString();
                    float rating = mRating.getRating();

                    Movies movieObj = new Movies(downloadUrlStr, name, dec, genre, cast, director, url, time, rating);

                    key = myRef.push().getKey();
                    movieObj.setKey(key);
                    myRef.child(key).setValue(movieObj);
                    progressDialog.cancel();
                    Toast.makeText(AddNew.this, "Movie Added Successfully", Toast.LENGTH_SHORT).show();

                    imageView.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
                    etName.setText("");
                    etDec.setText("");
                    etGenre.setText("");
                    etCast.setText("");
                    etDirector.setText("");
                    etUrl.setText("");
                    etTime.setText("");
                    mRating.setRating(0);
                }
            });
        } else {
            imgError.setText(R.string.img_err0r);
        }
    }

}