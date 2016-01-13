package com.finalandroidproject.quickmovie.DAL;

import com.finalandroidproject.quickmovie.Interfaces.iUserActions;
import com.finalandroidproject.quickmovie.Model.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

        import java.util.List;

/**
 * Created by nivg1 on 13/01/2016.
 */
        public class UserDAL implements iUserActions {
            private final static UserDAL instance = new UserDAL();

            @Override
            public User loginUser(String Phone, String Password) throws ParseException {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
                query.whereEqualTo("PhoneNumID", Phone);
        query.whereEqualTo("Password", Password);
        List<ParseObject> data = query.find();

        // Check if return one line
        if(data.size() == 1) {
            for (ParseObject po : data) {
                String PhoneNumID = po.getString("PhoneNumID");
                String name = po.getString("Name");

                User uUser = new User();
                uUser.setPhone(PhoneNumID);
                uUser.setName(name);

                return uUser;
            }
        }

        return null;
    }

    @Override
    public boolean reqisterUser(User newUser) {
        return false;
    }
}
