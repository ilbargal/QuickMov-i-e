package com.finalandroidproject.quickmovie.Model;


import java.util.ArrayList;
import java.util.List;

public class Cache {

    // TODO: save in cache all lists
    public static List<Movie> Movies = new ArrayList<Movie>();
    public static List<Friend> Friends = new ArrayList<Friend>();
    public static List<MovieInvitation> Invitations = new ArrayList<MovieInvitation>();

    static {
        // Add 3 sample items.
        Movies.add(new Movie("ספקטר", "Item 1"));
        Movies.add(new Movie("משחקי הרעב", "Item 2"));
        Movies.add(new Movie("אושן 13", "Item 3"));
        Friends.add(new Friend("Bar Gal", "123"));
        Friends.add(new Friend("נופר פיאנקו", "123"));

    }
}
