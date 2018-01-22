package com.example.farhan.moviereview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Farhan on 12/9/2017.
 */

public class Movies implements Parcelable {

    private String key;
    private String image;
    private String name;
    private String description;
    private String genre;
    private String cast;
    private String director;
    private String url;
    private String time;
    private float rating;

    public Movies() {
    }

    public Movies( String image, String name, String description, String genre, String cast, String director, String url, String time, float rating) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.cast = cast;
        this.director = director;
        this.url = url;
        this.time = time;
        this.rating = rating;
    }

    protected Movies(Parcel in) {
        key = in.readString();
        image = in.readString();
        name = in.readString();
        description = in.readString();
        genre = in.readString();
        cast = in.readString();
        director = in.readString();
        url = in.readString();
        time = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(genre);
        parcel.writeString(cast);
        parcel.writeString(director);
        parcel.writeString(url);
        parcel.writeString(time);
        parcel.writeFloat(rating);
    }
}
