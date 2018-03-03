package com.example.farhan.socialappfeature.Models;

/**
 * Created by Farhan on 2/27/2018.
 */

public class Post {
    private String userName;
    private String postText;
    private String postImgURL;
    private String userID;
    private String key;
    private String checkLike;

    public Post() {
    }

    public Post(String userName, String postText, String postImgURL, String userID, String checkLike) {
        this.userName = userName;
        this.postText = postText;
        this.postImgURL = postImgURL;
        this.userID = userID;
        this.checkLike = checkLike;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImgURL() {
        return postImgURL;
    }

    public void setPostImgURL(String postImgURL) {
        this.postImgURL = postImgURL;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String isCheckLike() {
        return checkLike;
    }

    public void setCheckLike(String checkLike) {
        this.checkLike = checkLike;
    }
}
