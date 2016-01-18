package com.finalandroidproject.quickmovie.Model;

import java.util.List;

/**
 * Created by Bar Gal on 06/01/2016.
 */
public class User {
    // PK
    private String ID;
    private String phone;
    private String name;
    private String password;
    private List<Friend> friends;

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public User(){};

    public User(String ID, String phone, String name, String password, List<Friend> friends) {
        this.ID = ID;
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.friends = friends;
    }
}
