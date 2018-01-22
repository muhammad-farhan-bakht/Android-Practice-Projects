package com.example.farhan.todoapp;

/**
 * Created by Farhan on 10/30/2017.
 */

public class DataModel {

    private String task;
    private String key;

    public DataModel() {
    }

    public DataModel(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    @Override
    public boolean equals(Object obj) {
        DataModel ds = (DataModel) obj;
        return (ds.key.equals(key));
    }
}
