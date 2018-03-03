package com.example.farhan.socialappfeature;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farhan.socialappfeature.Models.Post;
import com.example.farhan.socialappfeature.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPostActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    TextView tvUserName;
    EditText postText;
    ImageView postImage;
    TextView imgError;
    private static final int RESULT_LOAD_IMAGE = 1;
    Uri selectedImageUri = null;
    String userName;
    String userID;
    String key;
    ImageButton addImageFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        progressDialog = new ProgressDialog(AddPostActivity.this);
        progressDialog.setMessage("Loading please wait");

        tvUserName = findViewById(R.id.addPostUserName);
        postText = findViewById(R.id.etAddPostText);
        postImage = findViewById(R.id.addPostImage);
        imgError = findViewById(R.id.imgError);
        Button btnAddPost = findViewById(R.id.btnMakePost);
        addImageFab = findViewById(R.id.addImageFab);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Posts");

        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();
        firebaseDatabase.getReference().child("SocialAppFeaturesUsers/" + userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            User user = dataSnapshot.getValue(User.class);
                            userName = user.getUserName();
                            tvUserName.setText(userName);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(AddPostActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

        addImageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void insert() {
        progressDialog.show();
        final String text = postText.getText().toString();
        if (!text.isEmpty()) {
            if (selectedImageUri != null) {
                final FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference imgRef = storage.getReference().child(System.currentTimeMillis() + ".jpg");
                UploadTask uploadTask = imgRef.putFile(selectedImageUri);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(AddPostActivity.this, "Error while Uploading Image: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        String downloadUrlStr = downloadUrl.toString();

                        Post postObj = new Post(userName, text, downloadUrlStr, userID,"f");
                        key = myRef.push().getKey();
                        postObj.setKey(key);
                        myRef.child(key).setValue(postObj);

                        progressDialog.dismiss();
                        Toast.makeText(AddPostActivity.this, "Post Added Successfully", Toast.LENGTH_SHORT).show();
                        postText.setText("");
                        postImage.setImageResource(0);

                    }
                });
            } else {
                imgError.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }
        } else {
            postText.setError("Write some post");
            progressDialog.dismiss();
        }

    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE) {
                // Get the url from data
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Set the image in ImageView
                    imgError.setVisibility(View.GONE);
                    postImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}
