package com.example.farhan.moviereview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ViewDetails extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    final static private String URL_SEP = "=";
    RelativeLayout relativeLayout;
    EditText etComment;
    ImageButton btnSendComment;
    CommentArrayAdapter commentAdapter;
    ArrayList<Comment> commentArrayList;
    ArrayList<Rating> ratingArrayList;
    ArrayList<String> key;
    String dateString;
    RateUsArrayAdapter rateUsArrayAdapter;
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;
    private String userName;
    private String userKey;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 50;
    private final static String YOUTUBE_KEY = "AIzaSyBj95UJDRIC_45ETtKLnpiaRUOy2x28ICc";
    private String videoUrl;
    private String generatedKey;
    ImageView playVideo;
    TextView textRateUs;
    YouTubePlayerView YouTubePlayerView;
    private String movieKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        commentArrayList = new ArrayList<>();
        ratingArrayList = new ArrayList<>();
        key = new ArrayList<>();

        //Check for any issues in Youtube Players
        final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (result != YouTubeInitializationResult.SUCCESS) {
            //If there are any issues we can show an error dialog.
            result.getErrorDialog(this, 0).show();
        }

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd");
        dateString = sdf.format(date);

        relativeLayout = findViewById(R.id.viewDetailsRL);
        YouTubePlayerView = findViewById(R.id.mVideoPlayer);
        playVideo = findViewById(R.id.playVideo);
        ImageView imageView = findViewById(R.id.imgViewImg);
        TextView textViewName = findViewById(R.id.mTxtViewName);
        TextView textViewDec = findViewById(R.id.mTxtViewDecription);
        TextView textViewRating = findViewById(R.id.mTextViewRating);
        TextView textViewGenre = findViewById(R.id.mTextViewGenre);
        TextView textViewCast = findViewById(R.id.mTextViewCast);
        TextView textViewDirector = findViewById(R.id.mTextViewDirector);
        TextView textViewTime = findViewById(R.id.mTextViewTime);
        NonScrollListView non_scroll_list = findViewById(R.id.vdListView);
        etComment = findViewById(R.id.etComment);
        btnSendComment = findViewById(R.id.btnComment);
        btnSendComment.setEnabled(false);
        textRateUs = findViewById(R.id.mTextViewRateUs);

        YouTubePlayerView.initialize(YOUTUBE_KEY, this);

        // Getting Data from the Intent from ViewMovies Activity
        Intent i = getIntent();
        Movies moviesObj = i.getParcelableExtra("obj");
        userKey = i.getStringExtra("userKey");
        userName = i.getStringExtra("userName");

        // Setting the Values from Intent Data
        Glide.with(ViewDetails.this).load(moviesObj.getImage()).into(imageView);
        textViewName.setText(moviesObj.getName());
        textViewDec.setText(moviesObj.getDescription());
        textViewRating.append(String.valueOf(moviesObj.getRating() * 2) + "/10");
        textViewGenre.setText(moviesObj.getGenre());
        textViewCast.setText(moviesObj.getCast());
        textViewDirector.setText(moviesObj.getDirector());
        videoUrl = convertUrlToKey(moviesObj.getUrl());
        textViewTime.setText(moviesObj.getTime() + "min");
        movieKey = moviesObj.getKey();

        commentAdapter = new CommentArrayAdapter(ViewDetails.this, commentArrayList);
        non_scroll_list.setAdapter(commentAdapter);

        // Enable Send button when there's text to send
        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    btnSendComment.setEnabled(true);
                } else {
                    btnSendComment.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Setting the Filter into Comment EditText
        etComment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Rate US
        textRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRatingDialog();
            }
        });

        // Sending The Comment into FireBase Database
        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String comment = etComment.getText().toString();

                Comment commentObj = new Comment(userName, comment, dateString);

                if (!comment.isEmpty()) {

                    generatedKey = myRef.push().getKey();
                    commentObj.setKey(generatedKey);
                    myRef.child("Movies").child(movieKey).child("Comments").child(generatedKey).setValue(commentObj);

                    etComment.setText("");

                } else {
                    if (comment.isEmpty()) {
                        etComment.setError("Please Type Some Comment");
                    }
                }
            }
        });

        // When There is new Comment it will Trigger Updated ArrayList and Show in the ListView
        myRef.child("Movies").child(movieKey).child("Comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comment commentObj = dataSnapshot.getValue(Comment.class);
                commentArrayList.add(commentObj);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Comment commentObj = dataSnapshot.getValue(Comment.class);
                commentArrayList.add(commentObj);
                commentAdapter.notifyDataSetChanged();
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

        rateUsArrayAdapter = new RateUsArrayAdapter(ViewDetails.this, ratingArrayList);

        // When There is new Comment it will Trigger Updated ArrayList and Show in the ListView
        myRef.child("Movies").child(movieKey).child("Ratings").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Rating ratingObj = dataSnapshot.getValue(Rating.class);
                ratingArrayList.add(ratingObj);

                String objectKey = dataSnapshot.getKey();
                key.add(objectKey);

                rateUsArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Rating ratingObj = dataSnapshot.getValue(Rating.class);

                String objectKey = dataSnapshot.getKey();

                int index = key.indexOf(objectKey);
                ratingArrayList.set(index, ratingObj);
                rateUsArrayAdapter.notifyDataSetChanged();
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

    // YouTubePlayer Listener Calls when There is Any Video
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, final boolean b) {

        // add listeners to YouTubePlayer instance
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

        // Start Loading the Video
        if (!b) {
            youTubePlayer.cueVideo(videoUrl);
            relativeLayout.setVisibility(View.VISIBLE);
        }
        playVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                playVideo.setVisibility(View.GONE);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.play();

            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
            relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };

    // Convert Video URL into Key
    private String convertUrlToKey(String string) {

        String videoId = null;
        if (string.contains(URL_SEP)) {
            String[] parts = string.split(URL_SEP);
            videoId = parts[1];
        }
        return videoId;
    }

    // Pop up an Dialog Box which takes Rating
    private void showRatingDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        View promptsView = inflater.inflate(R.layout.rateus_dialog, null);

        final RatingBar ratingBar = promptsView.findViewById(R.id.rateUs);
        final TextView textView = promptsView.findViewById(R.id.rateUsNumber);

        int customGoldActive = Color.parseColor("#FFC107");
        int customGoldBack = Color.parseColor("#fffde7");

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(1).setColorFilter(customGoldActive, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(customGoldActive, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(customGoldBack, PorterDuff.Mode.SRC_ATOP);

        ListView rateUsListView = promptsView.findViewById(R.id.listViewRating);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(promptsView);

        builder.setTitle("Rate The Movie")
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        float rating = ratingBar.getRating();

                        Rating ratingObj = new Rating(userName, rating);

                        if (rating != 0.0f) {
                            myRef.child("Movies").child(movieKey).child("Ratings").child(userKey).setValue(ratingObj);

                        } else {
                            Toast.makeText(ViewDetails.this, "You Didn't Rate", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.notNow, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        rateUsListView.setAdapter(rateUsArrayAdapter);
        //builder.create().show();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        ratingBar.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        textView.setText(String.valueOf(rating * 2));
                    }
                }
        );
    }

}
