package com.finalandroidproject.quickmovie.Model;


import com.finalandroidproject.quickmovie.DAL.MovieDAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cache {

    // TODO: save in cache all lists
    public static List<Movie> Movies = new ArrayList<Movie>();
    public static List<Friend> Friends = new ArrayList<Friend>();
    public static List<MovieInvitation> Invitations = new ArrayList<MovieInvitation>();

    static {
        // Add 3 sample items.
        Movies = new MovieDAL().getAllMovies(1, 6, true);
        Friends.add(new Friend("נופר פיאנקו", "123"));
        Invitations.add(new MovieInvitation(1, new Friend("נופר פיאנקו", "123"), new Friend("Bar Gal", "123"),new Movie("משחקי הרעב", "Item 2"),"סינמה סיטי", new Date(2016, 1,1)));
    }
}
