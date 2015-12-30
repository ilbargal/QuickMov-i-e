package com.finalandroidproject.quickmovie.Model;

/**
 * Created by יגאל on 30/12/2015.
 */
public class Friend {
    private String name;

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
}
