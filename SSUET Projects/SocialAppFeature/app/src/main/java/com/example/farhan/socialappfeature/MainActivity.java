package com.example.farhan.socialappfeature;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.farhan.socialappfeature.Adapters.CommentAdapter;
import com.example.farhan.socialappfeature.Adapters.CustomRecyclerViewAdapter;
import com.example.farhan.socialappfeature.Models.Comment;
import com.example.farhan.socialappfeature.Models.Post;
import com.example.farhan.socialappfeature.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        CustomRecyclerViewAdapter.customButtonListener {

    ProgressBar progressBar;

    DatabaseReference myRef;
    DatabaseReference mRefLike;
    FirebaseAuth mAuth;
    ArrayList<Post> postArrayList;
    RecyclerView recyclerView;
    CustomRecyclerViewAdapter mAdapter;

    ArrayList<Comment> commentArrayList;
    int commentItemPosition;
    CommentAdapter commentAdapter;
    Post postKey;
    private String userName;

    private Boolean mProcessLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Method which initiate Views and Others Major's Things
        init();

        // Floating Button OnClick Action
        // Start Add Post Activity onClick Fab
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });

        // Hide Fab button on RecyclerView Scroll up and down
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    fab.hide();
                else if (dy < 0)
                    fab.show();
            }
        });

        // Call on Main Post Node on fireBase and populate data of RecyclerList
        myRef.child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                postArrayList.add(post);
                progressBar.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    // Method to init Major's Things
    private void init() {
        progressBar = findViewById(R.id.progressBar);

        // Connect to FireBase DataBASE
        FirebaseDatabase fDB = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        postArrayList = new ArrayList<>();
        mRefLike = FirebaseDatabase.getInstance().getReference().child("Likes");

        // Connect to Auth to get Current User UID
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUID = mAuth.getCurrentUser().getUid();

        // To get Current UserName
        DatabaseReference myRefToUser = fDB.getReference("SocialAppFeaturesUsers");
        myRefToUser.child(currentUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userName = user.getUserName();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.rootListView);
        mAdapter = new CustomRecyclerViewAdapter(this, postArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        // Add customListener on Main Activity
        mAdapter.setCustomCommentButtonListener(MainActivity.this);
        mAdapter.setCustomLikeButtonListener(MainActivity.this);
    }

    // Method Which is called when UserClick Comment ImageView
    public void showCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View promptsView = inflater.inflate(R.layout.comment_dialog, null);

        final EditText etCommentTxt = promptsView.findViewById(R.id.etCommentTxt);
        ImageButton btnSendComment = promptsView.findViewById(R.id.btnSendComment);
        ListView commentListView = promptsView.findViewById(R.id.commentListView);
        builder.setView(promptsView);
        builder.setTitle("Comments");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get Current Date
                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd");
                String dateString = sdf.format(date);

                String commentText = etCommentTxt.getText().toString();

                Comment commentObj = new Comment(userName, commentText, dateString);

                // Send Comment Object to FireBase Database
                if (!commentText.isEmpty()) {
                    String generatedCommentKey = myRef.push().getKey();
                    commentObj.setKey(generatedCommentKey);
                    myRef.child("Posts").child(postKey.getKey()).child("Comments").child(generatedCommentKey).setValue(commentObj);
                    etCommentTxt.setText("");
                } else {
                    if (commentText.isEmpty()) {
                        etCommentTxt.setError("Please Type Some Comment");
                    }
                }
            }
        });

        commentAdapter = new CommentAdapter(this, commentArrayList);
        commentListView.setAdapter(commentAdapter);

        // Get Comments from DataBase
        myRef.child("Posts").child(postKey.getKey()).child("Comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comment commentObj = dataSnapshot.getValue(Comment.class);
                commentArrayList.add(0, commentObj);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        builder.create().show();
    }

    // Custom onClick Comment ImageButton
    @Override
    public void onCommentButtonClickListener(int position) {
        commentItemPosition = position;
        postKey = postArrayList.get(commentItemPosition);
        commentArrayList = new ArrayList<>();
        showCommentDialog();
    }

    // Custom onClick Like ImageButton
    @Override
    public void onLikeButtonClickListener(int position) {
        final Post postKey = postArrayList.get(position);
        mProcessLike = true;

        mRefLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mProcessLike) {
                    if (dataSnapshot.child(postKey.getKey()).hasChild(mAuth.getCurrentUser().getUid())) {
                        mRefLike.child(postKey.getKey()).child(mAuth.getCurrentUser().getUid()).removeValue();
                        mProcessLike = false;
                    } else {
                        mRefLike.child(postKey.getKey()).child(mAuth.getCurrentUser().getUid()).setValue("Like");
                        mProcessLike = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    //add Item menu in MenuBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Handel that Item in Top Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
