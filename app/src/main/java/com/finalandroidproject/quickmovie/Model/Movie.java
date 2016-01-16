package com.finalandroidproject.quickmovie.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {
    private String name;
    private String description;
    private double rating;
    private String ganre;
    private List<String> cinemas;
    private String imagePath;

    public Movie() {

    }

    public Movie(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
        this.cinemas = new ArrayList<String>();
    }


    public Movie(String name, String description, double rating, String zaner, List<String> cinemas, String imagePath) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.ganre = zaner;
        this.cinemas = cinemas;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGanre() {
        return ganre;
    }

    public void setGanre(String zaner) {
        this.ganre = zaner;
    }

    public List<String> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<String> cinemas) {
        this.cinemas = cinemas;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
