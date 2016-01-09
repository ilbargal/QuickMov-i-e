package com.finalandroidproject.quickmovie.Interfaces;

import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.Model.User;

import java.util.List;

public interface IInvitationActions {
    List<MovieInvitation> getMyInvitations();
    List<MovieInvitation> getFriendInvitations(Friend friend);
    MovieInvitation inviteFriendToMovie(Friend friend, Movie currMovie);
    void addNewInvitation(MovieInvitation invitation);
    void updateInvitation(MovieInvitation invitation);
    void removeInvitation(MovieInvitation invitation);
}
