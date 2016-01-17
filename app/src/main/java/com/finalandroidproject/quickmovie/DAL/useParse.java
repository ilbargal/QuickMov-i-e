package com.finalandroidproject.quickmovie.DAL;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by nivg1 on 17/01/2016.
 */
public class useParse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
    }
}
