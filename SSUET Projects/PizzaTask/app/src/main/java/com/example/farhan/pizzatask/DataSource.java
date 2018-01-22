package com.example.farhan.pizzatask;


import com.orm.SugarRecord;

public class DataSource extends SugarRecord {
    private String name;
    private String Email;
    private String phone;
    byte[] bArray;

    public DataSource(String name, String email, String phone) {
        this.name = name;
        this.Email = email;
        this.phone = phone;

    }

    public DataSource(String name, String email, String phone, byte[] bArray) {
        this.name = name;
        this.Email = email;
        this.phone = phone;
        this.bArray = bArray;
    }

    public DataSource(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getbArray() {
        return bArray;
    }

    public void setbArray(byte[] bArray) {
        this.bArray = bArray;
    }
}
