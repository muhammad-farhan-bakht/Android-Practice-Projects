package com.example.farhan.moviereview;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMovies extends AppCompatActivity {

    TextView mEmptyStateTextView;
    FirebaseDatabase firebaseDatabase;
    private ArrayList<Movies> moviesArrayList;
    MoviesArrayAdapter moviesArrayAdapter;
    DatabaseReference myRef;
    NetworkInfo networkInfo;
    View loadingIndicator;
    FirebaseAuth mAuth;
    private User user;
    private String userName;
    private int checkAdmin;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_moives);

        mEmptyStateTextView = findViewById(R.id.empty_view);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Hide loading indicator because the data has been loaded
            loadingIndicator = findViewById(R.id.loading_indicator);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }

        final TextView tvName = findViewById(R.id.tvUname);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase mDb = FirebaseDatabase.getInstance();

        // Getting the user name who's recently Log-In
        uid = mAuth.getCurrentUser().getUid();
        mDb.getReference().child("users/" + uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        user = dataSnapshot.getValue(User.class);
                        userName = user.getName();
                        tvName.setText(user.getName());
                        checkAdmin = user.getAdminCheck();
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Movies");

        moviesArrayList = new ArrayList<>();

        ListView mListView = findViewById(R.id.mListView);
        moviesArrayAdapter = new MoviesArrayAdapter(this, moviesArrayList);
        mListView.setAdapter(moviesArrayAdapter);

        // On list click open's View Activity with that list Data
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(ViewMovies.this, ViewDetails.class);
                Movies m = moviesArrayList.get(position);
                intent.putExtra("obj", m);
                intent.putExtra("userKey", uid);
                intent.putExtra("userName", userName);

                startActivity(intent);
            }
        });

        // When There is new Movie it will Trigger Updated ArrayList and Show in the ListView
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Movies movies = dataSnapshot.getValue(Movies.class);
                moviesArrayList.add(movies);
                loadingIndicator.setVisibility(View.GONE);
                mEmptyStateTextView.setVisibility(View.GONE);
                moviesArrayAdapter.notifyDataSetChanged();

                Log.e("onChildAdded ", "onChildAdded: movies.getKey " + (movies.getKey()));

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

    //add Item menu in MenuBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.add);
        item.setVisible(false);
        if (networkInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    // Override this method to do what you want when the menu is recreated
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (checkAdmin == 1) {
            menu.findItem(R.id.add).setVisible(true);
        } else {
            menu.findItem(R.id.add).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    //Handel that Item in Top Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ViewMovies.this, MainActivity.class));
                return true;

            case R.id.add:
                Intent intent = new Intent(ViewMovies.this, AddNew.class);
                startActivity(intent);
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
