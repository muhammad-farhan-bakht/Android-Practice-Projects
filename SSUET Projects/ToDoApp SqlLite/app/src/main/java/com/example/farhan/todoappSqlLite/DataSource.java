package com.example.farhan.todoappSqlLite;

import com.orm.SugarRecord;

/**
 * Created by Farhan on 10/21/2017.
 */

public class DataSource extends SugarRecord {

    private String txt;

    public DataSource() {
    }

    public DataSource(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
