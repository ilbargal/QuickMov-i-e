package com.finalandroidproject.quickmovie.DAL;

import com.finalandroidproject.quickmovie.Interfaces.IInvitationActions;
import com.finalandroidproject.quickmovie.Model.Cinema;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.Model.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivg1 on 13/01/2016.
 */
public class InvitationDAL implements IInvitationActions {
    public final static InvitationDAL instance = new InvitationDAL();

    @Override
    public List<MovieInvitation> getMySendInvitations(User user) {
        ParseQuery<ParseObject> queryuser = ParseQuery.getQuery("Users");
        queryuser.whereEqualTo("objectId", user.getID());

        ParseQuery<ParseObject> queryInvitations = ParseQuery.getQuery("Invitations");
        queryInvitations.whereMatchesQuery("SenderFriend", queryuser);

        try {
            List<ParseObject> data = queryInvitations.find();
            List<MovieInvitation> arrInvitations = new ArrayList<MovieInvitation>();
            for (ParseObject InvitationObject : data) {
                Friend SenderFriend = Friend.createFriendFromObject(InvitationObject.getParseObject("SenderFriend"));
                Friend ReceiverFriend = Friend.createFriendFromObject(InvitationObject.getParseObject("ReceiverFriend"));
                Movie SelectMovie = null;
                Cinema SelectCinema = null;

                ParseQuery<ParseObject> queryMovie = ParseQuery.getQuery("Movies");
                queryMovie.whereEqualTo("objectId", InvitationObject.getParseObject("Movie").getObjectId());
                List<ParseObject> dataMovie = queryMovie.find();
                if(dataMovie.size() == 1) {
                    SelectMovie = Movie.createMovieFromObject(dataMovie.get(0));
                }

                ParseQuery<ParseObject> queryCinema = ParseQuery.getQuery("Movies");
                queryCinema.whereEqualTo("objectId", InvitationObject.getParseObject("Cinema").getObjectId());
                List<ParseObject> dataCiname = queryCinema.find();
                if(dataCiname.size() == 1) {
                    SelectCinema = Cinema.createCinemaFromObject(dataMovie.get(0));
                }

                MovieInvitation Invitation = new MovieInvitation(InvitationObject.getInt("ID"),
                                                                 SenderFriend,
                                                                 ReceiverFriend,
                                                                 SelectMovie,
                                                                 SelectCinema,
                                                                 InvitationObject.getDate("MovieTime"));
                arrInvitations.add(Invitation);
            }

            return arrInvitations;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MovieInvitation> getMyRecvInvitations(User user) {
        ParseQuery<ParseObject> queryuser = ParseQuery.getQuery("Users");
        queryuser.whereEqualTo("objectId", user.getID());

        ParseQuery<ParseObject> queryInvitations = ParseQuery.getQuery("Invitations");
        queryuser.whereEqualTo("ReceiverFriend", queryuser);

        try {
            List<ParseObject> data = queryInvitations.find();
            List<MovieInvitation> arrInvitations = new ArrayList<MovieInvitation>();
            for (ParseObject InvitationObject : data) {
                Friend SenderFriend = Friend.createFriendFromObject(InvitationObject.getParseObject("SenderFriend"));
                Friend ReceiverFriend = Friend.createFriendFromObject(InvitationObject.getParseObject("ReceiverFriend"));
                Movie SelectMovie = null;
                Cinema SelectCinema = null;

                ParseQuery<ParseObject> queryMovie = ParseQuery.getQuery("Movies");
                queryMovie.whereEqualTo("objectId", InvitationObject.getParseObject("Movie").getObjectId());
                List<ParseObject> dataMovie = queryMovie.find();
                if(dataMovie.size() == 1) {
                    SelectMovie = Movie.createMovieFromObject(dataMovie.get(0));
                }

                ParseQuery<ParseObject> queryCinema = ParseQuery.getQuery("Movies");
                queryCinema.whereEqualTo("objectId", InvitationObject.getParseObject("Cinema").getObjectId());
                List<ParseObject> dataCiname = queryCinema.find();
                if(dataCiname.size() == 1) {
                    SelectCinema = Cinema.createCinemaFromObject(dataMovie.get(0));
                }

                MovieInvitation Invitation = new MovieInvitation(InvitationObject.getInt("ID"),
                        SenderFriend,
                        ReceiverFriend,
                        SelectMovie,
                        SelectCinema,
                        InvitationObject.getDate("MovieTime"));
                arrInvitations.add(Invitation);
            }

            return arrInvitations;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public MovieInvitation inviteFriendToMovie(User user,Friend friend, Movie currMovie) {
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
