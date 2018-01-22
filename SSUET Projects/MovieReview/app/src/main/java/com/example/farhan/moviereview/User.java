package com.example.farhan.moviereview;

/**
 * Created by Farhan on 12/25/2017.
 */

public class User {

   private String name;
   private String id;
   private int adminCheck;


    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String id, int adminCheck) {
        this.name = name;
        this.id = id;
        this.adminCheck = adminCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAdminCheck() {
        return adminCheck;
    }

    public void setAdminCheck(int adminCheck) {
        this.adminCheck = adminCheck;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
