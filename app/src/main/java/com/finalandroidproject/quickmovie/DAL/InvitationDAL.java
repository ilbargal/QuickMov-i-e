package com.finalandroidproject.quickmovie.DAL;

import com.finalandroidproject.quickmovie.Interfaces.IInvitationActions;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;

import java.util.List;

/**
 * Created by nivg1 on 13/01/2016.
 */
public class InvitationDAL implements IInvitationActions {
    @Override
    public List<MovieInvitation> getMyInvitations() {
        return null;
    }

    @Override
    public List<MovieInvitation> getFriendInvitations(Friend friend) {
        return null;
    }

    @Override
    public MovieInvitation inviteFriendToMovie(Friend friend, Movie currMovie) {
        return null;
    }

    @Override
    public void addNewInvitation(MovieInvitation invitation) {

    }

    @Override
    public void updateInvitation(MovieInvitation invitation) {

    }

    @Override
    public void removeInvitation(MovieInvitation invitation) {

    }
}
