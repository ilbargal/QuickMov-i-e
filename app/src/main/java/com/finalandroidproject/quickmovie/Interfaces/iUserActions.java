package com.finalandroidproject.quickmovie.Interfaces;

import com.finalandroidproject.quickmovie.Model.User;
import com.parse.ParseException;

/**
 * Created by nivg1 on 13/01/2016.
 */
public interface iUserActions {
   User loginUser(String Phone, String Password) throws ParseException;
   boolean reqisterUser(User newUser);
}
