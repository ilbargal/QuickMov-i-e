package com.finalandroidproject.quickmovie.Model;

import android.location.Location;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

/**
 * Created by nivg1 on 16/01/2016.
 */
public class Cinema {
    private String objectID;
    private String Name;
    private Location location;

    public static Cinema createCinemaFromObject(ParseObject object) {
        Cinema newCinema = new Cinema(object.getString("Name"));
        ParseGeoPoint Geopoint = object.getParseGeoPoint("Location");
        Location loc = new Location("Parse");
        loc.setLatitude(Geopoint.getLatitude());
        loc.setLongitude(Geopoint.getLongitude());
        newCinema.setLocation(loc);
        newCinema.setObjectID(object.getObjectId());

        return newCinema;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public Cinema(String name) {
        Name = name;
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

    public Cinema() {}

    public Cinema(String objectID, String name, Location location) {
        this.objectID = objectID;
        Name = name;
        this.location = location;
    }
}
