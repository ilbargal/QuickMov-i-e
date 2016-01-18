package com.finalandroidproject.quickmovie.DAL;

import com.finalandroidproject.quickmovie.Interfaces.iUserActions;
import com.finalandroidproject.quickmovie.Model.User;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

        import java.util.List;

/**
 * Created by nivg1 on 13/01/2016.
 */
public class UserDAL implements iUserActions {
    public final static UserDAL instance = new UserDAL();

    @Override
    public User loginUser(String Phone, String Password) throws ParseException {
        // Parse get query for table Users
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        // Where PhoneNumID && Password
        query.whereEqualTo("PhoneNumID", Phone);
        query.whereEqualTo("Password", Password);

        // Execute query (select)
        List<ParseObject> data = query.find();

        User uUser = null;

        // Check if return one line
        if(data.size() == 1) {
            ParseObject poDBUser = data.get(0);

            String PhoneNumID = poDBUser.getString("PhoneNumID");
            String UserID = poDBUser.getObjectId();
            String name = poDBUser.getString("Name");
            ParseFile ProfilePic = poDBUser.getParseFile("ProfilePic");

            uUser = new User();
            uUser.setPhone(PhoneNumID);
            uUser.setID(UserID);
            uUser.setName(name);

            if(ProfilePic != null){
                uUser.setProfilePic(ProfilePic.getUrl());
            }

            FriendDAL.instance.getFriendsByUser(uUser);
        }

        return uUser;
    }

    @Override
    public boolean registerUser(User newUser) {
        ParseObject newUserObject = new ParseObject("Users");
        newUserObject.put("PhoneNumID",newUser.getPhone());
        newUserObject.put("Name",newUser.getName());
        newUserObject.put("ProfilePic",newUser.getProfilePic());
        newUserObject.put("Password",newUser.getPassword());

        newUserObject.saveInBackground();
        return false;
    }
}
