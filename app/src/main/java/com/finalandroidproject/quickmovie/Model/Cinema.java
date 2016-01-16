package com.finalandroidproject.quickmovie.Model;

import android.location.Location;

/**
 * Created by nivg1 on 16/01/2016.
 */
public class Cinema {
    private String Name;
    private Location location;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
