package com.finalandroidproject.quickmovie.Model;


import com.finalandroidproject.quickmovie.DAL.FriendDAL;
import com.finalandroidproject.quickmovie.DAL.InvitationDAL;
import com.finalandroidproject.quickmovie.DAL.MovieDAL;
import com.finalandroidproject.quickmovie.DAL.UserDAL;
import com.finalandroidproject.quickmovie.UsefulClasses.IntentHelper;

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
        User currentUser = (User) IntentHelper.getObjectForKey("currentUser");
        Movies = new MovieDAL().getAllMovies(0, 6, true);
        Friends = new FriendDAL().getFriendsByUser(currentUser);
        //Invitations = new InvitationDAL().getMyRecvInvitations(currentUser);
        //Invitations.add(new MovieInvitation(1, new Friend("נופר פיאנקו", "123",""), new Friend("Bar Gal", "123",""),new Movie("משחקי הרעב", "Item 2"),"סינמה סיטי", new Date(2016, 1,1)));
    }
}
