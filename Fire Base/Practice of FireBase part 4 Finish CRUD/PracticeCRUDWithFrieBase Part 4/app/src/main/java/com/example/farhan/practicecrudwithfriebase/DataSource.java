package com.example.farhan.practicecrudwithfriebase;

/**
 * Created by Farhan on 10/24/2017.
 */

public class DataSource {

    private String name;
    private String age;
    private String email;
    private String phone;
    String key;

    public DataSource() {
    }

    public DataSource(String name, String age, String email, String phone) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        DataSource ds = (DataSource) obj;
        return (ds.key.equals(key));
    }
}
