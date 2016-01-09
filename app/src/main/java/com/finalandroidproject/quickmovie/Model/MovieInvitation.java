package com.finalandroidproject.quickmovie.Model;

import java.util.Date;

/**
 * Created by Bar Gal on 06/01/2016.
 */
public class MovieInvitation {
    private int id;
    private Friend fromFriend;
    private Friend toFriend;
    private Movie movie;
    private String cinema;
    private Date invitationDate;
    private boolean isAccepted;

    public MovieInvitation(int id, Friend fromFriend, Friend toFriend, Movie movie, String cinema, Date invitationDate) {
        this.id = id;
        this.fromFriend = fromFriend;
        this.toFriend = toFriend;
        this.movie = movie;
        this.cinema = cinema;
        this.invitationDate = invitationDate;
        this.isAccepted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Friend getFromFriend() {
        return fromFriend;
    }

    public void setFromFriend(Friend fromFriend) {
        this.fromFriend = fromFriend;
    }

    public Friend getToFriend() {
        return toFriend;
    }

    public void setToFriend(Friend toFriend) {
        this.toFriend = toFriend;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public Date getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(Date invitationDate) {
        this.invitationDate = invitationDate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
