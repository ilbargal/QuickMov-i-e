package com.finalandroidproject.quickmovie.DAL;

import com.finalandroidproject.quickmovie.Interfaces.IFriendActions;
import com.finalandroidproject.quickmovie.Model.Friend;
import com.finalandroidproject.quickmovie.Model.User;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

            List<Friend> arrFriends = new ArrayList<Friend>();
            for (ParseObject FriendObject : data) {
                if (true) {
                    FriendObject.pinInBackground("Friends");
                }

                ParseFile pic = FriendObject.getParseFile("FriendPic");
                String picURL = "";
                if(pic != null) {
                    picURL = FriendObject.getParseFile("FriendPic").getUrl();
                }
                Friend fFriend = new Friend(FriendObject.getString("FriendName"),picURL);
                fFriend.setPhone(FriendObject.getString("FriendPhone"));

                arrFriends.add(fFriend);
            }

            return arrFriends;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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
        for (Friend newFriend : newFriends) {
            ParseObject Friend = new ParseObject("Friends");
            Friend.put("UserID",ParseObject.createWithoutData("Users", user.getID()));
            Friend.put("FriendName",newFriend.getName());

            Friend.saveInBackground();
        }

        return false;
    }
}
