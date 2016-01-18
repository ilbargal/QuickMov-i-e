package com.finalandroidproject.quickmovie.Model;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Bar Gal on 30/12/2015.
 */
public class Friend extends User{
    public Friend(String ID, String phone, String name) {
        super(ID, phone, name, "", null);
    }

    public static Friend createFriendFromObject(ParseObject object) {
        String PhoneNumID = object.getString("PhoneNumID");
        String UserID = object.getObjectId();
        String name = object.getString("Name");
        ParseFile ProfilePic = object.getParseFile("ProfilePic");

        Friend fFriend = new Friend(UserID,PhoneNumID,name);
        if(ProfilePic != null) {
            fFriend.setProfilePic(ProfilePic.getUrl());
        }

        return fFriend;
    }
}
