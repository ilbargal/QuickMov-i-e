package com.finalandroidproject.quickmovie.DAL;

import com.finalandroidproject.quickmovie.Interfaces.IInvitationActions;
import com.finalandroidproject.quickmovie.Model.Cinema;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.Movie;
import com.finalandroidproject.quickmovie.Model.MovieInvitation;
import com.finalandroidproject.quickmovie.Model.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
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

                ParseQuery<ParseObject> queryCinema = ParseQuery.getQuery("Cinemas");
                queryCinema.whereEqualTo("objectId", InvitationObject.getParseObject("Cinema").getObjectId());
                List<ParseObject> dataCiname = queryCinema.find();
                if(dataCiname.size() == 1) {
                    SelectCinema = Cinema.createCinemaFromObject(dataCiname.get(0));
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
        queryInvitations.whereMatchesQuery("ReceiverFriend", queryuser);
        queryInvitations.include("Users");
        try {
            List<ParseObject> data = queryInvitations.find();
            List<MovieInvitation> arrInvitations = new ArrayList<MovieInvitation>();
            for (ParseObject InvitationObject : data) {
                Movie SelectMovie = null;
                Cinema SelectCinema = null;
                Friend SenderFriend = null;
                Friend ReceiverFriend = null;

                if(InvitationObject.getParseObject("SenderFriend").isDataAvailable()) {
                    SenderFriend = Friend.createFriendFromObject(InvitationObject.getParseObject("SenderFriend"));
                } else {
                    ParseQuery<ParseObject> queryMovie = ParseQuery.getQuery("Users");
                    ParseObject dataSenderFriend = queryMovie.get(InvitationObject.getParseObject("SenderFriend").getObjectId());
                    if(dataSenderFriend != null) {
                        SenderFriend = Friend.createFriendFromObject(dataSenderFriend);
                    }
                }

                if(InvitationObject.getParseObject("ReceiverFriend").isDataAvailable()) {
                    ReceiverFriend = Friend.createFriendFromObject(InvitationObject.getParseObject("ReceiverFriend"));
                } else {
                    ParseQuery<ParseObject> queryMovie = ParseQuery.getQuery("Users");
                    ParseObject dataReceiverFriend = queryMovie.get(InvitationObject.getParseObject("ReceiverFriend").getObjectId());
                    if(dataReceiverFriend != null) {
                        ReceiverFriend = Friend.createFriendFromObject(dataReceiverFriend);
                    }
                }

                if(InvitationObject.getParseObject("Movie").isDataAvailable()) {
                    SelectMovie = Movie.createMovieFromObject(InvitationObject.getParseObject("Movie"));
                } else {
                    ParseQuery<ParseObject> queryMovie = ParseQuery.getQuery("Movies");
                    queryMovie.whereEqualTo("objectId", InvitationObject.getParseObject("Movie").getObjectId());
                    List<ParseObject> dataMovie = queryMovie.find();
                    if(dataMovie.size() == 1) {
                        SelectMovie = Movie.createMovieFromObject(dataMovie.get(0));
                    }
                }

                if(InvitationObject.getParseObject("Cinema").isDataAvailable()) {
                    SelectCinema = Cinema.createCinemaFromObject(InvitationObject.getParseObject("Cinema"));
                } else {
                    ParseQuery<ParseObject> queryCinema = ParseQuery.getQuery("Cinemas");
                    queryCinema.whereEqualTo("objectId", InvitationObject.getParseObject("Cinema").getObjectId());
                    List<ParseObject> dataCiname = queryCinema.find();
                    if(dataCiname.size() == 1) {
                        SelectCinema = Cinema.createCinemaFromObject(dataCiname.get(0));
                    }
                }


                MovieInvitation Invitation = new MovieInvitation(InvitationObject.getInt("ID"),
                        SenderFriend,
                        ReceiverFriend,
                        SelectMovie,
                        SelectCinema,
                        InvitationObject.getDate("MovieTime"));

                Invitation.setIsAccepted(InvitationObject.getBoolean("IsAccepted"));
                Invitation.setObjetcID(InvitationObject.getObjectId());
                arrInvitations.add(Invitation);
            }

            return arrInvitations;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addNewInvitation(MovieInvitation invitation) {
        ParseObject newInvitationObject = new ParseObject("Invitations");
        newInvitationObject.put("SenderFriend",ParseObject.createWithoutData("Users", invitation.getFromFriend().getID()));
        newInvitationObject.put("ReceiverFriend",ParseObject.createWithoutData("Users", invitation.getToFriend().getID()));
        newInvitationObject.put("Movie",ParseObject.createWithoutData("Movies", invitation.getMovie().getObjectID()));
        newInvitationObject.put("Cinema",ParseObject.createWithoutData("Cinemas", invitation.getCinema().getObjectID()));
        newInvitationObject.put("MovieTime",invitation.getInvitationDate());
        newInvitationObject.put("IsAccepted",invitation.isAccepted());
        newInvitationObject.put("ID",invitation.getId());

        newInvitationObject.saveEventually();

    }

    @Override
    public void updateInvitation(final MovieInvitation invitation) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Invitations");

        query.getInBackground(invitation.getObjetcID() ,new GetCallback<ParseObject>() {
            public void done(ParseObject newInvitationObject, ParseException e) {
                if (e == null) {
                    newInvitationObject.setObjectId(invitation.getObjetcID());
                    newInvitationObject.put("SenderFriend", ParseObject.createWithoutData("Users", invitation.getFromFriend().getID()));
                    newInvitationObject.put("ReceiverFriend", ParseObject.createWithoutData("Users", invitation.getToFriend().getID()));
                    newInvitationObject.put("Movie", ParseObject.createWithoutData("Movies", invitation.getMovie().getObjectID()));
                    newInvitationObject.put("Cinema", ParseObject.createWithoutData("Cinemas", invitation.getCinema().getObjectID()));
                    newInvitationObject.put("MovieTime", invitation.getInvitationDate());
                    newInvitationObject.put("IsAccepted", invitation.isAccepted());
                    newInvitationObject.put("ID", invitation.getId());

                    newInvitationObject.saveEventually();
                }
            }
        });
    }

    @Override
    public void removeInvitation(final MovieInvitation invitation) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Invitations");
        query.whereEqualTo("ID",invitation.getId());

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> invitationObject, ParseException e) {
                if (e == null) {
                    ParseObject newInvitationObject = new ParseObject("Invitations");
                    newInvitationObject.setObjectId(invitationObject.get(0).getObjectId());

                    newInvitationObject.deleteEventually();
                }
            }
        });
    }
}
