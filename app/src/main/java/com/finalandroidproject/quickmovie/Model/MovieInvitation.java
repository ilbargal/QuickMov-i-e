package com.finalandroidproject.quickmovie.Model;

import java.util.Date;

public class MovieInvitation {
    private int id;



    private String objetcID;
    private Friend fromFriend;
    private Friend toFriend;
    private Movie movie;
    private Cinema cinema;
    private Date invitationDate;
    private boolean isAccepted;

    public MovieInvitation(int id, Friend fromFriend, Friend toFriend, Movie movie, Cinema cinema, Date invitationDate) {
        this.id = id;
        this.fromFriend = fromFriend;
        this.toFriend = toFriend;
        this.movie = movie;
        this.cinema = cinema;
        this.invitationDate = invitationDate;
        this.isAccepted = false;
    }

    public String getObjetcID() {
        return objetcID;
    }

    public void setObjetcID(String objetcID) {
        this.objetcID = objetcID;
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

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
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
