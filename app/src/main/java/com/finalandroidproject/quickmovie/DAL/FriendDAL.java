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
        ParseQuery<ParseObject> queryuser = ParseQuery.getQuery("Users");
        queryuser.whereEqualTo("objectId", user.getID());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
         query.whereMatchesQuery("UserID", queryuser);

        query.include("Users");

        try {
            List<ParseObject> data = query.find();
            if(data.size() == 1) {
                List<Friend> Friends = getFriendsByRelation(data.get(0).getRelation("FriendsID").getQuery());
                user.setFriends(Friends);
                user.setFriendTableID(data.get(0).getObjectId());
                return Friends;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Friend> getFriendsByRelation(ParseQuery query) throws ParseException {
        List<ParseObject> data = query.find();

        List<Friend> arrFriends = new ArrayList<Friend>();
        for (ParseObject FriendObject : data) {
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
    public boolean addFriendstoUser(User user, List<Friend> newFriends) {
        String ObjectID = "";

        ParseQuery<ParseObject> queryObjectID = ParseQuery.getQuery("Friends");
        queryObjectID.whereEqualTo("UserID",ParseObject.createWithoutData("Users", user.getID()));

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
                            // Now let's update it with some new data. In this case, only cheatMode and score
                            // will get sent to the Parse Cloud. playerName hasn't changed.
                            FriendObject.getRelation("FriendsID").add(ParseObject.createWithoutData("Users", newFriend.getID()));
                            FriendObject.saveEventually();
                        }
                    }
                });

                user.getFriends().add(newFriend);
            }

            return true;
        }
        else
        {
            ParseObject Friend = new ParseObject("Friends");
            Friend.put("UserID", ParseObject.createWithoutData("Users", user.getID()));
            Friend.getRelation("FriendsID").add(ParseObject.createWithoutData("Users", newFriends.get(0).getID()));
            Friend.saveInBackground();
            user.getFriends().add(newFriends.get(0));
            newFriends.remove(0);

            return addFriendstoUser(user, newFriends);
        }
    }
}
