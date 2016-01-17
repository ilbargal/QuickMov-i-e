package com.finalandroidproject.quickmovie.Model;

/**
 * Created by Bar Gal on 30/12/2015.
 */
public class Friend {
    private String name;

    private String phone;

    public String getPicturePath() {
        return picturePath;
    }

    public Friend(String name, String picturePath) {
        this.name = name;
        this.picturePath = picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String picturePath;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
