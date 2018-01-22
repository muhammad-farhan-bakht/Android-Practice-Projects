package com.example.farhan.assignment_20_augest;


public class DataSource {
    private int image;
    private String name;
    private String description;
    private String rating;
    private String genre;

    public String getRating() {
        return rating;
    }

    public DataSource(int image, String name, String description, String rating, String genre) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.genre = genre;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }


}
