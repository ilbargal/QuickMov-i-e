package com.finalandroidproject.quickmovie.DAL;

import com.finalandroidproject.quickmovie.Interfaces.IFriendActions;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivg1 on 13/01/2016.
 */
public class FriendDAL implements IFriendActions {
    public final static FriendDAL instance = new FriendDAL();

    @Override
    public List<Friend> getFriendsByUser(User user) {
        try {

            // Sub Query from users
            ParseQuery<ParseObject> queryuser = ParseQuery.getQuery("Users");
            queryuser.whereEqualTo("objectId", user.getID());

            // query from friend
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
             query.whereMatchesQuery("UserID", queryuser);

            query.include("Users");

            List<ParseObject> data = query.find();
            if(data.size() == 1) {
                List<Friend> Friends = getFriendsByRelation(data.get(0).getRelation("FriendsID").getQuery());
                if(Friends.size() > 0) {
                    user.setFriends(Friends);
                    user.setFriendTableID(data.get(0).getObjectId());
                    return Friends;
                } else {
                    return new ArrayList<Friend>() ;
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new ArrayList<Friend>();
    }

    private List<Friend> getFriendsByRelation(ParseQuery query) throws ParseException {
        List<ParseObject> data = query.find();

        List<Friend> arrFriends = new ArrayList<Friend>();
        for (ParseObject FriendObject : data) {
            // Save for localDB
            if (true) {
                FriendObject.pinInBackground("Users");
            }

            arrFriends.add(Friend.createFriendFromObject(FriendObject));
        }

        return arrFriends;
    }

    @Override
    public boolean addFriendtoUser(User user, Friend newFriend) {
        List<Friend> Friends = new ArrayList<>();
        Friends.add(newFriend);
        addFriendstoUser(user, Friends);
        return true;
    }

    @Override
    public boolean addFriendstoUser(final User user, List<Friend> newFriends) {
        String ObjectID = "";

        ParseQuery<ParseObject> queryObjectID = ParseQuery.getQuery("Friends");
        queryObjectID.whereEqualTo("UserID", ParseObject.createWithoutData("Users", user.getID()));

        try {
            List<ParseObject> UserFriends = queryObjectID.find();
            if(UserFriends.size() == 1) {
                ObjectID = UserFriends.get(0).getObjectId();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(ObjectID != "") {
            for (final Friend newFriend : newFriends) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");

                query.getInBackground(ObjectID,new GetCallback<ParseObject>() {
                    public void done(ParseObject FriendObject, ParseException e) {
                        if (e == null) {
                            FriendObject.getRelation("FriendsID").add(ParseObject.createWithoutData("Users", newFriend.getID()));
                            FriendObject.saveEventually();

                            addUserToFriend(newFriend, user);
                        }
                    }
                });

                user.getFriends().add(newFriend);
            }

            return true;
        }
        else
        {
            ParseObject Friendobject = new ParseObject("Friends");
            Friendobject.put("UserID", ParseObject.createWithoutData("Users", user.getID()));
            Friendobject.getRelation("FriendsID").add(ParseObject.createWithoutData("Users", newFriends.get(0).getID()));
            Friendobject.saveEventually();

            addUserToFriend(newFriends.get(0),user);

            user.getFriends().add(newFriends.get(0));
            newFriends.remove(0);

            return addFriendstoUser(user, newFriends);
        }
    }

    private boolean addUserToFriend(Friend newFriend,final User user) {
        String ObjectID = "";
        ParseQuery<ParseObject> queryObjectID = ParseQuery.getQuery("Friends");
        queryObjectID.whereEqualTo("UserID", ParseObject.createWithoutData("Users", newFriend.getID()));

        try {
            List<ParseObject> UserFriends = queryObjectID.find();
            if(UserFriends.size() == 1) {
                ObjectID = UserFriends.get(0).getObjectId();
            } else {
                ObjectID = "";
            }

                if (ObjectID != "") {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");

                    query.getInBackground(ObjectID, new GetCallback<ParseObject>() {
                        public void done(ParseObject FriendObject, ParseException e) {
                            if (e == null) {
                                FriendObject.getRelation("FriendsID").add(ParseObject.createWithoutData("Users", user.getID()));
                                FriendObject.saveEventually();
                            }
                        }
                    });

                    return true;
                } else {
                    ParseObject Friend = new ParseObject("Friends");
                    Friend.put("UserID", ParseObject.createWithoutData("Users", newFriend.getID()));
                    Friend.getRelation("FriendsID").add(ParseObject.createWithoutData("Users", user.getID()));
                    Friend.saveEventually();

                    return true;
                }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
