package com.example.farhan.socialappfeature.Models;

/**
 * Created by Farhan on 2/27/2018.
 */

public class User {
    private String userName;
    private String uid;

    public User() {
    }

    public User(String userName, String uid) {
        this.userName = userName;
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
