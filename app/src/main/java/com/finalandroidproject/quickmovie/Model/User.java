package com.finalandroidproject.quickmovie.Model;

/**
 * Created by Bar Gal on 06/01/2016.
 */
public class User {
    // PK
    private String phone;
    private String name;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    //private List<Person> friends;
}
