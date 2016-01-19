package com.finalandroidproject.quickmovie.Model;

import android.location.Location;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by nivg1 on 16/01/2016.
 */
public class Cinema {
    private String Name;
    private Location location;

    public static Cinema createCinemaFromObject(ParseObject object) {
        Cinema newCinema = new Cinema();
        newCinema.setName(object.getString("Name"));
        ParseGeoPoint Geopoint = object.getParseGeoPoint("Location");
        Location loc = new Location("Parse");
        loc.setLatitude(Geopoint.getLatitude());
        loc.setLongitude(Geopoint.getLongitude());
        newCinema.setLocation(loc);

        return newCinema;
    }

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
