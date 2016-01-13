package com.finalandroidproject.quickmovie.Interfaces;

import com.finalandroidproject.quickmovie.Model.User;

/**
 * Created by nivg1 on 13/01/2016.
 */
public interface iUserActions {
   User loginUser(String Phone,String Password);
   boolean reqisterUser(User newUser);
}
